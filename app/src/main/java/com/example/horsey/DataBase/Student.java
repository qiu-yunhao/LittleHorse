package com.example.horsey.DataBase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Student", indices = {@Index(value = "Name" , unique = true)})
public class Student {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "Name")
    private String name;

    @ColumnInfo(name = "Password")
    private String password;

    @ColumnInfo(name = "BirthDay")
    private String birthDay;

    //gender,1表示男,0表示女
    @ColumnInfo(name = "Gender")
    private int gender = -1;

    public Student(String name, String password, int gender, String birthDay) {
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.birthDay = birthDay;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getGender() {
        return gender;
    }
}
