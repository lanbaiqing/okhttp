package com.lbq.okhttp3.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import okhttp3.Response;

public abstract class BitmapCallBack extends CallBack<Bitmap>
{
    @Override
    public Bitmap onParse(Response response, int id)
    {
        if (response.body() == null)
            return null;
        else
            return BitmapFactory.decodeStream(response.body().byteStream());
    }
}
