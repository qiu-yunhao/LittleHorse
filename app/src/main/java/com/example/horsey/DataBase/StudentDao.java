package com.example.horsey.DataBase;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StudentDao {


    @Insert
    void insert(Student student);

    @Query("SELECT * from STUDENT ")
    LiveData<List<Student>> getAllStudents();

    @Update
    int update(Student student);

    @Delete
    int delete(Student student);

    @Query("SELECT * from Student where Name = :name")
    LiveData<Student> getStudentByName(String name);
}
