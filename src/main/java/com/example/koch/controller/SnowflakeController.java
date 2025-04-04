package com.example.koch.controller;

import com.example.koch.model.LineSegment;
import com.example.koch.service.KochSnowflakeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SnowflakeController {

    private final KochSnowflakeService service;

    public SnowflakeController(KochSnowflakeService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String home(
            @RequestParam(defaultValue = "3") int depth,
            @RequestParam(defaultValue = "3") int edges,
            Model model
    ) {
        List<LineSegment> segments = service.generateSnowflake(depth, edges);
        model.addAttribute("segments", segments);
        model.addAttribute("depth", depth);
        model.addAttribute("edges", edges);
        return "index";
    }

}
