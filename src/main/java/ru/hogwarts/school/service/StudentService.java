package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    Logger logger = LoggerFactory.getLogger(StudentService.class);


    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public ResponseEntity<Student> getById(long id) {
        logger.info("Get student by id: {}", id);
        return ResponseEntity.ok(studentRepository.findById((int) id).get());
    }

    public void deleteById(long id) {
        logger.info("Delete student by id: {}", id);
        studentRepository.deleteById((int) id);
    }

    public ResponseEntity<Student> update(Student student) {
        logger.info("Update student by id: {}", student.getId());
        return ResponseEntity.ok(studentRepository.save(student));
    }

    public ResponseEntity<Student> create(Student student) {
        logger.info("Create student by id: {}", student.getId());
        return ResponseEntity.ok(studentRepository.save(student));
    }


    public ResponseEntity<List<Student>> getByAge(int age) {
        logger.info("Get students by age: {}", age);
        return ResponseEntity.ok(studentRepository.findAllByAge(age));
    }


    public ResponseEntity<List<Student>> getByAgeBetween(int min, int max) {
        logger.info("Get students by age between: {}", min);
        return ResponseEntity.ok(studentRepository.getStudentsByAgeBetween(min, max));
    }

    public ResponseEntity<Faculty> getFaculty(int id) {
        logger.info("Get student faculty by id: {}", id);
        return ResponseEntity.ok(studentRepository.findById(id).get().getFaculty());
    }

    public ResponseEntity<Integer> getCountOfStudents() {
        logger.info("Get student count");
        return ResponseEntity.ok(studentRepository.getCountOfStudents());
    }


    public ResponseEntity<Float> getAverage() {
        logger.info("Get student age average");
        return ResponseEntity.ok(studentRepository.getAverage());
    }

    public ResponseEntity<List<Student>> getLast5Students() {
        logger.info("Get student last 5 students");
        return ResponseEntity.ok(studentRepository.getLast5Students());
    }
}
