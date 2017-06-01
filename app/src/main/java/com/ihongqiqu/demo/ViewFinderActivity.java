package com.ihongqiqu.demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ihongqiqu.util.ViewFinder;

/**
 * viewFinder测试
 * <p/>
 * Created by zhenguo on 14/10/30.
 */
public class ViewFinderActivity extends Activity {

    private TextView title;
    private Button bitmapBtn;
    private ImageView imageView;

    private ViewFinder finder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_finder);

        finder = new ViewFinder(this);

        title = finder.find(R.id.textView1);
        bitmapBtn = finder.find(R.id.btn_bitmap);
        imageView = finder.find(R.id.imageView1);

        title.setText("text");
        bitmapBtn.setText("button");
        imageView.setImageResource(R.mipmap.beautiful);

    }
}
