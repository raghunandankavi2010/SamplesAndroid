package com.pucho.service.impl;

import com.google.api.GoogleAPI;
import com.google.api.translate.Language;
import com.google.api.translate.Translate;
import com.ning.http.client.Response;
import com.pucho.helper.GoogleTranslateResponse;
import com.pucho.service.TranslateService;
import com.pucho.utils.HttpClient;
import com.pucho.utils.JsonUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dinesh.rathore on 27/09/15.
 */
public class GoogleTranslateService implements TranslateService {

    @Override
    public String translateString(String input, String destLan)  {
        return translate(input,destLan);
    }

    @Override
    public String[] translateStrings(String[] input, String destLan) {
        return new String[0];
    }

    public static String translate(String request,String lan){
        String result="";
            try {

            HttpClient httpClient = HttpClient.INSTANCE;
            Map<String,String> params = new HashMap<>();
            params.put("key","AIzaSyA8bgcAUo6s8bnnsjPh00IjvBMgS1jt2IY");
            params.put("q",request);
            params.put("target",lan);

            Response response =  httpClient.executeGet("https://www.googleapis.com/language/translate/v2",params,new HashMap<String, String>());

            JSONObject jsonObject = new JSONObject(response.getResponseBody());
            GoogleTranslateResponse response1 = JsonUtil.deserializeJson(jsonObject.getString("data"), GoogleTranslateResponse.class);

            for(Object object : response1.getTranslations()){
                JSONObject json = new JSONObject(object.toString());
                result = json.getString("translatedText");

            }

        }catch (Exception e ){

            System.out.println(e.getMessage());
            e.printStackTrace();
        }
            return result;
    }
}
