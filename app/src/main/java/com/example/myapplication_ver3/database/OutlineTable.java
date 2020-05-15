package com.example.myapplication_ver3.database;

import androidx.room.Entity;

@Entity(primaryKeys = {"serialNo", "date"})
public class OutlineTable {
    public int date;
    public int serialNo;
    public int modelNo;
    public int BB;
    public int RB;
    public int TOTAL;
    public int LAST;
    public int DIFF;


    public OutlineTable(int date,int serialNo,int modelNo,int BB,int RB,int TOTAL,int LAST,int DIFF){
        this.date = date;
        this.serialNo = serialNo;
        this.modelNo = modelNo;
        this.BB  =BB;
        this.RB = RB;
        this.TOTAL = TOTAL;
        this.LAST = LAST;
        this.DIFF = DIFF;
    }
}
