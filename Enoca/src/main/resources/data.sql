-- Enoca Course Management System - Sample Data
-- 5 Teachers, 5 Students, 5 Courses with sample enrollments

-- ==============================
-- TEACHERS (5 Öğretmen)
-- ==============================
INSERT INTO teachers (id, first_name, last_name, email, phone, specialization, is_active, created_at, updated_at)
VALUES
(1, 'Ahmet', 'Yılmaz', 'ahmet.yilmaz@enoca.com', '555-0101', 'Java Backend Development', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Elif', 'Kaya', 'elif.kaya@enoca.com', '555-0102', 'Frontend Development', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Mehmet', 'Özkan', 'mehmet.ozkan@enoca.com', '555-0103', 'Mobile Development', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'Ayşe', 'Demir', 'ayse.demir@enoca.com', '555-0104', 'DevOps & Cloud', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'Mustafa', 'Şahin', 'mustafa.sahin@enoca.com', '555-0105', 'Data Science & AI', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ==============================
-- STUDENTS (5 Öğrenci)
-- ==============================
INSERT INTO students (id, first_name, last_name, email, phone, is_active, created_at, updated_at)
VALUES
(1, 'Fatma', 'Arslan', 'fatma.arslan@student.com', '555-1001', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Ali', 'Veli', 'ali.veli@student.com', '555-1002', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Zeynep', 'Çelik', 'zeynep.celik@student.com', '555-1003', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'Emre', 'Polat', 'emre.polat@student.com', '555-1004', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'Seda', 'Yıldız', 'seda.yildiz@student.com', '555-1005', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ==============================
-- CARTS (Her öğrenci için otomatik sepet)
-- ==============================
INSERT INTO carts (id, student_id, total_price, is_active, created_at, updated_at)
VALUES
(1, 1, 0.00, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 0.00, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 3, 0.00, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 4, 0.00, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 5, 0.00, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ==============================
-- COURSES (5 Kurs)
-- ==============================
INSERT INTO courses (id, title, description, price, max_students, current_students, is_available, teacher_id, is_active, created_at, updated_at)
VALUES
(1, 'Spring Boot Masterclass', 'Spring Boot ile microservice mimarisi geliştirme. REST API, Security, Database entegrasyonu', 449.99, 30, 2, true, 1, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'React.js & Redux Advanced', 'Modern React.js uygulamaları geliştirme. Hooks, Context API, Redux Toolkit', 389.99, 25, 1, true, 2, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Flutter Mobile Development', 'Cross-platform mobile uygulama geliştirme. Dart dili, UI/UX tasarım', 529.99, 20, 1, true, 3, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'AWS Cloud Architecture', 'Amazon Web Services ile cloud mimarisi. EC2, RDS, Lambda, API Gateway', 699.99, 15, 1, true, 4, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'Python Machine Learning', 'Python ile makine öğrenmesi algoritmaları. Pandas, Scikit-learn, TensorFlow', 599.99, 18, 0, true, 5, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ==============================
-- SAMPLE CART ITEMS (Bazı öğrencilerin sepetinde kurslar)
-- ==============================
-- Fatma'nın sepetinde 2 kurs
INSERT INTO cart_items (id, cart_id, course_id, price, is_active, created_at, updated_at)
VALUES
(1, 1, 1, 449.99, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 5, 599.99, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Ali'nin sepetinde 1 kurs
INSERT INTO cart_items (id, cart_id, course_id, price, is_active, created_at, updated_at)
VALUES
(3, 2, 3, 529.99, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Zeynep'in sepetinde 1 kurs
INSERT INTO cart_items (id, cart_id, course_id, price, is_active, created_at, updated_at)
VALUES
(4, 3, 2, 389.99, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Sepet toplam fiyatlarını güncelle
UPDATE carts SET total_price = 1049.98 WHERE id = 1; -- Fatma: 449.99 + 599.99
UPDATE carts SET total_price = 529.99 WHERE id = 2;  -- Ali: 529.99
UPDATE carts SET total_price = 389.99 WHERE id = 3;  -- Zeynep: 389.99

-- ==============================
-- SAMPLE ORDERS (Tamamlanmış siparişler) - DÜZELTME: "'orders'" kullanıyoruz
-- ==============================
INSERT INTO "'orders'" (id, order_code, student_id, total_price, status, is_active, created_at, updated_at)
VALUES
(1, 'ENOCA-2024-001', 4, 449.99, 'CONFIRMED', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'ENOCA-2024-002', 5, 918.98, 'CONFIRMED', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ==============================
-- ORDER ITEMS (Sipariş detayları)
-- ==============================
-- Emre'nin siparişi (Spring Boot)
INSERT INTO order_items (id, order_id, course_id, price, is_active, created_at, updated_at)
VALUES
(1, 1, 1, 449.99, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Seda'nın siparişi (React + Flutter)
INSERT INTO order_items (id, order_id, course_id, price, is_active, created_at, updated_at)
VALUES
(2, 2, 2, 389.99, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 2, 3, 529.99, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ==============================
-- COURSE ENROLLMENTS (Kurs kayıtları)
-- ==============================
-- Öğrencilerin satın aldıkları kurslara kayıt
INSERT INTO course_students (course_id, student_id) VALUES
(1, 4), -- Emre -> Spring Boot
(1, 1), -- Fatma -> Spring Boot (sepetten alacak)
(2, 5), -- Seda -> React
(3, 5), -- Seda -> Flutter
(4, 2); -- Ali -> AWS (sepetten alacak)

-- Kurs current_students sayılarını güncelle
UPDATE courses SET current_students = 2 WHERE id = 1; -- Spring Boot: Emre + Fatma
UPDATE courses SET current_students = 1 WHERE id = 2; -- React: Seda
UPDATE courses SET current_students = 1 WHERE id = 3; -- Flutter: Seda
UPDATE courses SET current_students = 1 WHERE id = 4; -- AWS: Ali