package com.example.myapplication_ver3.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public abstract class OutlineTableDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(OutlineTable outlineTable);

    @Query("delete from OutlineTable where serialNo = :no")
    abstract void delete(int no);

    @Query("select * from OutlineTable")
    public abstract List<OutlineTable> getAll();

    @Query("select * from OutlineTable where serialNo = :no")
    public abstract List<OutlineTable> querySerialNo(int no);


    @Query("select * from OutlineTable where modelNo = :modelNo")
    public abstract List<OutlineTable> queryModelNo(int modelNo);

}
