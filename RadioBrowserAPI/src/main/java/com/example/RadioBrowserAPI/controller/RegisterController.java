package com.example.RadioBrowserAPI.controller;

import com.example.RadioBrowserAPI.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller 
public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping ("/register")
    public String registerPage() {
        return "register"; // Retorna o nome do template HTML para a página de registro
    }
    

    @PostMapping ("/register")
    public String handleRegister(
        @RequestParam String username,
        @RequestParam String email,
        @RequestParam String password
    ) {
        try {
        // Chama o serviço para registrar o usuário
        userService.registerUser(username, email, password);
        return "redirect:/login"; // Redireciona para a página de login após o registro
        } catch (Exception e) {
            // Lida com erros, como usuário já existente
            return "redirect:/register?error=" + e.getMessage(); // Redireciona de volta para a página de registro com um parâmetro de erro
        }
    }
    
}
