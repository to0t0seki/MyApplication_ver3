package com.example.myapplication_ver3.database;

import android.content.Context;

import androidx.room.Room;

public class AppDatabaseSingleton {
    private  static AppDatabase instance = null;
    private AppDatabaseSingleton(){}

    public static AppDatabase getInstance(Context context){
        if(instance != null){
            return instance;
        }
        instance = Room.databaseBuilder(context,AppDatabase.class,"appdatabase2").build();
                //build(); allowMainThreadQueries()

        return instance;
    }
}
