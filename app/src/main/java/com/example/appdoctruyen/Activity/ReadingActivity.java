package com.example.appdoctruyen.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appdoctruyen.API.ApiGetComic;
import com.example.appdoctruyen.API.ApiGetImage;
import com.example.appdoctruyen.Adapter.ComicAdapter;
import com.example.appdoctruyen.Adapter.ImageAdapter;
import com.example.appdoctruyen.Interface.GetApiImage;
import com.example.appdoctruyen.Object.Truyentranh;
import com.example.appdoctruyen.R;
import java.lang.Object;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReadingActivity extends AppCompatActivity implements GetApiImage {

    ImageView m_image;
    ArrayList<String> arrUrlImg;
    int curPage, page;
    TextView m_page;
    ViewPager2 viewPager;
    String c_idChap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        init();
        anhXa();
        setUp();
        new ApiGetImage(this,c_idChap).execute();
    }

    private void init()
    {
        Bundle b = getIntent().getBundleExtra("data");
        c_idChap =  b.getString("c_idChap");
    }

    private void anhXa()
    {
        m_page = findViewById(R.id.m_page);
        m_image = findViewById(R.id.m_img);
        viewPager = findViewById(R.id.viewPager);

    }
    private void setUp() {
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                curPage = position + 1;
                m_page.setText(curPage + " / " + page);
            }
        });
    }
    @Override
    public void Start()
    {
        Toast.makeText(this,"Getting Api",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void Finish(String data)
    {
        arrUrlImg = new ArrayList<String>();
        try
        {
            JSONArray arr = new JSONArray(data);
            int l = arr.length();
            for(int i = 0; i<l;++i) {
                //JSONObject o = arr.getJSONObject(i);
                arrUrlImg.add(arr.getString(i));
            }
            page = arrUrlImg.size();
            curPage = 1;
            m_page.setText(curPage + " / " + page);
            viewPager.setAdapter(new ImageAdapter(arrUrlImg, this));
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void Fail()
    {
        Toast.makeText(this,"Fail !!!",Toast.LENGTH_SHORT).show();
    }

}


