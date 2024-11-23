package ru.hogwarts.school.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping(path = "avatar")
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "upload/{student_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long student_id,
                                               @RequestParam MultipartFile avatar) throws IOException {
        avatarService.uploadFile(student_id, avatar);
        return ResponseEntity.ok().build();
    }
    @GetMapping(value = "get_avatar/{id}")
    public void getAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.findAvatar(id);
        Path path = Path.of(avatar.getFilePath());
        try (
                InputStream input = Files.newInputStream(path);
                OutputStream output = response.getOutputStream()
                )
        {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            input.transferTo(output);
        }
    }

    @GetMapping(value = "get_avatar_from_db/{id}")
    public ResponseEntity<byte[]> get_avatar_from_db(@PathVariable Long id) {
        Avatar avatar = avatarService.findAvatar(id);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        httpHeaders.setContentLength(avatar.getFileSize());
        return ResponseEntity.ok().headers(httpHeaders).body(avatar.getData());
    }

    @GetMapping(value = "/get_avatars")
    public ResponseEntity<List<Avatar>> getAvatarsByPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return ResponseEntity.ok(avatarService.getAvatarByPage(page, size));
    }
 }
