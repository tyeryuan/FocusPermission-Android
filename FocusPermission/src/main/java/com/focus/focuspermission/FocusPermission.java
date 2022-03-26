package com.focus.focuspermission;

import android.app.Activity;

import com.focus.focuspermission.core.listener.OnMustPermissionListener;
import com.focus.focuspermission.core.listener.OnNormalPermissionListener;

/**
 * 权限帮助类，对外提供接口
 */
public class FocusPermission {

    /**
     * 请求权限，必须拥有此权限时，才会收到回调
     * @param activity activity
     * @param permissions 需要申请的权限数组
     * @param onMustPermissionListener 回调
     */
    public static void mustRequestPermissions(Activity activity, String[] permissions, OnMustPermissionListener onMustPermissionListener){
        FocusInternalManager.getInstance().mustRequestPermissions(activity, permissions, onMustPermissionListener);
    }

    public static void requestPermissions(Activity activity, String[] permissions, OnNormalPermissionListener onNormalPermissionListener){
        FocusInternalManager.getInstance().requestPermissions(activity, permissions, onNormalPermissionListener);
    }

}
