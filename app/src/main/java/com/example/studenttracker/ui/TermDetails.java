package com.example.studenttracker.ui;

import com.example.studenttracker.utils.DateUtil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studenttracker.R;
import com.example.studenttracker.database.Repository;
import com.example.studenttracker.entities.Classes;
import com.example.studenttracker.entities.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TermDetails extends AppCompatActivity {

    EditText editTitle;

    String termTitle;

    int termID;

    Term term;

    Repository repo;

    // For date picker
    String termStartDate;
    String termEndDate;
    DatePickerDialog.OnDateSetListener termDates;
    DatePickerDialog.OnDateSetListener termDatesEnd;

    // For Start & End Dates
    final Calendar currentCalendar = Calendar.getInstance();
    final Calendar currentCalendar2 = Calendar.getInstance();

    Term currentTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        RecyclerView rV = findViewById(R.id.classRecyclerView);
        repo = new Repository(getApplication());
        final ClassAdapter classAdapter = new ClassAdapter(this);
        rV.setAdapter(classAdapter);
        rV.setLayoutManager(new LinearLayoutManager(this));

        editTitle = findViewById(R.id.editTermName);
        termTitle = getIntent().getStringExtra("title");
        editTitle.setText(termTitle);
        termID = getIntent().getIntExtra("id", -1);
        repo = new Repository(getApplication());
        termStartDate = getIntent().getStringExtra("start date");
        termEndDate = getIntent().getStringExtra("end date");

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

        classAdapter.setClasses(filteredClasses);

        startDate.setText(termID < 0 ? currentDate : termStartDate);
        endDate.setText(termID < 0 ? currentDate : termEndDate);

        startDate.setOnClickListener(view -> {
            String stringFromButton = endDate.getText().toString();
            try {
                Date date = DateUtil.convertStringToDate(stringFromButton);
                currentCalendar2.setTime(date);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            new DatePickerDialog(TermDetails.this, termDates, currentCalendar2.get(Calendar.YEAR), currentCalendar2.get(Calendar.MONTH), currentCalendar2.get(Calendar.DAY_OF_MONTH)).show();
        });

        endDate.setOnClickListener(view -> {
            String stringFromButton = endDate.getText().toString();
            try {
                Date date = DateUtil.convertStringToDate(stringFromButton);
                currentCalendar2.setTime(date);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            new DatePickerDialog(TermDetails.this, termDatesEnd, currentCalendar2.get(Calendar.YEAR), currentCalendar2.get(Calendar.MONTH), currentCalendar2.get(Calendar.DAY_OF_MONTH)).show();
        });

        termDates = (view, year, month, dayOfMonth) -> {
            currentCalendar.set(Calendar.YEAR, year);
            currentCalendar.set(Calendar.MONTH, month);
            currentCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            startDate.setText(DateUtil.convertDateToString(currentCalendar.getTime()));
        };

        termDatesEnd = (view, year, month, dayOfMonth) -> {
            currentCalendar2.set(Calendar.YEAR, year);
            currentCalendar2.set(Calendar.MONTH, month);
            currentCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            endDate.setText(DateUtil.convertDateToString(currentCalendar2.getTime()));
        };

        button.setOnClickListener(v -> {
            termStartDate = startDate.getText().toString();
            termEndDate = endDate.getText().toString();
            String title = editTitle.getText().toString();

            if (termID < 0) {
                term = new Term(0, title, termStartDate, termEndDate);
                repo.insert(term);
            } else {
                term = new Term(termID, title, termStartDate, termEndDate);
                repo.update(term);
            }

            Intent intent = new Intent(TermDetails.this, TermList.class);
            startActivity(intent);
        });

        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(TermDetails.this, ClassDetails.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView rV = findViewById(R.id.classRecyclerView);
        final ClassAdapter classAdapter = new ClassAdapter(this);
        rV.setAdapter(classAdapter);
        rV.setLayoutManager(new LinearLayoutManager(this));

        List<Classes> filteredClasses = new ArrayList<>();
        for (Classes c : repo.getAllClasses()) {
            if (c.getTermID() == termID) filteredClasses.add(c);
        }
        classAdapter.setClasses(filteredClasses);
    }

    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.term_details_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            this.finish();
            return true;
        } else if (itemId == R.id.deleteTermSelect) {
            for (Term t : repo.getAllTerms()) {
                if (t.getTermID() == termID) {
                    currentTerm = t;
                }
            }
            int associatedClasses = 0;
            for (Classes c : repo.getAllClasses()) {
                if (c.getTermID() == termID) {
                    associatedClasses++;
                }
            }
            if (associatedClasses == 0) {
                repo.delete(currentTerm);
            } else {
                Toast.makeText(getApplicationContext(), "Cannot delete a term with associated classes", Toast.LENGTH_LONG).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
