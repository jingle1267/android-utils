Android工具类库
==============
  
  囊括了一大部分Android应用开发过程当中常用的工具类。工具类来源整理自网络和自己编写。
  
所有的工具类简介 (a - z):

|类|介绍|
| ------ | ------------ |
|[AnimationUtils][1]|Animation工具类|
|[AppUtils][2]|APP相关信息工具类|
|[AssetDatabaseOpenHelper][3]|读取Asset目录中数据库工具类|
|[BitmapUtil][4]|Bitmap工具类主要包括获取Bitmap和对Bitmap的操作|
|[CipherUtils][5]|加密与解密的工具类|
|[Colors][6]|常用颜色色值工具类|
|[CommonUtil][7]|一些通用的方法|
|[ChannelUtil][46]|为打包而生的渠道工具类 [极速打包传送门][47]|
|[DataCleanManager][8]|应用数据清除类，主要功能有清除内/外缓存，清除数据库，清除sharedPreference，清除files和清除自定义目录|
|[DatabaseExportUtils][9]|导出应用数据库工具类|
|[DateUtils][10]|日期工具类|
|[DeviceStatusUtils][11]|手机状态工具类 主要包括网络、蓝牙、屏幕亮度、飞行模式、音量等|
|[DisplayUtils][13]|系统显示相关工具类（包括键盘操作|
|[DoubleKeyValueMap][14]|双键值对|
|[DownloadManagerPro][15]|下载管理工具类|
|[FileUtils][16]|文件操作工具类|
|[HanziToPinyin][17]|汉字转拼音工具类|
|[ImsiUtil][18]|IMSI工具类|
|[JSONUtils][46]|Json解析工具类|
|[LocationUtils][19]|根据经纬度查询地址信息和根据地址信息查询经纬度|
|[LogUtils][20]|Log工具类。课参考博文:[Android Log工具类][43]。|
|[NetUtil][21]|网络工具类|
|[PackageUtils][22]|应用安装下载相关|
|[PhoneUtil][23]|手机组件调用工具类|
|[PollingUtils][24]|轮询服务工具类|
|[PreferencesCookieStore][25]|Cookie存储工具类|
|[RUtils][26]|R反射资源ID工具类|
|[RandomUtils][27]|随机工具类|
|[RegUtils][28]|数据校验工具类|
|[ResourceUtils][29]|文件资源读取工具类|
|[SDCardUtils][30]|SDcard操作工具类|
|[SettingUtils][31]|应用配置工具类|
|[ShellUtils][32]|shell工具类|
|[ShortCutUtils][33]|快捷方式工具类|
|[Singleton][34]|单例模式抽象类|
|[StringUtils][35]|字符串操作工具包。字符串其他操作可以使用TextUtils类。|
|[ViewAnimationUtils][36]|视图动画工具箱，提供简单的控制视图的动画的工具方法|
|[ViewUtils][37]|View相关工具类|
|[ViewFinder][45]|findViewById替代工具类|
|[WindowUtils][38]|窗口工具类|
|[BaseApplication][39]|应用Application此处主要是为了错误处理。|
|[BaseCrashHandler][40]|在Application中统一捕获异常，保存到文件中下次再打开时上传|
|[RebootThreadExceptionHandler][41]|重启线程异常处理器，当发生未知异常时会提示异常信息并在一秒钟后重新启动应用。|
|[StartAppReceiver][42]|重启应用广播接收器。|

需要权限 (Permission)
-------------------

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
<uses-permission android:name="android.permission.BLUETOOTH" />
```

配置 (Configuration)
------------------

```xml
<application
        android:name="com.ihongqiqu.app.BaseApplication" >
```
发布正式版本注释Log只需要设置 <code>LogUtils.DEBUG_LEVEL = Log.ASSERT</code> 。

混淆 (Proguard)
-------------

  代码混淆只需要在Proguard规则文件中添加如下代码即可(Eclipse下为proguard.cfg文件)：

``` xml
-keep class com.ihongqiqu.** { *; }
-keepclassmembers class com.ihongqiqu.** { *; }
-dontwarn com.ihongqiqu.**
```

开发者 (Developer)
----------------

* [Zhenguo Jin][44] - <jinzhenguo1990@gmail.com>


License
-------

    Copyright 2014-2015 Zhenguo Jin

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[1]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/AnimationUtils.java
[2]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/AppUtils.java
[3]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/AssetDatabaseOpenHelper.java
[4]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/BitmapUtil.java
[5]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/CipherUtils.java
[6]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/Colors.java
[7]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/CommonUtil.java
[8]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/DataCleanManager.java
[9]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/DatabaseExportUtils.java
[10]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/DateUtils.java
[11]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/DeviceStatusUtils.java
[12]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/
[13]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/DisplayUtils.java
[14]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/DoubleKeyValueMap.java
[15]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/DownloadManagerPro.java
[16]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/FileUtils.java
[17]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/HanziToPinyin.java
[18]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/ImsiUtil.java
[19]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/LocationUtils.java
[20]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/LogUtils.java
[21]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/NetUtil.java
[22]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/PackageUtils.java
[23]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/PhoneUtil.java
[24]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/PollingUtils.java
[25]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/PreferencesCookieStore.java
[26]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/RUtils.java
[27]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/RandomUtils.java
[28]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/RegUtils.java
[29]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/ResourceUtils.java
[30]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/SDCardUtils.java
[31]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/SettingUtils.java
[32]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/ShellUtils.java
[33]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/ShortCutUtils.java
[34]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/Singleton.java
[35]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/StringUtils.java
[36]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/ViewAnimationUtils.java
[37]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/ViewUtils.java
[38]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/WindowUtils.java
[46]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/JSONUtils.java

[39]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/app/BaseApplication.java
[40]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/app/BaseCrashHandler.java
[41]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/app/RebootThreadExceptionHandler.java
[42]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/app/StartAppReceiver.java
[43]: http://ihongqiqu.com/2014/10/16/android-log/
[44]: http://ihongqiqu.com
[45]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/ViewFinder.java
[46]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/util/ChannelUtil.java
[47]: http://ihongqiqu.com/2015/07/16/android-mutiple-channel-build/
