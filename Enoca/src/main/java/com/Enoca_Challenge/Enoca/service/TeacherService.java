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

    // Öğretmenleri getir (GetTeachers)
    public List<Teacher> getTeachers() {
        return teacherRepository.findByIsActiveTrue();
    }

    // Öğretmen oluştur (CreateTeacher)
    public Teacher createTeacher(Teacher teacher) {
        if (teacherRepository.existsByEmail(teacher.getEmail())) {
            throw new RuntimeException("Bu email zaten kayıtlı: " + teacher.getEmail());
        }
        return teacherRepository.save(teacher);
    }

    // Öğretmen güncelle (UpdateTeacher)
    public Teacher updateTeacher(Long id, Teacher teacherData) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Öğretmen bulunamadı: " + id));

        teacher.setFirstName(teacherData.getFirstName());
        teacher.setLastName(teacherData.getLastName());
        teacher.setEmail(teacherData.getEmail());
        teacher.setPhone(teacherData.getPhone());
        teacher.setSpecialization(teacherData.getSpecialization());

        return teacherRepository.save(teacher);
    }

    // Öğretmen sil (DeleteTeacher) - Soft delete
    public void deleteTeacher(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Öğretmen bulunamadı: " + id));

        teacher.setIsActive(false);
        teacherRepository.save(teacher);
    }
}