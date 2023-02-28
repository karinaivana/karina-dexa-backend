# Take Home Project (Backend)
## Aplikasi Absensi WFH

Pada aplikasi, terdapat 2 user yaitu employee (karyawan) dan admin. 
Aplikasi yang dikembangkan akan dapat dibuka melalui browser (laptop atau handphone)

Hal-hal yang dapat dilakukan oleh karyawan:
* Melakukan login ke dalam aplikasi
* Melihat profil pribadi
* Melakukan absensi. Absensi dibagi menjadi 2 kondisi yaitu, `masuk` dan `pulang`
* Melihat riwayat absensi yang pernah user lakukan. User dapat me-filter hasil riwayat yang ingin dilihat.
* Mengubah data pribadi (nomor handphone, password, foto)

Hal-hal yang dapat dilakukan oleh admin:
* Menambahkan dan mengubah data karyawan
* Melihat riwayat absensi dari keseluruhan karyawan

----
# Service
## Employee
* Digunakan untuk memproses business process yang berhubungan dengan karyawan, seperti:
  * Login karyawan
  * Create/Update/Read data karyawan
  * Read roles yang ada di perusahaan
* Terdapat 2 table yang digunakan, yaitu `employees` dan `roles`.
* Menggunakan port: 8084

## Attendance
* Digunakan untuk memproses business process yang berhubungan dengan riwayat absensi, seperti:
  * Melakukan absensi
  * Melihat riwayat absensi (seluruh atau sebagian)
* Terdapat 1 table yang digunakan, yaitu `attendance_lists`.
* Menggunakan port: 8081

## Admin
* Digunakan untuk memproses business process yang berhubungan dengan admin, seperti:
  * Login admin perusahaan
  * Create admin row (tidak ada tampilan UI)
* Terdapat 1 table yang digunakan, yaitu `admins`.
* Menggunakan port: 8082

# Notes
* Untuk membuat database, gunakan file intial_database.