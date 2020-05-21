package com.example.myapplication_ver3;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.example.myapplication_ver3.database.AppDatabase;
import com.example.myapplication_ver3.database.AppDatabaseSingleton;
import com.example.myapplication_ver3.database.OutlineTable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import androidx.lifecycle.ViewModelProvider;

public class GetOutlineTableToWebThread extends Thread {

    String modelNo;
    Context context;
    OutlineViewModel viewModel;

    GetOutlineTableToWebThread(OutlineViewModel viewModel,String modelNo, Context context){
        this.modelNo = modelNo;
        this.context = context;
        this.viewModel = viewModel;

    }

    @Override
    public void run() {
        String updateTime = getWebData();
        viewModel.updateTime.postValue(updateTime);
        viewModel.getHandler().post(()->{
            Toast.makeText(context, "OutlineTableを更新しました", Toast.LENGTH_LONG).show();
        });
    }

    public  String getWebData(){
        Map<String,Map<Integer, Map<Integer,Integer>>> map1 = new TreeMap<>();
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        int time[] = new int[6];
        for(int i=0;i<6;i++){
            time[i] =Integer.parseInt(simpleDateFormat.format(calendar.getTime()));
            calendar.add(Calendar.DATE, -1);
        }
        Map<String,String> contentMap = new HashMap(){
            {
                put("1/0", "BB");
                put("2/0", "RB");
                put("6/0", "TOTAL");
                put("8/0", "LAST");
                put("81/1", "DIFF");
            }
        };
        String[] contentNo = {"1/0","2/0","6/0","8/0","81/1"};
        String updateTime = null;
        for(String content:contentNo){
            String url = "https://papimo.jp/h/00041817/hit/index_sort/" + modelNo + "/1-20-262529/" + content;
            try {
                Document document = Jsoup.connect(url).timeout(10000).get();
                if(updateTime == null){
                    updateTime = document.select(".latest").text().replace("データ更新日時：","");
                }
                Elements elements = document.select("#table-sort tbody tr");
                Map<Integer, Map<Integer,Integer>> map2 = new TreeMap<>();
                for(Element TRelement:elements){
                    Elements TRelements = TRelement.select("td");
                    Element no = TRelements.remove(0);
                    Map<Integer,Integer> map3 = new TreeMap<>();
                    int i=0;
                    for(Element TDelement:TRelements){
                        map3.put(time[i],TDelement.text()==""?0:Integer.parseInt(TDelement.text().replace(",","")));
                        i++;
                    }
                    map2.put(Integer.parseInt(no.text()),map3);
                }
                map1.put(contentMap.get(content),map2);
            }catch (IOException e){
                viewModel.getHandler().post(()->{
                        Toast.makeText(context , "Outlineエラー", Toast.LENGTH_LONG).show();
                    });
            }
        }
        Map<String,Map<Integer,Map<Integer,Integer>>> amap = ChangeMap.<String,Integer, Integer, Integer>change1(map1);
        Map<Integer,Map<String,Map<Integer,Integer>>> bmap = ChangeMap.<String,Integer, Integer, Integer>change2(amap);
        Map<Integer,Map<Integer,Map<String,Integer>>> cmap = ChangeMap.<Integer, String, Integer, Integer>change1(bmap);
        setData(cmap);
        return updateTime;
    }

    public void setData(Map<Integer,Map<Integer,Map<String,Integer>>> map){
        AppDatabase appDatabase = AppDatabaseSingleton.getInstance(context);
        List<OutlineTable> list = new ArrayList<>();
        for(int date:map.keySet()){
            Map<Integer, Map<String,Integer>> dateMap = map.get(date);
            for(int no:dateMap.keySet()){
                Map<String,Integer> cateMap = dateMap.get(no);
               list.add(new OutlineTable(date,no,Integer.parseInt(modelNo),cateMap.get("BB"),cateMap.get("RB"),cateMap.get("TOTAL"),cateMap.get("LAST"),cateMap.get("DIFF")));
            }
        }
        appDatabase.outlineTableDao().queryModelNoDate(Integer.parseInt(modelNo),20200518);
        //appDatabase.outlineTableDao().insertAll(list);
    }
}