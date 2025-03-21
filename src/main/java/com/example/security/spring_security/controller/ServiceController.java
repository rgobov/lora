package com.example.security.spring_security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class ServiceController {

    @GetMapping("/smas-lifting")
    public String smasLifting() {
        return "lora/smas-lifting"; // Возвращает имя HTML-файла (smas-lifting.html)
    }

    @GetMapping("/rf-microneedling")
    public String microneedling() {
        return "lora/rf-microneedling"; // Возвращает имя HTML-файла (smas-lifting.html)
    }

    @GetMapping("/mediderma-skincare")
    public String mediderma() {
        return "lora/mediderma-skincare"; // Возвращает имя HTML-файла (smas-lifting.html)
    }

    @GetMapping("/chiromassage")
    public String chiromassage() {
        return "lora/chiromassage"; // Имя шаблона без расширения .html
    }
}

