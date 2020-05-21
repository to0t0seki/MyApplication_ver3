package com.example.myapplication_ver3;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelStoreOwner;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.example.myapplication_ver3.database.AppDatabase;
import com.example.myapplication_ver3.database.AppDatabaseSingleton;
import com.example.myapplication_ver3.database.OutlineTable;

import java.util.List;



public class OutlineViewModel extends ViewModel {
    private int i =5;

    public   MutableLiveData<String> updateTime = new MutableLiveData<>();

    private LiveData<List<OutlineTable>> liveData;
    private Handler handler;
    private Intent intent;
    private ViewModelStoreOwner viewModelStoreOwner;
    private int date;

    public LiveData<List<OutlineTable>> getLiveData(String modelNo,Context context,int date){

            AppDatabase appDatabase = AppDatabaseSingleton.getInstance(context);
            LiveData<List<OutlineTable>> l = appDatabase.outlineTableDao().queryModelNoDate(Integer.parseInt(modelNo),date);
            liveData = l;

        return liveData;
    }

    public LiveData<List<OutlineTable>> get(){
         liveData = new LiveData<List<OutlineTable>>() {
        };

        return liveData;
    }

    public void setHandler(Handler handler){
        this.handler = handler;
    }
    public Handler getHandler(){
        return handler;
    }

    public void setIntent(Intent intent){
        this.intent = intent;
    }
    public Intent getIntent(){
        return intent;
    }

    public void setDate(int date){
        this.date = date;
    }
    public int getDate(){ return date; }

}
