/**
 * Copyright 2014 Zhenguo Jin (jinzhenguo1990@gmail.com)
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
package com.worthed.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * SDcard操作工具类
 * 
 * @author jingle1267@163.com
 * 
 */
public class SDCardUtils {
	/**
	 * Check the SD card
	 * 
	 * @return
	 */
	public static boolean checkSDCardAvailable() {
		return android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}

	/**
	 * Check if the file is exists
	 * 
	 * @param filePath
	 * @param fileName
	 * @return
	 */
	public static boolean isFileExistsInSDCard(String filePath, String fileName) {
		boolean flag = false;
		if (checkSDCardAvailable()) {
			File file = new File(filePath, fileName);
			if (file.exists()) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * Write file to SD card
	 * 
	 * @param filePath
	 * @param filename
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static boolean saveFileToSDCard(String filePath, String filename,
			String content) throws Exception {
		boolean flag = false;
		if (checkSDCardAvailable()) {
			File dir = new File(filePath);
			if (!dir.exists()) {
				dir.mkdir();
			}
			File file = new File(filePath, filename);
			FileOutputStream outStream = new FileOutputStream(file);
			outStream.write(content.getBytes());
			outStream.close();
			flag = true;
		}
		return flag;
	}

	/**
	 * Read file as stream from SD card
	 * 
	 * @param fileName
	 *            String PATH =
	 *            Environment.getExternalStorageDirectory().getAbsolutePath() +
	 *            "/dirName";
	 * @return
	 */
	public static byte[] readFileFromSDCard(String filePath, String fileName) {
		byte[] buffer = null;
		try {
			if (checkSDCardAvailable()) {
				String filePaht = filePath + "/" + fileName;
				FileInputStream fin = new FileInputStream(filePaht);
				int length = fin.available();
				buffer = new byte[length];
				fin.read(buffer);
				fin.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer;
	}

	/**
	 * Delete file
	 * 
	 * @param filePath
	 * @param fileName
	 *            filePath =
	 *            android.os.Environment.getExternalStorageDirectory().getPath()
	 * @return
	 */
	public static boolean deleteSDFile(String filePath, String fileName) {
		File file = new File(filePath + "/" + fileName);
		if (file == null || !file.exists() || file.isDirectory())
			return false;
		return file.delete();
	}
}
