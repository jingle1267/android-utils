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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * 
 * @author jingle1267@163.com
 *
 */
public class DateUtils {

	/** 日期类型 **/
	public static final String yyyyMMDD = "yyyy-MM-dd";
	public static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
	public static final String HHmmss = "HH:mm:ss";
	public static final String hhmmss = "HH:mm:ss";
	public static final String LOCALE_DATE_FORMAT = "yyyy年M月d日 HH:mm:ss";
	public static final String DB_DATA_FORMAT = "yyyy-MM-DD HH:mm:ss";
	public static final String NEWS_ITEM_DATE_FORMAT = "hh:mm M月d日 yyyy";

	
	public static String dateToString(Date date, String pattern)
			throws Exception {
		return new SimpleDateFormat(pattern).format(date);
	}

	public static Date stringToDate(String dateStr, String pattern)
			throws Exception {
		return new SimpleDateFormat(pattern).parse(dateStr);
	}

	/**
	 * 将Date类型转换为日期字符串
	 * 
	 * @param date
	 * @param type
	 * @return
	 */
	public static String formatDate(Date date, String type) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(type);
			return df.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将日期字符串转换为Date类型
	 * 
	 * @param dateStr
	 * @param type
	 * @return
	 */
	public static Date parseDate(String dateStr, String type) {
		SimpleDateFormat df = new SimpleDateFormat(type);
		Date date = null;
		try {
			date = df.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;

	}

	/**
	 * 得到年
	 * 
	 * @param context
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	/**
	 * 得到月
	 * 
	 * @param context
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH) + 1;

	}

	/**
	 * 得到日
	 * 
	 * @param context
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}

}
