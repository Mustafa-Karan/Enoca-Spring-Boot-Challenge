package com.Enoca_Challenge.Enoca.repository;

import com.Enoca_Challenge.Enoca.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c WHERE c.teacher.id = :teacherId AND c.teacher.isActive = true")
    List<Course> findByTeacherIdAndTeacherActive(Long teacherId);

    @Query("SELECT c FROM Course c WHERE c.isAvailable = true AND c.isActive = true AND c.teacher.isActive = true")
    List<Course> findAvailableCourses();

    @Query("SELECT c FROM Course c JOIN c.enrolledStudents s WHERE s.id = :studentId")
    List<Course> findByStudentId(Long studentId);
}