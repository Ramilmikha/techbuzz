package com.example.techbuzzproject; // Replace with your package name

import android.content.Intent;
import android.net.Uri; // Import Uri for handling email links
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView; // Import TextView
import androidx.appcompat.app.AppCompatActivity;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help); // Set the content view to help.xml

        // Back button functionality
        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the MainActivity (home page)
                Intent intent = new Intent(Help.this, Home.class);
                startActivity(intent); // Start the main activity
                finish(); // Optional: Call finish to remove HelpActivity from the back stack
            }
        });

        // Set up the email click listener
        TextView emailTextView = findViewById(R.id.email_text_view);
        emailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:techbuzzagent@gmail.com")); // Only email apps should handle this
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });
    }
}