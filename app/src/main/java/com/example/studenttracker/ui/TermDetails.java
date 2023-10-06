package com.example.studenttracker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.studenttracker.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TermDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        // Lambda expression because I want to learn how to more effectively use them
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(TermDetails.this, ClassDetails.class);
            startActivity(intent);
        });
    }
}
