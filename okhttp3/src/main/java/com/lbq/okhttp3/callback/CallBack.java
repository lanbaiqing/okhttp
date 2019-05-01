package com.lbq.okhttp3.callback;

import okhttp3.Call;
import okhttp3.Response;

public abstract class CallBack<T>
{
    public abstract T onParse(Response response, int id) throws Exception;
    public abstract void onResponse(int id, T response);
    public abstract void onFailure(int id, Call call, Exception e, String data);
    public void onProgress(int id, long total, long index)
    {

    }
}
