package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Integer> {
    List<Faculty> findAllByColor(String color);

    Faculty findFirstByColorIgnoreCase(String color);

    Faculty findFirstByNameIgnoreCase(String name);

}
