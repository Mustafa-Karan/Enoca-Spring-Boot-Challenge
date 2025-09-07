package com.Enoca_Challenge.Enoca.controller;

import com.Enoca_Challenge.Enoca.entity.Course;
import com.Enoca_Challenge.Enoca.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    // Tüm kursları getir
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses() {
        try {
            List<Course> courses = courseService.getAllCourses();
            return ResponseEntity.ok(courses);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Tek kurs getir
    @GetMapping("/courses/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable Long id) {
        try {
            Course course = courseService.getCourseById(id);
            return ResponseEntity.ok(course);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Öğretmenin kurslarını getir (GetAllCoursesForTeacher)
    @GetMapping("/teachers/{teacherId}/courses")
    public ResponseEntity<List<Course>> getAllCoursesForTeacher(@PathVariable Long teacherId) {
        try {
            List<Course> courses = courseService.getAllCoursesForTeacher(teacherId);
            return ResponseEntity.ok(courses);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Öğrencinin kurslarını getir (GetCoursesForStudent)
    @GetMapping("/students/{studentId}/courses")
    public ResponseEntity<List<Course>> getCoursesForStudent(@PathVariable Long studentId) {
        try {
            List<Course> courses = courseService.getCoursesForStudent(studentId);
            return ResponseEntity.ok(courses);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Öğretmen için kurs oluştur (CreateCourseForTeacher)
    @PostMapping("/teachers/{teacherId}/courses")
    public ResponseEntity<Course> createCourseForTeacher(@PathVariable Long teacherId, @RequestBody Course course) {
        try {
            Course createdCourse = courseService.createCourseForTeacher(teacherId, course);
            return ResponseEntity.ok(createdCourse);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Öğretmenin kursunu güncelle (UpdateCourseForTeacher)
    @PutMapping("/teachers/{teacherId}/courses/{courseId}")
    public ResponseEntity<Course> updateCourseForTeacher(
            @PathVariable Long teacherId,
            @PathVariable Long courseId,
            @RequestBody Course course) {
        try {
            Course updatedCourse = courseService.updateCourseForTeacher(teacherId, courseId, course);
            return ResponseEntity.ok(updatedCourse);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Öğretmenin kursunu sil (DeleteCourseForTeacher)
    @DeleteMapping("/teachers/{teacherId}/courses/{courseId}")
    public ResponseEntity<String> deleteCourseForTeacher(
            @PathVariable Long teacherId,
            @PathVariable Long courseId) {
        try {
            courseService.deleteCourseForTeacher(teacherId, courseId);
            return ResponseEntity.ok("Kurs silindi");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Hata: " + e.getMessage());
        }
    }
}