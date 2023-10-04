package com.example.studenttracker.dao;

import com.example.studenttracker.entities.Classes;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface ClassesDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Classes classes);
    @Update
    void update(Classes classes);
    @Delete
    void delete(Classes classes);
    @Query("SELECT * FROM classes ORDER BY classID ASC")
    List<Classes> getAllClasses();

    @Query("SELECT * FROM classes WHERE termID= :termID ORDER BY classID ASC")
    List<Classes> getAllAssociatedClasses(int termID);
}

