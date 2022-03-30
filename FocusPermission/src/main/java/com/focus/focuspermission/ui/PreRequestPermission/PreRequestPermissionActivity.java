package com.focus.focuspermission.ui.PreRequestPermission;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.focus.focuspermission.R;
import com.focus.focuspermission.core.listener.OnMustPermissionListener;
import com.focus.focuspermission.core.listener.OnNormalPermissionListener;

public class PreRequestPermissionActivity extends Activity {
    private static String[] permissions;

    private static OnMustPermissionListener onMustPermissionListener;

    private static OnNormalPermissionListener onNormalPermissionListener;

    public static void setConfig(String[] permissions, OnMustPermissionListener onMustPermissionListener, OnNormalPermissionListener onNormalPermissionListener){
        PreRequestPermissionActivity.permissions = permissions;
        PreRequestPermissionActivity.onMustPermissionListener = onMustPermissionListener;
        PreRequestPermissionActivity.onNormalPermissionListener = onNormalPermissionListener;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI(){
        setContentView(R.layout.focus_permission_prerequest_dialog);
        getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        setFinishOnTouchOutside(false);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = (int)(display.getWidth() * 0.8);
        p.height = (int)(display.getWidth() * 0.8);
        getWindow().setAttributes(p);     //设置生效

    }

}
