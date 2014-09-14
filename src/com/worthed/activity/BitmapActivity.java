/**
 * Copyright 2014 Zhenguo Jin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.worthed.activity;

import com.worthed.R;
import com.worthed.util.BitmapUtil;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

/**
 * @author jingle1267@163.com
 * 
 */
public class BitmapActivity extends Activity {

	private final String TAG = BitmapActivity.class.getSimpleName(); 
	
	private ImageView imageViewOrigin, imageViewCombine;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bitmap_layout);
		imageViewOrigin = (ImageView) findViewById(R.id.iv_round_origin);
		imageViewCombine = (ImageView) findViewById(R.id.iv_round_combine);
		testProcess();
		testCombine();
	}

	private void testProcess() {
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.beautiful);
		imageViewOrigin.setImageBitmap(BitmapUtil.getRoundBitmap(bitmap));
		bitmap.recycle();
	}

	private void testCombine() {
		Bitmap bitmap = BitmapUtil.getRoundBitmap(BitmapFactory.decodeResource(getResources(),
				R.drawable.beautiful));
		Bitmap mask = BitmapFactory.decodeResource(getResources(),
				R.drawable.sea);
		
		Log.d(TAG, "foreground width - height : " + bitmap.getWidth() + " - " + bitmap.getHeight());
		Log.d(TAG, "background width - height : " + mask.getWidth() + " - " + mask.getHeight());
		
		imageViewCombine.setImageBitmap(BitmapUtil.combineImagesToSameSize(mask, bitmap));
		bitmap.recycle();
		mask.recycle();
	}

}
