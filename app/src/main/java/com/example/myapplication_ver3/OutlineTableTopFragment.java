package com.example.myapplication_ver3;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myapplication_ver3.database.OutlineTable;

import java.text.SimpleDateFormat;
import java.util.Calendar;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class OutlineTableTopFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout linearLayout = new LinearLayout(getActivity());
        OutlineViewModel viewModel = new ViewModelProvider(getActivity(),new ViewModelProvider.NewInstanceFactory()).get(OutlineViewModel.class);


        Button updateButton = new Button(getActivity());
        updateButton.setText("更新");
        updateButton.setOnClickListener((V)->{
            new GetOutlineTableToWebThread(viewModel,viewModel.getIntent().getStringExtra("modelNo"),getActivity()).start();
        });
        TextView textView = new TextView(getActivity());
        textView.setText("更新されていません");
        viewModel.updateTime.observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textView.setText(s!=null?"更新時間:"+ s:"更新していません");
                textView.setTextSize(12);
            }
        });


        linearLayout.addView(updateButton);
        linearLayout.addView(textView);

        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        String[] time = new String[6];
        for(int i=0;i<6;i++){
            time[i] =simpleDateFormat.format(calendar.getTime());
            calendar.add(Calendar.DATE, -1);
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                time);
        Spinner spinner = new Spinner(getActivity());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                              @Override
                                              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                                  String ss=  (String)parent.getSelectedItem();
//
//                                                  String s =viewModel.getIntent().getStringExtra("modelNo");

                                           //     viewModel.getLiveData(viewModel.getIntent().getStringExtra("modelNo"),getActivity(),Integer.parseInt((String)parent.getSelectedItem()));
                                              }

                                              @Override
                                              public void onNothingSelected(AdapterView<?> parent) {

                                              }
                                          });


                spinner.setAdapter(adapter);
        linearLayout.addView(spinner);




        return linearLayout;//super.onCreateView(inflater, container, savedInstanceState);
    }
}
