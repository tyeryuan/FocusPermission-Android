package com.focus.focuspermission.demo;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.focus.focuspermission.FocusPermission;
import com.focus.focuspermission.core.listener.OnMustPermissionListener;
import com.focus.focuspermission.core.listener.OnNormalPermissionListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FocusPermission.setCurrentActivity(this)
                .setRequestPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .setEnablePreRequestDialog(true)
                .setOnMustPermissionListener(new OnMustPermissionListener() {
                    @Override
                    public void onFinish() {
                        Toast.makeText(MainActivity.this, "权限申请通过",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public String getDontAskDeniedPermissionDialogTip() {
                        return "非常抱歉，游戏需要读写权限才能正常运行。是否跳转到权限设置，进行授权？";
                    }
                })
                /*
                .setOnNormalPermissionListener(new OnNormalPermissionListener() {
                    @Override
                    public void onFinish(List<String> deniedPermissionList) {

                    }
                })
                */
                .request();
    }
}
