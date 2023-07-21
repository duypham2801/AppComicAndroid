package com.example.appdoctruyen.LocalDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.appdoctruyen.LocalDatabase.DBSchema.ComicTable;
import com.example.appdoctruyen.Object.Truyentranh;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public List<Truyentranh> mList;
    private static final int VERSION = 1;

    public DBHelper(Context context, String DATABASE_NAME) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ComicTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                ComicTable.Cols.NAME + ", " +
                ComicTable.Cols.CNAME + ", " +
                ComicTable.Cols.IMAGE +
                ")"
        );
    }

    public void addFav(Truyentranh truyentranh){
        ContentValues contentValues = getContentValues(truyentranh);
        SQLiteDatabase mDatabase = this.getWritableDatabase();
        mDatabase.insert(ComicTable.NAME, null, contentValues);

        mDatabase.close();
    }
public void layTruyen(){
        mList = getTruyens();
}
    public List<Truyentranh> getTruyens(){
        List<Truyentranh> truyentranhs = new ArrayList<>();

        DBCursorWrapper cursor = queryTruyen(null, null);

        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                truyentranhs.add(cursor.getTruyen());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return truyentranhs;
    }
    public void deleteFav(Truyentranh truyentranh) {
        SQLiteDatabase mDatabase = this.getWritableDatabase();
        mDatabase.delete(
                ComicTable.NAME,
                ComicTable.Cols.NAME + " = ?",
                new String[] {truyentranh.getM_name()}
        );

        mDatabase.close();
    }
    private DBCursorWrapper queryTruyen(String whereClause, String[] whereArgs){
        SQLiteDatabase mDatabase = this.getReadableDatabase();
        Cursor cursor = mDatabase.query(
                ComicTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return  new DBCursorWrapper(cursor);
    }
    private static ContentValues getContentValues(Truyentranh truyentranh){
        ContentValues values = new ContentValues();
        values.put(ComicTable.Cols.NAME, truyentranh.getM_name());
        values.put(ComicTable.Cols.CNAME, truyentranh.getM_chap());
        values.put(ComicTable.Cols.IMAGE, truyentranh.getM_linkImage());

        return values;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
