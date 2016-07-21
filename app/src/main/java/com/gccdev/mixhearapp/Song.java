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

    public String getIMAGE_URL_MEDIUM(){
        return IMAGE_URL_MEDIUM;
    }

    public String getIMAGE_URL_LARGE() {
        return IMAGE_URL_LARGE;
    }

    public String getCREATED() {
        return CREATED;
    }

    public String getLENGTH() {
        return LENGTH;
    }

    public String getURL() {
        return URL;
    }

    public int getPLAY_COUNT() {
        return PLAY_COUNT;
    }

    public int getFAVORITE_COUNT() {
        return FAVORITE_COUNT;
    }

    public int getLISTENER_COUNT() {
        return LISTENER_COUNT;
    }

    public int getCOMMENT_COUNT() {
        return COMMENT_COUNT;
    }

    public void setIMAGE_URL_LARGE(String IMAGE_URL_LARGE) {
        this.IMAGE_URL_LARGE = IMAGE_URL_LARGE;
    }

    public void setCREATED(String CREATED) {
        this.CREATED = CREATED;
    }

    public void setLENGTH(String LENGTH) {
        this.LENGTH = LENGTH;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setPLAY_COUNT(int PLAY_COUNT) {
        this.PLAY_COUNT = PLAY_COUNT;
    }

    public void setFAVORITE_COUNT(int FAVORITE_COUNT) {
        this.FAVORITE_COUNT = FAVORITE_COUNT;
    }

    public void setLISTENER_COUNT(int LISTENER_COUNT) {
        this.LISTENER_COUNT = LISTENER_COUNT;
    }

    public void setCOMMENT_COUNT(int COMMENT_COUNT) {
        this.COMMENT_COUNT = COMMENT_COUNT;
    }
}
