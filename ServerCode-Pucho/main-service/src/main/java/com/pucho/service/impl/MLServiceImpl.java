package com.pucho.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.MalformedJsonException;
import com.google.inject.Inject;
import com.ning.http.client.Response;
import com.pucho.configuration.MlConfiguration;
import com.pucho.configuration.PuchoConfiguration;
import com.pucho.service.MLService;
import com.pucho.utils.HttpClient;

public class MLServiceImpl implements MLService{

    private final HttpClient httpClient = HttpClient.INSTANCE;
    private final MlConfiguration mlConfiguration;
    
    @Inject
    public MLServiceImpl(PuchoConfiguration configuration) {
        this.mlConfiguration = configuration.getMlConfiguration();
    }

    @Override
    public List<String> getTags(String ques) {
        List<String> result = new ArrayList<>();
        try {
            String taggingURl = this.mlConfiguration.getHost() + this.mlConfiguration.getTaggingApi();
            JsonObject body = new JsonObject();
            JsonArray questions = new JsonArray();
            JsonPrimitive question = new JsonPrimitive(ques);
            questions.add(question);
            body.add("questions", questions);
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "Application/json");
            try {
                Response res = this.httpClient.executePost(taggingURl, body.toString(), headers);
                String jsonBody = res.getResponseBody();
                JsonParser parser = new JsonParser();
                JsonObject bodyAsObject = parser.parse(jsonBody).getAsJsonObject();
                JsonArray data = bodyAsObject.get("data").getAsJsonArray();
                JsonObject first = data.get(0).getAsJsonObject();
                JsonArray tags = first.get("tags").getAsJsonArray();
                for (JsonElement tag : tags) {
                    result.add(tag.getAsString());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (MalformedJsonException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            //blanket exception, happens when tagging API fails
            e.printStackTrace();
        }
        return result;
    }
}
