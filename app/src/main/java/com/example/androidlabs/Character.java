package com.example.androidlabs;

public class Character {
    private String name;
    private String height;
    private String mass;
    private String birthYear;

    public Character(String name, String height, String mass, String birthYear) {
        this.name = name;
        this.height = height;
        this.mass = mass;
        this.birthYear = birthYear;
    }

    public String getName() { return name; }
    public String getHeight() { return height; }
    public String getMass() { return mass; }
    public String getBirthYear() { return birthYear; }
}

