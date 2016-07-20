package com.gccdev.mixhearapp;

/**
 * Created by rolac on 16/07/16.
 */
public class Song {

    private String name;
    private String url;


    public Song(String nameP, String Url){
        name = nameP;
        url = Url;
    }
    public String getName(){
        return name;
    }

    public String getUrl(){
        return url;
    }
}
