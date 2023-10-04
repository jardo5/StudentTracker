package com.example.studenttracker.ui;

import androidx.appcompat.app.AppCompatActivity;

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

public class TermList extends AppCompatActivity {
private Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        FloatingActionButton fab=findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view) {
                Intent intent=new Intent(TermList.this, TermDetails.class);
                startActivity(intent);
            }
        });


        System.out.println(getIntent().getStringExtra("test"));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_term_details, menu);
        return true;
    }

    public boolean onOptionsItemSelect(MenuItem item){
        if(item.getItemId()==R.id.mysample){
            repository = new Repository(getApplication());
            //Toast.makeText(TermList.this, "put in sample data", Toast.LENGTH_LONG).show();
            Term term = new Term(1, "First Term", "01/1/23", "12/31/23");
            repository.insert(term);
            Classes classes = new Classes(1, "Math Classes", "01/1/23", "12/31/23", "Almost Done", "Jane Doe", "1234567890", "test@test.org", 1, "Note Example" );
            repository.insert(classes.getClass());
            return true;
        }
        if(item.getItemId()==android.R.id.home){
            this.finish();
            return true;
        }
        return true;
    }
}