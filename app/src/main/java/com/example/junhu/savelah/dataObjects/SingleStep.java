package com.example.junhu.savelah.dataObjects;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

class SingleStep {
    private int number;
    private String step;

    public SingleStep (String json){
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        SingleStep step = gson.fromJson(json, SingleStep.class);
        this.number = step.getNumber();
        //Log.d("number", String.valueOf(this.number));
        this.step = step.getStep();
    }
    public String getJson() {
        //Gson gson = new Gson();
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(this);
    }

    public int getNumber() { return number; }

    public String getStep() { return step; }

}
