package com.example.appdoctruyen.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appdoctruyen.API.ApiGetChapterComic;
import com.example.appdoctruyen.Adapter.ComicChapterAdapter;
import com.example.appdoctruyen.Interface.GetChapApi;
import com.example.appdoctruyen.LocalDatabase.DBHelper;
import com.example.appdoctruyen.Object.ComicChapter;
import com.example.appdoctruyen.Object.Truyentranh;
import com.example.appdoctruyen.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ChapterActivity extends AppCompatActivity implements GetChapApi {

    TextView c_nameComic;
    TextView c_description;
    TextView c_author;
    TextView c_status;
    TextView c_type;
    ImageView c_imageComic;
    Truyentranh c_comic;

    ListView c_listView;

    ArrayList<ComicChapter> arrChap;
    DBHelper mDBHelper;
    DBHelper mDBHelperHistory;
    boolean isFollowed;

    ComicChapterAdapter comicChapterAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        init();
        anhxa();
        setUp();
        setClick();

        new ApiGetChapterComic(this,c_comic.getM_id()).execute();
    }
    private void init()
    {
        Bundle b = getIntent().getBundleExtra("data");
        c_comic = (Truyentranh) b.getSerializable("comic");
        arrChap = new ArrayList<>();
        mDBHelper = new DBHelper(ChapterActivity.this, "favoriteBase.db");
        mDBHelperHistory = new DBHelper(ChapterActivity.this, "historyBase.db");

    }
    private void anhxa()
    {
        c_nameComic = findViewById(R.id.c_nameComic);
        c_imageComic = findViewById(R.id.c_imageComic);
        c_listView = findViewById(R.id.c_listComic);
        c_description = findViewById(R.id.c_description);
        c_type = findViewById(R.id.c_type);
        c_author = findViewById(R.id.c_author);
        c_status = findViewById(R.id.c_status);
    }
    private void setUp()
    {
        c_nameComic.setText(c_comic.getM_name());
        c_description.setText(c_comic.getDescription());
        c_author.setText("Tác Giả: " + c_comic.getM_author());
        c_type.setText("Thể Loại: " + c_comic.getM_type());

        if (c_comic.getStatus())
            c_status.setText("Tình Trạng: Chưa hoàn thành");
        else if (!c_comic.getStatus())
            c_status.setText("Tình Trạng: Hoàn thành");



        Glide.with(this).load(c_comic.getM_linkImage()).into(c_imageComic);


    }
    private void setClick()
    {
        c_listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Truyentranh truyentranh = new Truyentranh(c_comic.getM_name(),
                    c_comic.getM_chap(),c_comic.getM_linkImage());
                mDBHelperHistory.addFav(truyentranh);
                Toast.makeText(this, "Đã add vao lich su", Toast.LENGTH_SHORT).show();
            Bundle b = new Bundle();
            b.putString("c_idChap",arrChap.get(i).getC_idChap());
            Intent intent = new Intent(ChapterActivity.this, ReadingActivity.class);
            intent.putExtra("data",b);
            startActivity(intent);

        });
    }

    @Override
    public void Start() {
        Toast.makeText(this,"Loading",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void Finish(String data) {

        try {
            JSONArray array = new JSONArray(data);
            int l = array.length();
            for (int i = 0;i< l ;++i)
            {
                ComicChapter comicChapter = new ComicChapter(array.getJSONObject(i));
                arrChap.add(comicChapter);
            }
            comicChapterAdapter = new ComicChapterAdapter(this,0,arrChap);
            c_listView.setAdapter(comicChapterAdapter);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void Fail() {

    }
    //add Manh

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_chapter, menu);
        MenuItem followItem = menu.findItem(R.id.addFollow);
        Truyentranh truyentranh = new Truyentranh(c_comic.getM_name(),
                c_comic.getM_chap(),c_comic.getM_linkImage());
        isFollowed = haveFollowed(truyentranh);
        if(isFollowed){
            followItem.setIcon(R.drawable.ic_unfollow);
        }else{
            followItem.setIcon(R.drawable.ic_follow);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.addFollow:
                Truyentranh truyentranh = new Truyentranh(c_comic.getM_name(),
                        c_comic.getM_chap(),c_comic.getM_linkImage());
                isFollowed = haveFollowed(truyentranh);
                if(isFollowed){
                    mDBHelper.deleteFav(truyentranh);
                    Toast.makeText(this, "Đã bỏ theo dõi", Toast.LENGTH_SHORT).show();
                }else {
                    mDBHelper.addFav(truyentranh);
                    Toast.makeText(this, "Đã theo dõi", Toast.LENGTH_SHORT).show();
                }
                isFollowed = !isFollowed;
                ChapterActivity.this.invalidateOptionsMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    private boolean haveFollowed(Truyentranh truyentranh){
        ArrayList<Truyentranh> listFollowed = new ArrayList<>();
        mDBHelper.layTruyen();
        for (int i = 0; i < mDBHelper.mList.size(); i++)
        {
            listFollowed.add(mDBHelper.mList.get(i));
        }
        for (int i = 0; i < listFollowed.size(); i++){
            if(listFollowed.get(i).getM_name().equals(truyentranh.getM_name()))
                return true;
        }
        return false;
    }
}