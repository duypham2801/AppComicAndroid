package com.example.appdoctruyen.API;

import android.os.AsyncTask;

import com.example.appdoctruyen.Interface.GetApi;
import com.example.appdoctruyen.Interface.GetChapApi;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ApiGetChapterComic extends AsyncTask<Void,Void,Void>
{
    String data;
    GetChapApi getChapApi;

    String c_idChap;

    public ApiGetChapterComic(GetChapApi getChapApi, String c_idChap)
    {
        this.getChapApi = getChapApi;
        this.getChapApi.Start();
        this.c_idChap = c_idChap;

    }
    @Override
    protected Void doInBackground(Void... voids)
    {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://netcomics.000webhostapp.com/ApiNetchaps.php?idComic=" + c_idChap)
                .build();
        data = null;
        try
        {
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            data = body.string(); //1
        }catch (IOException e)
        {
            data = null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        if(data==null)
        {
            this.getChapApi.Fail();
        }
        else
            this.getChapApi.Finish(data);
    }
}
