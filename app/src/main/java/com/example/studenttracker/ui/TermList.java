package com.example.studenttracker.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
        //TODO: Remove this debug code
        Log.d("TermList", "Retrieved " + allTerms.size() + " terms.");
        termAdapter.setTerm(allTerms);


        //Advance to Term Details
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(TermList.this, TermDetails.class);
            startActivity(intent);
        });


        System.out.println(getIntent().getStringExtra("test"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.term_details_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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