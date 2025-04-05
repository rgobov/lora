package com.example.security.spring_security.controller;

import com.example.security.spring_security.service.ImageCompressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ImageController {

    @Autowired
    private ImageCompressionService compressionService;

    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> getCompressedImage(
            @PathVariable String filename,
            @RequestParam(defaultValue = "800") int width,
            @RequestParam(defaultValue = "600") int height,
            @RequestParam(defaultValue = "0.7") float quality) throws IOException {

        // Загружаем оригинальное изображение из ресурсов
        ClassPathResource imageResource = new ClassPathResource("static/fotobank/myWorks/" + filename);
        if (!imageResource.exists()) {
            throw new IOException("Image not found: " + filename);
        }
        byte[] imageData = imageResource.getInputStream().readAllBytes();

        // Сжимаем изображение
        byte[] compressedImage = compressionService.compressImage(imageData, width, height, quality);

        // Возвращаем сжатое изображение
        ByteArrayResource resource = new ByteArrayResource(compressedImage);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .body(resource);
    }
}