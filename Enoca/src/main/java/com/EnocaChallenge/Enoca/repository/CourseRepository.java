package com.EnocaChallenge.Enoca.repository;

import com.EnocaChallenge.Enoca.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    // Bu query otomatik olarak sadece aktif öğretmenlerin kurslarını getirir
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