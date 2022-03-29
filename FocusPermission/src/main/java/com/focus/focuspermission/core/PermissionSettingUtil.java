package com.focus.focuspermission.core;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;


public class PermissionSettingUtil {
    public static void gotoPermission(PermissionFragment fragment, int requestCode) {
        String brand = Build.BRAND;//手机厂商
        if (TextUtils.equals(brand.toLowerCase(), "redmi") || TextUtils.equals(brand.toLowerCase(), "xiaomi")) {
            gotoMiuiPermission(fragment, requestCode);//小米
        } else if (TextUtils.equals(brand.toLowerCase(), "meizu")) {
            gotoMeizuPermission(fragment, requestCode);
        } else if (TextUtils.equals(brand.toLowerCase(), "huawei") || TextUtils.equals(brand.toLowerCase(), "honor")) {
            gotoHuaweiPermission(fragment, requestCode);
        } else {
            startActivityForResult(fragment, getAppDetailSettingIntent(fragment),requestCode);
        }
    }


    /**
     * 跳转到miui的权限管理页面
     */
    private static void gotoMiuiPermission(PermissionFragment fragment, int requestCode) {
        try { // MIUI 8
            Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            localIntent.putExtra("extra_pkgname", fragment.getRequestActivity().getPackageName());
            startActivityForResult(fragment, localIntent, requestCode);
        } catch (Exception e) {
            try { // MIUI 5/6/7
                Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                localIntent.putExtra("extra_pkgname", fragment.getRequestActivity().getPackageName());
                startActivityForResult(fragment, localIntent, requestCode);
            } catch (Exception e1) { // 否则跳转到应用详情
                startActivityForResult(fragment, getAppDetailSettingIntent(fragment), requestCode);
            }
        }
    }

    /**
     * 跳转到魅族的权限管理系统
     */
    private static void gotoMeizuPermission(PermissionFragment fragment, int requestCode) {
        try {
            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra("packageName", fragment.getRequestActivity().getPackageName());
            startActivityForResult(fragment,intent,requestCode);
        } catch (Exception e) {
            e.printStackTrace();
            startActivityForResult(fragment, getAppDetailSettingIntent(fragment), requestCode);
        }
    }

    /**
     * 华为的权限管理页面
     */
    private static void gotoHuaweiPermission(PermissionFragment fragment, int requestCode) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            intent.setComponent(comp);
            startActivityForResult(fragment,intent,requestCode);
        } catch (Exception e) {
            e.printStackTrace();
            startActivityForResult(fragment,getAppDetailSettingIntent(fragment), requestCode);
        }

    }

    /**
     * 获取应用详情页面intent（如果找不到要跳转的界面，也可以先把用户引导到系统设置页面）
     */
    private static Intent getAppDetailSettingIntent(PermissionFragment fragment) {

        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", fragment.getRequestActivity().getPackageName(), null);
        intent.setData(uri);
        return intent;
    }

    static void startActivityForResult(PermissionFragment fragment, Intent intent, int requestCode){
        fragment.startActivityForResult(intent, requestCode);
    }
}
