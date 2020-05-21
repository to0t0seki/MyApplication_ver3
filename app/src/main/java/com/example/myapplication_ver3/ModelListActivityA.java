package com.example.myapplication_ver3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.myapplication_ver3.database.ModelTable;

import java.util.List;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ModelListActivityA extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machinelist);
        final ScrollView scrollView = findViewById(R.id.baselayout);
        final LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(linearLayout);


        final Handler handler = new Handler();
        AccessModelListThread accessModelListThread = new AccessModelListThread(this);
        accessModelListThread.setCallbak(new AccessModelListThread.Callback() {
            @Override
            public void callback(final List<ModelTable> list) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Button updateButton = new Button(ModelListActivityA.this);
                        updateButton.setText("更新");
                        updateButton.setOnClickListener((v)->{

                        });
                        linearLayout.addView(updateButton);
                        for (ModelTable modelTable : list) {
                            Button button = new Button(ModelListActivityA.this);
                            button.setText(modelTable.modelName);
                            button.setTag(modelTable.modelNo);
                            linearLayout.addView(button);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Button b = (Button) v;
                                    Intent intent = new Intent(getApplicationContext(), OutlineTableActivity.class);
                                    intent.putExtra("modelName", b.getText());
                                    intent.putExtra("modelNo", b.getTag().toString());
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                });
            }
        });
        accessModelListThread.start();
    }
}