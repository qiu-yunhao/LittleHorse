package com.example.horsey.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.horsey.DataBase.Student;
import com.example.horsey.DataBase.StudentRepository;

import java.util.List;

public class StudentViewModel extends AndroidViewModel {
    private final StudentRepository repository;
    public StudentViewModel(@NonNull Application application) {
        super(application);
        repository = new StudentRepository(application);
    }

    public void insertNewStudent(Student student) {
        repository.insertNewStudent(student);
    }

    public int updateStudent(Student student) {
        return repository.updateStudent(student);
    }

    public int deleteStudent(Student student) {
        return repository.deleteStudent(student);
    }

    public LiveData<List<Student>> getAllStudent() {
        return repository.getAllStudents();
    }

    public LiveData<Student> getStudentByName(String name) {
        return repository.getStudentByName(name);
    }
}
