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
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * IMSI工具类
 *
 * @author jingle1267@163.com
 */
public class ImsiUtil {

    private Integer simId_1 = 0;
    private Integer simId_2 = 1;
    private String imsi_1 = "";
    private String imsi_2 = "";
    private String imei_1 = "";
    private String imei_2 = "";
    private Context mContext;

    public ImsiUtil(Context mContext) {
        // TODO Auto-generated constructor stub
        this.mContext = mContext;
    }

    /**
     * 获取IMSInfo
     *
     * @return
     */
    public IMSInfo getIMSInfo() {
        IMSInfo imsInfo = initQualcommDoubleSim();
        if (imsInfo != null) {
            return imsInfo;
        } else {
            imsInfo = initMtkDoubleSim();
            if (imsInfo != null) {
                return imsInfo;
            } else {
                imsInfo = initMtkSecondDoubleSim();
                if (imsInfo != null) {
                    return imsInfo;
                } else {
                    imsInfo = initSpreadDoubleSim();
                    if (imsInfo != null) {
                        return imsInfo;
                    } else {
                        imsInfo = getIMSI();
                        if (imsInfo != null) {
                            return imsInfo;
                        } else {
                            imsInfo = null;
                            return imsInfo;
                        }
                    }
                }
            }
        }
    }

    /**
     * MTK的芯片的判断
     *
     * @return
     */
    public IMSInfo initMtkDoubleSim() {
        IMSInfo imsInfo = null;
        try {
            TelephonyManager tm = (TelephonyManager) mContext
                    .getSystemService(Context.TELEPHONY_SERVICE);
            Class<?> c = Class.forName("com.android.internal.telephony.Phone");
            Field fields1 = c.getField("GEMINI_SIM_1");
            fields1.setAccessible(true);
            simId_1 = (Integer) fields1.get(null);
            Field fields2 = c.getField("GEMINI_SIM_2");
            fields2.setAccessible(true);
            simId_2 = (Integer) fields2.get(null);

            Method m = TelephonyManager.class.getDeclaredMethod(
                    "getSubscriberIdGemini", int.class);
            imsi_1 = (String) m.invoke(tm, simId_1);
            imsi_2 = (String) m.invoke(tm, simId_2);

            Method m1 = TelephonyManager.class.getDeclaredMethod(
                    "getDeviceIdGemini", int.class);
            imei_1 = (String) m1.invoke(tm, simId_1);
            imei_2 = (String) m1.invoke(tm, simId_2);

            imsInfo = new IMSInfo();
            imsInfo.chipName = "MTK芯片";
            imsInfo.imei_1 = imei_1;
            imsInfo.imei_2 = imei_2;
            imsInfo.imsi_1 = imsi_1;
            imsInfo.imsi_2 = imsi_2;

        } catch (Exception e) {
            imsInfo = null;
            return imsInfo;
        }
        return imsInfo;
    }

    /**
     * MTK的芯片的判断2
     *
     * @return
     */
    public IMSInfo initMtkSecondDoubleSim() {
        IMSInfo imsInfo = null;
        try {
            TelephonyManager tm = (TelephonyManager) mContext
                    .getSystemService(Context.TELEPHONY_SERVICE);
            Class<?> c = Class.forName("com.android.internal.telephony.Phone");
            Field fields1 = c.getField("GEMINI_SIM_1");
            fields1.setAccessible(true);
            simId_1 = (Integer) fields1.get(null);
            Field fields2 = c.getField("GEMINI_SIM_2");
            fields2.setAccessible(true);
            simId_2 = (Integer) fields2.get(null);

            Method mx = TelephonyManager.class.getMethod("getDefault",
                    int.class);
            TelephonyManager tm1 = (TelephonyManager) mx.invoke(tm, simId_1);
            TelephonyManager tm2 = (TelephonyManager) mx.invoke(tm, simId_2);

            imsi_1 = tm1.getSubscriberId();
            imsi_2 = tm2.getSubscriberId();

            imei_1 = tm1.getDeviceId();
            imei_2 = tm2.getDeviceId();

            imsInfo = new IMSInfo();
            imsInfo.chipName = "MTK芯片";
            imsInfo.imei_1 = imei_1;
            imsInfo.imei_2 = imei_2;
            imsInfo.imsi_1 = imsi_1;
            imsInfo.imsi_2 = imsi_2;

        } catch (Exception e) {
            imsInfo = null;
            return imsInfo;
        }
        return imsInfo;
    }

    /**
     * 展讯芯片的判断
     *
     * @return
     */
    public IMSInfo initSpreadDoubleSim() {
        IMSInfo imsInfo = null;
        try {
            Class<?> c = Class
                    .forName("com.android.internal.telephony.PhoneFactory");
            Method m = c.getMethod("getServiceName", String.class, int.class);
            String spreadTmService = (String) m.invoke(c,
                    Context.TELEPHONY_SERVICE, 1);
            TelephonyManager tm = (TelephonyManager) mContext
                    .getSystemService(Context.TELEPHONY_SERVICE);
            imsi_1 = tm.getSubscriberId();
            imei_1 = tm.getDeviceId();
            TelephonyManager tm1 = (TelephonyManager) mContext
                    .getSystemService(spreadTmService);
            imsi_2 = tm1.getSubscriberId();
            imei_2 = tm1.getDeviceId();
            imsInfo = new IMSInfo();
            imsInfo.chipName = "展讯芯片";
            imsInfo.imei_1 = imei_1;
            imsInfo.imei_2 = imei_2;
            imsInfo.imsi_1 = imsi_1;
            imsInfo.imsi_2 = imsi_2;
        } catch (Exception e) {
            imsInfo = null;
            return imsInfo;
        }
        return imsInfo;
    }

    /**
     * 高通芯片判断
     *
     * @return
     */
    public IMSInfo initQualcommDoubleSim() {
        IMSInfo imsInfo = null;
        try {
            Class<?> cx = Class
                    .forName("android.telephony.MSimTelephonyManager");
            Object obj = mContext.getSystemService("phone_msim");
            Method md = cx.getMethod("getDeviceId", int.class);
            Method ms = cx.getMethod("getSubscriberId", int.class);
            imei_1 = (String) md.invoke(obj, simId_1);
            imei_2 = (String) md.invoke(obj, simId_2);
            imsi_1 = (String) ms.invoke(obj, simId_1);
            imsi_2 = (String) ms.invoke(obj, simId_2);
            int statephoneType_2 = 0;
            boolean flag = false;
            try {
                Method mx = cx.getMethod("getPreferredDataSubscription",
                        int.class);
                Method is = cx.getMethod("isMultiSimEnabled", int.class);
                statephoneType_2 = (Integer) mx.invoke(obj);
                flag = (Boolean) is.invoke(obj);
            } catch (Exception e) {
                // TODO: handle exception
            }
            imsInfo = new IMSInfo();
            imsInfo.chipName = "高通芯片-getPreferredDataSubscription:"
                    + statephoneType_2 + ",flag:" + flag;
            imsInfo.imei_1 = imei_1;
            imsInfo.imei_2 = imei_2;
            imsInfo.imsi_1 = imsi_1;
            imsInfo.imsi_2 = imsi_2;

        } catch (Exception e) {
            imsInfo = null;
            return imsInfo;
        }
        return imsInfo;
    }

    /**
     * 系统的api
     *
     * @return
     */
    public IMSInfo getIMSI() {
        IMSInfo imsInfo = null;
        try {
            TelephonyManager tm = (TelephonyManager) mContext
                    .getSystemService(Context.TELEPHONY_SERVICE);
            imsi_1 = tm.getSubscriberId();
            imei_1 = tm.getDeviceId();
        } catch (Exception e) {
            // TODO: handle exception
            imsInfo = null;
            return imsInfo;
        }
        if (TextUtils.isEmpty(imsi_1) || imsi_1.length() < 10) {
            imsInfo = null;
            return imsInfo;
        } else {
            imsInfo = new IMSInfo();
            imsInfo.chipName = "单卡芯片";
            imsInfo.imei_1 = imei_1;
            imsInfo.imei_2 = "没有";
            imsInfo.imsi_1 = imsi_1;
            imsInfo.imsi_2 = "没有";
            return imsInfo;
        }
    }

    public class IMSInfo {
        public String chipName;
        public String imsi_1;
        public String imei_1;
        public String imsi_2;
        public String imei_2;
    }

}
