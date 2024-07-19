package com.example.comeonBusanView.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DangerAreaController {

    @GetMapping("/dangerArea")
    public String dangerArea() {
        return "dangerArea";
    }
}