package com.example.myapplication_ver3.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ModelTable {
        @PrimaryKey
        public int modelNo;
        public String modelName;
}
