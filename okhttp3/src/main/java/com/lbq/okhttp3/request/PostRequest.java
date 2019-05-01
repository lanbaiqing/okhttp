package com.lbq.okhttp3.request;

import com.lbq.okhttp3.builder.PostBuilder;
import com.lbq.okhttp3.callback.CallBack;
import com.lbq.okhttp3.utils.Platform;

import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PostRequest extends HttpRequest
{
    private List<PostBuilder.Files> files;
    public PostRequest(int id, String tag, String url, String json, Map<String, String> headers, Map<String, String> params, List<PostBuilder.Files> list)
    {
        super(id, tag, url, json, headers, params);
        this.files = list;
    }
    @Override
    public RequestBody onRequestBody()
    {
        if (json != null)
        {
            return FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        }
        else if (files.size() == 0)
        {
            FormBody.Builder builder = new FormBody.Builder();
            for (String key : params.keySet())
            {
                String value = params.get(key);
                if (value == null)
                    throw new IllegalArgumentException(String.format("params error {'url':'%s', 'key':'%s','value':null}", url, key));
                else
                    builder.add(key, value);
            }
            return builder.build();
        }
        else
        {
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            for (String key : params.keySet())
            {
                String value = params.get(key);
                if (value == null)
                    throw new IllegalArgumentException(String.format("params error {'url':'%s', 'key':'%s','value':null}", url, key));
                else
                    builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + key + "\""), RequestBody.create(null, value));
            }
            for (PostBuilder.Files file : files)
            {
                builder.addFormDataPart(file.name, file.fileName, RequestBody.create(MediaType.parse(getMediaType(file.fileName)), file.file));
            }
            return builder.build();
        }
    }
    @Override
    public RequestBody onRequestBody(RequestBody requestBody, final CallBack callBack)
    {
        HttpRequestData requestData = new HttpRequestData(requestBody, new HttpRequestData.Listener()
        {
            @Override
            public void onProgress(final long total, final long index)
            {
                Platform.getPlatform().defaultCallbackExecutor().execute(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        callBack.onProgress(id, total, index);
                    }
                });
            }
        });
        return requestData;
    }
    @Override
    public Request onRequest(RequestBody requestBody)
    {
        return builder.post(requestBody).build();
    }
    public String getMediaType(String path)
    {
        try
        {
            return URLConnection.getFileNameMap().getContentTypeFor(URLEncoder.encode(path, "UTF-8"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "application/octet-stream";
    }
}
