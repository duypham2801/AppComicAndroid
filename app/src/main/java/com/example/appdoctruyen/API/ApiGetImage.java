package com.example.appdoctruyen.API;

import android.os.AsyncTask;

import com.example.appdoctruyen.Interface.GetApi;
import com.example.appdoctruyen.Interface.GetApiImage;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ApiGetImage extends AsyncTask<Void,Void,Void>
{
    String data;
    GetApiImage getApiImage;
    String c_idChap;

    public ApiGetImage(GetApiImage getApiImage,String c_idChap)
    {
        this.getApiImage = getApiImage;
        this.c_idChap = c_idChap;
        this.getApiImage.Start();

    }
    @Override
    protected Void doInBackground(Void... voids)
    {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://netcomics.000webhostapp.com/ApiNetimage.php?c_idChap="+c_idChap)
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
            this.getApiImage.Fail();
        }
        else
            this.getApiImage.Finish(data);
    }
}
