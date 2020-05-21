package com.example.myapplication_ver3;

import android.content.Context;

import com.example.myapplication_ver3.database.AppDatabase;
import com.example.myapplication_ver3.database.AppDatabaseSingleton;
import com.example.myapplication_ver3.database.ModelTable;

import java.util.List;

public class GetModelListToDatabaseThread extends Thread {
    Context context;
    Callback callback;


    GetModelListToDatabaseThread(Context context){
        this.context = context;
    }

    @Override
    public void run() {
        AppDatabase appDatabase = AppDatabaseSingleton.getInstance(context);
        List<ModelTable> list = appDatabase.modelTableDao().getAll();
        callback.callback(list);
    }

    interface Callback{
        void callback(List<ModelTable> list);
    }

    public void setCallbak(Callback callback){
        this.callback = callback;
    }
}
