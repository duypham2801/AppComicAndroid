package com.example.appdoctruyen.Object;

import com.example.appdoctruyen.Adapter.ComicAdapter;

import org.json.JSONException;
import org.json.JSONObject;

public class ComicChapter {
    private String c_nameChap, c_release,c_idChap;

    /*
    c_idChap
    c_nameChap
    c_release */
    public ComicChapter(JSONObject o) throws JSONException
    {
        c_nameChap = o.getString("c_nameChap");
        c_release = o.getString("c_release");
        c_idChap = o.getString("c_idChap");

    }
    public ComicChapter()
    {

    }
    public ComicChapter(String nameChap, String release)
    {
        this.c_nameChap = nameChap;
        this.c_release = release;
    }

    public String getC_nameChap() {
        return c_nameChap;
    }

    public void setC_nameChap(String c_nameChap) {
        this.c_nameChap = c_nameChap;
    }

    public void setC_release(String c_release) {
        this.c_release = c_release;
    }

    public String getC_release() {
        return c_release;
    }

    public String getC_idChap() {
        return c_idChap;
    }

    public void setC_idChap(String c_idChap) {
        this.c_idChap = c_idChap;
    }
}
