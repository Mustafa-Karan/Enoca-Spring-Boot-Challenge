package com.Enoca_Challenge.Enoca.service;

import com.Enoca_Challenge.Enoca.entity.Course;
import com.Enoca_Challenge.Enoca.entity.Teacher;
import com.Enoca_Challenge.Enoca.repository.CourseRepository;
import com.Enoca_Challenge.Enoca.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;

    // Tüm kursları getir
    public List<Course> getAllCourses() {
        return courseRepository.findByIsAvailableTrueAndIsActiveTrue();
    }

    // Tek kurs getir
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kurs bulunamadı: " + id));
    }

    // Öğretmenin kurslarını getir (GetAllCoursesForTeacher)
    public List<Course> getAllCoursesForTeacher(Long teacherId) {
        return courseRepository.findByTeacherId(teacherId);
    }

    // Öğrencinin kurslarını getir (GetCoursesForStudent)
    public List<Course> getCoursesForStudent(Long studentId) {
        return courseRepository.findByStudentId(studentId);
    }

    // Öğretmen için kurs oluştur (CreateCourseForTeacher)
    public Course createCourseForTeacher(Long teacherId, Course course) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Öğretmen bulunamadı: " + teacherId));

        course.setTeacher(teacher);
        return courseRepository.save(course);
    }

    // Öğretmenin kursunu güncelle (UpdateCourseForTeacher)
    public Course updateCourseForTeacher(Long teacherId, Long courseId, Course courseData) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Kurs bulunamadı: " + courseId));

        if (!course.getTeacher().getId().equals(teacherId)) {
            throw new RuntimeException("Bu kurs bu öğretmene ait değil");
        }

        course.setTitle(courseData.getTitle());
        course.setDescription(courseData.getDescription());
        course.setPrice(courseData.getPrice());
        course.setMaxStudents(courseData.getMaxStudents());
        course.setIsAvailable(courseData.getIsAvailable());

        return courseRepository.save(course);
    }

    // Öğretmenin kursunu sil (DeleteCourseForTeacher)
    public void deleteCourseForTeacher(Long teacherId, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Kurs bulunamadı: " + courseId));

        if (!course.getTeacher().getId().equals(teacherId)) {
            throw new RuntimeException("Bu kurs bu öğretmene ait değil");
        }

        course.setIsActive(false);
        courseRepository.save(course);
    }
}