package com.markwebtechnologies.lupus;

/**
 * Created by arishballana on 3/1/16.
 */
public class Post_Data {

    String id = "";
    String postname = "";
    String posttype = "";
    String overview = "";
    String date = "";
    String img_path="";


    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosttype() {
        return posttype;
    }

    public void setPosttype(String posttype) {
        this.posttype = posttype;
    }

    public String getPostname() {
        return postname;
    }

    public void setPostname(String postname) {
        this.postname = postname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}