package com.example.techbuzzproject;

public class Event {
    private String name;
    private String location;
    private String date;
    private String time;
    private String price;

    // Default constructor for Firebase
    public Event() {}

    public Event(String name, String location, String date, String time, String price) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.time = time;
        this.price = price;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
