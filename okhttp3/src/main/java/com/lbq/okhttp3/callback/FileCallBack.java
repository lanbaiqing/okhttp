package com.lbq.okhttp3.callback;

import com.lbq.okhttp3.utils.Platform;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import okhttp3.Response;

public abstract class FileCallBack extends CallBack<File>
{
    private String dir;
    private String name;
    public FileCallBack(String dir, String name)
    {
        this.dir = dir;
        this.name = name;
    }
    @Override
    public File onParse(Response response, int id) throws Exception
    {
        return saveFile(response, id);
    }
    public File saveFile(Response response, final int id) throws Exception
    {
        File dirs = new File(this.dir);
        File file = new File(this.dir, this.name);
        if (!dirs.exists())
        {
            dirs.mkdirs();
        }
        if (response.body() != null)
        {
            int len;
            int index = 0;
            final long total = response.body().contentLength();
            final byte[] buffer = new byte[1024];

            InputStream input = response.body().byteStream();
            FileOutputStream output = new FileOutputStream(file);

            while ((len = input.read(buffer)) != -1)
            {
                output.write(buffer, 0, len);
                sendProgress(id, total, index += len);
            }
        }
        return file;
    }
    private void sendProgress(final int id, final long total, final long index)
    {
        Platform.getPlatform().execute(new Runnable()
        {
            @Override
            public void run()
            {
                onProgress(id, total, index);
            }
        });
    }
}
