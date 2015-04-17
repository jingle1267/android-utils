package com.ihongqiqu.demo;

import com.ihongqiqu.util.ViewFinder;
import com.worthed.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_bitmap:
                Intent intent = new Intent(this, BitmapActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_view_finder:
                Intent intent1 = new Intent(this, ViewFinderActivity.class);
                startActivity(intent1);
                break;
        }
    }

}
