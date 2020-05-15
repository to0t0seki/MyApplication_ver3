package com.example.myapplication_ver3;

import android.content.Context;


import com.example.myapplication_ver3.database.AppDatabase;
import com.example.myapplication_ver3.database.AppDatabaseSingleton;
import com.example.myapplication_ver3.database.ModelTable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AccessModelListThread extends Thread {

    Context context;
    Callback callback;
    String url = "https://papimo.jp/h/00041817/hit/index_machine/1-20-260540/";


    AccessModelListThread(Context context){
        this.context = context;
    }

    @Override
    public void run() {
        AppDatabase appDatabase = AppDatabaseSingleton.getInstance(context);
        //WEBアクセスON,OFF
        List<ModelTable> l = getModelList();
        appDatabase.modelTableDao().insert(l);

        List<ModelTable> list = appDatabase.modelTableDao().getAll();
        callback.callback(list);
    }

    interface Callback{
        void callback(List<ModelTable> list);
    }

    public void setCallbak(Callback callback){
        this.callback = callback;
    }

    public List<ModelTable> getModelList(){
        List<ModelTable> list = new ArrayList<>();
        try {
            Document document = Jsoup.connect(this.url).timeout(20000).get();
            String maxpage = document.select("#max_page").val();
            for (int i = 0; i < Integer.parseInt(maxpage); i++) {
                document = Jsoup.connect(this.url + "?page=" + String.valueOf((i + 1))).timeout(10000).get();
                Elements elements = document.select(".item li a");
                for (Element element : elements) {
                    ModelTable modelTable = new ModelTable();
                    int indexname = element.select(".name").text().indexOf("台") + 1;
                    int indexurl = element.attr("href").indexOf("index_sort") + 11;
                    modelTable.modelName = element.select(".name").text().substring(indexname);
                    modelTable.modelNo = Integer.parseInt(element.attr("href").substring(indexurl, indexurl + 9));
                    list.add(modelTable);
                }
            }
        }catch (IOException e){
            System.out.println(e);
        }
        return list;
    }
}
