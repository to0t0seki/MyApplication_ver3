package com.example.myapplication_ver3;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

public class OutlineTableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        Handler handler = new Handler();

        setTitle(intent.getStringExtra("modelName"));

        setContentView(R.layout.activity_detaillist);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.listlayout,new OutlineTableTopFragment());
        fragmentTransaction.add(R.id.listlayout,new OutlineTableCenterFragment());
        fragmentTransaction.commit();


        OutlineViewModel viewModel = new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory()).get(OutlineViewModel.class);
        viewModel.setHandler(handler);
        viewModel.setIntent(intent);
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        viewModel.setDate(Integer.parseInt(simpleDateFormat.format(calendar.getTime())));
    }
}

