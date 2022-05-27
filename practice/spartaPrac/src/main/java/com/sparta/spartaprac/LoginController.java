package com.sparta.spartaprac;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(){
        return "redirect:/login-form.html";
    }

    @PostMapping("/login")
    public String loginProcess(@RequestParam String id, @RequestParam String password, Model model){
        if(id.equals(password)) {
            model.addAttribute("loginId", id);
        }
        return "login-result";
    }
}
