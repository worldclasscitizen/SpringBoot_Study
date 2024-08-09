package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {
    @GetMapping("/hi")
    public String niceToMeetYou(Model model) {
        // model 객체가 "진우" 값을 "username" 에 연결해 웹 브라우저로 보낸다.
        model.addAttribute("username", "Jinwoo");
        return "greetings"; // greetings.mustsache 파일 반환
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model) {
        model.addAttribute("nickname", "최진우");
        return "goodbye";
    }

}
