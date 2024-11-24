package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class AvatarService {
    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;
    Logger logger = LoggerFactory.getLogger(AvatarService.class);

    public AvatarService(AvatarRepository avatarRepository, StudentRepository studentRepository) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
    }

    public void uploadFile(Long student_id, MultipartFile file) throws IOException {
        logger.info("(uploadFile) Start uploading file avatar");
        Student student = studentRepository.findById(Math.toIntExact(student_id)).get();
        Path pathFile = Path.of(student.getAvatarsDir(), student.getId() + "." +
                file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".") + 1));
        Files.createDirectories(pathFile.getParent());
        Files.deleteIfExists(pathFile);

        try (
                InputStream inputStream = file.getInputStream();
                OutputStream outputStream = Files.newOutputStream(pathFile);
                BufferedInputStream bis = new BufferedInputStream(inputStream, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(outputStream, 1024);
                ) {bis.transferTo(bos);}
        System.out.println("findAvatar(student_id) = " + findAvatar(student_id));
        Avatar avatar = findAvatar(student_id);
        avatar.setId(student.getId());
        avatar.setStudent(student);
        avatar.setFilePath(pathFile.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());
        avatarRepository.save(avatar);
        logger.info("(uploadFile) End uploading file avatar");

    }

    public Avatar findAvatar(Long student_id) {
        logger.info("(findAvatar) Start finding avatar for " + student_id);
        Student avatar = studentRepository.findById(Math.toIntExact(student_id)).get();
        if (avatar.getAvatar() == null) {
            logger.info("(findAvatar) Avatar not found");
            return new Avatar();
        }
        logger.info("(findAvatar) End finding avatar for " + student_id);
        return avatar.getAvatar();
    }


    public List<Avatar> getAvatarByPage(int page, int size) {
        if (page < 1) {
            logger.info("(getAvatarByPage) Page number is less than 1 in getAvatarByPage");
            return new ArrayList<>();
        }
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        logger.info("(getAvatarByPage) Finding avatars at {} with size of page {}", page, size);
        return avatarRepository.findAll(pageRequest).getContent();
    }
}
