package com.example.comeonBusanView.model;

public class DangerZone {
    private String name;
    private double lat;
    private double lon;
    private double radius;

    public DangerZone(String name, double lat, double lon, double radius) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.radius = radius;
    }

    // Getters
    public String getName() { return name; }
    public double getLat() { return lat; }
    public double getLon() { return lon; }
    public double getRadius() { return radius; }
}