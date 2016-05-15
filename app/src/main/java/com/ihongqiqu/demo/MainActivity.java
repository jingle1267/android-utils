package com.ihongqiqu.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ihongqiqu.util.AppUtils;
import com.ihongqiqu.util.NetUtil;
import com.ihongqiqu.util.ViewFinder;
import com.worthed.R;

import java.util.Map;

/**
 * 测试代码
 */
public class MainActivity extends Activity {

    private TextView title;
    private Button btnBitmap, btnViewFinder;

    private ViewFinder finder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        finder = new ViewFinder(this);
        title = finder.find(R.id.title);
        btnBitmap = finder.find(R.id.btn_bitmap);
        btnViewFinder = finder.find(R.id.btn_view_finder);

        title.setText("Demos:");
        btnBitmap.setText("BitmapDemo");
        btnViewFinder.setText("ViewFinderDemo");

        Log.d("MainActivity", AppUtils.getUUID(this));
        netTest();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_bitmap) {
            Intent intent = new Intent(this, BitmapActivity.class);
            startActivity(intent);
        } else if (i == R.id.btn_view_finder) {
            Intent intent1 = new Intent(this, ViewFinderActivity.class);
            startActivity(intent1);
        }
    }

    void netTest() {
        String url = "http://baidu.com";
        String url2 = "ftp://baidu.com?a=1&b=";
        String url3 = "https://baidu.com?a=1&b=";
        String url4 = "010%";
        String url5 = " ";
        Log.d("MainActivity", "NetUtil.isUrl(url):" + NetUtil.isUrl(url));
        Log.d("MainActivity", "NetUtil.isUrl(url2):" + NetUtil.isUrl(url2));
        Log.d("MainActivity", "NetUtil.isUrl(url3):" + NetUtil.isUrl(url3));
        Log.d("MainActivity", "NetUtil.isUrl(url4):" + NetUtil.isUrl(url4));
        Log.d("MainActivity", "NetUtil.isUrl(url5):" + NetUtil.isUrl(url5));

        Map<String, String> params = NetUtil.getUrlParams("http://www.baidu.com/abc/c.html?a=1&b=&c=");
        for (String key : params.keySet()) {
            String value = params.get(key);
            Log.d("MainActivity", "key-value : " + key + "-" + value);
        }
    }

}
