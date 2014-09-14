android-utils
=============

  It contains most of the utility classes. 
  
  Android utility class, include BitmapUtil, DataCleanManager, FileUtils, HanziToPinyin, NetUtil, PreferencesCookieStore, RandomUtils, Singleton, LogUtils, BaseCrashHandler, and so on.

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

Proguard
--------

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
