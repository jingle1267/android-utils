package com.worthed.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.widget.Toast;

public class CommonUtil {
	 
    public static boolean hasSDCard() {
 
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }
 
    /**
     * ��ȡ����ڴ�
     * 
     * @return
     */
    public static long getMaxMemory() {
 
        return Runtime.getRuntime().maxMemory() / 1024;
    }
 
    /**
     * �������
     * 
     * @param context
     * @return
     */
    public static boolean checkNetState(Context context) {
 
        boolean netstate = false;
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
 
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
 
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
 
                        netstate = true;
                        break;
                    }
                }
            }
        }
        return netstate;
    }
 
    public static void showToast(Context context, String tip) {
 
        Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
    }
 
    public static DisplayMetrics metric = new DisplayMetrics();
 
    /**
     * �õ���Ļ�߶�
     * 
     * @param context
     * @return
     */
    public static int getScreenHeight(Activity context) {
 
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }
 
    /**
     * �õ���Ļ���
     * 
     * @param context
     * @return
     */
    public static int getScreenWidth(Activity context) {
 
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }
 
    /**
     * ����ֻ�ķֱ��ʴ� dp �ĵ�λ ת��Ϊ px(����)
     */
 
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
 
    /**
     * ����ֻ�ķֱ��ʴ� px(����) �ĵ�λ ת��Ϊ dp
     */
    public static int px2dip(Context context, float pxValue) {
 
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
 
    /**
     * ��ѯ�ֻ��ڷ�ϵͳӦ��
     * 
     * @param context
     * @return
     */
    public static List<PackageInfo> getAllApps(Context context) {
 
        List<PackageInfo> apps = new ArrayList<PackageInfo>();
        PackageManager pManager = context.getPackageManager();
        // ��ȡ�ֻ�������Ӧ��
        List<PackageInfo> paklist = pManager.getInstalledPackages(0);
        for (int i = 0; i < paklist.size(); i++) {
            PackageInfo pak = (PackageInfo) paklist.get(i);
            // �ж��Ƿ�Ϊ��ϵͳԤװ��Ӧ�ó���
            if ((pak.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                // customs applications
                apps.add(pak);
            }
        }
        return apps;
    }
 
    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }
 
    /**
     * ��ȡ�汾�źͰ汾����
     * 
     * @param context
     * @return
     */
    public static String getVersionCode(Context context, int type) {
 
        try {
 
            PackageInfo pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            if (type == 1) {
 
                return String.valueOf(pi.versionCode);
            } else {
 
                return pi.versionName;
            }
        } catch (NameNotFoundException e) {
 
            e.printStackTrace();
            return null;
        }
    }
 
    // ͨ��Service���������ж��Ƿ�����ĳ������
    public static boolean messageServiceIsStart(
            List<ActivityManager.RunningServiceInfo> mServiceList,
            String className) {
 
        for (int i = 0; i < mServiceList.size(); i++) {
            if (className.equals(mServiceList.get(i).service.getClassName())) {
 
                return true;
            }
        }
        return false;
    }
}
