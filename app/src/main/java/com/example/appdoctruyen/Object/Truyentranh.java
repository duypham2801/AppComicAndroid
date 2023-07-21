package com.example.appdoctruyen.Object;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Truyentranh implements Serializable{
    private String m_name, m_chap, m_linkImage,m_id;
    private String m_author,m_type;
    private String m_description;
    private boolean status;

    public Truyentranh()
    {

    }
    /*
    {
    "nameComic":"",
    "nameChap":"",
    "linkImage":"",
    "idComic":""
    }
    */
    public Truyentranh(JSONObject o) throws JSONException
    {
        m_id = o.getString("idComic");
        m_name = o.getString("nameComic");
        m_chap = o.getString("nameChap");
        m_linkImage = o.getString("linkImage");
        status = o.getBoolean("status");
        m_description = o.getString("description");
        m_author = o.getString("author");
        m_type = o.getString("genre");


    }
    public Truyentranh(String m_name, String m_chap, String m_linkImage) {
        this.m_name = m_name;
        this.m_chap = m_chap;
        this.m_linkImage = m_linkImage;
        this.status = status;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public String getM_chap() {
        return m_chap;
    }

    public void setM_chap(String m_chap) {
        this.m_chap = m_chap;
    }

    public String getM_linkImage() {
        return m_linkImage;
    }

    public void setM_linkImage(String m_linkImage) {
        this.m_linkImage = m_linkImage;
    }

    public String getM_id() {
        return m_id;
    }

    public void setM_id(String m_id) {
        this.m_id = m_id;
    }

    public boolean getStatus(){return status;}

    public void setStatus(boolean status) {this.status = status;}

    public String getDescription(){return m_description;}

    public void setDescription(String description) {this.m_description = description;}

    public String getM_author() {return m_author;}

    public void setM_author(String m_author) {this.m_author = m_author;}

    public String getM_type(){return m_type;}

    public void setM_type(String m_type) {this.m_type = m_type;}





}
