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
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(FacultyController.class)
public class FacultyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyService facultyService;

    @InjectMocks
    private FacultyController facultyController;

    Faculty faculty;
    String name;
    String color;
    JSONObject facultyObject;
    Faculty faculty2;

    @BeforeEach
    public void setUp() throws JSONException {
        faculty = new Faculty();
        name = "123";
        color = "red";
        faculty.setName(name);
        faculty.setColor(color);
        faculty2 = new Faculty();
        faculty2.setName(name);
        faculty2.setColor(color);
        facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("color", color);
    }

    @Test
    public void getFacultyByIdTest() throws Exception {
        when(facultyRepository.findById(any(Integer.class)))
                .thenReturn(Optional.of(faculty));
        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/1" + faculty.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));


    }

    @Test
    public void createFacultyTest() throws Exception {
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders.post("/faculty")
                .contentType(MediaType.APPLICATION_JSON)
                .content(facultyObject.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void updateFacultyTest() throws Exception {
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        mockMvc.perform(MockMvcRequestBuilders.put("/faculty")
                .content(facultyObject.toString())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void getFacultyByColorTest() throws Exception {
        when(facultyRepository.findAllByColor(any(String.class))).thenReturn(List.of(faculty));
        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/by-color/red" + color))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value(name));
    }

    @Test
    public void getFacultyByColorOrNameTest() throws Exception {
        when(facultyRepository.findFirstByColorIgnoreCase(any(String.class))).thenReturn(faculty);
        when(facultyRepository.findFirstByNameIgnoreCase(any(String.class))).thenReturn(faculty2);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/find_by_color_or_name?color=red"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name));
        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/find_by_color_or_name?name=red"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name));
    }






}
