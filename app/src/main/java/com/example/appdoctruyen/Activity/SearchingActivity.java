package com.example.appdoctruyen.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
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

public class SearchingActivity extends AppCompatActivity implements GetApi {

    ComicAdapter adapter;
    GridView gdvNameComic;

    TextView editText;
    ImageView backButton;
    ArrayList<Truyentranh> comicArrayList;
    String textSearch;
    String link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);
        init();
        anhXa();
        setUp();
        setClick();
        new ApiGetComic(this).execute();
    }

    private void init()
    {
        Intent intent = getIntent();
        textSearch = intent.getStringExtra("textSearch");
        comicArrayList = new ArrayList<>();
        adapter = new ComicAdapter(this, 0,comicArrayList);
    }
    private void anhXa()
    {
        gdvNameComic = findViewById(R.id.gdvListComic);
        editText = findViewById(R.id.edtSearch);
        backButton = findViewById(R.id.buttonSearch);

    }
    private void setUp()
    {
        gdvNameComic.setAdapter(adapter);
        editText.setHint(textSearch);
    }
    private void setClick()
    {
        gdvNameComic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Truyentranh comic = comicArrayList.get(i);
                Bundle b = new Bundle();
                b.putSerializable("comic",comic);
                Intent intent = new Intent(getApplicationContext(), ChapterActivity.class);
                intent.putExtra("data",b);
                startActivity(intent);
            }
        });
    }
    @Override
    public void Start() {
        Toast.makeText(this,"Loading",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void Finish(String data) {
        try
        {
            comicArrayList.clear();
            JSONArray arr = new JSONArray(data);
            int l = arr.length();
            textSearch = textSearch.toLowerCase();
            for(int i = 0; i<l;++i)
            {
                JSONObject o = arr.getJSONObject(i);
                String nameComic = o.getString("nameComic").toLowerCase();
                String typeComic = o.getString("genre").toLowerCase();
                if (nameComic.contains(textSearch) || typeComic.contains(textSearch))
                {
                    comicArrayList.add(new Truyentranh(o));
                }
            }
            adapter = new ComicAdapter(this, 0,comicArrayList);
            gdvNameComic.setAdapter(adapter);
        }
        catch(JSONException e)
        {

        }
    }


    @Override
    public void Fail() {
        Toast.makeText(this,"Fail !!!",Toast.LENGTH_SHORT).show();
    }
}