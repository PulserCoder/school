package ru.hogwarts.school.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final Map<Long, Faculty> facultyMap;
    private long id;

    public FacultyService() {
        facultyMap = new HashMap<>();
    }

    public ResponseEntity<Faculty> getFaculty(long id) {
        if (!facultyMap.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyMap.get(id));
    }

    public ResponseEntity<Faculty> addFaculty(Faculty faculty) {
        faculty.setId(id + 1);
        facultyMap.put(++id, faculty);
        return ResponseEntity.ok(facultyMap.get(id));
    }

    public ResponseEntity<Faculty> updateFaculty(Faculty faculty) {
        if (!facultyMap.containsKey(faculty.getId())) {
            return ResponseEntity.notFound().build();
        }
        facultyMap.put(faculty.getId(), faculty);
        return ResponseEntity.ok(faculty);
    }

    public ResponseEntity<Faculty> deleteFaculty(long id) {
        if (!facultyMap.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyMap.remove(id));
    }

    public ResponseEntity<List<Faculty>> getFacultyByColor(String color) {
        List<Faculty> result = facultyMap.values().stream().filter(e -> e.getColor().equals(color)).toList();
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

}
