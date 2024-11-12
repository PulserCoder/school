package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public ResponseEntity<Faculty> getFaculty(long id) {
        return ResponseEntity.ok(facultyRepository.findById((int) id).get());
    }

    public ResponseEntity<Faculty> addFaculty(Faculty faculty) {
        return ResponseEntity.ok(facultyRepository.save(faculty));
    }

    public ResponseEntity<Faculty> updateFaculty(Faculty faculty) {
        return ResponseEntity.ok(facultyRepository.save(faculty));
    }

    public void deleteFaculty(long id) {
        facultyRepository.deleteById((int) id);
    }

    public ResponseEntity<List<Faculty>> getFacultyByColor(String color) {
        return ResponseEntity.ok(facultyRepository.findAllByColor(color));
    }

    public ResponseEntity<Faculty> findByFacultyByColorOrName(String color, String name) {
        if (color == null) {
            return ResponseEntity.ok(facultyRepository.findFirstByNameIgnoreCase(name));
        } else if (name == null) {
            return ResponseEntity.ok(facultyRepository.findFirstByColorIgnoreCase(color));
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<String>> getAllStudents(int id) {
        return ResponseEntity.ok(facultyRepository.findById(id).get().getStudents().stream().map(e -> e.getName()).toList());
    }
}
