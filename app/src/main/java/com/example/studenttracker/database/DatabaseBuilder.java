package com.example.studenttracker.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.studenttracker.dao.AssessmentDAO;
import com.example.studenttracker.dao.ClassesDAO;
import com.example.studenttracker.dao.TermDAO;
import com.example.studenttracker.entities.Assessment;
import com.example.studenttracker.entities.Term;

@androidx.room.Database(entities = {Term.class, Class.class, Assessment.class}, version = 1, exportSchema = false)
public abstract class DatabaseBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();

    public abstract ClassesDAO classDAO();

    public abstract AssessmentDAO assessmentDAO();

    public static volatile DatabaseBuilder INSTANCE;

    static DatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseBuilder.class, "DatabaseBuilder.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
