package com.example.appdoctruyen.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.appdoctruyen.API.ApiGetComic;
import com.example.appdoctruyen.Adapter.ComicAdapter;
import com.example.appdoctruyen.Interface.GetApi;
import com.example.appdoctruyen.LocalDatabase.DBHelper;
import com.example.appdoctruyen.Object.Truyentranh;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import com.example.appdoctruyen.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity implements GetApi  {
    GridView gdvListComic;
    ComicAdapter adapter;
    DBHelper dbHelper;
    ArrayList<Truyentranh> mTruyentranhList;
    ImageView buttonSearch;
    EditText edtSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        init();
        anhXa();
        setUp();
        setClick();
        new ApiGetComic(this).execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateData();
    }

    private void init()
    {
        mTruyentranhList = new ArrayList<>();
        adapter = new ComicAdapter(this, 0,mTruyentranhList);
        dbHelper = new DBHelper(FavoriteActivity.this, "favoriteBase.db");
    }

    private void anhXa()
    {
        gdvListComic = findViewById(R.id.gdvListComic);
        buttonSearch= findViewById(R.id.buttonSearch);
        edtSearch = findViewById(R.id.edtSearch);

    }
    private void setUp()
    {
        gdvListComic.setAdapter(adapter);
    }
    private void setClick()
    {

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                String s = edtSearch.getText().toString();
                //adapter.sortComic(s);
                buttonSearch.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(FavoriteActivity.this,SearchingActivity.class);
                        intent.putExtra("textSearch",s);
                        startActivity(intent);
                    }
                });
            }
        });
        gdvListComic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Truyentranh comic = mTruyentranhList.get(i);
                Bundle b = new Bundle();
                b.putSerializable("comic",comic);
                Intent intent = new Intent(FavoriteActivity.this, ChapterActivity.class);
                intent.putExtra("data",b);
                startActivity(intent);
            }
        });
    }

    public void updateData(){
        /* mTruyentranhList.clear();
        dbHelper.layTruyen();
        for (int i = 0; i < dbHelper.mList.size(); i++)
        {
            mTruyentranhList.add(dbHelper.mList.get(i));
        }
        adapter = new ComicAdapter(this, 0,mTruyentranhList);
        gdvListComic.setAdapter(adapter);

         */
    }

    public void update(View view)
    {
        updateData();

    }

    @Override
    public void Start() {

    }

    @Override
    public void Finish(String data) {
        try
        {
            mTruyentranhList.clear();
            JSONArray arr = new JSONArray(data);
            int la = arr.length();
            dbHelper.layTruyen();
            int ls = dbHelper.mList.size();
            for (int i = 0 ; i< la ; ++i)
            {
                JSONObject o = arr.getJSONObject(i);
                String nameComic = o.getString("nameComic");
                for (int j = 0; j < ls; ++j)
                {
                    //mTruyentranhList.add(dbHelper.mList.get(i));
                    if (dbHelper.mList.get(j).getM_name().equals(nameComic))
                        mTruyentranhList.add(new Truyentranh(o));
                }
            }
        adapter = new ComicAdapter(this, 0, mTruyentranhList);
        gdvListComic.setAdapter(adapter);
        } catch(JSONException e) {
        }
    }

    @Override
    public void Fail() {

    }
}