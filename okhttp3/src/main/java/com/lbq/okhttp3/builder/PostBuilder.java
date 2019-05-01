package com.lbq.okhttp3.builder;

import com.lbq.okhttp3.request.HttpParams;
import com.lbq.okhttp3.request.HttpRequestBuilder;
import com.lbq.okhttp3.request.HttpRequestCall;
import com.lbq.okhttp3.request.PostRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PostBuilder extends HttpRequestBuilder<PostBuilder> implements HttpParams
{
    private final List<Files> files = new ArrayList<>();
    @Override
    public HttpRequestCall build()
    {
        return new PostRequest(id, tag, url, json, headers, params, files).build();
    }
    @Override
    public PostBuilder json(String json)
    {
        return super.json(json);
    }
    @Override
    public PostBuilder params(Map<String, String> map)
    {
        this.params = map;
        return this;
    }
    @Override
    public PostBuilder addParam(String key, int value)
    {
        this.params.put(key, String.valueOf(value));
        return this;
    }
    @Override
    public PostBuilder addParam(String key, String value)
    {
        this.params.put(key, value);
        return this;
    }
    public PostBuilder files(int name, Map<String, File> map)
    {
        for (String fileName : map.keySet())
        {
            this.files.add(new Files(String.valueOf(name), fileName, map.get(fileName)));
        }
        return this;
    }
    public PostBuilder files(String name, Map<String, File> map)
    {
        for (String fileName : map.keySet())
        {
            this.files.add(new Files(name, fileName, map.get(fileName)));
        }
        return this;
    }
    public PostBuilder addFile(int name, File file)
    {
        this.files.add(new Files(String.valueOf(name), file));
        return this;
    }
    public PostBuilder addFile(String name, File file)
    {
        this.files.add(new Files(name, file));
        return this;
    }
    public PostBuilder addFile(String name, String fileName, File file)
    {
        this.files.add(new Files(name, fileName, file));
        return this;
    }

    public static class Files
    {
        public File file;
        public String name;
        public String fileName;
        public Files(String name, String fileName, File file)
        {
            this.file = file;
            this.name = name;
            this.fileName = fileName;
        }
        public Files(String name, File file)
        {
            this.name = name;
            this.file = file;
            this.fileName = file.getName();
        }
        @Override
        public String toString()
        {
            return String.format("{'name':'%s','fileName':'%s','file':'%s'}", name, fileName, file.getAbsolutePath());
        }
    }
}
