package com.example.techbuzzproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log; // Import for logging
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView; // Ensure this is imported
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewEvents extends AppCompatActivity {

    private EditText searchBar;
    private Button searchButton; // Added search button
    private Button homeButton; // Added home button
    private Button backButton; // Added back button
    private LinearLayout eventList; // Container for event items
    private List<Event> events; // List of events

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_event); // Ensure this matches your layout file name

        searchBar = findViewById(R.id.search_bar);
        searchButton = findViewById(R.id.search_button); // Initialize the search button
        homeButton = findViewById(R.id.home_button); // Initialize the home button
        backButton = findViewById(R.id.back_button); // Initialize the back button
        eventList = findViewById(R.id.event_list); // Initialize the event list container

        // Initialize the events
        initializeEvents();

        // Set up search functionality
        setupSearchFunctionality();

        // Set click listeners for existing buttons
        setupButtonListeners();

        // Set listener for home button
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(ViewEvents.this, Home.class); // Adjust the target class as necessary
            startActivity(intent);
        });

        // Set listener for back button
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ViewEvents.this, Home.class); // Navigate back to Home
            startActivity(intent);
        });
    }

    private void initializeEvents() {
        events = new ArrayList<>(Arrays.asList(
                new Event("Tech Conference", "Nairobi", "30/4/2025", "9:00 AM", "5:00 PM", "ksh:1000", R.drawable.tech),
                new Event("AI Workshop", "Mombasa", "15/5/2025", "10:00 AM", "12:00 PM", "ksh:5000", R.drawable.work),
                new Event("Web Development Bootcamp", "Kisumu", "20/6/2025", "12:00 PM", "3:00 PM", "ksh:750", R.drawable.web),
                new Event("Mobile App Development", "Eldoret", "10/7/2025", "9:00 AM", "12:00 PM", "ksh:6000", R.drawable.mobile),
                new Event("Data Science Seminar", "Nyeri", "25/8/2025", "10:00 AM", "1:00 PM", "ksh:8000", R.drawable.data)
        ));
    }

    private void setupButtonListeners() {
        // Set up click listeners for the existing buttons
        setupButtonClickListener(R.id.buy_ticket_button_1, events.get(0));
        setupButtonClickListener(R.id.buy_ticket_button_2, events.get(1));
        setupButtonClickListener(R.id.buy_ticket_button_3, events.get(2));
        setupButtonClickListener(R.id.buy_ticket_button_4, events.get(3));
        setupButtonClickListener(R.id.buy_ticket_button_5, events.get(4));
    }

    private void setupButtonClickListener(int buttonId, Event event) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(v -> buyTicket(event));
    }

    private void setupSearchFunctionality() {
        searchButton.setOnClickListener(v -> {
            String query = searchBar.getText().toString().toLowerCase().trim();
            Log.d("SearchQuery", "Searching for: " + query); // Log the search query
            if (query.isEmpty()) {
                displayAllEvents(); // If query is empty, display all events
            } else {
                filterEvents(query);
            }
        });
    }

    private void displayAllEvents() {
        // Clear existing views
        eventList.removeAllViews();
        for (Event event : events) {
            createEventCard(event);
        }
    }

    private void filterEvents(String query) {
        // Clear the existing views
        eventList.removeAllViews();

        boolean foundEvent = false;
        String lowerCaseQuery = query.toLowerCase(); // Prepare for case-insensitive comparison

        // Debug log
        Log.d("SearchQuery", "Searching for: " + lowerCaseQuery);

        // Filter events based on the query
        for (Event event : events) {
            Log.d("EventName", "Checking event: " + event.getName()); // Log event names
            if (event.getName().toLowerCase().contains(lowerCaseQuery)) {
                foundEvent = true;
                createEventCard(event); // Create card for matching event
            }
        }

        // Show a message if no events are found
        if (!foundEvent) {
            TextView noResultsText = new TextView(this);
            noResultsText.setText("No events found.");
            noResultsText.setTextColor(getResources().getColor(android.R.color.black));
            eventList.addView(noResultsText);
        }
    }

    private void createEventCard(Event event) {
        // Create a CardView for the event dynamically
        CardView cardView = new CardView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(16, 16, 16, 16); // Add margin around the CardView
        cardView.setLayoutParams(layoutParams);
        cardView.setCardBackgroundColor(getResources().getColor(R.color.white));
        cardView.setCardElevation(4);
        cardView.setPadding(16, 16, 16, 16); // Padding for CardView

        LinearLayout cardContent = new LinearLayout(this);
        cardContent.setOrientation(LinearLayout.VERTICAL);
        cardContent.setPadding(16, 16, 16, 16);

        // Create and configure ImageView
        ImageView eventImageView = new ImageView(this);
        eventImageView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                200 // Set a fixed height for the image
        ));
        eventImageView.setImageResource(event.getImageResId()); // Set the image resource from the event
        eventImageView.setAdjustViewBounds(true); // Maintain aspect ratio

        // Create and configure TextViews
        TextView eventTextView = new TextView(this);
        eventTextView.setText("Event Name: " + event.getName());
        eventTextView.setTextColor(getResources().getColor(android.R.color.black));

        TextView locationTextView = new TextView(this);
        locationTextView.setText("Location: " + event.getLocation());
        locationTextView.setTextColor(getResources().getColor(android.R.color.black));

        TextView dateTextView = new TextView(this);
        dateTextView.setText("Date: " + event.getDate());
        dateTextView.setTextColor(getResources().getColor(android.R.color.black));

        TextView timeTextView = new TextView(this);
        timeTextView.setText("Time: " + event.getStartTime() + " - " + event.getEndTime());
        timeTextView.setTextColor(getResources().getColor(android.R.color.black));

        TextView priceTextView = new TextView(this);
        priceTextView.setText("Price: " + event.getPrice());
        priceTextView.setTextColor(getResources().getColor(android.R.color.black));

        // Create a Buy Ticket button
        Button buyTicketButton = new Button(this);
        buyTicketButton.setText("Buy Ticket");
        buyTicketButton.setBackgroundColor(getResources().getColor(R.color.blue));
        buyTicketButton.setTextColor(getResources().getColor(android.R.color.white));

        // Set the click listener for the Buy Ticket button
        buyTicketButton.setOnClickListener(v -> buyTicket(event));

        // Add elements to the card
        cardContent.addView(eventImageView); // Add the ImageView for the event
        cardContent.addView(eventTextView);
        cardContent.addView(locationTextView);
        cardContent.addView(dateTextView);
        cardContent.addView(timeTextView);
        cardContent.addView(priceTextView);
        cardContent.addView(buyTicketButton); // Add Buy Ticket button to card
        cardView.addView(cardContent); // Add card content to card view
        eventList.addView(cardView); // Add card view to event list
    }

    private void buyTicket(Event event) {
        Intent intent = new Intent(ViewEvents.this, BuyTicket.class);
        String eventDetails = "Event: " + event.getName() +
                "\nLocation: " + event.getLocation() +
                "\nDate: " + event.getDate() +
                "\nTime: " + event.getStartTime() + " - " + event.getEndTime() +
                "\nPrice: " + event.getPrice();
        intent.putExtra("EVENT_DETAILS", eventDetails);
        intent.putExtra("EVENT_IMAGE", event.getImageResId()); // Pass the image resource ID
        startActivity(intent);
    }

    // Event class to hold event details
    private static class Event {
        private String name;
        private String location;
        private String date;
        private String startTime;
        private String endTime;
        private String price;
        private int imageResId; // Added field for image resource ID

        public Event(String name, String location, String date, String startTime, String endTime, String price, int imageResId) {
            this.name = name;
            this.location = location;
            this.date = date;
            this.startTime = startTime;
            this.endTime = endTime;
            this.price = price;
            this.imageResId = imageResId; // Initialize image resource ID
        }

        // Getters
        public String getName() { return name; }
        public String getLocation() { return location; }
        public String getDate() { return date; }
        public String getStartTime() { return startTime; }
        public String getEndTime() { return endTime; }
        public String getPrice() { return price; }
        public int getImageResId() { return imageResId; } // Getter for image resource ID
    }
}