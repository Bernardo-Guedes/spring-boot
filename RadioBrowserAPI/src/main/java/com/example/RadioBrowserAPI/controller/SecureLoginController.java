package com.example.RadioBrowserAPI.controller;

import com.example.RadioBrowserAPI.entities.User;
import com.example.RadioBrowserAPI.model.RadioStation;
import com.example.RadioBrowserAPI.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import com.example.RadioBrowserAPI.service.RadioBrowserApiService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Set;
import java.util.Collections;
import com.example.RadioBrowserAPI.service.FavoriteService;

@Controller
public class SecureLoginController {

    @Autowired
    private RadioBrowserApiService radioBrowserApiService;

    @Autowired
    private FavoriteService favoriteService;

    private final UserRepository userRepository;

    public SecureLoginController(UserRepository userRepository, FavoriteService favoriteService, RadioBrowserApiService radioBrowserApiService) {
        this.userRepository = userRepository;
        this.favoriteService = favoriteService;
        this.radioBrowserApiService = radioBrowserApiService;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    
    @GetMapping("/home")
    public String listRadioStations(
        @RequestParam(name = "state", defaultValue = "Minas Gerais") String state, 
        @RequestParam(name = "city", defaultValue = "Belo Horizonte") String city, 
        Model model, 
        Authentication authentication) {

        List<RadioStation> radioStations = radioBrowserApiService.listRadioStations(state, city);
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);
        Set<String> favoriteStationUuids = user != null ? favoriteService.getFavoriteStationUuids(user) : Collections.emptySet();


        model.addAttribute("stations", radioStations);
        model.addAttribute("favoriteUuids", favoriteStationUuids);
        model.addAttribute("city", city);
        model.addAttribute("state", state);
        adicionarNomeUsuario(model, authentication);
        return "home";
    }

    @GetMapping("/loginerror")
    public String loginerror(){
        return "loginerror";
    }

    @GetMapping("/admin")
    public String admin(Model model, Authentication authentication){
        adicionarNomeUsuario(model, authentication);
        return "admin";
    }

    private void adicionarNomeUsuario(Model model, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);
        model.addAttribute("username", user != null ? user.getUsername() : email);
    }

    @GetMapping("/recoverpassword")
    public String recoverpassword(){
        return "recoverpassword";
    }
}
