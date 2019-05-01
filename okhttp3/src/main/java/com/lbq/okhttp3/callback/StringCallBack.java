package com.lbq.okhttp3.callback;

import okhttp3.Response;

public abstract class StringCallBack extends CallBack<String>
{
    @Override
    public String onParse(Response response, int id) throws Exception
    {
        if (response.body() == null)
            return null;
        else
            return new String(response.body().bytes());
    }
}
