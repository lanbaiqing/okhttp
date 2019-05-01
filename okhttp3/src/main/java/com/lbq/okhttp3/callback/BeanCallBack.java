package com.lbq.okhttp3.callback;

import com.google.gson.Gson;

import okhttp3.Response;

public abstract class BeanCallBack<T> extends CallBack<T>
{
    private Class<T> clazz;
    public BeanCallBack<T> setClazz(Class<T> clazz)
    {
        this.clazz = clazz;
        return this;
    }
    @Override
    public T onParse(Response response, int id) throws Exception
    {
        if (response.body() == null)
            return null;
        String json = new String(response.body().bytes());
        return new Gson().fromJson(json, clazz);
    }
}
