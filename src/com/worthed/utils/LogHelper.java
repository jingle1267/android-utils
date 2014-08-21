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

import android.util.Log;

/**
 * Manage whether print log or not.
 * @author jingle1267@163.com
 *
 */
public class LogHelper {

	/**
	 * Master switch.When it is true, you can see the log. Otherwise, you
	 * cannot.
	 */
	public static boolean LOG_SWITCH = true;
	/**
	 * 'System.out' switch.When it is true, you can see the 'System.out' log. Otherwise, you cannot.
	 */
	public static boolean LOG_SWITCH_SYSOUT = false; 
	
	/**
	 * Print method name and position in black.
	 */
	public static void print(){
		if(LOG_SWITCH){
			String tag = getClassName();
			String method = callMethodAndLine();
			Log.v(tag, method);
			if(LOG_SWITCH_SYSOUT){
				System.out.println(tag + "  " + method);
			}
		}
	}
	
	/**
	 * Print debug log in blue.
	 * @param object The object to print.
	 */
	public static void print(Object object){
		if(LOG_SWITCH){
			String tag = getClassName();
			String method = callMethodAndLine();
			String content = "";
			if(object != null){
				content = object.toString() + "                    ----    " + method;
			}else{
				content = " ## " + "                ----    " + method;
			}
			Log.d(tag, content);
			if(LOG_SWITCH_SYSOUT){
				System.out.println(tag + "  " + content + "  " + method);
			}
		}
	}
	
	/**
	 * Print error log in red.
	 * @param object The object to print.
	 */
	public static void printError(Object object){
		if(LOG_SWITCH){
			String tag = getClassName();
			String method = callMethodAndLine();
			String content = "";
			if(object != null){
				content = object.toString() + "                    ----    " + method;
			}else{
				content =" ## " + "                    ----    " + method;
			}
			Log.e(tag, content);
			if(LOG_SWITCH_SYSOUT){
				System.err.println(tag + "  " + method + "  " + content);
			}
		}
	}
	
	/**
	 * Print the array of stack trace elements of this method in black.
	 * @return
	 */
	public static void printCallHierarchy(){
		if(LOG_SWITCH){
			String tag = getClassName();
			String method = callMethodAndLine();
			String hierarchy = getCallHierarchy();
			Log.v(tag, method + hierarchy);
			if(LOG_SWITCH_SYSOUT){
				System.out.println(tag + "  " + method + hierarchy);
			}
		}
	}
	
	/**
	 * Print debug log in blue.
	 * @param object The object to print.
	 */
	public static void printMyLog(Object object) {
		if(LOG_SWITCH){
			String tag = "MYLOG";
			String method = callMethodAndLine();
			String content = "";
			if(object != null){
				content = object.toString() + "                    ----    " + method;
			}else{
				content = " ## " + "                ----    " + method;
			}
			Log.d(tag, content);
			if(LOG_SWITCH_SYSOUT){
				System.out.println(tag + "  " + content + "  " + method);
			}
		}
	}
	
	private static String getCallHierarchy(){
		String result = "";
        StackTraceElement[] trace = (new Exception()).getStackTrace();
        for(int i = 2; i < trace.length; i++){
        	result += "\r\t" + trace[i].getClassName() + "." + trace[i].getMethodName() + "():" + trace[i].getLineNumber();
        }
        return result;
	}
	
	private static String getClassName(){
		String result = "";
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[2]; 
        result = thisMethodStack.getClassName();
        return result;
	}
	
	/**
	 * Realization of double click jump events.
	 * @return
	 */
	private static String callMethodAndLine() {
		String result = "at ";
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[2]; 
        result += thisMethodStack.getClassName() + ".";
        result += thisMethodStack.getMethodName();
        result += "(" + thisMethodStack.getFileName();
		result += ":" + thisMethodStack.getLineNumber() + ")  ";
        return result;
    }
	
}
