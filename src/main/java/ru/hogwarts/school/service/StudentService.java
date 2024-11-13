package ru.hogwarts.school.service;

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



    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public ResponseEntity<Student> getById(long id) {
        return ResponseEntity.ok(studentRepository.findById((int) id).get());
    }

    public void deleteById(long id) {
        studentRepository.deleteById((int) id);
    }

    public ResponseEntity<Student> update(Student student) {
        return ResponseEntity.ok(studentRepository.save(student));
    }

    public ResponseEntity<Student> create(Student student) {
        return ResponseEntity.ok(studentRepository.save(student));
    }


    public ResponseEntity<List<Student>> getByAge(int age) {
        return ResponseEntity.ok(studentRepository.findAllByAge(age));
    }


    public ResponseEntity<List<Student>> getByAgeBetween(int min, int max) {
        return ResponseEntity.ok(studentRepository.getStudentsByAgeBetween(min, max));
    }

    public ResponseEntity<Faculty> getFaculty(int id) {
        return ResponseEntity.ok(studentRepository.findById(id).get().getFaculty());
    }
}
