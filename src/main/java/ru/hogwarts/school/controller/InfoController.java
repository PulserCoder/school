package ru.hogwarts.school.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    @Value("${server.port:8080}")
    private Integer port;

    @GetMapping("port")
    public Integer getPort() {
        return port;
    }
}
