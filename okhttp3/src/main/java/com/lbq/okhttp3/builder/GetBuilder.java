package com.lbq.okhttp3.builder;

import android.net.Uri;

import com.lbq.okhttp3.request.GetRequest;
import com.lbq.okhttp3.request.HttpParams;
import com.lbq.okhttp3.request.HttpRequestBuilder;
import com.lbq.okhttp3.request.HttpRequestCall;
import java.util.Map;

public class GetBuilder extends HttpRequestBuilder<GetBuilder> implements HttpParams
{
    @Override
    public HttpRequestCall build()
    {
        if (params.size() > 0)
        {
            Uri.Builder builder = Uri.parse(url).buildUpon();
            for (String key : params.keySet())
            {
                builder.appendQueryParameter(key, params.get(key));
            }
            this.url = builder.build().toString();
        }
        return new GetRequest(id, tag, this.url, json, headers, params).build();
    }
    @Override
    public GetBuilder params(Map<String, String> map)
    {
        this.params = map;
        return this;
    }
    @Override
    public GetBuilder addParam(String key, int value)
    {
        this.params.put(key, String.valueOf(value));
        return this;
    }
    @Override
    public GetBuilder addParam(String key, String value)
    {
        this.params.put(key, value);
        return this;
    }
}
