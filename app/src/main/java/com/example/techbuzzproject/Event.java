package com.example.techbuzzproject; // Ensure this matches your package structure

public class Event {
    private String name;
    private String location;
    private String date;
    private String startTime;
    private String endTime;
    private String price;
    private String imageUrl; // URL for the image

    // Default constructor required for calls to DataSnapshot.getValue(Event.class)
    public Event() { }

    // Parameterized constructor for creating an event
    public Event(String name, String location, String date, String startTime, String endTime, String price, String imageUrl) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.imageUrl = imageUrl; // Assign image URL
    }

    // Getters for accessing event properties
    public String getName() { return name; }
    public String getLocation() { return location; }
    public String getDate() { return date; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
    public String getPrice() { return price; }
    public String getImageUrl() { return imageUrl; } // Getter for image URL
}