package com.lbq.okhttp3.request;

import com.lbq.okhttp3.callback.CallBack;

import java.util.Map;

import okhttp3.Request;
import okhttp3.RequestBody;

public abstract class HttpRequest
{
    protected int id;
    protected String tag;
    protected String url;
    protected String json;
    protected Map<String, String> headers;
    protected Map<String, String> params;
    protected okhttp3.Request.Builder builder = new okhttp3.Request.Builder();

    public HttpRequest(int id, String tag, String url, String json, Map<String, String> headers, Map<String, String> params)
    {
        this.id = id;
        this.tag = tag;
        this.url = url;
        this.json = json;
        this.headers = headers;
        this.params = params;
        this.builder.url(url).tag(tag);
        if (this.url == null)
            throw new IllegalArgumentException("url is empty");
        else
        {
            for (String key : headers.keySet())
            {
                String value = headers.get(key);
                if (value == null)
                    throw new IllegalArgumentException(String.format("headers error {'url':'%s','key':'%s','value':null}", url, key));
                else
                    builder.header(key, value);
            }
        }
    }
    public int getId()
    {
        return id;
    }
    public HttpRequestCall build()
    {
        return new HttpRequestCall(this);
    }

    public abstract Request onRequest(RequestBody requestBody);
    public abstract RequestBody onRequestBody();
    public abstract RequestBody onRequestBody(RequestBody requestBody, CallBack callBack);
}
