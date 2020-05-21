package com.example.myapplication_ver3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;


import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout baseLayout = findViewById(R.id.baselayout);
        Button triggerButton = new Button(this);
        //Toast.makeText(this , "更新しました。", Toast.LENGTH_LONG).show();
        triggerButton.setText("機種一覧");
        triggerButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ModelListActivity.class);
            startActivity(intent);
        });
        baseLayout.addView(triggerButton);

        ViewModelTest viewModel=  new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory()).get(ViewModelTest.class);
        viewModel.liveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                System.out.println(1);
            }
        });
        Button test = new Button(this);
        test.setOnClickListener((v)->{
            new Thread(()->{
                viewModel.liveData.postValue("s");
            }).start();

        });
        baseLayout.addView(test);

    }
}
