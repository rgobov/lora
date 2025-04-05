package com.example.security.spring_security.service;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class ImageCompressionService {

    @Cacheable(value = "images", key = "{T(java.util.Arrays).hashCode(#imageData), #width, #height, #quality}")
    public byte[] compressImage(byte[] imageData, int width, int height, float quality) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(new ByteArrayInputStream(imageData))
                .size(width, height)
                .outputQuality(quality)
                .outputFormat("jpg")
                .toOutputStream(outputStream);
        return outputStream.toByteArray();
    }
}