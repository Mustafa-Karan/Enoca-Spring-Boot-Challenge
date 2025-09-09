package com.Enoca_Challenge.Enoca.repository;

import com.Enoca_Challenge.Enoca.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    // DOĞRU KULLANIM: Bu query otomatik olarak sadece aktif öğretmenlerin kurslarını getirir
    // Eğer öğretmen inactive ise (isActive = false), bu query boş liste döner
    @Query("SELECT c FROM Course c WHERE c.teacher.id = :teacherId AND c.teacher.isActive = true")
    List<Course> findByTeacherIdAndTeacherActive(Long teacherId);

    // Genel olarak mevcut kurslar (aktif öğretmen + aktif kurs + müsait kurs)
    @Query("SELECT c FROM Course c WHERE c.isAvailable = true AND c.isActive = true AND c.teacher.isActive = true")
    List<Course> findAvailableCourses();

    // Öğrencinin kayıtlı olduğu kurslar
    @Query("SELECT c FROM Course c JOIN c.enrolledStudents s WHERE s.id = :studentId")
    List<Course> findByStudentId(Long studentId);
}

/*
AÇIKLAMA:

1. findByTeacherIdAndTeacherActive(teacherId) metodu:
   - Sadece belirtilen teacherId'ye ait kursları getirir
   - VE aynı zamanda o öğretmen aktif olmalıdır (teacher.isActive = true)
   - Eğer öğretmen pasif ise, bu query otomatik olarak boş liste döndürür

2. Senaryonuz:
   - Öğretmen kursu açtı
   - Öğretmen kaza geçirdi, status'ü false yapıldı
   - getAllCoursesForTeacher() çağrıldığında:
     * Service önce teacher'ı kontrol eder
     * Eğer inactive ise hata fırlatır
     * Eğer active ise repository query'si çalışır
     * Query teacher.isActive = true şartı nedeniyle boş liste döner

3. Güvenlik Katmanları:
   - Service seviyesinde: Teacher durumu kontrol edilir
   - Repository seviyesinde: Query otomatik filtreleme yapar
   - Entity seviyesinde: canEnroll() metodu teacher aktiflik kontrolü yapar

Bu yaklaşım defensive programming prensibiyle çoklu güvenlik sağlar.
*/