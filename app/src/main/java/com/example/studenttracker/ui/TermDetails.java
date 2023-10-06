package com.example.studenttracker.ui;
import com.example.studenttracker.utils.DateUtil;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.studenttracker.R;
import com.example.studenttracker.database.Repository;
import com.example.studenttracker.entities.Classes;
import com.example.studenttracker.entities.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TermDetails extends AppCompatActivity {

    EditText termTitle;

    String termTitleString;

    int termID;

    Term term;

    Repository repo;

    // For date picker
    String termStartDate;
    String termEndDate;
    DatePickerDialog.OnDateSetListener termDate;
    DatePickerDialog.OnDateSetListener termDateEnd;

    // For Start & End Dates
    final Calendar currentCalendar = Calendar.getInstance();
    final Calendar currentCalendar2 = Calendar.getInstance();

    Term currentTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);


        termTitle = findViewById(R.id.editTermName);
        termTitleString = getIntent().getStringExtra("termTitle");
        termTitle.setText(termTitleString);
        termID = getIntent().getIntExtra("termID", -1);
        repo = new Repository(getApplication());
        termStartDate = getIntent().getStringExtra("termStartDate");
        termEndDate = getIntent().getStringExtra("termEndDate");

        // For Retrieving Start & End Dates
        Button button = findViewById(R.id.termSaveButton);
        Button startDate = findViewById(R.id.startDate);
        Button endDate = findViewById(R.id.endDate);

        startDate.setText(termStartDate);
        endDate.setText(termEndDate);
        // Grab Date
        String currentDate = DateUtil.getCurrentFormattedDate();


        List<Classes> filteredClasses = new ArrayList<>();
        for (Classes c : repo.getAllClasses()) {
            if (c.getTermID() == termID) filteredClasses.add(c);
        }










        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        // Lambda expression because I want to learn how to more effectively use them
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(TermDetails.this, ClassDetails.class);
            startActivity(intent);
        });
    }
}
