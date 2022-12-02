package com.jnu.booklistmainapplication.Data;

import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpDataLoader {
    @NonNull
    public  String getHtml(String path) {
        try{
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);//建立链接的时间
            conn.setReadTimeout(5000);//读取的时间
            conn.setRequestMethod("GET");
            conn.setUseCaches(false);//是否使用缓存
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader inputStreamReader=new InputStreamReader(conn.getInputStream());
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String tempLine = null;//按行读取文件
                StringBuffer resultBuffer = new StringBuffer();
                while ((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                    resultBuffer.append("\n");
                }
                Log.i("test data",resultBuffer.toString());

                return resultBuffer.toString();
            }
        }catch (Exception exception)
        {
            Log.e("error",exception.getMessage());
        }
        return "";
    }
    @NonNull
    //解析数据
    public ArrayList<ShopLocation> ParseJsonData(String JsonText)
    {
        ArrayList<ShopLocation> locations=new ArrayList<>();
        try {
            //root是对象，其有一个shops属性
            JSONObject root = new JSONObject(JsonText);

            JSONArray shops = root.getJSONArray("shops");
            for(int i=0;i<shops.length();++i){
                JSONObject shop=shops.getJSONObject(i);

                ShopLocation shopLocation=new ShopLocation();
                shopLocation.setName(shop.getString("name"));
                shopLocation.setLatitude(shop.getDouble("latitude"));
                shopLocation.setLongitude(shop.getDouble("longitude"));
                shopLocation.setMemo(shop.getString("memo"));

                locations.add(shopLocation);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return locations;
    }
}
