package ru.hogwarts.school.restControllersTests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.NoSuchElementException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() throws Exception {
        assertThat(studentController).isNotNull();
    }

    @Test
    public void testGetStudent() throws Exception {
        assertThat(restTemplate.getForObject("http://localhost:" + port + "/student/1", String.class)).isNotNull();
    }

    @Test
    public void createTest() throws Exception {
        Student student = new Student();
        student.setAge(0);
        student.setName("asd");
        assertThat(restTemplate.postForObject("http://localhost:" + port + "/student", student, String.class)).isNotNull();
    }

    @Test
    public void updateStudentTest() throws Exception {
        Student student = new Student();
        student.setAge(0);
        student.setName("asd");
        student.setId(2);

        restTemplate.put("http://localhost:" + port + "/student", student);

        ResponseEntity<Student> response = restTemplate.getForEntity("http://localhost:" + port + "/student/2", Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("asd");
        assertThat(response.getBody().getAge()).isEqualTo(0);
        assertThat(response.getBody().getId()).isEqualTo(2);
    }


    @Test
    public void testDeleteStudentTest() throws Exception {
        String obj = restTemplate.getForObject("http://localhost:" + port + "/student/1", String.class);
        int deleted = 0;
        Assertions.assertNotNull(obj, "student must exist before deleting");
        restTemplate.delete("http://localhost:" + port + "/student/1", String.class);
        try {
            ResponseEntity<Student> student = studentService.getById(1);
        } catch (NoSuchElementException e) {
            deleted = 1;
            restTemplate.put("http://localhost:" + port + "/student/", obj);
        }
        assertThat(deleted).isEqualTo(1);
    }

    @Test
    public void getStudentsByAgeTest() throws Exception {
        String response = restTemplate.getForObject("http://localhost:" + port + "/student/by-age/0", String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        List<Student> students = objectMapper.readValue(response, new TypeReference<List<Student>>() {});
        Assertions.assertTrue(students.stream().allMatch(student -> student.getAge() == 0));
    }

    @Test
    public void getStudentsWhoAgeBetweenTest() throws Exception {
        String response = restTemplate.getForObject("http://localhost:" + port + "/student/age-between?min=15&max=25", String.class);
        System.out.println("response = " + response);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Student> student = objectMapper.readValue(response, new TypeReference<List<Student>>() {});
        Assertions.assertTrue(student.stream().allMatch(e -> e.getAge() >= 15 && e.getAge() <= 25));
    }

    @Test
    public void getFacultyByIdTest() throws Exception {
        Faculty faculty = restTemplate.getForObject("http://localhost:" + port + "/student/get_faculty/202", Faculty.class);
        Assertions.assertNotNull(faculty);
        Assertions.assertEquals(2, faculty.getId());
    }


}