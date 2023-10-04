package com.example.studenttracker.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "classes")
public class Classes {
    @PrimaryKey(autoGenerate = true)
    private int classID;
    private String classTitle;
    private String classStart;
    private String classEnd;
    private String classProgress;
    private String classInstructorName;
    private String classInstructorPhone;
    private String classInstructorEmail;
    private int termID;

    public String getClassNotes() {
        return classNotes;
    }

    public void setClassNotes(String classNotes) {
        this.classNotes = classNotes;
    }

    private String classNotes;

    public Classes(int classID, String classTitle, String classStart, String classEnd, String classProgress, String classInstructorName, String classInstructorPhone, String classInstructorEmail, int termID, String classNotes) {
        this.classID = classID;
        this.classTitle = classTitle;
        this.classStart = classStart;
        this.classEnd = classEnd;
        this.classProgress = classProgress;
        this.classInstructorName = classInstructorName;
        this.classInstructorPhone = classInstructorPhone;
        this.classInstructorEmail = classInstructorEmail;
        this.termID = termID;
        this.classNotes = classNotes;
    }

    public Classes() {
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public String getClassTitle() {
        return classTitle;
    }

    public void setClassTitle(String classTitle) {
        this.classTitle = classTitle;
    }

    public String getClassStart() {
        return classStart;
    }

    public void setClassStart(String classStart) {
        this.classStart = classStart;
    }

    public String getClassEnd() {
        return classEnd;
    }

    public void setClassEnd(String classEnd) {
        this.classEnd = classEnd;
    }

    public String getClassProgress() {
        return classProgress;
    }

    public void setClassProgress(String classProgress) {
        this.classProgress = classProgress;
    }

    public String getClassInstructorName() {
        return classInstructorName;
    }

    public void setClassInstructorName(String classInstructorName) {
        this.classInstructorName = classInstructorName;
    }

    public String getClassInstructorPhone() {
        return classInstructorPhone;
    }

    public void setClassInstructorPhone(String classInstructorPhone) {
        this.classInstructorPhone = classInstructorPhone;
    }

    public String getClassInstructorEmail() {
        return classInstructorEmail;
    }

    public void setClassInstructorEmail(String classInstructorEmail) {
        this.classInstructorEmail = classInstructorEmail;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }
}
