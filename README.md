android-utils
=============

  It contains most of the utility classes. 
  
Android utility classes (a - z):
 * [AnimationUtils][1]
 * [AppUtils][2]
 * [AssetDatabaseOpenHelper][3]
 * [BitmapUtil][4]
 * [CipherUtils][5]
 * [Colors][6] 
 * [CommonUtil][7]
 * [DataCleanManager][8]
 * [DatabaseExportUtils][9]
 * [DateUtils][10]
 * [DeviceStatusUtils][11]
 * [DigestUtils][12]
 * [DisplayUtils][13]
 * [DoubleKeyValueMap][14] 
 * [DownloadManagerPro][15]
 * [FileUtils][16]
 * [HanziToPinyin][17]
 * [ImsiUtil][18]
 * [LocationUtils][19]
 * [LogUtils][20]
 * [NetUtil][21]
 * [PackageUtils][22] 
 * [PhoneUtil][23]
 * [PollingUtils][24]
 * [PreferencesCookieStore][25]
 * [RUtils][26]
 * [RandomUtils][27]
 * [RegUtils][28]
 * [ResourceUtils][29]
 * [SDCardUtils][30] 
 * [SettingUtils][31]
 * [ShellUtils][32]
 * [ShortCutUtils][33]
 * [Singleton][34]
 * [StringUtils][35]
 * [ViewAnimationUtils][36]
 * [ViewUtils][37]
 * [WindowUtils][38] 

 * [BaseApplication][39]
 * [BaseCrashHandler][40]
 * [RebootThreadExceptionHandler][41]
 * [StartAppReceiver][42]

Permission requirement
----------------------

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

Configuration
-------------

```xml
<application
        android:name="com.worthed.app.BaseApplication" >
```
You need set 'LogUtils.DEBUG_SYSOUT = false' for release version.

Third party library
-------------------

```
%JAVA_HOME%\jre\lib\rt.jar
```


Proguard
--------

  ProGuard obfuscates method names. Use the following snip in your ProGuard configuration file (proguard.cfg):

``` xml
-keep class com.worthed.** { *; }
-keepclassmembers class com.worthed.** { *; }
-dontwarn com.worthed.**
```

Developer
---------

* [Zhenguo Jin](https://worthed.com) - <jinzhenguo1990@gmail.com>


License
-------

    Copyright 2014 Zhenguo Jin

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[1]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/AnimationUtils.java
[2]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/AppUtils.java
[3]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/AssetDatabaseOpenHelper.java
[4]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/BitmapUtil.java
[5]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/CipherUtils.java
[6]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/Colors.java
[7]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/CommonUtil.java
[8]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/DataCleanManager.java
[9]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/DatabaseExportUtils.java
[10]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/DateUtils.java
[11]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/DeviceStatusUtils.java
[12]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/DigestUtils.java
[13]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/DisplayUtils.java
[14]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/DoubleKeyValueMap.java
[15]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/DownloadManagerPro.java
[16]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/FileUtils.java
[17]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/HanziToPinyin.java
[18]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/ImsiUtil.java
[19]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/LocationUtils.java
[20]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/LogUtils.java
[21]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/NetUtil.java
[22]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/PackageUtils.java
[23]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/PhoneUtil.java
[24]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/PollingUtils.java
[25]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/PreferencesCookieStore.java
[26]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/RUtils.java
[27]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/RandomUtils.java
[28]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/RegUtils.java
[29]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/ResourceUtils.java
[30]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/SDCardUtils.java
[31]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/SettingUtils.java
[32]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/ShellUtils.java
[33]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/ShortCutUtils.java
[34]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/Singleton.java
[35]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/StringUtils.java
[36]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/ViewAnimationUtils.java
[37]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/ViewUtils.java
[38]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/util/WindowUtils.java

[39]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/app/BaseApplication.java
[40]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/app/BaseCrashHandler.java
[41]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/app/RebootThreadExceptionHandler.java
[42]: https://github.com/jingle1267/android-utils/blob/master/src/com/worthed/app/StartAppReceiver.java
 