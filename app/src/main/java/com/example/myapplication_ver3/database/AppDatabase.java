package com.example.myapplication_ver3.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {OutlineTable.class,ModelTable.class},version=1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract OutlineTableDao outlineTableDao();
    public abstract ModelTableDao modelTableDao();
}

