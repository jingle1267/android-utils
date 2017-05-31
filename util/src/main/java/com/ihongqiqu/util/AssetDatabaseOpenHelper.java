/**
 * Copyright 2014 Zhenguo Jin
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
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * AssetDatabaseOpenHelper
 * <ul>
 * <li>Auto copy databse form assets to /data/data/package_name/databases</li>
 * <li>You can use it like {@link SQLiteDatabase}, use
 * {@link #getWritableDatabase()} to create and/or open a database that will be
 * used for reading and writing. use {@link #getReadableDatabase()} to create
 * and/or open a database that will be used for reading only.</li>
 * </ul>
 *
 * @author jingle1267@163.com
 */
public class AssetDatabaseOpenHelper {

    private Context context;
    private String databaseName;

    public AssetDatabaseOpenHelper(Context context, String databaseName) {
        this.context = context;
        this.databaseName = databaseName;
    }

    /**
     * Create and/or open a database that will be used for reading and writing.
     *
     * @return SQLiteDatabase对象
     * @throws RuntimeException if cannot copy database from assets
     * @throws SQLiteException  if the database cannot be opened
     */
    public synchronized SQLiteDatabase getWritableDatabase() {
        File dbFile = context.getDatabasePath(databaseName);
        if (dbFile != null && !dbFile.exists()) {
            try {
                copyDatabase(dbFile);
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }

        assert dbFile != null;
        return SQLiteDatabase.openDatabase(dbFile.getPath(), null,
                SQLiteDatabase.OPEN_READWRITE);
    }

    /**
     * Create and/or open a database that will be used for reading only.
     *
     * @return SQLiteDatabase对象
     * @throws RuntimeException if cannot copy database from assets
     * @throws SQLiteException  if the database cannot be opened
     */
    public synchronized SQLiteDatabase getReadableDatabase() {
        File dbFile = context.getDatabasePath(databaseName);
        if (dbFile != null && !dbFile.exists()) {
            try {
                copyDatabase(dbFile);
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }

        return SQLiteDatabase.openDatabase(dbFile.getPath(), null,
                SQLiteDatabase.OPEN_READONLY);
    }

    /**
     * 获取数据库名称
     *
     * @return the database name
     */
    public String getDatabaseName() {
        return databaseName;
    }

    private void copyDatabase(File dbFile) throws IOException {
        InputStream stream = context.getAssets().open(databaseName);
        FileUtils.writeFile(dbFile, stream);
        stream.close();
    }
}
