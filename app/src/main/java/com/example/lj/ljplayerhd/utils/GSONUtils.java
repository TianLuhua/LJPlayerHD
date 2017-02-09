package com.example.lj.ljplayerhd.utils;

import com.example.lj.ljplayerhd.bean.SearchBean;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wh on 2016/12/8.
 */

public class GSONUtils {

    public static List<SearchBean> parseJsonArrayString(String jsonArray) {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonArray jarray = parser.parse(jsonArray).getAsJsonArray();

        ArrayList<SearchBean> datas = new ArrayList<SearchBean>();

        for (JsonElement obj : jarray) {
            SearchBean bean = gson.fromJson(obj, SearchBean.class);
            datas.add(bean);
        }
        return datas;
    }
}
