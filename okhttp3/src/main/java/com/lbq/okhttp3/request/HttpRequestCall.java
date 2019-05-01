package com.lbq.okhttp3.request;

import com.lbq.okhttp3.OkHttp3;
import com.lbq.okhttp3.callback.BeanCallBack;
import com.lbq.okhttp3.callback.CallBack;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class HttpRequestCall
{
    private Call call;
    private OkHttpClient client;
    private okhttp3.Request request;
    private HttpRequest httpRequest;

    private long timeOut;
    private long readTimeOut;
    private long writeTimeOut;
    private long defaultTimeOut = 10_000L;

    public HttpRequestCall(HttpRequest httpRequest)
    {
        this.httpRequest = httpRequest;
    }
    public HttpRequestCall setTimeOut(long timeOut)
    {
        this.timeOut = timeOut;
        return this;
    }
    public HttpRequestCall setReadTimeOut(long readTimeOut)
    {
        this.readTimeOut = readTimeOut;
        return this;
    }
    public HttpRequestCall setWriteTimeOut(long writeTimeOut)
    {
        this.writeTimeOut = writeTimeOut;
        return this;
    }

    public Call getCall()
    {
        return call;
    }
    public void cancel()
    {
        if (call != null)
            call.cancel();
    }
    public HttpRequest getHttpRequest()
    {
        return httpRequest;
    }

    public void execute(CallBack callBack)
    {
        RequestBody requestBody = httpRequest.onRequestBody();
        RequestBody requestData = httpRequest.onRequestBody(requestBody, callBack);
        request = httpRequest.onRequest(requestData);

        if (timeOut > 0 || readTimeOut > 0 || writeTimeOut > 0)
        {
            OkHttpClient.Builder builder = OkHttp3.getInstance().getOkHttpClient().newBuilder();
            builder.connectTimeout(timeOut > 0 ? timeOut : defaultTimeOut, TimeUnit.MILLISECONDS);
            builder.readTimeout(readTimeOut > 0 ? readTimeOut : defaultTimeOut, TimeUnit.MILLISECONDS);
            builder.writeTimeout(writeTimeOut > 0 ? writeTimeOut : defaultTimeOut, TimeUnit.MILLISECONDS);
            client = builder.build();
            call = client.newCall(request);
        }
        else
        {
            call = OkHttp3.getInstance().getOkHttpClient().newCall(request);
        }
        OkHttp3.getInstance().execute(this, callBack);
    }
    public <T> void execute(Class<T> clazz, BeanCallBack<T> callBack)
    {
        execute(callBack == null ? null : callBack.setClazz(clazz));
    }

    public void callBack(CallBack callBack)
    {
        RequestBody requestBody = httpRequest.onRequestBody();
        RequestBody requestData = httpRequest.onRequestBody(requestBody, callBack);
        request = httpRequest.onRequest(requestData);

        if (timeOut > 0 || readTimeOut > 0 || writeTimeOut > 0)
        {
            OkHttpClient.Builder builder = OkHttp3.getInstance().getOkHttpClient().newBuilder();
            builder.connectTimeout(timeOut > 0 ? timeOut : defaultTimeOut, TimeUnit.MILLISECONDS);
            builder.readTimeout(readTimeOut > 0 ? readTimeOut : defaultTimeOut, TimeUnit.MILLISECONDS);
            builder.writeTimeout(writeTimeOut > 0 ? writeTimeOut : defaultTimeOut, TimeUnit.MILLISECONDS);
            client = builder.build();
            call = client.newCall(request);
        }
        else
        {
            call = OkHttp3.getInstance().getOkHttpClient().newCall(request);
        }
        OkHttp3.getInstance().callBack(this, callBack);
    }
    public <T> void callBack(Class<T> clazz, BeanCallBack<T> callBack)
    {
        callBack(callBack == null ? null : callBack.setClazz(clazz));
    }
}
