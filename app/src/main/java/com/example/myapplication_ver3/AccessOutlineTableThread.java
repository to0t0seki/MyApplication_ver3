package com.example.myapplication_ver3;


import android.content.Context;

import com.example.myapplication_ver3.database.AppDatabase;
import com.example.myapplication_ver3.database.AppDatabaseSingleton;
import com.example.myapplication_ver3.database.ModelTable;
import com.example.myapplication_ver3.database.OutlineTable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AccessOutlineTableThread extends Thread {
    String hallNo;
    String modelNo;
    String[] contentNo;
    Callback callback;
    Context context;
    AppDatabase appDatabase;


    AccessOutlineTableThread(String hallNo, String modelNo, String[] contentNo, Context context){
        this.hallNo = hallNo;
        this.modelNo = modelNo;
        this.contentNo = contentNo;
        this.context = context;
    }

    @Override
    public void run() {
        //データベース作成
        appDatabase = AppDatabaseSingleton.getInstance(context);
        //Webデータ取得、ソートしてデータベース登録
        getWebData();
        //データベースから取得
        List<OutlineTable> outlineTables = getData();
        //コールバック
        callback.callback(outlineTables);
    }

    public  void getWebData(){
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
                put("81/1", "DIFF");
                put("1/0", "BB");
                put("2/0", "RB");
                put("6/0", "TOTAL");
                put("8/0", "LAST");
            }
        };
        for(String content:contentNo){
            String url = "https://papimo.jp/h/"+ hallNo + "/hit/index_sort/" + modelNo + "/1-20-262529/" + content;
            try {
                Document document = Jsoup.connect(url).timeout(10000).get();
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
                System.out.println("error");
            }
        }
        Map<String,Map<Integer,Map<Integer,Integer>>> amap = ChangeMap.<String,Integer, Integer, Integer>change1(map1);
        Map<Integer,Map<String,Map<Integer,Integer>>> bmap = ChangeMap.<String,Integer, Integer, Integer>change2(amap);
        Map<Integer,Map<Integer,Map<String,Integer>>> cmap = ChangeMap.<Integer, String, Integer, Integer>change1(bmap);
        setData(cmap,modelNo);
    }

    public void setData(Map<Integer,Map<Integer,Map<String,Integer>>> map,String modelNo){
        for(int date:map.keySet()){
            Map<Integer, Map<String,Integer>> dateMap = map.get(date);
            for(int no:dateMap.keySet()){
                Map<String,Integer> cateMap = dateMap.get(no);
                appDatabase.outlineTableDao().insert(new OutlineTable(date,no,Integer.parseInt(modelNo),cateMap.get("BB"),cateMap.get("RB"),cateMap.get("TOTAL"),cateMap.get("LAST"),cateMap.get("DIFF")));
            }
        }
    }

    public List<OutlineTable> getData(){
        List<OutlineTable> outlineTableList = appDatabase.outlineTableDao().queryModelNo(Integer.parseInt(modelNo));
        return outlineTableList;
    }

    public void setCallback(Callback callback){
        this.callback = callback;
    }

    interface Callback{
        void callback(List<OutlineTable> outlineTableList);
    }
}
