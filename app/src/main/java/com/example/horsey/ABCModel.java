package com.example.horsey;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;

public class ABCModel {

    public ArrayList<HashMap<String,String>> getProblems(Context context){
        ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
        try {
            StringBuffer buffer = new StringBuffer();
            InputStream inputStream = context.getAssets().open("ABC.json");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            byte[] b=new byte[bufferedInputStream.available()];

            while(bufferedInputStream.read(b)!=-1){
                String sta=new String(b);
                buffer.append(sta);
            }

            String problems=buffer.toString();
            try {
                JSONArray jsonArray=new JSONArray(URLDecoder.decode(problems,"GB2312"));
                System.out.println(jsonArray);

                //生成一个问题
                for(int i=0 ; i<jsonArray.length() ; i++){
                    HashMap<String,String> map=new HashMap<String,String>();
                    map.put("order",jsonArray.getJSONObject(i).getString("order"));
                    map.put("title",jsonArray.getJSONObject(i).getString("title"));
                    map.put("type",jsonArray.getJSONObject(i).optString("type"));

                    JSONObject j1=new JSONObject(URLDecoder.decode(jsonArray.getJSONObject(i).getString("option1"),"GB2312"));
                    JSONObject j2=new JSONObject(URLDecoder.decode(jsonArray.getJSONObject(i).getString("option2"),"GB2312"));

                    map.put("opt1name", j1.getString("name"));
                    map.put("opt1score", j1.getString("score"));
                    map.put("opt2name", j2.getString("name"));
                    map.put("opt2score", j2.getString("score"));
                    arrayList.add(map);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public String[] getResultList(Context context){
        String[] result = new String[6];

        try {
            StringBuffer buffer=new StringBuffer();
            StringBuffer buffer1=new StringBuffer();
            InputStream inputStream = null;
            InputStream resultIS=null;

            inputStream = context.getAssets().open("ABCRESULT.json");
            resultIS = context.getAssets().open("ABCEVA.json");

            BufferedInputStream bufferedInputStream=new BufferedInputStream(inputStream);
            BufferedInputStream bufferedInputStream1=new BufferedInputStream(resultIS);

            byte[] b=new byte[1024];
            while(bufferedInputStream.read(b)!=-1){
                String sta=new String(b);
                buffer.append(sta);
            }
            String problems=buffer.toString();

            byte[] b1=new byte[problems.getBytes().length];
            while(bufferedInputStream1.read(b1)!=-1){
                String sta=new String(b1);
                buffer1.append(sta);
            }
            String problems1=buffer1.toString();

            JSONArray jsonArray= null;
            JSONArray jsonArray1=null;

            try {
                jsonArray = new JSONArray(URLDecoder.decode(problems,"GB2312"));
                //System.out.println(jsonArray);
                result[0] = jsonArray.getJSONObject(0).getString("nickname");
                result[1] = jsonArray.getJSONObject(0).getString("sex");
                result[2] = jsonArray.getJSONObject(0).getString("age");

                jsonArray1=new JSONArray(URLDecoder.decode(problems1,"GB2312"));
                //System.out.println(jsonArray1);
                result[3]=jsonArray1.getJSONObject(0).getString("testResult1");
                result[4]=jsonArray1.getJSONObject(0).getString("testResult2");
                result[5]=jsonArray1.getJSONObject(0).getString("testResult3");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
