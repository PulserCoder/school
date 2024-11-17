package ru.hogwarts.school.restControllersTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() throws Exception {
        assertThat(facultyController).isNotNull();
    }

    @Test
    public void testGetFacultyById() throws Exception {
        Faculty faculty = restTemplate.getForObject("http://localhost:" + port + "/faculty/2", Faculty.class);
        assertThat(faculty).isNotNull();
        assertThat(faculty.getId()).isEqualTo(2);
    }

    @Test
    public void createFacultyTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Science");
        faculty.setColor("Green");

        Faculty createdFaculty = restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class);
        assertThat(createdFaculty).isNotNull();
        assertThat(createdFaculty.getName()).isEqualTo("Science");
        assertThat(createdFaculty.getColor()).isEqualTo("Green");
    }

    @Test
    public void updateFacultyTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(2);
        faculty.setName("Updated Faculty");
        faculty.setColor("Blue");

        restTemplate.put("http://localhost:" + port + "/faculty", faculty);

        ResponseEntity<Faculty> response = restTemplate.getForEntity("http://localhost:" + port + "/faculty/2", Faculty.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Updated Faculty");
        assertThat(response.getBody().getColor()).isEqualTo("Blue");
    }

    @Test
    public void getFacultyByColorTest() throws Exception {
        String response = restTemplate.getForObject("http://localhost:" + port + "/faculty/by-color/Green", String.class);

        assertThat(response).isNotNull();
    }

    @Test
    public void getFacultyByColorOrNameTest() throws Exception {
        String response = restTemplate.getForObject("http://localhost:" + port + "/faculty/find_by_color_or_name?color=Green", String.class);

        assertThat(response).isNotNull();
    }

    @Test
    public void getAllStudentsInFacultyTest() throws Exception {
        String response = restTemplate.getForObject("http://localhost:" + port + "/faculty/get_all_students/2", String.class);

        assertThat(response).isNotNull();
    }
}
