package com.example.myapplication_ver3.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public abstract class ModelTableDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(ModelTable modelTable);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(List<ModelTable> list);

    @Query("delete from ModelTable where modelNo = :no")
    abstract void delete(int no);

    @Query("select * from ModelTable")
    public abstract List<ModelTable> getAll();

    @Query("select * from ModelTable where modelNo = :no")
    public abstract List<ModelTable> getData(int no);

}

