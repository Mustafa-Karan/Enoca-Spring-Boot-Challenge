# Enoca Course Management System

Spring Boot tabanlı kurs yönetim sistemi. Öğretmenler kurs açabilir, öğrenciler kurslara kaydolabilir ve sipariş verebilir.

## Teknolojiler

- **Java 21**
- **Spring Boot 3.5.5**
- **Spring Data JPA**
- **H2 Database** (In-Memory)
- **Maven** (Dependency Management)
- **Lombok** (Boilerplate Code Reduction)

## H2 Database Neden Kullanıldı

H2 Database hafif, hızlı ve kurulum gerektirmeyen bir in-memory veritabanıdır. Bu proje için tercih edilme nedenleri:

- Kurulum gerektirmez, uygulamayla birlikte çalışır
- Test ve development ortamları için idealdir
- SQL standartlarını destekler
- Web tabanlı admin paneli vardır
- Hızlı başlatma ve test döngüleri sağlar

## Kurulum ve Çalıştırma

### Gereksinimler
- Java 21 veya üstü
- Maven 3.6+ (opsiyonel, wrapper kullanılabilir)

### Çalıştırma Adımları

```bash
# Projeyi klonla
git clone <repository-url>
cd Enoca

# Maven wrapper ile çalıştır (önerilen)
./mvnw spring-boot:run

# Veya Maven yüklüyse
mvn spring-boot:run
```

Uygulama varsayılan olarak `http://localhost:8080` adresinde çalışır.

### H2 Database Console

H2 veritabanı admin paneline erişim:
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (boş)

## Alternatif: PostgreSQL Kullanımı

Eğer PostgreSQL kullanmak istiyorsanız:

### PostgreSQL Docker ile Çalıştırma

```bash
# PostgreSQL container başlat
docker run --name enoca-postgres -e POSTGRES_DB=enoca -e POSTGRES_USER=enoca -e POSTGRES_PASSWORD=123456 -p 5432:5432 -d postgres:15

# Container durumunu kontrol et
docker ps
```

### application.properties Değişiklikleri

H2 yerine PostgreSQL kullanmak için `src/main/resources/application.properties` dosyasını değiştirin:

```properties
# PostgreSQL Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/enoca
spring.datasource.username=enoca
spring.datasource.password=123456
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA Configuration
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# Remove H2 console
spring.h2.console.enabled=false
```

## API Endpoints

### Öğretmen (Teacher) İşlemleri

```http
GET    /api/teachers                    # Tüm aktif öğretmenleri listele
GET    /api/teachers/{id}               # Tekil öğretmen getir
POST   /api/teachers                    # Yeni öğretmen oluştur
PUT    /api/teachers/{id}               # Öğretmen bilgilerini güncelle
DELETE /api/teachers/{id}               # Öğretmeni sil (soft delete)
```

### Öğrenci (Student/Customer) İşlemleri

```http
GET    /api/customers                   # Tüm öğrencileri listele
GET    /api/customers/{id}              # Tekil öğrenci getir
POST   /api/customers                   # Yeni öğrenci oluştur
```

### Kurs (Course) İşlemleri

```http
GET    /api/courses                               # Tüm mevcut kursları listele
GET    /api/courses/{id}                          # Tekil kurs getir
GET    /api/teachers/{teacherId}/courses          # Öğretmenin kurslarını listele
GET    /api/students/{studentId}/courses          # Öğrencinin kayıtlı olduğu kurslar
POST   /api/teachers/{teacherId}/courses          # Öğretmen için yeni kurs oluştur
PUT    /api/teachers/{teacherId}/courses/{courseId} # Kurs bilgilerini güncelle
DELETE /api/teachers/{teacherId}/courses/{courseId} # Kursu sil
```

### Sepet (Cart) İşlemleri

```http
GET    /api/students/{studentId}/cart                    # Sepeti getir
POST   /api/students/{studentId}/cart/courses/{courseId} # Sepete kurs ekle
DELETE /api/students/{studentId}/cart/courses/{courseId} # Sepetten kurs çıkar
PUT    /api/students/{studentId}/cart                    # Sepeti güncelle
DELETE /api/students/{studentId}/cart                    # Sepeti boşalt
```

### Sipariş (Order) İşlemleri

```http
POST   /api/students/{studentId}/orders    # Sepetteki kurslar için sipariş ver
GET    /api/orders/{orderCode}             # Sipariş koduna göre sipariş getir
GET    /api/students/{studentId}/orders    # Öğrencinin tüm siparişleri
```

## Örnek İstekler

### Yeni Öğretmen Oluşturma

```json
POST /api/teachers
{
    "firstName": "Ahmet",
    "lastName": "Yılmaz",
    "email": "ahmet@example.com",
    "phone": "555-1234",
    "specialization": "Java Development"
}
```

### Yeni Öğrenci Oluşturma

```json
POST /api/customers
{
    "firstName": "Fatma",
    "lastName": "Arslan",
    "email": "fatma@student.com",
    "phone": "555-5678"
}
```

### Yeni Kurs Oluşturma

```json
POST /api/teachers/1/courses
{
    "title": "Spring Boot Masterclass",
    "description": "Comprehensive Spring Boot course",
    "price": 449.99,
    "maxStudents": 30,
    "isAvailable": true
}
```

## Örnek Veriler

Uygulama başladığında otomatik olarak örnek veriler yüklenir:
- 5 öğretmen
- 5 öğrenci (her birinin sepeti otomatik oluşturulur)
- 5 kurs
- Bazı sepetlerde örnek kurslar
- Tamamlanmış örnek siparişler

## Proje Yapısı

```
src/
├── main/
│   ├── java/com/Enoca_Challenge/Enoca/
│   │   ├── controller/     # REST Controllers
│   │   ├── entity/         # JPA Entities
│   │   ├── repository/     # Data Access Layer
│   │   ├── service/        # Business Logic Layer
│   │   └── EnocaApplication.java
│   └── resources/
│       ├── application.properties
│       └── data.sql        # Örnek veriler
└── test/
    └── java/               # Test sınıfları
```

## İş Mantığı

1. **Öğretmenler** kurs oluşturabilir, güncelleyebilir ve silebilir
2. **Öğrenciler** mevcut kursları görüntüleyebilir ve sepete ekleyebilir
3. **Sepet** sistemi ile öğrenciler birden fazla kursu toplayabilir
4. **Sipariş** verme ile sepetteki kurslar satın alınır ve öğrenci kurslara kaydolur
5. **Soft Delete** ile öğretmenler silindiğinde kursları da erişilemez hale gelir

## Önemli Notlar

- Öğretmen pasif duruma geçirildiğinde kursları da otomatik olarak erişilemez olur
- Her öğrenci kaydolduğunda otomatik olarak boş bir sepet oluşturulur
- Siparişler benzersiz kod ile takip edilebilir
- Kursların maksimum öğrenci kapasitesi vardır
- Tüm işlemler transaction güvenliği ile yapılır

## Test Etme

Postman, curl veya benzeri araçlarla API endpoints'lerini test edebilirsiniz. H2 console üzerinden veritabanı durumunu görüntüleyebilirsiniz.
