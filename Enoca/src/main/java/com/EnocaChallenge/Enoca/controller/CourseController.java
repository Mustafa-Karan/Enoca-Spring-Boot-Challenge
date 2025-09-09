package com.EnocaChallenge.Enoca.controller;

import com.EnocaChallenge.Enoca.entity.Course;
import com.EnocaChallenge.Enoca.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    // Get all available courses
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses() {
        try {
            List<Course> courses = courseService.getAllCourses();
            return ResponseEntity.ok(courses);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get single course
    @GetMapping("/courses/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable Long id) {
        try {
            Course course = courseService.getCourseById(id);
            return ResponseEntity.ok(course);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get all courses for teacher
    @GetMapping("/teachers/{teacherId}/courses")
    public ResponseEntity<List<Course>> getAllCoursesForTeacher(@PathVariable Long teacherId) {
        try {
            List<Course> courses = courseService.getAllCoursesForTeacher(teacherId);
            return ResponseEntity.ok(courses);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get courses for student
    @GetMapping("/students/{studentId}/courses")
    public ResponseEntity<List<Course>> getCoursesForStudent(@PathVariable Long studentId) {
        try {
            List<Course> courses = courseService.getCoursesForStudent(studentId);
            return ResponseEntity.ok(courses);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Create course for teacher
    @PostMapping("/teachers/{teacherId}/courses")
    public ResponseEntity<Course> createCourseForTeacher(@PathVariable Long teacherId, @RequestBody Course course) {
        try {
            Course createdCourse = courseService.createCourseForTeacher(teacherId, course);
            return ResponseEntity.ok(createdCourse);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Update course for teacher
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

    // Delete course for teacher
    @DeleteMapping("/teachers/{teacherId}/courses/{courseId}")
    public ResponseEntity<String> deleteCourseForTeacher(
            @PathVariable Long teacherId,
            @PathVariable Long courseId) {
        try {
            courseService.deleteCourseForTeacher(teacherId, courseId);
            return ResponseEntity.ok("Course deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}