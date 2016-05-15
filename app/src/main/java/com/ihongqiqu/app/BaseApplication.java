package com.ihongqiqu.app;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * 捕获应用异常Application
 * 在这里完成整个应用退出；在这里进行全局变量的传递；在这里完成低内存的释放；在这里捕获未抓住的异常；用于应用配置, 预加载处理
 *
 * @author jingle1267@163.com
 */

public class BaseApplication extends Application {

    /**
     * activity栈保存
     */
    public List<Activity> activityStack = null;

    @Override
    public void onCreate() {
        super.onCreate();
        // activity管理
        activityStack = new ArrayList<Activity>();
        // 异常处理
        BaseCrashHandler handler = BaseCrashHandler.getInstance();
        handler.init(this);

        // 程序异常关闭1s之后重新启动
        new RebootThreadExceptionHandler(getBaseContext());
    }

    @Override
    public void onTerminate() {

        super.onTerminate();

    }

    // 在内存低时,发送广播可以释放一些内存
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    // /退出整个应用
    public void exitApp() {
        for (Activity activity : activityStack) {
            if (activity != null) {
                activity.finish();
            }
        }
    }

}
