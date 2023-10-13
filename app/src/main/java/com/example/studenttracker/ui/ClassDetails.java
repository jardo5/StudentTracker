package com.example.studenttracker.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.studenttracker.R;
import com.example.studenttracker.database.Repository;
import com.example.studenttracker.entities.Assessment;
import com.example.studenttracker.entities.Classes;
import com.example.studenttracker.utils.DateUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ClassDetails extends AppCompatActivity {

    EditText editTitle;
    String classTitle;
    String classStart;
    String classEnd;
    String classStatus;
    String professorName;
    String professorPhone;
    String professorEmail;
    EditText classNoteEdit;
    String classNote;

    int classID;
    int termID;
    Classes classes;
    Repository repo;
    EditText professorNameEdit;
    EditText professorPhoneEdit;
    EditText professorEmailEdit;

    DatePickerDialog.OnDateSetListener classDatePickerStart;
    DatePickerDialog.OnDateSetListener classDatePickerEnd;
    final Calendar currentCalendar = Calendar.getInstance();
    final Calendar currentCalendar2 = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_details);
        RecyclerView rV = findViewById(R.id.termRecyclerView);
        repo = new Repository(getApplication());
        final AssessmentAdapter aD = new AssessmentAdapter(this);
        rV.setAdapter(aD);
        rV.setLayoutManager(new LinearLayoutManager(this));

        Button saveButton = findViewById(R.id.classSaveButton);
        Button startButton = findViewById(R.id.startDateButton);
        Button endButton = findViewById(R.id.endDateButton);

        editTitle = findViewById(R.id.titletext);
        classTitle = getIntent().getStringExtra("classTitle");
        editTitle.setText(classTitle);

        professorNameEdit = findViewById(R.id.profNameEdit);
        professorName = getIntent().getStringExtra("classProfName");
        professorNameEdit.setText(professorName);

        professorEmailEdit = findViewById(R.id.profEmailEdit);
        professorEmail = getIntent().getStringExtra("classProfEmail");
        professorEmailEdit.setText(professorEmail);

        professorPhoneEdit = findViewById(R.id.profPhoneEdit);
        professorPhone = getIntent().getStringExtra("classProfPhone");
        professorPhoneEdit.setText(professorPhone);

        classNoteEdit = findViewById(R.id.notesEditText);
        classNote = getIntent().getStringExtra("notes");
        classNoteEdit.setText(classNote);

        classStart = getIntent().getStringExtra("classStart");
        classEnd = getIntent().getStringExtra("classEnd");
        classStatus = getIntent().getStringExtra("classStatus");

        classID = getIntent().getIntExtra("classID", -1);
        termID = getIntent().getIntExtra("termID", -1);
        Log.d("ClassDetails", "Received termID: " + termID);



        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(ClassDetails.this, AssessmentDetails.class);
            intent.putExtra("classID", classID);
            startActivity(intent);
        });

        repo = new Repository(getApplication());
        startButton.setText(classStart);
        endButton.setText(classEnd);
        String currentDate = DateUtil.getCurrentFormattedDate();

        List<Assessment> filteredAssessments = new ArrayList<>();
        for (Assessment a : repo.getAllAssessments()) {
            //TODO: Fix name discrepancy between classID and courseID, prefer classID
            if (a.getCourseID() == classID) filteredAssessments.add(a);
        }
        aD.setAssessments(filteredAssessments);


        if (classID < 0) {
            startButton.setText(currentDate);
            endButton.setText(currentDate);
        } else {
            startButton.setText(classStart);
            endButton.setText(classEnd);
        }
        startButton.setOnClickListener(view -> {
            String stringDate = startButton.getText().toString();
            try {
                Date date = DateUtil.convertStringToDate(stringDate);
                currentCalendar.setTime(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
            new DatePickerDialog(ClassDetails.this, classDatePickerStart, currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH), currentCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        endButton.setOnClickListener(view -> {
            String stringDate = endButton.getText().toString();
            try {
                Date date = DateUtil.convertStringToDate(stringDate);
                currentCalendar2.setTime(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
            new DatePickerDialog(ClassDetails.this, classDatePickerEnd, currentCalendar2.get(Calendar.YEAR), currentCalendar2.get(Calendar.MONTH), currentCalendar2.get(Calendar.DAY_OF_MONTH)).show();
        });

        classDatePickerStart = (view, year, month, dayOfMonth) -> {
            currentCalendar.set(Calendar.YEAR, year);
            currentCalendar.set(Calendar.MONTH, month);
            currentCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            startButton.setText(DateUtil.convertDateToString(currentCalendar.getTime()));
        };

        classDatePickerEnd = (view, year, month, dayOfMonth) -> {
            currentCalendar2.set(Calendar.YEAR, year);
            currentCalendar2.set(Calendar.MONTH, month);
            currentCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            endButton.setText(DateUtil.convertDateToString(currentCalendar2.getTime()));
        };

        //Spinner for class status
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.class_status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        int position;
        if (classStatus == null) {
            position = 1;
        } else {
            switch (classStatus) {
                case "In Progress":
                    position = 0;
                    break;
                case "Completed":
                    position = 1;
                    break;
                case "Dropped":
                    position = 2;
                    break;
                case "Plan to Take":
                    position = 3;
                    break;
                default:
                    position = 1;
                    break;
            }
        }
        spinner.setSelection(position);

        saveButton.setOnClickListener(view -> {
            String title = editTitle.getText().toString();
            String profName = professorNameEdit.getText().toString();
            String profPhone = professorPhoneEdit.getText().toString();
            String profEmail = professorEmailEdit.getText().toString();
            String note = classNoteEdit.getText().toString();

            classStart = startButton.getText().toString();
            classEnd = endButton.getText().toString();
            classStatus = spinner.getSelectedItem().toString();

            classes = new Classes(classID < 0 ? 0 : classID, title, classStart, classEnd, classStatus, profName, profPhone, profEmail, termID, note);
            Log.d("ClassDetails", "Using termID: " + termID + " when saving class");
            if (classID < 0) {
                repo.insert(classes);
            } else {
                repo.update(classes);
            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
        RecyclerView rV = findViewById(R.id.termRecyclerView);
        final AssessmentAdapter aD = new AssessmentAdapter(this);
        rV.setAdapter(aD);
        rV.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> assessmentsOnResume = new ArrayList<>();
        for (Assessment a : repo.getAllAssessments()) {
            if (a.getCourseID() == classID) assessmentsOnResume.add(a);
        }
        aD.setAssessments(assessmentsOnResume);
    }

    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_class_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int itemID = item.getItemId();

        if (itemID == android.R.id.home) {
            finish();
            return true;
        }

        if (itemID == R.id.classShareMenu) {
            shareClassNote();
            return true;
        }

        if (itemID == R.id.classDeleteMenu) {
            deleteClass();
            return true;
        }

        if (itemID == R.id.classStartMenu) {
            setNotification(classStart, "Class " + classTitle + " starts today!");
            return true;
        }

        if (itemID == R.id.classEndMenu) {
            setNotification(classEnd, "Class " + classTitle + " ends today!");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void shareClassNote() {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, classNoteEdit.getText().toString());
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, null));
    }

    private void deleteClass() {
        for (Classes classes : repo.getAllClasses()) {
            if (classes.getClassID() == classID) {
                repo.delete(classes);
                break;
            }
        }
    }

    private void setNotification(String classDate, String notificationText) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date;
        try {
            date = dateFormat.parse(classDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        long trigger = date.getTime();

        Intent intent = new Intent(ClassDetails.this, NotifcationReceiver.class);
        intent.putExtra("key", notificationText);
        PendingIntent sender = PendingIntent.getBroadcast(ClassDetails.this, ++MainActivity.alertCount, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
    }

}

