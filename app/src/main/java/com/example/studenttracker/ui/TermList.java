package com.example.studenttracker.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.studenttracker.R;
import com.example.studenttracker.database.Repository;
import com.example.studenttracker.entities.Classes;
import com.example.studenttracker.entities.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TermList extends AppCompatActivity {
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        //Retrieve & Display Terms Using RecyclerView
        RecyclerView rV = findViewById(R.id.termrecyclerview); //rV = RecyclerView
        final TermAdapter termAdapter = new TermAdapter(this);
        rV.setAdapter(termAdapter);
        rV.setLayoutManager(new LinearLayoutManager(this));
        repository = new Repository(getApplication());
        List<Term> allTerms = repository.getAllTerms();
        termAdapter.setTerm(allTerms);


        //Advance to Term Details
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermList.this, TermDetails.class);
                startActivity(intent);
            }
        });


        System.out.println(getIntent().getStringExtra("test"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_term_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mysample) {
            repository = new Repository(getApplication());
            Term term = new Term(1, "First Term", "01/1/23", "12/31/23");
            repository.insert(term);
            Term term2 = new Term(2, "Second Term", "01/1/23", "12/31/23");
            repository.insert(term2);
            Term term3 = new Term(3, "Third Term", "01/1/23", "12/31/23");
            repository.insert(term3);
            Term term4 = new Term(4, "Fourth Term", "01/1/23", "12/31/23");
            repository.insert(term4);



            Classes classes = new Classes(1, "Math Classes", "01/1/23", "12/31/23", "Almost Done", "Jane Doe", "1234567890", "test@test.org", 1, "Note Example");
            repository.insert(classes);
            Classes classes2 = new Classes(2, "Science Classes", "01/2/23", "12/30/23", "Almost Done", "Jane Doe", "1234567890", "test@test.org", 2, "Note Example");
            repository.insert(classes2);
            Classes classes3 = new Classes(3, "English Classes", "01/3/23", "12/29/23", "Almost Done", "Jane Doe", "1234567890", "test@test.org" , 3, "Note Example");
            repository.insert(classes3);
            Classes classes4 = new Classes(4, "History Classes", "01/4/23", "12/28/23", "Almost Done", "Jane Doe", "1234567890", "test@test.org", 4, "Note Example");
            repository.insert(classes4);

            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Term> allTerm = repository.getAllTerms();
        RecyclerView rV = findViewById(R.id.termrecyclerview);
        final TermAdapter termAdapter = new TermAdapter(this);
        rV.setAdapter(termAdapter);
        rV.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerm(allTerm);
    }
}