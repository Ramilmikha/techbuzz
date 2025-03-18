package com.example.techbuzzproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TicketConfirmation extends AppCompatActivity {

    private TextView confirmationMessage;
    private TextView thankYouMessage;
    private Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_confirmation); // Ensure this matches your layout file name

        // Initialize UI elements
        confirmationMessage = findViewById(R.id.confirmation_message);
        thankYouMessage = findViewById(R.id.thank_you_message);
        homeButton = findViewById(R.id.home_button);

        // Set the confirmation message
        confirmationMessage.setText("🎉 Ticket Purchased Successfully!");

        // Set the thank you message
        thankYouMessage.setText("Thank you for choosing TECHBUZZ Events! Check your email for ticket details.");

        // Set up the Home button click listener
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(TicketConfirmation.this, Home.class);
            startActivity(intent);
            finish(); // Optionally finish this activity
        });
    }
}