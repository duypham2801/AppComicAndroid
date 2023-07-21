package com.example.appdoctruyen.LocalDatabase;



import static com.example.appdoctruyen.LocalDatabase.DBSchema.*;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.appdoctruyen.Object.Truyentranh;

import java.util.Date;
import java.util.UUID;

public class DBCursorWrapper extends CursorWrapper {
    public DBCursorWrapper(Cursor cursor){
        super(cursor);
    }
    public Truyentranh getTruyen(){
        String name = getString(getColumnIndex(ComicTable.Cols.NAME));
        String chapter = getString(getColumnIndex(ComicTable.Cols.CNAME));
        String image = getString(getColumnIndex(ComicTable.Cols.IMAGE));


        Truyentranh truyentranh = new Truyentranh(name, chapter, image);

        return truyentranh;
    }
}
