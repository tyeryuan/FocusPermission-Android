package com.focus.focuspermission;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.focus.focuspermission.core.listener.OnMustPermissionListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FocusPermission.mustRequestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                new OnMustPermissionListener() {
                    @Override
                    public void onFinish() {
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this,"权限全部通过" , Toast.LENGTH_LONG);
                            }
                        });

                    }

                    @Override
                    public String getDontAskDeniedPermissionDialogTip() {
                        return "非常抱歉，游戏需要读写权限才能正常运行。是否跳转到权限设置，进行授权？";
                    }
                });
    }

}