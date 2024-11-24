package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;


@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }


    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFacultyById(@PathVariable("id") long id) {
        return facultyService.getFaculty(id);
    }

    @PutMapping
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        return facultyService.updateFaculty(faculty);
    }

    @DeleteMapping("{id}")
    public String deleteFaculty(@PathVariable("id") long id) {
        facultyService.deleteFaculty(id);
        return "deleted";
    }

    @GetMapping("by-color/{color}")
    public ResponseEntity<List<Faculty>> getFacultyByColor(@PathVariable("color") String color) {
        return facultyService.getFacultyByColor(color);
    }

    @GetMapping("find_by_color_or_name")
    public ResponseEntity<Faculty> getFacultyByColorOrName(@RequestParam(value = "color", required = false) String color,
                                                           @RequestParam(value = "name", required = false) String name) {
        return facultyService.findByFacultyByColorOrName(color, name);
    }

    @GetMapping("get_all_students/{id}")
    public ResponseEntity<List<String>> getAllStudents(@PathVariable("id") int id) {
        return facultyService.getAllStudents(id);
    }

    @GetMapping("get_longest_name")
    public ResponseEntity<String> getLongestName() {
        return facultyService.getLongestName();
    }

    @GetMapping("get_some_value_from_task")
    public ResponseEntity<Integer> getSomeValue() {
        return facultyService.getSomeValue();
    }

}






