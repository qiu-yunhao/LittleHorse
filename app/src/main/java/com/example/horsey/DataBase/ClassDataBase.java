package com.example.horsey.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Student.class}, version = 2,exportSchema = false)
public abstract class ClassDataBase extends RoomDatabase {
    public abstract StudentDao getStudentDao();
    private static ClassDataBase INSTANCE;

    public static ClassDataBase getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (ClassDataBase.class){
                INSTANCE = Room.databaseBuilder(context, ClassDataBase.class, "ClassDataBase")
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return INSTANCE;
    }
}
