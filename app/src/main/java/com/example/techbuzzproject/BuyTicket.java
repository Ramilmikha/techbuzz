package com.example.techbuzzproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView; // Import ImageView
import android.widget.TextView;
import android.widget.Toast; // Import Toast
import androidx.appcompat.app.AppCompatActivity;

public class BuyTicket extends AppCompatActivity {

    private EditText nameInput;
    private EditText emailInput;
    private EditText phoneInput;
    private EditText ticketNumberInput;
    private Button buyTicketButton;
    private Button homeButton; // Add home button
    private TextView eventDetailsTextView;
    private ImageView eventImageView; // Add ImageView for the event image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_ticket); // Ensure this matches your layout file name

        // Get the event details and image from the Intent
        String eventDetails = getIntent().getStringExtra("EVENT_DETAILS");
        int eventImageResource = getIntent().getIntExtra("EVENT_IMAGE", 0); // Retrieve the image resource ID

        // Initialize UI elements
        eventDetailsTextView = findViewById(R.id.event_details);
        nameInput = findViewById(R.id.name_input);
        emailInput = findViewById(R.id.email_input);
        phoneInput = findViewById(R.id.phone_input);
        ticketNumberInput = findViewById(R.id.ticket_number_input);
        buyTicketButton = findViewById(R.id.buy_ticket_button);
        homeButton = findViewById(R.id.home_button); // Initialize the Home button
        eventImageView = findViewById(R.id.event_image); // Initialize the ImageView

        // Display the event details
        if (eventDetails != null) {
            eventDetailsTextView.setText(eventDetails);
        } else {
            eventDetailsTextView.setText("No event details provided.");
        }

        // Set the image resource to the ImageView
        if (eventImageResource != 0) {
            eventImageView.setImageResource(eventImageResource);
        }

        // Set up the Buy Ticket button click listener
        buyTicketButton.setOnClickListener(v -> {
            // Handle ticket purchase logic here
            String name = nameInput.getText().toString();
            String email = emailInput.getText().toString();
            String phone = phoneInput.getText().toString();
            String ticketNumber = ticketNumberInput.getText().toString();

            // Validate inputs and process the purchase
            if (validateInputs(name, email, phone, ticketNumber)) {
                // Proceed with ticket purchase logic
                Intent intent = new Intent(BuyTicket.this, TicketConfirmation.class);
                intent.putExtra("EVENT_DETAILS", eventDetails); // Pass event details to confirmation page
                startActivity(intent);
                finish(); // Optionally finish this activity
            }
        });

        // Set up the Home button click listener
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(BuyTicket.this, Home.class);
            startActivity(intent);
            finish(); // Optionally finish this activity
        });
    }

    private boolean validateInputs(String name, String email, String phone, String ticketNumber) {
        // Basic validation for the inputs
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || ticketNumber.isEmpty()) {
            // Show error message
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}