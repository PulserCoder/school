package ru.hogwarts.school.mvcControllersTest;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;


    private String name;
    private int age;
    private JSONObject userObject;
    private Student student;
    private Faculty faculty;

    @BeforeEach
    public void setUp() throws JSONException {
        name = "123";
        age = 0;
        userObject = new JSONObject();
        userObject.put("name", name);
        userObject.put("age", age);

        student = new Student();
        student.setName(name);
        student.setAge(age);

        faculty = new Faculty();
        String facultyName = "123";
        String facultyColor = "0";
        faculty.setName(facultyName);
        faculty.setColor(facultyColor);
        student.setFaculty(faculty);
    }

    @Test
    public void createStudentTest() throws Exception {
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Integer.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders.post("/student")
                        .content(userObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));


    }

    @Test
    public void getStudentByIdTest() throws Exception {
        when(studentRepository.findById(any(Integer.class))).thenReturn(Optional.of(student));
        mockMvc.perform(MockMvcRequestBuilders.get("/student/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void updateStudentTest() throws Exception {
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders.put("/student")
                .content(userObject.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void getStudentsByAgeTest() throws Exception {
        when(studentRepository.findAllByAge(any(Integer.class)))
                .thenReturn(List.of(student, student, student));
        mockMvc.perform(MockMvcRequestBuilders.get("/student/by-age/0").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name").value(name));
    }

    @Test
    public void getStudentsWhoAgeBetweenTest() throws Exception {
        when(studentRepository.getStudentsByAgeBetween(any(Integer.class), any(Integer.class))).thenReturn(List.of(student, student, student));
        mockMvc.perform(MockMvcRequestBuilders.get("/student/age-between?min=15&max=25").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name").value(name));
    }

    @Test
    public void getFacultyByIdTest() throws Exception {
        when(studentRepository.findById(any(Integer.class))).thenReturn(Optional.of(student));
        mockMvc.perform(MockMvcRequestBuilders.get("/student/get_faculty/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(faculty.getName()));

    }
}
