package com.example.horsey.DataBase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class StudentRepository {
    private final StudentDao dao;

    public StudentRepository(Context context){
        ClassDataBase dataBase = ClassDataBase.getINSTANCE(context);
        dao = dataBase.getStudentDao();
    }

    public LiveData<List<Student>> getAllStudents() {
        return dao.getAllStudents();
    }

    public void insertNewStudent(Student student) {
        new InsertStudent(dao).execute(student);
    }

    public int deleteStudent(Student student) {
        return dao.delete(student);
    }

    public int updateStudent(Student student) {
        return dao.update(student);
    }

    public LiveData<Student> getStudentByName(String name) {
        return dao.getStudentByName(name);
    }


    static class InsertStudent extends AsyncTask<Student,Void,Void> {

        private final StudentDao dao;

        InsertStudent(StudentDao studentDao) {
            this.dao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            dao.insert(students[0]);
            return null;
        }
    }
}


