package com.lbq.okhttp3.request;

import java.util.Map;

public interface HttpHeaders
{
    HttpRequestBuilder headers(Map<String, String> map);
    HttpRequestBuilder addHeader(String key, int value);
    HttpRequestBuilder addHeader(String key, String value);
}
