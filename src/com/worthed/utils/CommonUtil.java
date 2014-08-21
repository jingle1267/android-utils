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
package com.worthed.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.widget.Toast;

/**
 * 
 * @author jingle1267@163.com
 * 
 */
public class CommonUtil {

	/**
	 * 是否有SDCard
	 * 
	 * @return
	 */
	public static boolean hasSDCard() {

		String status = Environment.getExternalStorageState();
		return status.equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 获取应用运行的最大内存
	 * 
	 * @return
	 */
	public static long getMaxMemory() {

		return Runtime.getRuntime().maxMemory() / 1024;
	}

	/**
	 * 检测网络状态
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkNetState(Context context) {

		boolean netstate = false;
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {

			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {

					if (info[i].getState() == NetworkInfo.State.CONNECTED) {

						netstate = true;
						break;
					}
				}
			}
		}
		return netstate;
	}

	public static void showToast(Context context, String tip) {

		Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
	}

	public static DisplayMetrics metric = new DisplayMetrics();

	/**
	 * 获取系统高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Activity context) {

		context.getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric.heightPixels;
	}

	/**
	 * 获取系统宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Activity context) {

		context.getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric.widthPixels;
	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {

		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 获取系统中所有的应用
	 * 
	 * @param context
	 * @return
	 */
	public static List<PackageInfo> getAllApps(Context context) {

		List<PackageInfo> apps = new ArrayList<PackageInfo>();
		PackageManager pManager = context.getPackageManager();
		List<PackageInfo> paklist = pManager.getInstalledPackages(0);
		for (int i = 0; i < paklist.size(); i++) {
			PackageInfo pak = (PackageInfo) paklist.get(i);
			if ((pak.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
				// customs applications
				apps.add(pak);
			}
		}
		return apps;
	}

	public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) width / w);
		float scaleHeight = ((float) height / h);
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
		return newbmp;
	}

	/**
	 * 获取本应用版本号
	 * 
	 * @param context
	 * @return
	 */
	public static String getVersionCode(Context context, int type) {

		try {

			PackageInfo pi = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			if (type == 1) {

				return String.valueOf(pi.versionCode);
			} else {

				return pi.versionName;
			}
		} catch (NameNotFoundException e) {

			e.printStackTrace();
			return null;
		}
	}

}
