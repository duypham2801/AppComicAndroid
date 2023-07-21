package com.example.appdoctruyen.API;

import android.os.AsyncTask;

import com.example.appdoctruyen.Interface.GetApi;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ApiGetComic extends AsyncTask<Void,Void,Void>
{
    String data;
    GetApi getApi;

    String link;

    public ApiGetComic(GetApi getApi)
    {
        this.getApi = getApi;
        this.getApi.Start();
    }


    @Override
    protected Void doInBackground(Void... voids)
    {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                //.url("https://api.jsonserve.com/_IdOGm")
                .url("https://netcomics.000webhostapp.com/ApiNetcomics.php")
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
            this.getApi.Fail();
        }
        else
            this.getApi.Finish(data);
    }
}
