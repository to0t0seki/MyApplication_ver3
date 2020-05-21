package com.example.myapplication_ver3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class OutlineTableCenterFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        LinearLayout baseLayout = new LinearLayout(getActivity());

        OutlineViewModel viewModel = new ViewModelProvider(getActivity(),new ViewModelProvider.NewInstanceFactory()).get(OutlineViewModel.class);
        viewModel.getLiveData(viewModel.getIntent().getStringExtra("modelNo"),getActivity(),20200519).observe(this, new Observer<List<OutlineTable>>() {
            @Override
            public void onChanged(List<OutlineTable> outlineTableList) {
                    baseLayout.removeAllViews();
                    baseLayout.addView(creatTable(outlineTableList));
                Button b = new Button(getActivity());
                b.setOnClickListener((v)->{
                    viewModel.getLiveData(viewModel.getIntent().getStringExtra("modelNo"),getActivity(),20200517);
                });
                baseLayout.addView(b);
            }
        });


        return baseLayout;//super.onCreateView(inflater, container, savedInstanceState);
    }

    public TableLayout creatTable(List<OutlineTable> outlineTableList){
        TableLayout tableLayout = new TableLayout(getActivity());

        String[] array = {"DATE","NO","BB","RB","TOTAL","LAST","DIFF"} ;
        TableRow tableRowHeader = new TableRow(getActivity());
        for(String s:array){
            TextView textView = new TextView(getActivity());
            textView.setText(s);
            textView.setBackgroundResource(R.drawable.border);
            textView.setTextSize(12);
            tableRowHeader.addView(textView);
        }
        tableLayout.addView(tableRowHeader);

        for(OutlineTable outlineTable:outlineTableList){
            TableRow tableRow = new TableRow(getActivity());
            Field[] outlineFields = outlineTable.getClass().getFields();
            List<TextView> textViews = new ArrayList<>();
            for(Field f:outlineFields){
                try {
                    String s = f.getName();
                    if(s=="modelNo"){
                        continue;
                    }
                    if(f.get(outlineTable) instanceof Integer){
                        TextView textView = new TextView(getActivity());
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
        return tableLayout;
    }
}
