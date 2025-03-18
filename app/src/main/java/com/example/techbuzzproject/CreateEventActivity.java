package com.example.techbuzzproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CreateEventActivity extends AppCompatActivity {

    private EditText eventName, eventLocation, eventDate, eventStartTime, eventEndTime, eventPrice;
    private Button createEventButton, selectImageButton, deleteImageButton;
    private ImageView eventImageView;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private Uri imageUri; // URI of the selected image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        // Initialize UI components
        eventName = findViewById(R.id.event_name);
        eventLocation = findViewById(R.id.event_location);
        eventDate = findViewById(R.id.event_date);
        eventStartTime = findViewById(R.id.event_start_time);
        eventEndTime = findViewById(R.id.event_end_time);
        eventPrice = findViewById(R.id.event_price);
        createEventButton = findViewById(R.id.create_event_button);
        selectImageButton = findViewById(R.id.select_image_button);
        deleteImageButton = findViewById(R.id.delete_image_button);
        eventImageView = findViewById(R.id.event_image_view);

        // Initialize Firebase Database and Storage
        databaseReference = FirebaseDatabase.getInstance().getReference("events");
        storageReference = FirebaseStorage.getInstance().getReference("event_images");

        // Set button click listeners
        selectImageButton.setOnClickListener(v -> selectImage());
        deleteImageButton.setOnClickListener(v -> deleteImage());
        createEventButton.setOnClickListener(v -> createEvent());
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            eventImageView.setImageURI(imageUri);
            eventImageView.setVisibility(View.VISIBLE); // Show the image view
            deleteImageButton.setVisibility(View.VISIBLE); // Show delete button
        }
    }

    private void deleteImage() {
        imageUri = null; // Clear the image URI
        eventImageView.setImageURI(null); // Clear the image view
        eventImageView.setVisibility(View.GONE); // Hide the image view
        deleteImageButton.setVisibility(View.GONE); // Hide delete button
    }

    private void createEvent() {
        String name = eventName.getText().toString().trim();
        String location = eventLocation.getText().toString().trim();
        String date = eventDate.getText().toString().trim();
        String startTime = eventStartTime.getText().toString().trim();
        String endTime = eventEndTime.getText().toString().trim();
        String price = eventPrice.getText().toString().trim();

        // Validate input fields
        if (name.isEmpty() || location.isEmpty() || date.isEmpty() || startTime.isEmpty() || endTime.isEmpty() || price.isEmpty() || imageUri == null) {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        String eventId = databaseReference.push().getKey(); // Generate a unique ID for the event

        // Upload the image to Firebase Storage
        StorageReference fileReference = storageReference.child(eventId + ".jpg");
        UploadTask uploadTask = fileReference.putFile(imageUri);

        uploadTask.addOnSuccessListener(taskSnapshot -> fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
            String imageUrl = uri.toString();
            Event event = new Event(name, location, date, startTime, endTime, price, imageUrl);

            if (eventId != null) { // Ensure the ID is not null
                databaseReference.child(eventId).setValue(event)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(CreateEventActivity.this, "Event created successfully", Toast.LENGTH_SHORT).show();
                                finish(); // Close activity and return to previous
                            } else {
                                Toast.makeText(CreateEventActivity.this, "Failed to create event", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        })).addOnFailureListener(e -> Toast.makeText(CreateEventActivity.this, "Image upload failed", Toast.LENGTH_SHORT).show());
    }
}