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

    // Get all available courses (only active teachers and active courses)
    public List<Course> getAllCourses() {
        return courseRepository.findAvailableCourses();
    }

    // Get single course by ID
    public Course getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found: " + id));

        // Check if teacher is still active
        if (!course.getTeacher().getIsActive()) {
            throw new RuntimeException("Course is not available - teacher is inactive");
        }

        return course;
    }

    // Get all courses for active teacher
    public List<Course> getAllCoursesForTeacher(Long teacherId) {
        // First verify teacher is active
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found: " + teacherId));

        if (!teacher.getIsActive()) {
            throw new RuntimeException("Teacher is inactive");
        }

        return courseRepository.findByTeacherIdAndTeacherActive(teacherId);
    }

    // Get enrolled courses for student
    public List<Course> getCoursesForStudent(Long studentId) {
        return courseRepository.findByStudentId(studentId);
    }

    // Create course for teacher
    public Course createCourseForTeacher(Long teacherId, Course course) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found: " + teacherId));

        if (!teacher.getIsActive()) {
            throw new RuntimeException("Cannot create course - teacher is inactive");
        }

        course.setTeacher(teacher);
        return courseRepository.save(course);
    }

    // Update course for teacher
    public Course updateCourseForTeacher(Long teacherId, Long courseId, Course courseData) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found: " + courseId));

        if (!course.getTeacher().getId().equals(teacherId)) {
            throw new RuntimeException("This course does not belong to this teacher");
        }

        if (!course.getTeacher().getIsActive()) {
            throw new RuntimeException("Cannot update course - teacher is inactive");
        }

        course.setTitle(courseData.getTitle());
        course.setDescription(courseData.getDescription());
        course.setPrice(courseData.getPrice());
        course.setMaxStudents(courseData.getMaxStudents());
        course.setIsAvailable(courseData.getIsAvailable());

        return courseRepository.save(course);
    }

    // Delete course for teacher (soft delete)
    public void deleteCourseForTeacher(Long teacherId, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found: " + courseId));

        if (!course.getTeacher().getId().equals(teacherId)) {
            throw new RuntimeException("This course does not belong to this teacher");
        }

        course.setIsActive(false);
        courseRepository.save(course);
    }
}