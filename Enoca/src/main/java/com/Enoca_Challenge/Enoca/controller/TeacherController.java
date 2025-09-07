package com.Enoca_Challenge.Enoca.controller;
import com.Enoca_Challenge.Enoca.entity.Teacher;
import com.Enoca_Challenge.Enoca.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    // Öğretmenleri getir (GetTeachers)
    @GetMapping
    public ResponseEntity<List<Teacher>> getTeachers() {
        List<Teacher> teachers = teacherService.getTeachers();
        return ResponseEntity.ok(teachers);
    }

    // Öğretmen oluştur (CreateTeacher)
    @PostMapping
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher teacher) {
        Teacher createdTeacher = teacherService.createTeacher(teacher);
        return ResponseEntity.ok(createdTeacher);
    }

    // Öğretmen güncelle (UpdateTeacher)
    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable Long id, @RequestBody Teacher teacher) {
        Teacher updatedTeacher = teacherService.updateTeacher(id, teacher);
        return ResponseEntity.ok(updatedTeacher);
    }

    // Öğretmen sil (DeleteTeacher)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.ok("Öğretmen silindi");
    }
}