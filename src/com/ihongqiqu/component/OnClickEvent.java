package com.ihongqiqu.component;

import android.view.View;

/**
 * 防止Button的频繁点击,多次执行点击事件
 * <p/>
 * Created by zhenguo on 4/16/16.
 */
public abstract class OnClickEvent implements View.OnClickListener {

    public static long lastTime;

    public abstract void singleClick(View v);

    @Override
    public void onClick(View v) {
        if (onDoubClick()) {
            return;
        }
        singleClick(v);
    }

    public boolean onDoubClick() {
        boolean flag = false;
        long time = System.currentTimeMillis() - lastTime;

        if (time > 500) {
            flag = true;
        }
        lastTime = System.currentTimeMillis();
        return flag;
    }

}
