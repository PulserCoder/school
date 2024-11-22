package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findAllByAge(int age);

    List<Student> getStudentsByAgeBetween(int min, int max);

    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    Integer getCountOfStudents();

    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    Float getAverage();

    @Query(value = "SELECT * FROM student OFFSET (SELECT COUNT(*) FROM student) - 5", nativeQuery = true)
    List<Student> getLast5Students();
}
