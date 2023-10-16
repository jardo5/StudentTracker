package com.example.studenttracker.ui;

import static com.example.studenttracker.utils.DateUtil.convertDateToString;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.studenttracker.R;
import com.example.studenttracker.database.Repository;
import com.example.studenttracker.entities.Assessment;
import com.example.studenttracker.utils.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AssessmentDetails extends AppCompatActivity {

    Assessment assessment;
    EditText editTitle;
    String assessmentName;
    String assessmentStartDate;
    String assessmentEndDate;
    String assessmentType;

    DatePickerDialog.OnDateSetListener assessmentDateStart;
    DatePickerDialog.OnDateSetListener assessmentDateEnd;

    final Calendar currentCalendar = Calendar.getInstance();
    final Calendar currentCalendar2 = Calendar.getInstance();

    Repository repo;
    int classID;
    int assessmentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asssessment_details);
        repo = new Repository(getApplication());
        Button saveButton = findViewById(R.id.assessmentSaveButton);
        Button startDateButton = findViewById(R.id.assessmentStartDateButton);
        Button endDateButton = findViewById(R.id.assessmentEndDateButton);

        editTitle = findViewById(R.id.assessmentTitle);
        assessmentName = getIntent().getStringExtra("assessmentName");
        editTitle.setText(assessmentName);
        assessmentStartDate = getIntent().getStringExtra("assessmentStartDate");
        assessmentEndDate = getIntent().getStringExtra("assessmentEndDate");
        assessmentType = getIntent().getStringExtra("assessmentSpinner");
        classID = getIntent().getIntExtra("classID", -1);
        assessmentID = getIntent().getIntExtra("assessmentID", -1);

        repo = new Repository(getApplication());
        startDateButton.setText(assessmentStartDate);
        endDateButton.setText(assessmentEndDate);

        String currentDate = DateUtil.getCurrentFormattedDate();

        if (assessmentID < 0) {
            startDateButton.setText(currentDate);
            endDateButton.setText(currentDate);
        } else {
            startDateButton.setText(assessmentStartDate);
            endDateButton.setText(assessmentEndDate);
        }

        startDateButton.setOnClickListener(view -> {
            try {
                Date date;
                if (assessmentStartDate != null && !assessmentStartDate.isEmpty()) {
                    date = DateUtil.convertStringToDate(assessmentStartDate);
                } else {
                    date = new Date(); // default to today fixes crash
                }
                currentCalendar.setTime(date);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            new DatePickerDialog(AssessmentDetails.this, assessmentDateStart, currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH), currentCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });


        endDateButton.setOnClickListener(view -> {
            try {
                Date date;
                if (assessmentEndDate != null && !assessmentEndDate.isEmpty()) {
                    date = DateUtil.convertStringToDate(assessmentEndDate);
                } else {
                    date = new Date();
                }
                currentCalendar2.setTime(date);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            new DatePickerDialog(AssessmentDetails.this, assessmentDateEnd, currentCalendar2.get(Calendar.YEAR), currentCalendar2.get(Calendar.MONTH), currentCalendar2.get(Calendar.DAY_OF_MONTH)).show();
        });


        assessmentDateStart = (datePicker, year, month, dayOfMonth) -> {
            currentCalendar.set(Calendar.YEAR, year);
            currentCalendar.set(Calendar.MONTH, month);
            currentCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            startDateButton.setText(convertDateToString(currentCalendar.getTime()));
        };

        assessmentDateEnd = (datePicker, year, month, dayOfMonth) -> {
            currentCalendar2.set(Calendar.YEAR, year);
            currentCalendar2.set(Calendar.MONTH, month);
            currentCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            endDateButton.setText(convertDateToString(currentCalendar2.getTime()));
        };

        Spinner spinner = findViewById(R.id.assessmentSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.assessment_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        if (assessmentType == null) {
            spinner.setSelection(0);
        } else if (assessmentType.equals("Objective")) {
            spinner.setSelection(0);
        } else if (assessmentType.equals("Performance")) {
            spinner.setSelection(1);
        } else {
            return;
        }


        saveButton.setOnClickListener(view -> {
            assessmentStartDate = startDateButton.getText().toString();
            assessmentEndDate = endDateButton.getText().toString();
            assessmentType = spinner.getSelectedItem().toString();
            if (assessmentID < 0) {
                assessment = new Assessment(0, editTitle.getText().toString(), assessmentType, assessmentStartDate, assessmentEndDate, classID);
                repo.insert(assessment);
            } else {
                assessment = new Assessment(assessmentID, editTitle.getText().toString(), assessmentType, assessmentStartDate, assessmentEndDate, classID);
                repo.update(assessment);
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.assessment_details_menu, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int itemID = item.getItemId();
        if(itemID == android.R.id.home){
            this.finish();
            return true;
        } else if (itemID == R.id.assessmentDelete){
            for (Assessment assessment : repo.getAllAssessments()) {
                if (assessment.getAssessmentID() == assessmentID) {
                    Assessment currentAssessment = assessment;
                    repo.delete(currentAssessment);
                }
            }
            return true;
        } else if (itemID == R.id.assessmentStart) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
            Date date = null;
            try {
                date = dateFormat.parse(assessmentStartDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long trigger = date.getTime();
            Intent intent = new Intent(AssessmentDetails.this, NotifcationReceiver.class);
            intent.putExtra("key", "Assessment " + assessmentName + " is starting today!");
            PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetails.this, ++MainActivity.alertCount, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            return true;
        } else if (itemID == R.id.assessmentEnd) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
            Date date = null;
            try {
                date = dateFormat.parse(assessmentEndDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long trigger = date.getTime();
            Intent intent = new Intent(AssessmentDetails.this, NotifcationReceiver.class);
            intent.putExtra("key", "Assessment " + assessmentName + " is ending today!");
            PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetails.this, ++MainActivity.alertCount, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}