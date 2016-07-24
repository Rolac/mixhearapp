package com.gccdev.mixhearapp;

/**
 * Created by rolac on 16/07/16.
 */
public class Song {

    private String NAME;
    private String IMAGE_URL_MEDIUM;
    private String IMAGE_URL_LARGE ;
    private String CREATED ;
    private String LENGTH ;
    private String URL ;
    private int PLAY_COUNT ;
    private int FAVORITE_COUNT ;
    private int LISTENER_COUNT;
    private int COMMENT_COUNT;


    public Song(String nameP, String Url){
        NAME = nameP;
        IMAGE_URL_MEDIUM = Url;
    }
    public String getName(){
        return NAME;
    }


}
