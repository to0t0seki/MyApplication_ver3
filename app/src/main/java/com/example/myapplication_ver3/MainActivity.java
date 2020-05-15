package com.example.myapplication_ver3;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout baseLayout = findViewById(R.id.baselayout);
        Button tiggerButton = new Button(this);
        tiggerButton.setText("機種一覧");
        tiggerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ModelListActivity.class);
                startActivity(intent);
            }
        });
        baseLayout.addView(tiggerButton);
    }
}
