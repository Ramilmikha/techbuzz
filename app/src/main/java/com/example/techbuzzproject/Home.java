package com.example.techbuzzproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log; // Import for logging
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    private Button logInButton;
    private Button signUpButton;
    private Button viewEventsButton;
    private Button searchButton;
    private Button backButton; // Added back button
    private EditText searchInput;
    private ImageView helpCenterIcon;
    private LinearLayout eventList;

    // Sample event data (can be moved to a data source)
    private Event[] events = {
            new Event("Cybersecurity", "Kasarani", "22/3/2025", "10:00 AM - 12:00 PM", "ksh:1000"),
            new Event("Web Design", "Nyayo Stadium", "24/3/2025", "2:00 PM - 4:00 PM", "ksh :2000"),
            new Event("AI Workshop", "Mombasa", "25/3/2025", "1:00 PM - 3:00 PM", "ksh:3000")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        // Initialize UI components
        logInButton = findViewById(R.id.buttonlog_in);
        signUpButton = findViewById(R.id.sign_up_button);
        viewEventsButton = findViewById(R.id.view_eventsbutton);
        searchInput = findViewById(R.id.search_input);
        helpCenterIcon = findViewById(R.id.help_center_icon);
        eventList = findViewById(R.id.event_list);
        searchButton = findViewById(R.id.search_button);
        backButton = findViewById(R.id.back_button); // Initialize back button

        // Set click listeners for buttons
        logInButton.setOnClickListener(v -> startActivity(new Intent(Home.this, LogIn.class)));
        signUpButton.setOnClickListener(v -> startActivity(new Intent(Home.this, RegisterUser.class)));
        viewEventsButton.setOnClickListener(v -> startActivity(new Intent(Home.this, ViewEvents.class)));
        helpCenterIcon.setOnClickListener(v -> startActivity(new Intent(Home.this, Help.class)));

        // Set click listener for the back button
        backButton.setOnClickListener(v -> {
            // Return to the home activity
            displayAllEvents(); // Make sure to display all events
        });

        // Display all events initially
        displayAllEvents();

        // Set click listener for the search button
        searchButton.setOnClickListener(v -> filterEvents(searchInput.getText().toString().trim()));
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
        androidx.cardview.widget.CardView cardView = new androidx.cardview.widget.CardView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(16, 16, 16, 16); // Add margin around the CardView
        cardView.setLayoutParams(layoutParams);
        cardView.setCardBackgroundColor(getResources().getColor(R.color.blue));
        cardView.setCardElevation(4);
        cardView.setPadding(16, 16, 16, 16); // Padding for CardView

        LinearLayout cardContent = new LinearLayout(this);
        cardContent.setOrientation(LinearLayout.VERTICAL);
        cardContent.setPadding(16, 16, 16, 16);

        // Create and configure TextViews
        TextView eventTextView = new TextView(this);
        eventTextView.setText("Event Name: " + event.getName());
        eventTextView.setTextColor(getResources().getColor(android.R.color.white));

        TextView locationTextView = new TextView(this);
        locationTextView.setText("Location: " + event.getLocation());
        locationTextView.setTextColor(getResources().getColor(android.R.color.white));

        TextView dateTextView = new TextView(this);
        dateTextView.setText("Date: " + event.getDate());
        dateTextView.setTextColor(getResources().getColor(android.R.color.white));

        TextView timeTextView = new TextView(this);
        timeTextView.setText("Time: " + event.getTime());
        timeTextView.setTextColor(getResources().getColor(android.R.color.white));

        TextView priceTextView = new TextView(this);
        priceTextView.setText("Price: " + event.getPrice());
        priceTextView.setTextColor(getResources().getColor(android.R.color.white));

        // Create a Buy Ticket button
        Button buyTicketButton = new Button(this);
        buyTicketButton.setText("Buy Ticket");
        buyTicketButton.setBackgroundColor(getResources().getColor(R.color.blue));
        buyTicketButton.setTextColor(getResources().getColor(android.R.color.white));

        // Set the click listener for the Buy Ticket button
        buyTicketButton.setOnClickListener(v -> buyTicket(event));

        // Add elements to the card
        cardContent.addView(eventTextView);
        cardContent.addView(locationTextView);
        cardContent.addView(dateTextView);
        cardContent.addView(timeTextView);
        cardContent.addView(priceTextView);

        // Add space above the button
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        buttonParams.setMargins(0, 16, 0, 0); // Margin above the button
        buyTicketButton.setLayoutParams(buttonParams);

        cardContent.addView(buyTicketButton); // Add Buy Ticket button to card
        cardView.addView(cardContent); // Add card content to card view
        eventList.addView(cardView); // Add card view to event list
    }

    private void buyTicket(Event event) {
        Intent intent = new Intent(Home.this, BuyTicket.class);
        String eventDetails = "Event: " + event.getName() +
                "\nLocation: " + event.getLocation() +
                "\nDate: " + event.getDate() +
                "\nTime: " + event.getTime() +
                "\nPrice: " + event.getPrice();
        intent.putExtra("EVENT_DETAILS", eventDetails);
        startActivity(intent);
    }

    // Event class to hold event details
    private static class Event {
        private String name;
        private String location;
        private String date;
        private String time;
        private String price;

        public Event(String name, String location, String date, String time, String price) {
            this.name = name;
            this.location = location;
            this.date = date;
            this.time = time;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public String getLocation() {
            return location;
        }

        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }

        public String getPrice() {
            return price;
        }
    }
}