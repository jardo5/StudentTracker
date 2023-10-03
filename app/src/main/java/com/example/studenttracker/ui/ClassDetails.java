package com.example.studenttracker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.studenttracker.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ClassDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_details);
        FloatingActionButton fab=findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view) {
                Intent intent=new Intent(ClassDetails.this, TermDetails.class);
                startActivity(intent);
            }
        });
    }
}