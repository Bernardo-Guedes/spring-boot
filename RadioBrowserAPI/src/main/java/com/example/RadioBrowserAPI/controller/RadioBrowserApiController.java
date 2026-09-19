package com.example.RadioBrowserAPI.controller;

import com.example.RadioBrowserAPI.model.RadioStation;
import com.example.RadioBrowserAPI.service.RadioBrowserApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class RadioBrowserApiController {

    @Autowired
    private RadioBrowserApiService radioBrowserApiService;

    @GetMapping("/")
    public String listRadioStations(@RequestParam(name = "state", defaultValue = "Minas Gerais") String state, @RequestParam(name = "city", defaultValue = "Belo Horizonte") String city, Model model) {
        List<RadioStation> radioStations = radioBrowserApiService.listRadioStations(state, city);
        model.addAttribute("stations", radioStations);
        model.addAttribute("city", city);
        model.addAttribute("state", state);
        return "home";
    }
}