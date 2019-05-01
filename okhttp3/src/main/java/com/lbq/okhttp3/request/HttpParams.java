package com.lbq.okhttp3.request;

import java.util.Map;

public interface HttpParams
{
    HttpRequestBuilder params(Map<String, String> map);
    HttpRequestBuilder addParam(String key, int value);
    HttpRequestBuilder addParam(String key, String value);
}
