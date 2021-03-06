package com.example.myapplication_ver3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.myapplication_ver3.database.ModelTable;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ModelListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machinelist);
        ScrollView scrollView = findViewById(R.id.baselayout);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(linearLayout);

        Handler handler = new Handler();
        GetModelListToDatabaseThread getModelListToDatabaseThread = new GetModelListToDatabaseThread(this);
        getModelListToDatabaseThread.setCallbak(new GetModelListToDatabaseThread.Callback() {
            @Override
            public void callback(final List<ModelTable> list) {
                handler.post(()-> {
                        Button updateButton = new Button(ModelListActivity.this);
                        updateButton.setText("更新");
                        updateButton.setOnClickListener((v)->{
                            GetModelListToWebThread getModelListToWebThread = new GetModelListToWebThread(ModelListActivity.this,handler);
                            getModelListToWebThread.start();
                        });
                        linearLayout.addView(updateButton);
                        for (ModelTable modelTable : list) {
                            Button button = new Button(ModelListActivity.this);
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
                });
            }
        });
        getModelListToDatabaseThread.start();
    }
}