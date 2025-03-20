package com.example.security.spring_security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class SmasLiftingController {

    @GetMapping("/smas-lifting")
    public String smasLifting() {
        return "lora/smas-lifting"; // Возвращает имя HTML-файла (smas-lifting.html)
    }
}
