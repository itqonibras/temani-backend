# Deploy TemanMu Backend ke Render

## Prerequisites
- Akun Render (https://render.com)
- GitHub repository dengan backend code
- Supabase database sudah setup

## Environment Variables yang Dibutuhkan di Render

Saat deploy di Render, tambahkan environment variables berikut:

### Database Configuration
```
DATABASE_URL=jdbc:postgresql://[HOST]:[PORT]/[DATABASE]?user=[USER]&password=[PASSWORD]
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=[your-password]
```

**Catatan:** Ambil connection string dari Supabase Dashboard → Settings → Database → Connection String (URI mode)

### JWT Configuration
```
JWT_SECRET_KEY=[your-secret-key]
```
Generate random string 64 karakter untuk production

### Midtrans Configuration (Optional, untuk payment)
```
MIDTRANS_SERVER_KEY=[your-server-key]
MIDTRANS_CLIENT_KEY=[your-client-key]
MIDTRANS_MERCHANT_ID=[your-merchant-id]
```

### Supabase Configuration (untuk file storage)
```
SUPABASE_URL=https://[your-project].supabase.co
SUPABASE_ANON_KEY=[your-anon-key]
SUPABASE_SERVICE_ROLE_KEY=[your-service-role-key]
SUPABASE_STORAGE_BUCKET=profile-pictures
```

### Spring Profile
```
SPRING_PROFILES_ACTIVE=prod
```

## Langkah Deploy di Render

1. **Login ke Render Dashboard**
   - Buka https://dashboard.render.com

2. **Buat Web Service Baru**
   - Klik "New +" → "Web Service"
   - Connect ke GitHub repository Anda
   - Pilih repository `temanmu-backend`

3. **Konfigurasi Service**
   - **Name**: `temanmu-backend` (atau nama pilihan Anda)
   - **Region**: Singapore (terdekat dengan Indonesia)
   - **Branch**: `dev` atau `main`
   - **Root Directory**: Leave empty (Dockerfile ada di root)
   - **Runtime**: Docker
   - **Instance Type**: Free (untuk testing) atau Starter ($7/month untuk production)

4. **Tambahkan Environment Variables**
   - Scroll ke bagian "Environment Variables"
   - Klik "Add Environment Variable"
   - Tambahkan semua variables di atas satu per satu

5. **Advanced Settings (Optional)**
   - **Health Check Path**: `/` (atau buat endpoint khusus `/health`)
   - **Auto-Deploy**: Yes (deploy otomatis saat push ke GitHub)

6. **Deploy**
   - Klik "Create Web Service"
   - Tunggu proses build (~5-10 menit untuk pertama kali)
   - Status akan berubah menjadi "Live" jika berhasil

## URL Backend
Setelah deploy berhasil, Anda akan mendapat URL seperti:
```
https://temanmu-backend.onrender.com
```

## Update Flutter Frontend
Setelah backend live, update URL di Flutter:

```dart
// lib/core/environments/endpoints.dart
static const String baseUrl = 'https://temanmu-backend.onrender.com';
```

## Testing Backend
Test dengan curl:
```bash
curl https://temanmu-backend.onrender.com/
```

## Troubleshooting

### Build Failed
- Cek logs di Render dashboard
- Pastikan Dockerfile ada di root folder `temanmu-backend`
- Pastikan path di Dockerfile benar (`temanmu/pom.xml` dan `temanmu/src`)

### Application Crashed
- Cek environment variables sudah lengkap
- Pastikan DATABASE_URL format benar (JDBC format)
- Cek Render logs untuk error message

### Database Connection Failed
- Verifikasi connection string dari Supabase
- Pastikan Supabase database sudah running
- Cek whitelist IP di Supabase (Render IPs harus diizinkan)

### CORS Issues
- Pastikan WebConfig sudah ditambahkan
- Update `allowedOrigins` dari `"*"` ke URL Flutter Firebase Hosting Anda

## Notes
- Render Free tier akan sleep setelah 15 menit tidak ada request
- First request setelah sleep butuh ~30 detik untuk wake up
- Untuk production, gunakan Starter plan ($7/month) agar always active
