package com.worthed.demo;

import com.worthed.R;
import com.worthed.util.ViewFinder;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView title;
    private Button bitmapBtn;
    private ImageView imageView;

    private ViewFinder finder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		finder = new ViewFinder(this);
		title = finder.find(R.id.textView1);
		bitmapBtn = finder.find(R.id.btn_bitmap);
		imageView = finder.find(R.id.imageView1);

        title.setText("Demo");
        bitmapBtn.setText("BitmapDemo");
        imageView.setImageResource(R.drawable.beautiful);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, BitmapActivity.class);
        startActivity(intent);
    }

}
