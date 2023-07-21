package com.example.appdoctruyen.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;

import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import android.widget.Toast;

import com.example.appdoctruyen.API.ApiGetComic;
import com.example.appdoctruyen.Adapter.ComicAdapter;
import com.example.appdoctruyen.Interface.GetApi;
import com.example.appdoctruyen.Object.Truyentranh;
import com.example.appdoctruyen.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainInterface extends AppCompatActivity implements GetApi {
    GridView gdvNameComic;
    GridView gdvNameComic1;
    ComicAdapter adapter;
    ComicAdapter adapterFull;
    ArrayList<Truyentranh> comicArrayList;
    ArrayList<Truyentranh> comicFullArrayList;
    ImageView buttonSearch;
    ImageView buttonFollow;
    ImageView buttonHistory;
    EditText edtSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_interface);
        init();
        anhXa();
        setUp();
        setClick();
        new ApiGetComic(this).execute();

    }

    private void init()
    {
        comicArrayList = new ArrayList<>();
        comicFullArrayList = new ArrayList<>();
        adapter = new ComicAdapter(this, 0,comicArrayList);
        adapterFull = new ComicAdapter(this, 0,comicFullArrayList);
    }

    private void anhXa()
    {
        gdvNameComic = findViewById(R.id.gdvListComic);
        gdvNameComic1 = findViewById(R.id.gdvListComic1);
        buttonSearch= findViewById(R.id.buttonSearch);
        edtSearch = findViewById(R.id.edtSearch);
        buttonFollow = findViewById(R.id.buttonFollow);
        buttonHistory = findViewById(R.id.buttonHistory);

    }
    private void setUp()
    {
        gdvNameComic.setAdapter(adapter);
        gdvNameComic1.setAdapter(adapterFull);
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
                buttonSearch.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainInterface.this,SearchingActivity.class);
                        intent.putExtra("textSearch",s);
                        startActivity(intent);
                    }
                });
            }
        });
        gdvNameComic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Truyentranh comic = comicArrayList.get(i);
                Bundle b = new Bundle();
                b.putSerializable("comic",comic);
                Intent intent = new Intent(MainInterface.this, ChapterActivity.class);
                intent.putExtra("data",b);
                startActivity(intent);
            }
        });

        gdvNameComic1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Truyentranh comic = comicFullArrayList.get(i);
                Bundle b = new Bundle();
                b.putSerializable("comic",comic);
                Intent intent = new Intent(MainInterface.this, ChapterActivity.class);
                intent.putExtra("data",b);
                startActivity(intent);
            }
        });
        buttonFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainInterface.this, FavoriteActivity.class);
                startActivity(intent);
            }
        });
        buttonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainInterface.this, HistoryActivity.class);
                startActivity(intent);
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
        try
        {
            comicArrayList.clear();
            comicFullArrayList.clear();
            JSONArray arr = new JSONArray(data);
            int l = arr.length();
            for(int i = 0; i<l;++i)
            {
                JSONObject o = arr.getJSONObject(i);
                Boolean status = o.getBoolean("status");
                if (status)
                comicArrayList.add(new Truyentranh(o));
                else comicFullArrayList.add(new Truyentranh(o));
            }
            adapter = new ComicAdapter(this, 0,comicArrayList);
            adapterFull = new ComicAdapter(this,0,comicFullArrayList);
            gdvNameComic.setAdapter(adapter);
            gdvNameComic1.setAdapter(adapterFull);
        }
        catch(JSONException e)
        {

        }
        Toast.makeText(this,"Loading",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void Fail()
    {
        Toast.makeText(this,"Fail !!!",Toast.LENGTH_SHORT).show();
    }

    public void update(View view)
    {
        new ApiGetComic(this).execute();

    }
}
