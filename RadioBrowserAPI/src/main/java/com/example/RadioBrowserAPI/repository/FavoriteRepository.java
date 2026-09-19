package com.example.RadioBrowserAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.RadioBrowserAPI.entities.Favorite;
import com.example.RadioBrowserAPI.entities.User;
import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUser(User user);
    boolean existsByUserAndStationUuid(User user, String stationUuid);
    void deleteByUserAndStationUuid(User user, String stationUuid);
}
