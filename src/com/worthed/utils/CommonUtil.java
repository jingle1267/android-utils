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


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

/**
 * 通用工具类
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

}
