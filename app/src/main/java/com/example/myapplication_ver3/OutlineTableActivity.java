package com.example.myapplication_ver3;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.myapplication_ver3.database.OutlineTable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class OutlineTableActivity extends AppCompatActivity {
    String hallNo;
    String[] contentNo = {"1/0","2/0","6/0","8/0","81/1"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setTitle(intent.getStringExtra("modelName"));
        this.hallNo = "00041817";
        dataUpdate(intent);
    }


    public void dataUpdate(final Intent intent) {
        final Handler handler = new Handler();
        AccessOutlineTableThread getDetailDataThread = new AccessOutlineTableThread(hallNo, intent.getStringExtra("modelNo"), contentNo, this);
        getDetailDataThread.setCallback(new AccessOutlineTableThread.Callback() {
            @Override
            public void callback(final List<OutlineTable> outlineTableList) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        setContentView(R.layout.activity_detaillist);
                        ViewGroup baseLayout = findViewById(R.id.listlayout);

                        TableLayout tableLayout = new TableLayout(OutlineTableActivity.this);

                        TextView headdate = new TextView(OutlineTableActivity.this);
                        headdate.setText("DATE");
                        TextView headN = new TextView(OutlineTableActivity.this);
                        headN.setText("NO");
                        TextView headBB = new TextView(OutlineTableActivity.this);
                        headBB.setText("BB");
                        TextView headRB = new TextView(OutlineTableActivity.this);
                        headRB.setText("RB");
                        TextView headtotal = new TextView(OutlineTableActivity.this);
                        headtotal.setText("TOTAL");
                        TextView headlast = new TextView(OutlineTableActivity.this);
                        headlast.setText("LAST");
                        TextView headdiff = new TextView(OutlineTableActivity.this);
                        headdiff.setText("DIFF");

                        TableRow headRow = new TableRow(OutlineTableActivity.this);

                        headdate.setBackgroundResource(R.drawable.background);
                        headN.setBackgroundResource(R.drawable.background);
                        headBB.setBackgroundResource(R.drawable.background);
                        headRB.setBackgroundResource(R.drawable.background);
                        headtotal.setBackgroundResource(R.drawable.background);
                        headlast.setBackgroundResource(R.drawable.background);
                        headdiff.setBackgroundResource(R.drawable.background);

                        headdate.setTextSize(12);
                        headN.setTextSize(12);
                        headBB.setTextSize(12);
                        headRB.setTextSize(12);
                        headtotal.setTextSize(12);
                        headlast.setTextSize(12);
                        headdiff.setTextSize(12);

                        headRow.addView(headdate);
                        headRow.addView(headN);
                        headRow.addView(headBB);
                        headRow.addView(headRB);
                        headRow.addView(headtotal);
                        headRow.addView(headlast);
                        headRow.addView(headdiff);
                        tableLayout.addView(headRow);



                        for(OutlineTable outlineTable:outlineTableList){
                            TableRow tableRow = new TableRow(OutlineTableActivity.this);
                            Field[] outlineFields = outlineTable.getClass().getFields();
                            List<TextView> textViews = new ArrayList<>();
                            for(Field f:outlineFields){
                                try {
                                    String s = f.getName();
                                    if(s=="modelNo"){
                                        continue;
                                    }
                                    if(f.get(outlineTable) instanceof Integer){
                                        TextView textView = new TextView(OutlineTableActivity.this);
                                        textView.setBackgroundResource(R.drawable.border);
                                        String ss = f.getName();
                                        switch (f.getName()){
                                            case "date":textView.setTag(1);
                                                int i = (Integer)f.get(outlineTable);
                                                textView.setText(String.valueOf((Integer)f.get(outlineTable)).substring(4,8));
                                                textView.setWidth(125);
                                                break;
                                            case "serialNo":textView.setTag(3);
                                                textView.setText(String.valueOf((Integer)f.get(outlineTable)));
                                                textView.setWidth(125);
                                                break;
                                            case "modelNo":textView.setTag(2);
                                                textView.setText(String.valueOf((Integer)f.get(outlineTable)));
                                                textView.setWidth(125);
                                                break;
                                            case "BB":textView.setTag(4);
                                                textView.setText(String.valueOf((Integer)f.get(outlineTable)));
                                                textView.setWidth(125);
                                                break;
                                            case "RB":textView.setTag(5);
                                                textView.setText(String.valueOf((Integer)f.get(outlineTable)));
                                                textView.setWidth(125);
                                                break;
                                            case "TOTAL":textView.setTag(6);
                                                textView.setText(String.valueOf((Integer)f.get(outlineTable)));
                                                textView.setWidth(125);
                                                break;
                                            case "LAST":textView.setTag(7);
                                                textView.setText(String.valueOf((Integer)f.get(outlineTable)));
                                                textView.setWidth(125);
                                                break;
                                            case "DIFF":textView.setTag(8);
                                                textView.setText(String.valueOf((Integer)f.get(outlineTable)));
                                                textView.setWidth(125);
                                                break;
                                        }
                                        textViews.add(textView);
                                    }else if(f.get(outlineTable) instanceof String){
                                        System.out.println("s");
                                    }
                                }catch (IllegalAccessException e){
                                    System.out.println("error");
                                }
                            }

                            List<TextView> l = textViews.stream()
                                    .sorted(Comparator.comparing(new Function<TextView, String>() {
                                        @Override
                                        public String apply(TextView textView) {
                                            return textView.getTag().toString();
                                        }
                                    })).collect(Collectors.toList());
                            for(TextView t:l){
                                tableRow.addView(t);
                            }
                            tableLayout.addView(tableRow);
                        }
                        baseLayout.addView(tableLayout);
                    }
                });
            }
        });
        getDetailDataThread.start();
    }
}

