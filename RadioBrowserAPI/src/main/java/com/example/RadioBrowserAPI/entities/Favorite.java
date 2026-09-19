package com.example.RadioBrowserAPI.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "favorites", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "station_uuid"})
})
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "station_uuid", nullable = false)
    private String stationUuid;

    @Column(name = "station_name")
    private String stationName;

    @Column(name = "station_url")
    private String stationUrl;

    @Column(name = "station_favicon")
    private String stationFavicon;

    // construtores, getters e setters
    public Favorite() {}

    public Favorite(User user, String stationUuid, String stationName, String stationUrl, String stationFavicon) {
        this.user = user;
        this.stationUuid = stationUuid;
        this.stationName = stationName;
        this.stationUrl = stationUrl;
        this.stationFavicon = stationFavicon;
    }

    public Long getId() {return id;}
    public User getUser() {    return user;}
    public void setUser(User user) {    this.user = user;}
    public String getStationUuid() {    return stationUuid;}
    public void setStationUuid(String stationUuid) {    this.stationUuid = stationUuid;}
    public String getStationName() {    return stationName;}
    public void setStationName(String stationName) {    this.stationName = stationName;}
    public String getStationUrl() {    return stationUrl;}
    public void setStationUrl(String stationUrl) {    this.stationUrl = stationUrl;}
    public String getStationFavicon() {    return stationFavicon;}
    public void setStationFavicon(String stationFavicon) {    this.stationFavicon = stationFavicon;}


}