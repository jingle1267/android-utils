/**
 * Copyright 2014 Zhenguo Jin (jingle1267@163.com)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ihongqiqu.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

/**
 * 应用数据库导出工具类
 *
 * @author jingle1267@163.com
 */
public final class DatabaseExportUtils {

    private static final boolean DEBUG = true;
    private static final String TAG = "DatabaseExportUtils";

    /**
     * Don't let anyone instantiate this class.
     */
    private DatabaseExportUtils() {
        throw new Error("Do not need instantiate!");
    }

    /**
     * 开始导出数据 此操作比较耗时,建议在线程中进行
     *
     * @param context      上下文
     * @param targetFile   目标文件
     * @param databaseName 要拷贝的数据库文件名
     * @return 是否倒出成功
     */
    public boolean startExportDatabase(Context context, String targetFile,
                                       String databaseName) {
        if (DEBUG) {
            LogUtils.d(TAG, "start export ...");
        }
        if (!Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            if (DEBUG) {
                LogUtils.w(TAG, "cannot find SDCard");
            }
            return false;
        }
        String sourceFilePath = Environment.getDataDirectory() + "/data/"
                + context.getPackageName() + "/databases/" + databaseName;
        String destFilePath = Environment.getExternalStorageDirectory()
                + (TextUtils.isEmpty(targetFile) ? (context.getPackageName() + ".db")
                : targetFile);
        boolean isCopySuccess = FileUtils
                .copyFile(sourceFilePath, destFilePath);
        if (DEBUG) {
            if (isCopySuccess) {
                LogUtils.d(TAG, "copy database file success. target file : "
                        + destFilePath);
            } else {
                LogUtils.w(TAG, "copy database file failure");
            }
        }
        return isCopySuccess;
    }
}
