package com.temani.temani.features.profile.infrastructure.storage;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SupabaseStorageService {

	private final WebClient webClient;
	private final String supabaseUrl;
	private final String bucketName;

	public SupabaseStorageService(@Value("${temani.supabase.url}") String supabaseUrl,
			@Value("${temani.supabase.serviceRoleKey}") String serviceRoleKey,
			@Value("${temani.supabase.storage.bucket}") String bucketName) {
		this.supabaseUrl = supabaseUrl.trim();
		this.bucketName = bucketName.trim();
		this.webClient = WebClient.builder()
				.baseUrl(supabaseUrl.trim() + "/storage/v1")
				.defaultHeader("apikey", serviceRoleKey)
				.defaultHeader("Authorization", "Bearer " + serviceRoleKey)
				.build();
	}

	private String sanitizeFileName(String fileName) {
		if (fileName == null || fileName.isEmpty()) {
			return "profile.jpg";
		}
		// Remove or replace invalid characters, keep only alphanumeric, dots, hyphens, underscores
		String sanitized = fileName.replaceAll("[^a-zA-Z0-9._-]", "_");
		// Ensure it's not empty after sanitization
		if (sanitized.isEmpty()) {
			return "profile.jpg";
		}
		return sanitized;
	}

	public String uploadFile(byte[] fileBytes, String fileName, String contentType) {
		try {
			// Sanitize file name to remove special characters that might cause issues
			String safeFileName = sanitizeFileName(fileName);
			String filePath = "profiles/" + UUID.randomUUID() + "_" + safeFileName;

			log.info("Uploading file to Supabase: bucket='{}', path='{}'", bucketName, filePath);
			log.debug("Bucket name length: {}, Bucket name bytes: {}", bucketName.length(), bucketName.getBytes(StandardCharsets.UTF_8).length);

			// Supabase Storage API: POST /storage/v1/object/{bucket}/{path}
			// The {path} parameter can contain slashes (e.g., "profiles/file.jpg")
			// We need to pass the path as-is and let WebClient handle encoding
			// Build the URI manually to have full control
			String uploadUri = "/object/" + bucketName + "/" + filePath;
			
			log.debug("Upload URI (before encoding): {}", uploadUri);

			String response = webClient.post()
					.uri(uploadUri) // WebClient will encode this properly
					.header(HttpHeaders.CONTENT_TYPE, contentType)
					.bodyValue(fileBytes)
					.retrieve()
					.bodyToMono(String.class)
					.block();

			log.info("File uploaded successfully: {}", filePath);
			if (response != null && !response.isEmpty()) {
				log.debug("Supabase response: {}", response);
			}

			// Build the public URL - encode path segments for the public URL
			String[] pathSegments = filePath.split("/");
			StringBuilder publicPath = new StringBuilder();
			for (int i = 0; i < pathSegments.length; i++) {
				if (i > 0) {
					publicPath.append("/");
				}
				publicPath.append(UriUtils.encodePathSegment(pathSegments[i], StandardCharsets.UTF_8));
			}

			String publicUrl = supabaseUrl + "/storage/v1/object/public/" + bucketName + "/" + publicPath.toString();
			log.debug("Public URL: {}", publicUrl);

			return publicUrl;
		}
		catch (org.springframework.web.reactive.function.client.WebClientResponseException e) {
			String errorBody = e.getResponseBodyAsString();
			log.error("HTTP error uploading file to Supabase: Status={}, Response={}", e.getStatusCode(), errorBody);
			log.error("Request details: bucket={}, filePath would be: profiles/...", bucketName);
			throw new RuntimeException("Failed to upload file to Supabase: HTTP " + e.getStatusCode() + " - " + errorBody);
		}
		catch (Exception e) {
			log.error("Error uploading file to Supabase: {}", e.getMessage(), e);
			if (e.getCause() != null) {
				log.error("Root cause: {}", e.getCause().getMessage());
			}
			throw new RuntimeException("Failed to upload file to Supabase: " + e.getMessage());
		}
	}

	public String uploadBase64Image(String base64Image, String fileName) {
		try {
			// Remove data URL prefix if present
			String base64Data = base64Image;
			if (base64Image.contains(",")) {
				base64Data = base64Image.split(",")[1];
			}

			byte[] imageBytes = Base64.getDecoder().decode(base64Data);
			String contentType = "image/jpeg"; // Default, can be enhanced to detect from base64 prefix

			// Detect content type from base64 prefix
			if (base64Image.startsWith("data:image/png")) {
				contentType = "image/png";
				if (!fileName.endsWith(".png")) {
					fileName = fileName.replaceAll("\\.[^.]+$", ".png");
				}
			}
			else if (base64Image.startsWith("data:image/jpeg") || base64Image.startsWith("data:image/jpg")) {
				contentType = "image/jpeg";
				if (!fileName.endsWith(".jpg") && !fileName.endsWith(".jpeg")) {
					fileName = fileName.replaceAll("\\.[^.]+$", ".jpg");
				}
			}
			else if (base64Image.startsWith("data:image/webp")) {
				contentType = "image/webp";
				if (!fileName.endsWith(".webp")) {
					fileName = fileName.replaceAll("\\.[^.]+$", ".webp");
				}
			}

			return uploadFile(imageBytes, fileName, contentType);
		}
		catch (Exception e) {
			log.error("Error uploading base64 image: {}", e.getMessage(), e);
			throw new RuntimeException("Failed to upload image: " + e.getMessage());
		}
	}

	public void deleteFile(String fileUrl) {
		try {
			// Extract path from URL
			// URL format: https://xxx.supabase.co/storage/v1/object/public/bucket-name/path/to/file
			String bucketPrefix = "/" + bucketName + "/";
			int bucketIndex = fileUrl.indexOf(bucketPrefix);
			if (bucketIndex == -1) {
				log.warn("Cannot extract path from URL: {}", fileUrl);
				return;
			}

			String path = fileUrl.substring(bucketIndex + bucketPrefix.length());
			// Decode the path if it's URL encoded
			String decodedPath = UriUtils.decode(path, StandardCharsets.UTF_8);
			String encodedPath = UriUtils.encodePath(decodedPath, StandardCharsets.UTF_8);
			String deleteUri = "/object/" + bucketName + "/" + encodedPath;

			log.info("Deleting file: {}", deleteUri);

			webClient.delete()
					.uri(deleteUri)
					.retrieve()
					.bodyToMono(Void.class)
					.block();

			log.info("File deleted successfully: {}", decodedPath);
		}
		catch (Exception e) {
			log.error("Error deleting file from Supabase: {}", e.getMessage(), e);
			// Don't throw exception, just log - file might not exist
		}
	}

}

