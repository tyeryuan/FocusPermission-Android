package com.focus.focuspermission.core;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermissionFragment extends Fragment {
    private Activity activity;
    private OnCallback onAttachCallback;

    private static final int REQUEST_CDOE_START = 10000;
    private Map<String,PermissionRequester> permissionRequesterMap = new HashMap<>();

    public PermissionFragment setActivity(Activity activity) {
        this.activity = activity;
        return this;
    }

    public Activity getRequestActivity() {
        return activity;
    }

    public PermissionFragment setAttackCallback(OnCallback onAttachCallback) {
        this.onAttachCallback = onAttachCallback;
        return this;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (onAttachCallback!=null){
            onAttachCallback.onFinish(this);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void requestPermissions(PermissionRequester requester) {
        List<String> deniedPermissionList = new ArrayList<>();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            notify(requester, deniedPermissionList, null);
            return;
        }
        for (int i = 0; i < requester.getPermissions().size(); i++) {
            String permission = requester.getPermissions().get(i);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    deniedPermissionList.add(permission);
                }
            }
        }
        if (deniedPermissionList.size() == 0) {
            //权限全部通过了
            notify(requester, deniedPermissionList, null);
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = deniedPermissionList.toArray(new String[deniedPermissionList.size()]);
            int requestCode = REQUEST_CDOE_START + permissionRequesterMap.size() + 1;
            requester.setRequestCode(requestCode);
            permissionRequesterMap.put(requestCode + "", requester);
            requestPermissions(permissions, requester.getRequestCode());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                reRequestPermissions(requestCode);
            }
        }, 100);
    }

    private void reRequestPermissions(int requestCode){
        String key = requestCode + "";
        PermissionRequester requester = null;

        if (permissionRequesterMap.containsKey(key)){
            requester = permissionRequesterMap.get(key);
        }
        if (requester == null){
            return;
        }
        Log.i(activity.getPackageName(),"收到了onActivityResult111");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = requester.getPermissions().toArray(new String[requester.getPermissions().size()]);
            Log.i(activity.getPackageName(),"请求一波权限：" + permissions.length);
            requestPermissions(permissions, requester.getRequestCode());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String key = requestCode + "";
        PermissionRequester requester = null;
        if (permissionRequesterMap.containsKey(key)){
            requester = permissionRequesterMap.get(key);
        }
        if (requester == null){
            return;
        }
        List<String> deniedPermissionList = new ArrayList<>();
        List<String> dontAskDeniedPermissionList = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            String permission = permissions[i];
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                deniedPermissionList.add(permission);
                boolean flag = true;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    flag = shouldShowRequestPermissionRationale(permission);
                }
                if (!flag){
                    dontAskDeniedPermissionList.add(permission);
                }
            }

        }
        notify(requester, deniedPermissionList, dontAskDeniedPermissionList);
    }

    public void notify(PermissionRequester requester, List<String> deniedPermissionList, List<String> dontAskDeniedPermissionList){
        switch (requester.getRequestorType()){
            case RT_MUST:
                if (deniedPermissionList.size() == 0) {
                    //没有被拒绝的权限
                    if (requester.getOnMustPermissionListener() != null) {
                        requester.getOnMustPermissionListener().onFinish();
                    }
                    return;
                }
                if (deniedPermissionList.size()>dontAskDeniedPermissionList.size()){
                    //再请求一次
                    String[] permissions = (String[])deniedPermissionList.toArray(new String[deniedPermissionList.size()]);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(permissions, requester.getRequestCode());
                    }
                }
                else {
                    //都是被拒绝的权限，弹出提示框
                    showMustPermissionDialog(requester, dontAskDeniedPermissionList);
                }
                break;
            case RT_NORMAL:
                if (requester.getOnNormalPermissionListener()!=null){
                    requester.getOnNormalPermissionListener().onFinish(deniedPermissionList);
                }
                break;
        }
    }

    private void showMustPermissionDialog(PermissionRequester requester, List<String> dontAskDeniedPermissionList){
        new AlertDialog.Builder(activity)
                .setTitle("警告")
                .setMessage(requester.getOnMustPermissionListener().getDontAskDeniedPermissionDialogTip())
                .setCancelable(false)
                .setPositiveButton("跳转", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionSettingUtil.gotoPermission(PermissionFragment.this, requester.getRequestCode());
                    }
                })
                .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                })
                .show();

    }
}
