package com.example.studenttracker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.studenttracker.R;
import com.example.studenttracker.database.Repository;
import com.example.studenttracker.entities.Term;

public class MainActivity extends AppCompatActivity {

    public static int alertCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);

        // Test data array for terms
        Term[] terms = {
                new Term(1, "Summer Term", "04/31/23", "09/31/23"),
                new Term(2, "Winter Term", "11/31/24", "04/31/24"),
                new Term(3, "Fall Term", "08/31/24", "12/31/24"),
                new Term(4, "Spring Term", "01/31/25", "05/31/25")
        };

        Repository repo = new Repository(getApplication());

        for (Term term : terms) {
            repo.insert(term);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TermList.class);
                intent.putExtra("test", "this is a test");
                startActivity(intent);
            }

        });
    }
}