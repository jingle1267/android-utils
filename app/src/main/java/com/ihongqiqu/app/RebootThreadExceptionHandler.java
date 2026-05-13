/**
 * Copyright 2014 Zhenguo Jin (jinzhenguo1990@gmail.com)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ihongqiqu.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * 重启线程异常处理器，当发生未知异常时会提示异常信息并在一秒钟后重新启动应用
 * <br>使用此功能的第一步需要你在AndroidMainfest.xml中注册me.xiaopan.android.content.StartApplicationBrocastReceiver广播（注意不要任何的filter）
 * <br>第二步就是在你的Application的onCreate()方法中加上new RebootThreadExceptionHandler(getBaseContext());即可
 *
 * @author zhenguo
 */
public class RebootThreadExceptionHandler implements UncaughtExceptionHandler {

    private static final String TAG = "RebootThreadExcHandler";

    private final Context context;
    private final String hintText;
    private final UncaughtExceptionHandler delegate;

    public RebootThreadExceptionHandler(Context context, String hintText) {
        this.context = context.getApplicationContext();
        this.hintText = hintText;
        this.delegate = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public RebootThreadExceptionHandler(Context context) {
        this(context, null);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e(TAG, "Uncaught exception", ex);

        AlarmManager alarmManager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.SECOND, 1);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                PendingIntent.getBroadcast(context, 0, new Intent(context,
                        StartAppReceiver.class), 0));

        if (!TextUtils.isEmpty(hintText)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    Toast.makeText(context, hintText, Toast.LENGTH_SHORT)
                            .show();
                    Looper.loop();
                }
            }).start();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Log.w(TAG, "Interrupted while waiting for toast", e);
            }
        }

        if (delegate != null && delegate != this) {
            try {
                delegate.uncaughtException(thread, ex);
            } catch (Throwable t) {
                Log.e(TAG, "Delegate uncaughtException failed", t);
            }
        }

        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
