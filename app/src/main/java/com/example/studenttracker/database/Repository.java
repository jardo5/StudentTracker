package com.example.studenttracker.database;

import android.app.Application;
import android.util.Log;

import com.example.studenttracker.dao.AssessmentDAO;
import com.example.studenttracker.dao.ClassesDAO;
import com.example.studenttracker.dao.TermDAO;
import com.example.studenttracker.entities.Assessment;
import com.example.studenttracker.entities.Classes;
import com.example.studenttracker.entities.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private ClassesDAO mClassesDAO;
    private TermDAO mTermDAO;
    private AssessmentDAO mAssessmentDAO;

    private List<Classes> mAllClasses;
    private List<Term> mAllTerms;
    private List<Assessment> mAllAssessments;

    private List<Classes> mGetAllAssociatedClasses;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        DatabaseBuilder db = DatabaseBuilder.getDatabase(application);
        mClassesDAO = db.classDAO();
        mTermDAO = db.termDAO();
        mAssessmentDAO = db.assessmentDAO();
    }

    //Associated Classes
    public List<Classes> getAllAssociatedClasses(int termID) {
        databaseExecutor.execute(() -> {
            mGetAllAssociatedClasses = mClassesDAO.getAllAssociatedClasses(termID);
        });
        return mGetAllAssociatedClasses;
    }


    public List<Term> getAllTerms() {
        databaseExecutor.execute(() -> {
            mAllTerms = mTermDAO.getAllTerms();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllTerms;
    }

    public List<Classes> getAllClasses() {
        databaseExecutor.execute(() -> {
            mAllClasses = mClassesDAO.getAllClasses();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllClasses;
    }

    public List<Assessment> getAllAssessments() {
        databaseExecutor.execute(() -> {
            mAllAssessments = mAssessmentDAO.getAllAssessments();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllAssessments;
    }

    public void insert(Term term) {
        databaseExecutor.execute(() -> {
            mTermDAO.insert(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(Classes classes) {
        databaseExecutor.execute(() -> {
            Log.d("Repository", "Inserting class with termID: " + classes.getTermID());
            mClassesDAO.insert(classes);;

        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(Assessment assessments) {
        databaseExecutor.execute(() -> {
            mAssessmentDAO.insert(assessments);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Term term) {
        databaseExecutor.execute(() -> {
            mTermDAO.update(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Classes classes) {
        databaseExecutor.execute(() -> {
            Log.d("Repository", "Updating class with termID: " + classes.getTermID());
            mClassesDAO.update(classes);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Assessment assessments) {
        databaseExecutor.execute(() -> {
            mAssessmentDAO.update(assessments);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Term term) {
        databaseExecutor.execute(() -> {
            mTermDAO.delete(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Classes classes) {
        databaseExecutor.execute(() -> {
            mClassesDAO.delete(classes);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Assessment assessments) {
        databaseExecutor.execute(() -> {
            mAssessmentDAO.delete(assessments);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
