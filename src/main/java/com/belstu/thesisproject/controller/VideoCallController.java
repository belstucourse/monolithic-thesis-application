package com.belstu.thesisproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VideoCallController {
    @GetMapping(value = "")
    public String index(@RequestParam String roomId, Model model) {
        model.addAttribute("roomId", roomId);
        return "index.html";
    }
}
