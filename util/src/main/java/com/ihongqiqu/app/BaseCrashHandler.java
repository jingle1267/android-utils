/**
 * Copyright 2014 Zhenguo Jin
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

import android.content.Context;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;

import com.ihongqiqu.util.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Date;

/**
 * 在Application中统一捕获异常，保存到文件中下次再打开时上传
 *
 * @author jingle1267@163.com
 */
public class BaseCrashHandler implements UncaughtExceptionHandler {

    /**
     * 是否开启日志输出,在Debug状态下开启, 在Release状态下关闭以提示程序性能
     */
    public static final boolean DEBUG = true;

    /**
     * 系统默认的UncaughtException处理类
     */
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    /**
     * CrashHandler实例
     */
    private static BaseCrashHandler INSTANCE;

    private Context mContext;

    /**
     * 保证只有一个CrashHandler实例
     */
    private BaseCrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static BaseCrashHandler getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new BaseCrashHandler();
        }
        return INSTANCE;
    }

    /**
     * 初始化,注册Context对象, 获取系统默认的UncaughtException处理器, 设置该CrashHandler为程序的默认处理器
     *
     * @param context 上下文
     */
    public void init(Context context) {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else { // 如果自己处理了异常，则不会弹出错误对话框，则需要手动退出app
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(10);
        }

    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @return true代表处理该异常，不再向上抛异常，
     * false代表不处理该异常(可以将该log信息存储起来)然后交给上层(这里就到了系统的异常处理)去处理，
     * 简单来说就是true不会弹出那个错误提示框，false就会弹出
     */
    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return false;
        }

        final StackTraceElement[] stack = ex.getStackTrace();
        final String message = ex.getMessage();

        // 使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {

                Looper.prepare();

                Toast.makeText(mContext,
                        "程序出现异常,3秒后即将关闭该页面,请联系我们,我们会尽快处理,给你带来的不便请原谅!",
                        Toast.LENGTH_LONG).show();
                LogUtils.i(message);
                dosave(stack, message);
                Looper.loop();
            }

            private void dosave(final StackTraceElement[] stack,
                                final String message) {
                // 保存出错信息到文件
                String fileName = "crash-" + new Date().getTime() + ".txt";
                File file = new File(Environment.getExternalStorageDirectory()
                        + "/log");
                if (!file.exists()) {
                    file.mkdirs();
                }
                File file1 = new File(file, fileName);
                try {
                    FileOutputStream fos = new FileOutputStream(file1, true);
                    fos.write(message.getBytes());
                    for (int i = 0; i < stack.length; i++) {
                        fos.write(stack[i].toString().getBytes());
                    }

                    fos.flush();
                    fos.close();
                } catch (Exception e) {
                }
            }

        }.start();
        return true;
    }

    /**
     * 获取手机信息
     *
     * @return
     */
    public String getPhoneInfo() {
        String phoneInfo = "Product: " + android.os.Build.PRODUCT;
        phoneInfo += ", CPU_ABI: " + android.os.Build.CPU_ABI;
        phoneInfo += ", TAGS: " + android.os.Build.TAGS;
        phoneInfo += ", VERSION_CODES.BASE: "
                + android.os.Build.VERSION_CODES.BASE;
        phoneInfo += ", MODEL: " + android.os.Build.MODEL;
        phoneInfo += ", SDK: " + android.os.Build.VERSION.SDK_INT;
        phoneInfo += ", VERSION.RELEASE: " + android.os.Build.VERSION.RELEASE;
        phoneInfo += ", DEVICE: " + android.os.Build.DEVICE;
        phoneInfo += ", DISPLAY: " + android.os.Build.DISPLAY;
        phoneInfo += ", BRAND: " + android.os.Build.BRAND;
        phoneInfo += ", BOARD: " + android.os.Build.BOARD;
        phoneInfo += ", FINGERPRINT: " + android.os.Build.FINGERPRINT;
        phoneInfo += ", ID: " + android.os.Build.ID;
        phoneInfo += ", MANUFACTURER: " + android.os.Build.MANUFACTURER;
        phoneInfo += ", USER: " + android.os.Build.USER;
        return phoneInfo;
    }
}