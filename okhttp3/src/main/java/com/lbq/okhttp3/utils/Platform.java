/*
 * Copyright (C) 2013 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lbq.okhttp3.utils;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Platform
{
    private static final Platform platform = findPlatform();
    public static Platform getPlatform()
    {
        return platform;
    }
    public static Platform findPlatform()
    {
        try
        {
            Class.forName("android.os.Build");
            if (Build.VERSION.SDK_INT != 0)
            {
                return new Android();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new Platform();
    }
    public void execute(Runnable command)
    {
        defaultCallbackExecutor().execute(command);
    }
    public Executor defaultCallbackExecutor()
    {
        return Executors.newCachedThreadPool();
    }
    static class Android extends Platform
    {
        @Override
        public Executor defaultCallbackExecutor()
        {
            return new MainThreadExecutor();
        }
        static class MainThreadExecutor implements Executor
        {
            private Handler mHandler = new Handler(Looper.getMainLooper());
            @Override
            public void execute(Runnable command)
            {
                mHandler.post(command);
            }
        }
    }
}