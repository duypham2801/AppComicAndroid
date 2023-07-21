package com.example.appdoctruyen.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.appdoctruyen.Object.ComicChapter;
import com.example.appdoctruyen.R;

import java.util.ArrayList;
import java.util.List;

public class ComicChapterAdapter extends ArrayAdapter<ComicChapter>{
    private Context ct;
    private ArrayList<ComicChapter> arr;

    public ComicChapterAdapter(@NonNull Context context, int resource, @NonNull List<ComicChapter> objects) {
        super(context, resource, objects);
        this.ct = context;
        this.arr = new ArrayList<>(objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater)ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_comic_chapter, null);
        }
        if (arr.size()>0)
        {
            TextView itemNameComic, itemRelease;
            itemNameComic = convertView.findViewById(R.id.item_nameComic);
            itemRelease = convertView.findViewById(R.id.item_release);
            ComicChapter comicChapter = arr.get(position);
            itemNameComic.setText(comicChapter.getC_nameChap());
            itemRelease.setText(comicChapter.getC_release());
        }
        return convertView;
    }
}
