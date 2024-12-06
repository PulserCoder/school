package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.*;

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

    public ResponseEntity<List<String>> getAllStudentsWhosNameStartsWith(String letter) {
        List<Student> students = studentRepository.findAll();
        List<String> proceedStudents = students.stream()
                .parallel()
                .map(i -> i.getName().toUpperCase())
                .filter(i -> i.startsWith(letter.toUpperCase()))
                .sorted()
                .toList();
        return ResponseEntity.ok(proceedStudents);
    }

    public ResponseEntity<Integer> getAverageAge() {
        logger.info("Get student average age");
        List<Student> students = studentRepository.findAll();
        double proceedStudents = (double) (students.stream()
                .parallel()
                .mapToInt(Student::getAge)
                .sum() / students.size());
        return ResponseEntity.ok((int) Math.round(proceedStudents));
    }

    public void printAllStudentsParallel() {
        List<Student> students = studentRepository.findAll();
        new Thread(() -> {
            printAllStudents(0,1, students);
        }).start();
        new Thread(() -> {
            printAllStudents(2,3, students);
        }).start();
        new Thread(() -> {
            printAllStudents(4,5, students);
        }).start();


    }

    public void printAllStudents(int indexLeft, int indexRight, List<Student> students) {
        for (int i = indexLeft; i <= indexRight; i++) {
            System.out.println(students.get(i).getName());
        }
    }

    public void printSync() {
        List<Student> students = studentRepository.findAll();
        new Thread(() -> {
            printAllStudentsSync(0,1, students);
        }).start();
        new Thread(() -> {
            printAllStudentsSync(2,3, students);
        }).start();
        new Thread(() -> {
            printAllStudentsSync(4,5, students);
        }).start();
    }

    public void printAllStudentsSync(int indexLeft, int indexRight, List<Student> students) {
        synchronized (students) {
            for (int i = indexLeft; i <= indexRight; i++) {
                System.out.println(students.get(i).getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
