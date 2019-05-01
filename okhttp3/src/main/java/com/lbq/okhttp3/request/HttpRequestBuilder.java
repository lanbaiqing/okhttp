package com.lbq.okhttp3.request;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public abstract class HttpRequestBuilder<T extends HttpRequestBuilder> implements HttpHeaders
{
    protected int id;
    protected String tag;
    protected String url;
    protected String json;

    protected Map<String, String> headers = new LinkedHashMap<>();
    protected Map<String, String> params = new LinkedHashMap<>();

    public T id(int id)
    {
        this.id = id;
        return (T) this;
    }
    public T tag(String tag)
    {
        this.tag = tag;
        return (T) this;
    }
    public T url(String url)
    {
        this.url = url;
        return (T) this;
    }

    public T json(String json)
    {
        this.json = json;
        return (T) this;
    }

    @Override
    public T headers(Map<String, String> map)
    {
        this.headers = map;
        return (T) this;
    }
    @Override
    public T addHeader(String key, int value)
    {
        this.headers.put(key, String.valueOf(value));
        return (T) this;
    }
    @Override
    public T addHeader(String key, String value)
    {
        this.headers.put(key, value);
        return (T) this;
    }
    public abstract HttpRequestCall build();
}
