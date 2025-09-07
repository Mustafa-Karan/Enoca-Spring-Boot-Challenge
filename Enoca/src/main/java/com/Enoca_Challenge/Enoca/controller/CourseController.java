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

    // Öğretmenin kurslarını getir (GetAllCoursesForTeacher)
    @GetMapping("/teachers/{teacherId}/courses")
    public ResponseEntity<List<Course>> getAllCoursesForTeacher(@PathVariable Long teacherId) {
        List<Course> courses = courseService.getAllCoursesForTeacher(teacherId);
        return ResponseEntity.ok(courses);
    }

    // Öğrencinin kurslarını getir (GetCoursesForStudent)
    @GetMapping("/students/{studentId}/courses")
    public ResponseEntity<List<Course>> getCoursesForStudent(@PathVariable Long studentId) {
        List<Course> courses = courseService.getCoursesForStudent(studentId);
        return ResponseEntity.ok(courses);
    }

    // Öğretmen için kurs oluştur (CreateCourseForTeacher)
    @PostMapping("/teachers/{teacherId}/courses")
    public ResponseEntity<Course> createCourseForTeacher(@PathVariable Long teacherId, @RequestBody Course course) {
        Course createdCourse = courseService.createCourseForTeacher(teacherId, course);
        return ResponseEntity.ok(createdCourse);
    }

    // Öğretmenin kursunu güncelle (UpdateCourseForTeacher)
    @PutMapping("/teachers/{teacherId}/courses/{courseId}")
    public ResponseEntity<Course> updateCourseForTeacher(
            @PathVariable Long teacherId,
            @PathVariable Long courseId,
            @RequestBody Course course) {
        Course updatedCourse = courseService.updateCourseForTeacher(teacherId, courseId, course);
        return ResponseEntity.ok(updatedCourse);
    }

    // Öğretmenin kursunu sil (DeleteCourseForTeacher)
    @DeleteMapping("/teachers/{teacherId}/courses/{courseId}")
    public ResponseEntity<String> deleteCourseForTeacher(
            @PathVariable Long teacherId,
            @PathVariable Long courseId) {
        courseService.deleteCourseForTeacher(teacherId, courseId);
        return ResponseEntity.ok("Kurs silindi");
    }
}
