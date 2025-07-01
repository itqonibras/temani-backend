package com.temani.temani.features.counseling.presentation;

import com.temani.temani.common.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class JwtChannelInterceptor implements ChannelInterceptor {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor != null && accessor.getUser() == null) {
            Object authAttr = accessor.getSessionAttributes() != null
                    ? accessor.getSessionAttributes().get("Authorization")
                    : null;
            System.out.println("Auth attribute from session: " + authAttr);
            String token = null;
            if (authAttr instanceof String) {
                token = ((String) authAttr).replace("Bearer ", "");
            } else {
                // Fallback: Try to get token from STOMP headers
                String authHeader = accessor.getFirstNativeHeader("Authorization");
                System.out.println("Auth header from STOMP: " + authHeader);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    token = authHeader.replace("Bearer ", "");
                }
            }
            if (token != null) {
                System.out.println("Extracted token: " + token);
                if (jwtUtils.validateToken(token)) {
                    String username = jwtUtils.getUsername(token);
                    System.out.println("Authenticated WebSocket user: " + username);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    accessor.setUser(authentication);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    System.out.println("Invalid JWT token for WebSocket connection.");
                }
            } else {
                System.out.println("No Authorization attribute found in session or STOMP headers.");
            }
        }
        return message;
    }
}