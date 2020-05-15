package com.example.myapplication_ver3;

import java.util.Map;
import java.util.TreeMap;

public class ChangeMap<T,V,S,R> {
    static public <T, V, S, R> Map<T, Map<S, Map<V, R>>> change1(Map<T, Map<V, Map<S, R>>> map) {
        Map<T, Map<S, Map<V, R>>> tmp1 = new TreeMap<>();
        for (T kate : map.keySet()) {
            Map<S, Map<V, R>> tmp2 = new TreeMap<>();
            for (V no : map.get(kate).keySet()) {
                for (S day : map.get(kate).get(no).keySet()) {
                    Map<V, R> tmp3 = new TreeMap<>();
                    if (tmp2.containsKey(day)) {
                        tmp2.get(day).put(no, map.get(kate).get(no).get(day));
                    } else {
                        tmp3.put(no, map.get(kate).get(no).get(day));
                        tmp2.put(day, tmp3);
                    }
                }
            }
            tmp1.put(kate, tmp2);
        }
        return tmp1;
    }

    static public <T, S, V, R> Map<S, Map<T, Map<V, R>>> change2(Map<T, Map<S, Map<V, R>>> tmp) {
        Map<S, Map<T, Map<V, R>>> tmp1 = new TreeMap<>();
        for (T kate : tmp.keySet()) {
            for (S day : tmp.get(kate).keySet()) {
                Map<T, Map<V, R>> tmp2 = new TreeMap<>();
                if (tmp1.containsKey(day)) {
                    tmp1.get(day).put(kate, tmp.get(kate).get(day));
                } else {
                    tmp2.put(kate, tmp.get(kate).get(day));
                    tmp1.put(day, tmp2);
                }
            }
        }
        return tmp1;
    }
}
