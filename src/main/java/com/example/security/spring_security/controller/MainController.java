package com.example.security.spring_security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/smas-lifting")
    public String smasLifting() {
        return "lora/smas-lifting"; // Возвращает имя HTML-файла (smas-lifting.html)
    }

    @GetMapping("/rf-microneedling")
    public String microneedling() {
        return "lora/rf-microneedling"; // Возвращает имя HTML-файла (rf-microneedling.html)
    }

    @GetMapping("/mediderma-skincare")
    public String mediderma() {
        return "lora/mediderma-skincare"; // Возвращает имя HTML-файла (mediderma-skincare.html)
    }

    @GetMapping("/chiromassage")
    public String chiromassage() {
        return "lora/chiromassage"; // Имя шаблона без расширения .html
    }

    @GetMapping("/about")
    public String about() {
        return "lora/about"; // Возвращает имя HTML-файла (about.html)
    }

    @GetMapping("/works")
    public String works() {
        return "lora/works"; // Возвращает имя HTML-файла (works.html)
    }
}