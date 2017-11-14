package com.example.dragoon.listview.adapter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dragoon on 2017.10.31..
 */

public class Informations {

    private int id;
    private String name;
    private String information;

    public static final String USER_NAME = "name";
    public static final String USER_Information = "information";
    public static final String USER_Id = "id";

    public Informations(int id, String name,String information) {
        this.id = id;
        this.name = name;
        this.information=information;
    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JSONObject toJsonObject() throws JSONException{
        JSONObject object = new JSONObject();
        object.put(USER_Id, id);
        object.put(USER_NAME, name);
        object.put(USER_Information, information);
        return object;
    }


}
