package com.example.techbuzzproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ViewEvents extends AppCompatActivity {

    private FirebaseFirestore db;
    private LinearLayout eventList;
    private EditText searchBar;
    private Button searchButton;
    private List<Event> events; // List of events fetched from Firestore

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_events); // Ensure this matches your layout file name

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize UI components
        eventList = findViewById(R.id.event_list);
        searchBar = findViewById(R.id.search_bar);
        searchButton = findViewById(R.id.search_button);

        // Fetch and display all events from Firestore
        fetchAllEvents();

        // Set up search functionality
        searchButton.setOnClickListener(v -> {
            String query = searchBar.getText().toString().trim().toLowerCase();
            if (query.isEmpty()) {
                fetchAllEvents(); // Show all events if search query is empty
            } else {
                searchEvents(query);
            }
        });
    }

    private void fetchAllEvents() {
        db.collection("events").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    events = new ArrayList<>();
                    eventList.removeAllViews(); // Clear the current list

                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        Event event = document.toObject(Event.class);
                        if (event != null) {
                            events.add(event);
                            createEventCard(event);
                        }
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to fetch events.", Toast.LENGTH_SHORT).show());
    }

    private void searchEvents(String query) {
        eventList.removeAllViews(); // Clear the current list

        for (Event event : events) {
            if (event.getName().toLowerCase().contains(query) || event.getLocation().toLowerCase().contains(query)) {
                createEventCard(event);
            }
        }
    }

    private void createEventCard(Event event) {
        // Dynamically create a TextView for each event
        TextView eventCard = new TextView(this);
        eventCard.setText("Event: " + event.getName() + "\nLocation: " + event.getLocation() +
                "\nDate: " + event.getDate() + "\nPrice: " + event.getPrice());
        eventCard.setPadding(16, 16, 16, 16);
        eventCard.setBackgroundResource(R.drawable.card_background); // Optional: Add a custom background

        // Add onClickListener to open a detailed view or ticket purchase
        eventCard.setOnClickListener(v -> {
            Intent intent = new Intent(ViewEvents.this, BuyTicket.class);
            intent.putExtra("EVENT_DETAILS", "Event: " + event.getName() +
                    "\nLocation: " + event.getLocation() +
                    "\nDate: " + event.getDate() +
                    "\nPrice: " + event.getPrice());
            startActivity(intent);
        });

        // Add event card to the list
        eventList.addView(eventCard);
    }
}
