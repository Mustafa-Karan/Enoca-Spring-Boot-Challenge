package com.Enoca_Challenge.Enoca.service;

import com.Enoca_Challenge.Enoca.entity.Teacher;
import com.Enoca_Challenge.Enoca.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;

    // Get all active teachers
    public List<Teacher> getTeachers() {
        return teacherRepository.findByIsActiveTrue();
    }

    // Get teacher by ID
    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found: " + id));
    }

    // Create new teacher
    public Teacher createTeacher(Teacher teacher) {
        if (teacherRepository.existsByEmail(teacher.getEmail())) {
            throw new RuntimeException("Email already exists: " + teacher.getEmail());
        }
        return teacherRepository.save(teacher);
    }

    // Update teacher
    public Teacher updateTeacher(Long id, Teacher teacherData) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found: " + id));

        // Check if email is being updated and if it already exists
        if (!teacher.getEmail().equals(teacherData.getEmail()) &&
                teacherRepository.existsByEmail(teacherData.getEmail())) {
            throw new RuntimeException("Email already exists: " + teacherData.getEmail());
        }

        teacher.setFirstName(teacherData.getFirstName());
        teacher.setLastName(teacherData.getLastName());
        teacher.setEmail(teacherData.getEmail());
        teacher.setPhone(teacherData.getPhone());
        teacher.setSpecialization(teacherData.getSpecialization());

        return teacherRepository.save(teacher);
    }

    // Delete teacher (soft delete)
    public void deleteTeacher(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found: " + id));

        teacher.setIsActive(false);
        teacherRepository.save(teacher);
    }
}