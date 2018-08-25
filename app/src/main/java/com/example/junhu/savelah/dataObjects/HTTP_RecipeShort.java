package com.example.junhu.savelah.dataObjects;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class HTTP_RecipeShort {
    private List<Recipe_Short> results;
    private String baseUri;
    private int offset;
    private int number;
    private int totalResults;
    private int processingTimeMs;
    private long expires;
    private boolean isStale;

    private HTTP_RecipeShort(String baseUri, long expires, boolean isStale, int number, int offset, int processingTimeMs, List<Recipe_Short> results, int totalResults) {
        this.baseUri = baseUri;
        this.expires = expires;
        this.isStale = isStale;
        this.number = number;
        this.offset = offset;
        this.processingTimeMs = processingTimeMs;
        this.results = results;
        this.totalResults = totalResults;
    }
    public HTTP_RecipeShort(String json) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        HTTP_RecipeShort handler = gson.fromJson(json, HTTP_RecipeShort.class);
        this.baseUri = handler.getBaseUri();
        this.expires = handler.getExpires();
        this.isStale = handler.isStale();
        this.offset = handler.getOffset();
        this.number = handler.getNumber();
        this.processingTimeMs = handler.getProcessingTimeMs();
        this.results = handler.getResults();
        this.totalResults = handler.getTotalResults();
    }

    public HTTP_RecipeShort(JSONObject obj) {
        try {
            JSONArray array = obj.getJSONArray("results");
            List<Recipe_Short> resultList = new ArrayList<Recipe_Short>();
            for(int i = 0 ; i < array.length() ; i++){
                Recipe_Short temp = new Recipe_Short();
                JSONObject childJSONObject = array.getJSONObject(i);
                temp.id = childJSONObject.getInt("id");
                temp.title = childJSONObject.getString("title");
                temp.readyInMinutes = childJSONObject.getInt("readyInMinutes");
                temp.image = childJSONObject.getString("image");
                JSONArray jArray = childJSONObject.getJSONArray("imageUrls");
                List<String> returnList = new ArrayList<String>();
                for (int t = 0; t < jArray.length(); t++) {
                    String val = jArray.getString(t);
                    returnList.add(val);
                }
                temp.imageUrls = returnList;
                resultList.add(temp);
            }
            this.results = resultList;
            this.baseUri = obj.getString("baseUri");
            this.expires = obj.getLong("expires");
            this.isStale = obj.getBoolean("isStale");
            this.offset = obj.getInt("offset");
            this.number = obj.getInt("number");
            this.processingTimeMs = obj.getInt("processingTimeMs");
            this.totalResults = obj.getInt("totalResults");
        } catch (JSONException e) {
            Log.d("reason", e.getMessage());
        }
    }

    public String getBaseUri() {
        return baseUri;
    }

    public long getExpires() {
        return expires;
    }

    public boolean isStale() {
        return isStale;
    }

    public int getNumber() {
        return number;
    }

    public int getOffset() {
        return offset;
    }

    public int getProcessingTimeMs() {
        return processingTimeMs;
    }

    public List<Recipe_Short> getResults() { return results; }

    public int getTotalResults() {
        return totalResults;
    }

    @Override
    public String toString() {
        return "API_Handler{" +
                "baseUri='" + baseUri + '\'' +
                ", results='" + results + '\'' +
                ", offset=" + offset +
                ", number=" + number +
                ", totalResults=" + totalResults +
                ", processingTimeMs=" + processingTimeMs +
                ", expires=" + expires +
                ", isStale=" + isStale +
                '}';
    }

}
