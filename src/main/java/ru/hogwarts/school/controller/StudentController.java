package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return studentService.create(student);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") long id) {
        return studentService.getById(id);
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        return studentService.update(student);
    }

    @DeleteMapping("{id}")
    public String deleteStudentById(@PathVariable("id") long id) {
        studentService.deleteById(id);
        return "deleted";
    }

    @GetMapping("by-age/{age}")
    public ResponseEntity<List<Student>> getStudentsByAge(@PathVariable("age") int age) {
        return studentService.getByAge(age);
    }

    @GetMapping("age-between")
    public ResponseEntity<List<Student>> getStudentsWhoAgeBetween(@RequestParam("min") int min,
                                                                  @RequestParam("max") int max) {
        return studentService.getByAgeBetween(min, max);
    }

    @GetMapping("get_faculty/{id}")
    public ResponseEntity<Faculty> getFacultyById(@PathVariable("id") int id) {
        return studentService.getFaculty(id);
    }


}
