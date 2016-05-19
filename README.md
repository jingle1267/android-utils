##  Android工具类库 [![Build Status](https://travis-ci.org/kibotu/android-utils.svg?branch=master)](https://travis-ci.org/kibotu/android-utils) [![](https://jitpack.io/v/kibotu/android-utils.svg)](https://jitpack.io/#kibotu/android-utils)  [![API](https://img.shields.io/badge/API-3%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=3) [![Gradle Version](https://img.shields.io/badge/gradle-2.13-green.svg)](https://docs.gradle.org/current/release-notes) [![Licence](https://img.shields.io/badge/licence-Apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)
  
  囊括了一大部分Android应用开发过程当中常用的工具类。工具类来源整理自网络和自己编写。
  
## 所有的工具类简介 (a - z):

|类|介绍|
| ------ | ------------ |
|[AnimationUtils][1]|Animation 工具类|
|[AppUtils][2]|APP 相关信息工具类|
|[AssetDatabaseOpenHelper][3]|读取 Asset 目录中数据库工具类|
|[BitmapUtil][4]|Bitmap 工具类主要包括获取 Bitmap 和对 Bitmap 的操作|
|[CipherUtils][5]|加密与解密的工具类|
|[Colors][6]|常用颜色色值工具类|
|[CommonUtil][7]|一些通用的方法|
|[ChannelUtil][46]|为打包而生的渠道工具类 [极速打包传送门][47]|
|[DataCleanManager][8]|应用数据清除类，主要功能有清除内/外缓存，清除数据库，清除 SharedPreference，清除 files 和清除自定义目录|
|[DatabaseExportUtils][9]|导出应用数据库工具类|
|[DateUtils][10]|日期工具类|
|[DeviceStatusUtils][11]|手机状态工具类 主要包括网络、蓝牙、屏幕亮度、飞行模式、音量等|
|[DisplayUtils][13]|系统显示相关工具类|
|[DoubleKeyValueMap][14]|双键值对|
|[DownloadManagerPro][15]|下载管理工具类|
|[FileUtils][16]|文件操作工具类|
|[HanziToPinyin][17]|汉字转拼音工具类|
|[ImsiUtil][18]|IMSI 工具类|
|[JSONUtils][48]|Json 解析工具类|
|[LocationUtils][19]|根据经纬度查询地址信息和根据地址信息查询经纬度|
|[LogUtils][20]|Log工具类。课参考博文:[Android Log 工具类][43]。|
|[NetUtil][21]|网络工具类|
|[PackageUtils][22]|应用安装下载相关|
|[PhoneUtil][23]|手机组件调用工具类|
|[PollingUtils][24]|轮询服务工具类|
|[PreferencesCookieStore][25]|Cookie 存储工具类|
|[RUtils][26]|R 反射资源 ID 工具类|
|[RandomUtils][27]|随机工具类|
|[RegUtils][28]|数据校验工具类|
|[ResourceUtils][29]|文件资源读取工具类|
|[SDCardUtils][30]|SDcard 操作工具类|
|[SettingUtils][31]|应用配置工具类|
|[ShellUtils][32]|shell 工具类|
|[ShortCutUtils][33]|快捷方式工具类|
|[Singleton][34]|单例模式抽象类|
|[StringUtils][35]|字符串操作工具包。字符串其他操作可以使用 TextUtils 类。|
|[ViewAnimationUtils][36]|视图动画工具箱，提供简单的控制视图的动画的工具方法|
|[ViewUtils][37]|View 相关工具类|
|[ViewFinder][45]|findViewById 替代工具类|
|[WindowUtils][38]|窗口工具类|
|[BaseApplication][39]|应用 Application 此处主要是为了错误处理。|
|[BaseCrashHandler][40]|在 Application 中统一捕获异常，保存到文件中下次再打开时上传|
|[RebootThreadExceptionHandler][41]|重启线程异常处理器，当发生未知异常时会提示异常信息并在一秒钟后重新启动应用。|
|[StartAppReceiver][42]|重启应用广播接收器。|

### Framework 内置工具类

AOSP 源码中包含Util关键字的类，整理出的列表如下：

```java
// 系统
./android/database/DatabaseUtils.java
./android/transition/TransitionUtils.java
./android/view/animation/AnimationUtils.java
./android/view/ViewAnimationUtils.java
./android/webkit/URLUtil.java
./android/bluetooth/le/BluetoothLeUtils.java
./android/gesture/GestureUtils.java
./android/text/TextUtils.java
./android/text/format/DateUtils.java
./android/os/FileUtils.java
./android/os/CommonTimeUtils.java
./android/net/NetworkUtils.java
./android/util/MathUtils.java
./android/util/TimeUtils.java
./android/util/ExceptionUtils.java
./android/util/DebugUtils.java
./android/drm/DrmUtils.java
./android/media/ThumbnailUtils.java
./android/media/ImageUtils.java
./android/media/Utils.java
./android/opengl/GLUtils.java
./android/opengl/ETC1Util.java
./android/telephony/PhoneNumberUtils.java

// 设计和支持库
./design/src/android/support/design/widget/ViewGroupUtils.java
./design/src/android/support/design/widget/ThemeUtils.java
./design/src/android/support/design/widget/ViewUtils.java
./design/lollipop/android/support/design/widget/ViewUtilsLollipop.java
./design/base/android/support/design/widget/AnimationUtils.java
./design/base/android/support/design/widget/MathUtils.java
./design/honeycomb/android/support/design/widget/ViewGroupUtilsHoneycomb.java
./v7/recyclerview/src/android/support/v7/widget/helper/ItemTouchUIUtil.java
./v7/recyclerview/src/android/support/v7/widget/helper/ItemTouchUIUtilImpl.java
./v7/recyclerview/src/android/support/v7/util/MessageThreadUtil.java
./v7/recyclerview/src/android/support/v7/util/AsyncListUtil.java
./v7/recyclerview/src/android/support/v7/util/ThreadUtil.java
./v7/recyclerview/tests/src/android/support/v7/widget/AsyncListUtilLayoutTest.java
./v7/recyclerview/tests/src/android/support/v7/util/AsyncListUtilTest.java
./v7/recyclerview/tests/src/android/support/v7/util/ThreadUtilTest.java
./v7/appcompat/src/android/support/v7/graphics/drawable/DrawableUtils.java
./v7/appcompat/src/android/support/v7/widget/DrawableUtils.java
./v7/appcompat/src/android/support/v7/widget/ThemeUtils.java
./v7/appcompat/src/android/support/v7/widget/ViewUtils.java
./v4/tests/java/android/support/v4/graphics/ColorUtilsTest.java
./v4/jellybean-mr1/android/support/v4/text/TextUtilsCompatJellybeanMr1.java
./v4/jellybean/android/support/v4/app/BundleUtil.java
./v4/jellybean/android/support/v4/app/NavUtilsJB.java
./v4/java/android/support/v4/app/NavUtils.java
./v4/java/android/support/v4/database/DatabaseUtilsCompat.java
./v4/java/android/support/v4/graphics/ColorUtils.java
./v4/java/android/support/v4/text/TextUtilsCompat.java
./v4/java/android/support/v4/util/TimeUtils.java
./v4/java/android/support/v4/util/DebugUtils.java
./v4/java/android/support/v4/content/res/TypedArrayUtils.java
```

### 如何使用 (How to install)

```xml	
repositories {
    maven {
        url "https://jitpack.io"
    }
}
		
dependencies {
    compile 'com.github.kibotu:android-utils:1.0.0'
}
```

### 需要权限 (Permission)

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
<uses-permission android:name="android.permission.BLUETOOTH" />
<!-- 获取 UUID 用到 -->
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
```

### 配置 (Configuration)

```xml
<application
        android:name="com.ihongqiqu.app.BaseApplication" >
```
发布正式版本注释 Log 只需要设置 <code>LogUtils.DEBUG_LEVEL = Log.ASSERT</code> 。

### 混淆 (Proguard)

  代码混淆只需要在 Proguard 规则文件中添加如下代码即可( Eclipse 下为 proguard.cfg 文件)：

```xml
-keep class com.ihongqiqu.** { *; }
-keepclassmembers class com.ihongqiqu.** { *; }
-dontwarn com.ihongqiqu.**
```

### 开发者 (Developer)

* [Zhenguo Jin][44] - <jinzhenguo1990@gmail.com>


## License

    Copyright 2014-2016 Zhenguo Jin

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[1]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/AnimationUtils.java
[2]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/AppUtils.java
[3]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/AssetDatabaseOpenHelper.java
[4]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/BitmapUtil.java
[5]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/CipherUtils.java
[6]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/Colors.java
[7]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/CommonUtil.java
[8]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/DataCleanManager.java
[9]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/DatabaseExportUtils.java
[10]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/DateUtils.java
[11]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/DeviceStatusUtils.java
[12]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/
[13]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/DisplayUtils.java
[14]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/DoubleKeyValueMap.java
[15]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/DownloadManagerPro.java
[16]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/FileUtils.java
[17]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/HanziToPinyin.java
[18]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/ImsiUtil.java
[19]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/LocationUtils.java
[20]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/LogUtils.java
[21]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/NetUtil.java
[22]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/PackageUtils.java
[23]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/PhoneUtil.java
[24]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/PollingUtils.java
[25]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/PreferencesCookieStore.java
[26]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/RUtils.java
[27]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/RandomUtils.java
[28]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/RegUtils.java
[29]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/ResourceUtils.java
[30]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/SDCardUtils.java
[31]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/SettingUtils.java
[32]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/ShellUtils.java
[33]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/ShortCutUtils.java
[34]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/Singleton.java
[35]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/StringUtils.java
[36]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/ViewAnimationUtils.java
[37]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/ViewUtils.java
[38]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/WindowUtils.java

[39]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/app/BaseApplication.java
[40]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/app/BaseCrashHandler.java
[41]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/app/RebootThreadExceptionHandler.java
[42]: https://github.com/jingle1267/android-utils/blob/master/src/com/ihongqiqu/app/StartAppReceiver.java
[43]: http://ihongqiqu.com/2014/10/16/android-log/
[44]: http://ihongqiqu.com
[45]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/ViewFinder.java
[46]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/ChannelUtil.java
[47]: http://ihongqiqu.com/2015/07/16/android-mutiple-channel-build/
[48]: https://github.com/jingle1267/android-utils/tree/master/app/src/main/java/com/ihongqiqu/util/JSONUtils.java

