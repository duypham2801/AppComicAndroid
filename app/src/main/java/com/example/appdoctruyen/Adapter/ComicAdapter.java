package com.example.appdoctruyen.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appdoctruyen.Object.Truyentranh;
import com.example.appdoctruyen.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ComicAdapter extends ArrayAdapter<Truyentranh> {

    private Context ct;
    private ArrayList<Truyentranh> arr;

    public ComicAdapter(Context context, int resource, List<Truyentranh> objects)
    {
        super(context,resource,objects);
        this.ct = context;
        this.arr = new ArrayList<>(objects);
    }
    /*
    public void sortComic(String s)
    {
        s = s.toUpperCase();
        int k = 0;
        int l = arr.size();
        for (int i= 0; i<l;++i)
        {
            Truyentranh t = arr.get(i);
            String name = t.getM_name().toUpperCase();
            if (name.indexOf(s)>=0)
            {
                arr.set(i,arr.get(k));
                arr.set(k,t);
                k++;
            }
        }
        notifyDataSetChanged();
    }

     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater)ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_truyen,null);
        }

        if(arr.size()>0)
        {
            Truyentranh truyenTranh = this.arr.get(position);
            TextView name = convertView.findViewById(R.id.nameComic);
            TextView nameChap = convertView.findViewById(R.id.nameChap);
            ImageView imgComic = convertView.findViewById(R.id.imgTruyen);

            name.setText(truyenTranh.getM_name());
            nameChap.setText(truyenTranh.getM_chap());
            Glide.with(this.ct).load(truyenTranh.getM_linkImage()).into(imgComic);
        }
        return convertView;
    }
}
