package com.focus.focuspermission;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.focus.focuspermission.core.listener.OnMustPermissionListener;
import com.focus.focuspermission.core.listener.OnNormalPermissionListener;
import com.focus.focuspermission.ui.PreRequestPermission.PreRequestPermissionActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限帮助类，对外提供接口
 */
public class FocusPermission {

    private Activity activity;

    private String[] permissions;

    private OnMustPermissionListener onMustPermissionListener;

    private OnNormalPermissionListener onNormalPermissionListener;

    private boolean enablePreRequestDialog = true;


    private static FocusPermission _focusPermission = new FocusPermission();

    /**
     * 设置当前的Activity
     * @param activity
     * @return
     */
    public static FocusPermission setCurrentActivity(Activity activity){
        _focusPermission.activity = activity;
        return _focusPermission;
    }

    /**
     * 设置请求的权限列表
     * @param permissions
     * @return
     */
    public FocusPermission setRequestPermissions(String... permissions){
        this.permissions = permissions;
        return this;
    }

    /**
     * 设置是否启用权限的预请求弹窗
     * @param enablePreRequestDialog
     * @return
     */
    public FocusPermission setEnablePreRequestDialog(boolean enablePreRequestDialog){
        this.enablePreRequestDialog = enablePreRequestDialog;
        return this;
    }

    /**
     * 当是请求权限，直到权限全部被授权时设置此回调
     * @param onMustPermissionListener
     * @return
     */
    public FocusPermission setOnMustPermissionListener(OnMustPermissionListener onMustPermissionListener){
        this.onMustPermissionListener = onMustPermissionListener;
        return this;
    }

    /**
     * 当是请求权限，但不管权限是授权还是拒绝时设置此回调
     * @param onNormalPermissionListener
     * @return
     */
    public FocusPermission setOnNormalPermissionListener(OnNormalPermissionListener onNormalPermissionListener){
        this.onNormalPermissionListener = onNormalPermissionListener;
        return this;
    }

    /**
     * 一切准备就绪，开始请求权限
     */
    public void request(){
        if (onMustPermissionListener == null && onNormalPermissionListener == null){
            Toast.makeText(activity,"请设置权限回调!",Toast.LENGTH_LONG).show();
            return;
        }
        if (onMustPermissionListener != null && onNormalPermissionListener != null){
            Toast.makeText(activity,"权限回调中，onMustPermissionListener和onNormalPermissionListener同时只能存在一个!",Toast.LENGTH_LONG).show();
            return;
        }
        if (enablePreRequestDialog){
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    List<String> deniedList = new ArrayList<>();
                    for (int i = 0; i < permissions.length; i++) {
                        String permission = permissions[i];
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED){
                                deniedList.add(permission);
                            }
                        }
                    }
                    if (deniedList.size() > 0 ) {
                        PreRequestPermissionActivity.setConfig(permissions, onMustPermissionListener, onNormalPermissionListener);
                        Intent intent = new Intent(activity, PreRequestPermissionActivity.class);
                        activity.startActivity(intent);
                    }
                    else {
                        if (onMustPermissionListener != null) {
                            onMustPermissionListener.onFinish();
                        }
                        else if (onNormalPermissionListener != null) {
                            onNormalPermissionListener.onFinish(deniedList);
                        }
                    }
                }
            });
        }
        else {
            if (onMustPermissionListener != null) {
                FocusInternalManager.getInstance().mustRequestPermissions(activity, permissions, onMustPermissionListener);
            }
            else if (onNormalPermissionListener != null) {
                FocusInternalManager.getInstance().requestPermissions(activity, permissions, onNormalPermissionListener);
            }
        }
    }
}
