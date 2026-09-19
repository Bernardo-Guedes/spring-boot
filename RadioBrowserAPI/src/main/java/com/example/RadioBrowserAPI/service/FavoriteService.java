package com.example.RadioBrowserAPI.service;

import com.example.RadioBrowserAPI.entities.Favorite;
import com.example.RadioBrowserAPI.entities.User;
import com.example.RadioBrowserAPI.model.RadioStation;
import com.example.RadioBrowserAPI.repository.FavoriteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Set;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public List<RadioStation> listRadioFavorites(User user) {
        List<Favorite> favorites = favoriteRepository.findByUser(user);
        return favorites.stream()
                .map(this::toRadioStation)
                .collect(Collectors.toList());

        // List<RadioStation> resultado = new ArrayList<>();
        // for (Favorite favorite : favorites) {
        //     resultado.add(toRadioStation(favorite));
        // }
        // return resultado;

    }

    private RadioStation toRadioStation(Favorite favorite) {
        RadioStation station = new RadioStation();
        station.setStationuuid(favorite.getStationUuid());
        station.setName(favorite.getStationName());
        station.setUrl(favorite.getStationUrl());
        station.setFavicon(favorite.getStationFavicon());
        return station;
    }

    public Favorite addFavorite(User user, String stationUuid, String stationName, String stationUrl, String stationFavicon) {
        if(favoriteRepository.existsByUserAndStationUuid(user, stationUuid)){
            throw new RuntimeException("Estação já favoritada!");
        }

        Favorite favorite = new Favorite(user, stationUuid, stationName, stationUrl, stationFavicon);
        return favoriteRepository.save(favorite);
    }

    @Transactional
    public void removeFavorite(User user, String stationUuid) {
        favoriteRepository.deleteByUserAndStationUuid(user, stationUuid);
    }

    public Set<String> getFavoriteStationUuids(User user) {
        return favoriteRepository.findByUser(user).stream()
                .map(Favorite::getStationUuid)
                .collect(Collectors.toSet());

        //List<Favorite> favorites = favoriteRepository.findByUser(user);
        // Set<String> favoriteStationUuids = new HashSet<>();
        // for (Favorite favorite : favorites) {
        //     favoriteStationUuids.add(favorite.getStationUuid());
        // }
        // return favoriteStationUuids;
    }
}