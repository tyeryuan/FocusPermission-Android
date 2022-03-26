package com.focus.focuspermission;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.FragmentTransaction;

import com.focus.focuspermission.core.OnCallback;
import com.focus.focuspermission.core.PermissionFragment;
import com.focus.focuspermission.core.PermissionRequester;
import com.focus.focuspermission.core.RequestorType;
import com.focus.focuspermission.core.listener.OnMustPermissionListener;
import com.focus.focuspermission.core.listener.OnNormalPermissionListener;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 内部管理器
 */
class FocusInternalManager {

    private static FocusInternalManager instance = new FocusInternalManager();

    private final String FRAGMENT_TAG= "PermissionFragment";

    public static FocusInternalManager getInstance(){
        return instance;
    }

    private void checkPermissionFragment(Activity activity, OnCallback onCallback){
        PermissionFragment permissionFragment = new PermissionFragment()
                .setActivity(activity)
                .setAttackCallback(onCallback);
        activity.getFragmentManager()
                .beginTransaction()
                .add(permissionFragment, FRAGMENT_TAG)
                .commit();
    }

    private PermissionRequester createPermissionRequester(String[] permissions){
        PermissionRequester requester = new PermissionRequester()
                .setPermissions(Arrays.asList(permissions));
        return requester;
    }

    public void mustRequestPermissions(Activity activity, String[] permissions, OnMustPermissionListener onMustPermissionListener){

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (onMustPermissionListener!=null){
                onMustPermissionListener.onFinish();
            }
            return;
        }
        PermissionRequester requester = createPermissionRequester(permissions)
                .setRequestorType(RequestorType.RT_MUST)
                .setOnMustPermissionListener(onMustPermissionListener);

        checkPermissionFragment(activity, new OnCallback() {
            @Override
            public void onFinish(PermissionFragment permissionFragment) {
                permissionFragment.requestPermissions(requester);
            }
        });

    }

    public void requestPermissions(Activity activity, String[] permissions, OnNormalPermissionListener onNormalPermissionListener){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (onNormalPermissionListener!=null){
                onNormalPermissionListener.onFinish(null);
            }
            return;
        }
        PermissionRequester requester = createPermissionRequester(permissions)
                .setRequestorType(RequestorType.RT_NORMAL)
                .setOnNormalPermissionListener(onNormalPermissionListener);
        checkPermissionFragment(activity, new OnCallback() {
            @Override
            public void onFinish(PermissionFragment permissionFragment) {
                permissionFragment.requestPermissions(requester);
            }
        });

    }
}
