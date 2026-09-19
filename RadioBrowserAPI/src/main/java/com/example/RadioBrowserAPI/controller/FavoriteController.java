package com.example.RadioBrowserAPI.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.RadioBrowserAPI.model.RadioStation;
import com.example.RadioBrowserAPI.service.FavoriteService;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import com.example.RadioBrowserAPI.entities.User;
import com.example.RadioBrowserAPI.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@Controller 
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final UserRepository userRepository;

    public FavoriteController(FavoriteService favoriteService, UserRepository userRepository) {
        this.favoriteService = favoriteService;
        this.userRepository = userRepository;
    }

    @GetMapping("/favorites")
    public String listRadioFavorites(Model model, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);
        List<RadioStation> radioFavorites = favoriteService.listRadioFavorites(user);
        model.addAttribute("favorites", radioFavorites);
        adicionarNomeUsuario(model, authentication);
        return "favorites";
    }

    @PostMapping ("/favorite/add")
    public String favorite(
        @RequestParam("stationUuid") String stationUuid,
        @RequestParam("stationName") String stationName,
        @RequestParam("stationUrl") String stationUrl,
        @RequestParam("stationFavicon") String stationFavicon,
        Authentication authentication,
        HttpServletRequest request) {

        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);

         try {
            favoriteService.addFavorite(user, stationUuid, stationName, stationUrl, stationFavicon);
        } catch (RuntimeException e) {
            // já é favorito — apenas ignora e segue o fluxo normalmente
        }
        return "redirect:" + request.getHeader("Referer");
    }
    
    @PostMapping ("/favorite/remove")
    public String unfavorite(
        @RequestParam("stationUuid") String stationUuid,
        Authentication authentication,
        HttpServletRequest request) {

        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
        return "redirect:/login";
        }

        favoriteService.removeFavorite(user, stationUuid);
        return "redirect:" + request.getHeader("Referer");
    }

    protected void adicionarNomeUsuario(Model model, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);
        model.addAttribute("username", user != null ? user.getUsername() : email);
    }
}
