package ru.hogwarts.school.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {
    private final Map<Long, Student> students;
    private Long id;

    public StudentService() {
        this.students = new HashMap<Long, Student>();
        this.id = 0L;
    }

    public ResponseEntity<Student> getById(long id) {
        if (!students.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students.get(id));
    }

    public ResponseEntity<Student> deleteById(long id) {
        if (!students.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students.remove(id));
    }

    public ResponseEntity<Student> update(Student student) {
        if (!students.containsKey(student.getId())) {
            return ResponseEntity.notFound().build();
        }
        students.put(student.getId(), student);
        return ResponseEntity.ok(student);
    }

    public ResponseEntity<Student> create(Student student) {
        student.setId(id + 1);
        students.put(++id, student);
        return ResponseEntity.ok(students.get(id - 1));
    }


    public ResponseEntity<List<Student>> getByAge(int age) {
        List<Student> result = students.values().stream().filter(e -> e.getAge() == age).toList();
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }




}
