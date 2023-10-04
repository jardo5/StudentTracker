package com.example.studenttracker.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.studenttracker.entities.Classes;

import java.util.List;

@Dao
public interface ClassesDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Class classes);
    @Update
    void update(Class classes);
    @Delete
    void delete(Class classes);
    @Query("SELECT * FROM classes ORDER BY classID ASC")
    List<Classes> getAllClasses();

    @Query("SELECT * FROM classes WHERE termID = :termID")
    List<Class> getAllAssociatedClasses(int termID);
}

