package com.lbq.okhttp3;

import com.lbq.okhttp3.builder.GetBuilder;
import com.lbq.okhttp3.builder.PostBuilder;
import com.lbq.okhttp3.callback.CallBack;
import com.lbq.okhttp3.request.HttpRequestCall;
import com.lbq.okhttp3.utils.Platform;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

@SuppressWarnings("unchecked")
public class OkHttp3
{
    private static OkHttp3 okHttp3;
    private OkHttpClient okHttpClient;
    public OkHttp3(OkHttpClient client)
    {
        this.okHttpClient = client != null ? client : new OkHttpClient();
    }
    public static OkHttp3 setOkHttpClient(OkHttpClient client)
    {
        synchronized (OkHttp3.class)
        {
            if (okHttp3 == null)
                okHttp3 = new OkHttp3(client);
        }
        return okHttp3;
    }
    public static OkHttp3 getInstance()
    {
        return setOkHttpClient(null);
    }
    public OkHttpClient getOkHttpClient()
    {
        return okHttpClient;
    }
    public void callBack(HttpRequestCall requestCall, CallBack callBack)
    {
        Response response = null;
        int id = requestCall.getHttpRequest().getId();
        try
        {
            response = requestCall.getCall().execute();
            if (requestCall.getCall().isCanceled())
            {
                callBack.onFailure(id, requestCall.getCall(), new IOException("canceled"), "");
            }
            else if (!response.isSuccessful())
            {
                if (response.body() == null)
                    callBack.onFailure(id, requestCall.getCall(), new IOException("request failed , response's code is : " + response.code()), "unknown");
                else
                    callBack.onFailure(id, requestCall.getCall(), new IOException("request failed , response's code is : " + response.code()), new String(response.body().bytes()));
            }
            else
            {
                callBack.onResponse(id, callBack.onParse(response, id));
            }
        }
        catch (Exception e)
        {
            callBack.onFailure(id, requestCall.getCall(), e, e.toString());
        }
        finally
        {
            if (response != null)
                response.close();
        }
    }
    public void execute(final HttpRequestCall requestCall, final CallBack callBack)
    {
        final int id = requestCall.getHttpRequest().getId();
        requestCall.getCall().enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                sendFailure(id, call, e, e.toString(), callBack);
            }
            @Override
            public void onResponse(Call call, Response response)
            {
                try
                {
                    if (call.isCanceled())
                    {
                        sendFailure(id, call, new IOException("canceled"), "", callBack);
                    }
                    else if (!response.isSuccessful())
                    {
                        if (response.body() == null)
                            sendFailure(id, call, new IOException("request failed , response's code is : " + response.code()), "unknown", callBack);
                        else
                            sendFailure(id, call, new IOException("request failed , response's code is : " + response.code()), new String(response.body().bytes()), callBack);
                    }
                    else
                    {
                        sendSuccess(id, callBack.onParse(response, id), callBack);
                    }
                }
                catch (Exception e)
                {
                    sendFailure(id, call, e, e.toString(), callBack);
                }
                finally
                {
                    if (response.body() != null)
                        response.close();
                }
            }
        });
    }
    private void sendFailure(final int id, final Call call, final Exception e, final String errorContent, final CallBack callBack)
    {
        Platform.getPlatform().execute(new Runnable() {
            @Override
            public void run() {
                callBack.onFailure(id, call, e, errorContent);
            }
        });
    }
    private void sendSuccess(final int id, final Object object, final CallBack callBack)
    {
        Platform.getPlatform().execute(new Runnable() {
            @Override
            public void run()
            {
                callBack.onResponse(id, object);
            }
        });
    }
    public void cancelTag(String tag)
    {
        for (Call call : okHttpClient.dispatcher().queuedCalls())
        {
            if (tag.equals(call.request().tag()))
            {
                call.cancel();
            }
        }
        for (Call call : okHttpClient.dispatcher().runningCalls())
        {
            if (tag.equals(call.request().tag()))
            {
                call.cancel();
            }
        }
    }
    public static GetBuilder get()
    {
        return new GetBuilder();
    }
    public static PostBuilder post()
    {
        return new PostBuilder();
    }
}
