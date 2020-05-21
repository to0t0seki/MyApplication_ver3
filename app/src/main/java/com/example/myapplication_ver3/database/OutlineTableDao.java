package com.example.myapplication_ver3.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public abstract class OutlineTableDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(OutlineTable outlineTable);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertAll(List<OutlineTable> outlineTableList);

    @Query("delete from OutlineTable where serialNo = :no")
    abstract void delete(int no);

    @Query("select * from OutlineTable")
    public abstract List<OutlineTable> getAll();

    @Query("select * from OutlineTable where serialNo = :no")
    public abstract List<OutlineTable> querySerialNo(int no);


//    @Query("select * from OutlineTable where modelNo = :modelNo")
//    public abstract LiveData<List<OutlineTable>> queryModelNo(int modelNo);

    @Query("select * from OutlineTable where modelNo = :modelNo and date = :date" )
    public abstract LiveData<List<OutlineTable>> queryModelNoDate(int modelNo,int date);

}
