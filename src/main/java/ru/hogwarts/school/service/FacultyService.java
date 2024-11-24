package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public ResponseEntity<Faculty> getFaculty(long id) {
        logger.info("Get faculty by id: {}", id);
        return ResponseEntity.ok(facultyRepository.findById((int) id).get());
    }

    public ResponseEntity<Faculty> addFaculty(Faculty faculty) {
        logger.info("Add faculty: {}", faculty);
        return ResponseEntity.ok(facultyRepository.save(faculty));
    }

    public ResponseEntity<Faculty> updateFaculty(Faculty faculty) {
        logger.info("Update faculty: {}", faculty);
        return ResponseEntity.ok(facultyRepository.save(faculty));
    }

    public void deleteFaculty(long id) {
        logger.info("Delete faculty by id: {}", id);
        facultyRepository.deleteById((int) id);
    }

    public ResponseEntity<List<Faculty>> getFacultyByColor(String color) {
        logger.info("Get faculty by color: {}", color);
        return ResponseEntity.ok(facultyRepository.findAllByColor(color));
    }

    public ResponseEntity<Faculty> findByFacultyByColorOrName(String color, String name) {
        logger.info("Find faculty by color or name null where color = {} and name = {}", color, name);
        if (color == null) {
            return ResponseEntity.ok(facultyRepository.findFirstByNameIgnoreCase(name));
        } else if (name == null) {
            return ResponseEntity.ok(facultyRepository.findFirstByColorIgnoreCase(color));
        }
        logger.error("No one faculty with color and name");
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<String>> getAllStudents(int id) {
        logger.info("Get faculty students by id: {}", id);
        return ResponseEntity.ok(facultyRepository.findById(id).get().getStudents().stream().map(e -> e.getName()).toList());
    }

    public ResponseEntity<String> getLongestName() {
        List<Faculty> facultyList = facultyRepository.findAll();
        Faculty faculty = facultyList.get(facultyList.size() - 1);
        return ResponseEntity.ok(facultyList.stream().max(Comparator.comparingInt(e -> e.getName().length()))
                .orElse(faculty).getName());
    }

    public ResponseEntity<Integer> getSomeValue() {
        return ResponseEntity.ok(Stream.iterate(1, a -> a + 1)
                .parallel()
                .limit(1000000)
                .reduce(0, (a, b) -> a + b));
    }
}
