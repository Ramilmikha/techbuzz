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
    private Button backButton; // Back button
    private EditText searchInput;
    private ImageView helpCenterIcon;
    private LinearLayout eventList;

    // Sample event data
    private Event[] events = {
            new Event("Cybersecurity", "Kasarani", "22/3/2025", "10:00 AM - 12:00 PM", "ksh:3000", R.drawable.cyber),
            new Event("Web Design", "Nyayo Stadium", "24/3/2025", "2:00 PM - 4:00 PM", "ksh:1000", R.drawable.design),
            new Event("AI Workshop", "Mombasa", "25/3/2025", "1:00 PM - 3:00 PM", "ksh:4000", R.drawable.ai)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home); // Ensure this layout file exists

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
        backButton.setOnClickListener(v -> startActivity(new Intent(Home.this, Home.class)));

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
            Log.d("EventName", "Checking event: " + event.getName());
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
        layoutParams.setMargins(16, 16, 16, 16);
        cardView.setLayoutParams(layoutParams);
        cardView.setCardBackgroundColor(getResources().getColor(R.color.white));
        cardView.setCardElevation(4);
        cardView.setPadding(16, 16, 16, 16); // Padding for CardView

        LinearLayout cardContent = new LinearLayout(this);
        cardContent.setOrientation(LinearLayout.VERTICAL);
        cardContent.setPadding(16, 16, 16, 16);

        // Create and configure ImageView
        ImageView eventImageView = new ImageView(this);
        LinearLayout.LayoutParams imageLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, // Set width to MATCH_PARENT
                200 // Height for the image
        );
        eventImageView.setLayoutParams(imageLayoutParams);
        eventImageView.setImageResource(event.getImageResource()); // Set image resource from event

        // Set adjustViewBounds and scaleType programmatically
        eventImageView.setAdjustViewBounds(true);
        eventImageView.setScaleType(ImageView.ScaleType.FIT_START);

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
        timeTextView.setText("Time: " + event.getTime());
        timeTextView.setTextColor(getResources().getColor(android.R.color.black));

        TextView priceTextView = new TextView(this);
        priceTextView.setText("Price: " + event.getPrice());
        priceTextView.setTextColor(getResources().getColor(android.R.color.black));

        // Create a Buy Ticket button
        Button buyTicketButton = new Button(this);
        buyTicketButton.setText("Buy Ticket");
        buyTicketButton.setBackgroundColor(getResources().getColor(R.color.blue));
        buyTicketButton.setTextColor(getResources().getColor(android.R.color.white));
        buyTicketButton.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        buyTicketButton.setGravity(View.TEXT_ALIGNMENT_CENTER); // Center align the button

        // Set the click listener for the Buy Ticket button
        buyTicketButton.setOnClickListener(v -> buyTicket(event));

        // Add elements to the card
        cardContent.addView(eventImageView); // Add the ImageView
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
        Intent intent = new Intent(Home.this, BuyTicket.class);
        String eventDetails = "Event: " + event.getName() +
                "\nLocation: " + event.getLocation() +
                "\nDate: " + event.getDate() +
                "\nTime: " + event.getTime() +
                "\nPrice: " + event.getPrice();
        intent.putExtra("EVENT_DETAILS", eventDetails);
        intent.putExtra("EVENT_IMAGE", event.getImageResource()); // Pass the image resource ID
        startActivity(intent);
    }

    // Event class to hold event details
    private static class Event {
        private String name;
        private String location;
        private String date;
        private String time;
        private String price;
        private int imageResource; // Resource ID for the event image

        public Event(String name, String location, String date, String time, String price, int imageResource) {
            this.name = name;
            this.location = location;
            this.date = date;
            this.time = time;
            this.price = price;
            this.imageResource = imageResource; // Assign image resource
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

        public int getImageResource() { // Getter for image resource
            return imageResource;
        }
    }
}