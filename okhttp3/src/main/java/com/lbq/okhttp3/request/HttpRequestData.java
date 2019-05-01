package com.lbq.okhttp3.request;

import java.io.IOException;

import okhttp3.MediaType;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;

public class HttpRequestData extends okhttp3.RequestBody
{
    private Listener listener;
    private ForwardingSink forwardingSink;
    private okhttp3.RequestBody requestBody;

    public HttpRequestData(okhttp3.RequestBody requestBody, Listener listener)
    {
        this.listener = listener;
        this.requestBody = requestBody;
    }
    @Override
    public void writeTo(BufferedSink sink) throws IOException
    {
        forwardingSink = new ForwardingSink(sink);
        BufferedSink bufferedSink = Okio.buffer(forwardingSink);
        requestBody.writeTo(bufferedSink);
        bufferedSink.flush();
    }
    @Override
    public MediaType contentType()
    {
        return requestBody.contentType();
    }
    @Override
    public long contentLength() throws IOException
    {
        return requestBody.contentLength();
    }
    protected final class ForwardingSink extends okio.ForwardingSink
    {
        private long byteCount;
        public ForwardingSink(Sink delegate)
        {
            super(delegate);
        }
        @Override
        public void write(Buffer source, long byteCount) throws IOException
        {
            super.write(source, byteCount);
            this.byteCount += byteCount;
            listener.onProgress(contentLength(), this.byteCount);
        }
    }

    public interface Listener
    {
        void onProgress(long total, long index);
    }
}
