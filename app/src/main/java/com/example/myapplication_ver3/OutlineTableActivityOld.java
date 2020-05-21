package com.example.myapplication_ver3;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.myapplication_ver3.database.OutlineTable;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class OutlineTableActivityOld extends AppCompatActivity {

//    static String updateTime;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Intent intent = getIntent();
//        Handler handler = new Handler();
//
//        setTitle(intent.getStringExtra("modelName"));
//
//        setContentView(R.layout.activity_detaillist);
//        LinearLayout baseLayout = findViewById(R.id.listlayout);
//
//
//        LinearLayout linearLayout = new LinearLayout(this);
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//        baseLayout.addView(linearLayout);
//
//
//        OutlineViewModel viewModel = new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory()).get(OutlineViewModel.class);
//
//        viewModel.getLiveData(intent.getStringExtra("modelNo"),this).observe(this, new Observer<List<OutlineTable>>() {
//            @Override
//            public void onChanged(List<OutlineTable> outlineTableList) {
//                    linearLayout.removeAllViews();
//                    linearLayout.addView(creatHeader(intent,handler));
//                    linearLayout.addView(creatTable(outlineTableList));
//            }
//        });
//    }
//
//    public LinearLayout creatHeader(Intent intent,Handler handler){
//        LinearLayout linearLayout = new LinearLayout(this);
//
//        Button updateButton = new Button(this);
//        updateButton.setText("更新");
//        updateButton.setOnClickListener((V)->{
//            new GetOutlineTableToWebThread(intent.getStringExtra("modelNo"), OutlineTableActivityOld.this,handler).start();
//        });
//        TextView textView = new TextView(this);
//        textView.setText(updateTime!=null?"更新時間:"+ updateTime:"更新していません");
//        linearLayout.addView(updateButton);
//        linearLayout.addView(textView);
//
//        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyyMMdd");
//        Calendar calendar = Calendar.getInstance();
//        String[] time = new String[6];
//        for(int i=0;i<6;i++){
//            time[i] =simpleDateFormat.format(calendar.getTime());
//            calendar.add(Calendar.DATE, -1);
//        }
//
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(
//                this,
//                android.R.layout.simple_spinner_item,
//                time);
//        Spinner spinner = new Spinner(this);
//        spinner.setAdapter(adapter);
//        linearLayout.addView(spinner);
//        return linearLayout;
//    }
//
//
//    public TableLayout creatTable(List<OutlineTable> outlineTableList){
//        TableLayout tableLayout = new TableLayout(this);
//
//        String[] array = {"DATE","NO","BB","RB","TOTAL","LAST","DIFF"} ;
//        TableRow tableRowHeader = new TableRow(this);
//        for(String s:array){
//            TextView textView = new TextView(this);
//            textView.setText(s);
//            textView.setBackgroundResource(R.drawable.border);
//            textView.setTextSize(12);
//            tableRowHeader.addView(textView);
//        }
//        tableLayout.addView(tableRowHeader);
//
//        for(OutlineTable outlineTable:outlineTableList){
//            TableRow tableRow = new TableRow(OutlineTableActivityOld.this);
//            Field[] outlineFields = outlineTable.getClass().getFields();
//            List<TextView> textViews = new ArrayList<>();
//            for(Field f:outlineFields){
//                try {
//                    String s = f.getName();
//                    if(s=="modelNo"){
//                        continue;
//                    }
//                    if(f.get(outlineTable) instanceof Integer){
//                        TextView textView = new TextView(OutlineTableActivityOld.this);
//                        textView.setBackgroundResource(R.drawable.border);
//                        String ss = f.getName();
//                        switch (f.getName()){
//                            case "date":textView.setTag(1);
//                                int i = (Integer)f.get(outlineTable);
//                                textView.setText(String.valueOf((Integer)f.get(outlineTable)).substring(4,8));
//                                textView.setWidth(125);
//                                break;
//                            case "serialNo":textView.setTag(3);
//                                textView.setText(String.valueOf((Integer)f.get(outlineTable)));
//                                textView.setWidth(125);
//                                break;
//                            case "modelNo":textView.setTag(2);
//                                textView.setText(String.valueOf((Integer)f.get(outlineTable)));
//                                textView.setWidth(125);
//                                break;
//                            case "BB":textView.setTag(4);
//                                textView.setText(String.valueOf((Integer)f.get(outlineTable)));
//                                textView.setWidth(125);
//                                break;
//                            case "RB":textView.setTag(5);
//                                textView.setText(String.valueOf((Integer)f.get(outlineTable)));
//                                textView.setWidth(125);
//                                break;
//                            case "TOTAL":textView.setTag(6);
//                                textView.setText(String.valueOf((Integer)f.get(outlineTable)));
//                                textView.setWidth(125);
//                                break;
//                            case "LAST":textView.setTag(7);
//                                textView.setText(String.valueOf((Integer)f.get(outlineTable)));
//                                textView.setWidth(125);
//                                break;
//                            case "DIFF":textView.setTag(8);
//                                textView.setText(String.valueOf((Integer)f.get(outlineTable)));
//                                textView.setWidth(125);
//                                break;
//                        }
//                        textViews.add(textView);
//                    }else if(f.get(outlineTable) instanceof String){
//                        System.out.println("s");
//                    }
//                }catch (IllegalAccessException e){
//                    System.out.println("error");
//                }
//            }
//
//            List<TextView> l = textViews.stream()
//                    .sorted(Comparator.comparing(new Function<TextView, String>() {
//                        @Override
//                        public String apply(TextView textView) {
//                            return textView.getTag().toString();
//                        }
//                    })).collect(Collectors.toList());
//            for(TextView t:l){
//                tableRow.addView(t);
//            }
//            tableLayout.addView(tableRow);
//        }
//
//        return tableLayout;
//    }
}

