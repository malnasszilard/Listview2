package com.example.dragoon.listview.adapter;

/**
 * Created by dragoon on 2017.10.31..
 */

public class Informations {

    private int id;
    private String name;


    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Informations(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
