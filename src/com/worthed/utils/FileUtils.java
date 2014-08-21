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

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

/**
 * 文件操作工具类
 * 
 * @author jingle1267@163.com
 * 
 */
public class FileUtils {

	public static final int GB = 1024 * 1024 * 1024;
	public static final int MB = 1024 * 1024;
	public static final int KB = 1024;

	public static final int ICON_TYPE_ROOT = 1;
	public static final int ICON_TYPE_FOLDER = 2;
	public static final int ICON_TYPE_MP3 = 3;
	public static final int ICON_TYPE_MTV = 4;
	public static final int ICON_TYPE_JPG = 5;
	public static final int ICON_TYPE_FILE = 6;

	public static final String MTV_REG = "^.*\\.(mp4|3gp)$";
	public static final String MP3_REG = "^.*\\.(mp3|wav)$";
	public static final String JPG_REG = "^.*\\.(gif|jpg|png)$";

	private static final String FILENAME_REGIX = "^[^\\/?\"*:<>\\]{1,255}$";

	/**
	 * 删除文件或者空的文件夹
	 * 
	 * @param file
	 * @return
	 */
	public boolean deleteFile(File file) {

		return file.delete();
	}

	/**
	 * 递归删除文件和文件夹
	 * 
	 * @param file
	 *            要删除的根目录
	 */
	public void DeleteFile(File file) {
		if (file.exists() == false) {
			return;
		} else {
			if (file.isFile()) {
				file.delete();
				return;
			}
			if (file.isDirectory()) {
				File[] childFile = file.listFiles();
				if (childFile == null || childFile.length == 0) {
					file.delete();
					return;
				}
				for (File f : childFile) {
					DeleteFile(f);
				}
				file.delete();
			}
		}
	}

	/**
	 * 重命名文件和文件夹
	 * 
	 * @param file
	 * @param newFileName
	 * @return
	 */
	public boolean renameFile(File file, String newFileName) {
		if (newFileName.matches(FILENAME_REGIX)) {
			File newFile = null;
			if (file.isDirectory()) {
				newFile = new File(file.getParentFile(), newFileName);
			} else {
				String temp = newFileName
						+ file.getName().substring(
								file.getName().lastIndexOf('.'));
				newFile = new File(file.getParentFile(), temp);
			}
			if (file.renameTo(newFile)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 文件大小获取
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileSize(File file) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			int length = fis.available();
			if (length >= GB) {
				return String.format("%.2f GB", length * 1.0 / GB);
			} else if (length >= MB) {
				return String.format("%.2f MB", length * 1.0 / MB);
			} else {
				return String.format("%.2f KB", length * 1.0 / KB);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "未知";
	}

	/**
	 * 使用系统程序打开文件
	 * 
	 * @param Activity
	 * @param map
	 * @throws Exception
	 */
	public static void openFile(Activity activity, File file) throws Exception {
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file), getMimeType(file, activity));
		activity.startActivity(intent);
	}

	/**
	 * 获取以后缀名为ID的值
	 * 
	 * @param file
	 * @param activity
	 * @return
	 * @throws Exception
	 */
	public static String getMimeType(File file, Activity activity)
			throws Exception {

		String name = file.getName()
				.substring(file.getName().lastIndexOf('.') + 1).toLowerCase();
		int id = activity.getResources().getIdentifier(
				activity.getPackageName() + ":string/" + name, null, null);

		// 特殊处理
		if ("class".equals(name)) {
			return "application/octet-stream";
		}
		if ("3gp".equals(name)) {
			return "video/3gpp";
		}
		if ("nokia-op-logo".equals(name)) {
			return "image/vnd.nok-oplogo-color";
		}
		if (id == 0) {
			throw new Exception("未找到分享该格式的应用");
		}
		return activity.getString(id);
	}

	/**
	 * 用于递归查找文件夹下面的符合条件的文件
	 * 
	 * @param folder
	 * @param filter
	 * @param isSub
	 * @return
	 */
	public static List<HashMap<String, Object>> recursionFolder(File folder,
			FileFilter filter) {

		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		// 获得文件夹下的所有目录和文件集合
		File[] files = folder.listFiles();
		/** 如果文件夹下没内容,会返回一个null **/
		// 判断适配器是否为空
		if (filter != null) {
			files = folder.listFiles(filter);
		}
		// 找到合适的文件返回
		if (files != null) {
			for (int m = 0; m < files.length; m++) {
				File file = files[m];
				if (file.isDirectory()) {
					// 是否递归调用
					list.addAll(recursionFolder(file, filter));

				} else {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("file", file);
					// 设置图标种类
					if (file.getAbsolutePath().toLowerCase().matches(MP3_REG)) {
						map.put("iconType", 3);
					} else if (file.getAbsolutePath().toLowerCase()
							.matches(MTV_REG)) {
						map.put("iconType", 4);
					} else if (file.getAbsolutePath().toLowerCase()
							.matches(JPG_REG)) {
						map.put("iconType", 5);
					} else {
						map.put("iconType", 6);
					}
					list.add(map);
				}
			}
		}
		return list;
	}

	/**
	 * 资源管理器,查找该文件夹下的文件和目录
	 * 
	 * @param folder
	 * @param filter
	 * @param isSub
	 * @return
	 */
	public static List<HashMap<String, Object>> unrecursionFolder(File folder,
			FileFilter filter) {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		// 如果是SD卡路径,不添加父路径
		if (!folder.getAbsolutePath().equals(
				Environment.getExternalStorageDirectory().getAbsolutePath())) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("file", folder.getParentFile());
			map.put("iconType", ICON_TYPE_ROOT);
			list.add(map);
		}
		// 获得文件夹下的所有目录和文件集合
		File[] files = folder.listFiles();
		/** 如果文件夹下没内容,会返回一个null **/
		// 判断适配器是否为空
		if (filter != null) {
			files = folder.listFiles(filter);
		}
		if (files != null && files.length > 0) {
			for (File p : files) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("file", p);
				// 设置图标种类
				if (p.isDirectory()) {
					map.put("iconType", ICON_TYPE_FOLDER);
				} else {
					if (p.getAbsolutePath().toLowerCase().matches(MP3_REG)) {
						map.put("iconType", ICON_TYPE_MP3);
					} else if (p.getAbsolutePath().toLowerCase()
							.matches(MTV_REG)) {
						map.put("iconType", ICON_TYPE_MTV);
					} else if (p.getAbsolutePath().toLowerCase()
							.matches(JPG_REG)) {
						map.put("iconType", ICON_TYPE_JPG);
					} else {
						map.put("iconType", ICON_TYPE_FILE);
					}
				}
				// 添加
				list.add(map);
			}
		}
		return list;
	}

	/**
	 * 示例:"^.*\\.(mp3|mp4|3gp)$"
	 * 
	 * @param reg
	 *            目前允许取值 REG_MTV, REG_MP3, REG_JPG三种
	 * @return
	 */
	public static FileFilter getFileFilter(final String reg, boolean isdir) {
		if (isdir) {
			return new FileFilter() {
				@Override
				public boolean accept(File pathname) {

					return pathname.getAbsolutePath().toLowerCase()
							.matches(reg)
							|| pathname.isDirectory();
				}
			};
		} else {
			return new FileFilter() {
				@Override
				public boolean accept(File pathname) {

					return pathname.getAbsolutePath().toLowerCase()
							.matches(reg)
							&& pathname.isFile();
				}
			};
		}
	}
}
