package com.lbq.okhttp3.request;

import com.lbq.okhttp3.callback.CallBack;

import java.util.Map;
import okhttp3.Request;
import okhttp3.RequestBody;

public class GetRequest extends HttpRequest
{
    public GetRequest(int id, String tag, String url, String json, Map<String, String> headers, Map<String, String> params)
    {
        super(id, tag, url, json, headers, params);
    }
    @Override
    public RequestBody onRequestBody()
    {
        return null;
    }
    public RequestBody onRequestBody(RequestBody requestBody, CallBack callBack)
    {
        return requestBody;
    }
    @Override
    public Request onRequest(RequestBody requestBody)
    {
        return builder.get().build();
    }
}
