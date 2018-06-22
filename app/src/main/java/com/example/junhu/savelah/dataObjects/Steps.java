package com.example.junhu.savelah.dataObjects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class Steps {
    private String name;
    private List<SingleStep> steps;

    public String getJson() {
        //Gson gson = new Gson();
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(this);
    }

    public Steps(String json) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        Steps steps = gson.fromJson(json, Steps.class);
        this.name = steps.getName();
        this.steps = steps.getSteps();
    }

    public String getName() { return name; }

    public List<SingleStep> getSteps() { return steps; }
}
