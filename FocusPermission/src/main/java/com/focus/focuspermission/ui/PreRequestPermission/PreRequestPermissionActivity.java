package com.focus.focuspermission.ui.PreRequestPermission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.focus.focuspermission.FocusInternalManager;
import com.focus.focuspermission.R;
import com.focus.focuspermission.core.PermissionInfo;
import com.focus.focuspermission.core.Utils;
import com.focus.focuspermission.core.listener.OnMustPermissionListener;
import com.focus.focuspermission.core.listener.OnNormalPermissionListener;

import java.util.ArrayList;
import java.util.List;

public class PreRequestPermissionActivity extends Activity implements OnClickGrantBtnCallback {

    //控件
    private TextView textView_title;
    private ListView listView_permissions;
    private Button button_oneGrant;

    private int width;
    private int height;
    private List<PreRequestPermissionItemInfo> preRequestPermissionItemInfos = new ArrayList<>();
    private PreRequestPermissionListViewAdapter preRequestPermissionListViewAdapter = null;

    private boolean clickOneGrant = false;

    //------申请的权限时传过来的参数

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
        preRequestPermissionItemInfos.clear();
        clickOneGrant = false;
        updatePermissionList();
    }

    private void initUI(){
        View view = View.inflate(this,R.layout.focus_permission_prerequest_dialog, null);
        setContentView(R.layout.focus_permission_prerequest_dialog);
        getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        setFinishOnTouchOutside(false);

        handleActivitySize();

        //
        textView_title = findViewById(R.id.textView_Title);
        listView_permissions = findViewById(R.id.listView_Permissions);
        button_oneGrant = findViewById(R.id.button_grant);
        button_oneGrant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickOneGrantButton();
            }
        });
        Utils.changeShapeRoundButtonColor(button_oneGrant, "#FF33B5E5");
    }

    //处理ActivityUI的宽高
    private void handleActivitySize(){
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = (int)(display.getWidth() * 0.8);
        p.height = (int)(display.getHeight() * 0.8);
        getWindow().setAttributes(p);     //设置生效
        width = p.width;
        height = p.height;

    }

    private void updatePermissionList(){
        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            PreRequestPermissionItemInfo itemInfo = new PreRequestPermissionItemInfo();
            itemInfo.setPermission(permission);
            itemInfo.setGrant(false);
            itemInfo.setPermissionInfo(PermissionInfo.GetPermissionInfo(this, permission));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED){
                    itemInfo.setGrant(true);
                }
            }
            itemInfo.setOnClickGrantBtnCallback(this::onRequestPermission);
            preRequestPermissionItemInfos.add(itemInfo);

        }
        preRequestPermissionListViewAdapter = new PreRequestPermissionListViewAdapter(this, preRequestPermissionItemInfos);
        listView_permissions.setAdapter(preRequestPermissionListViewAdapter);
    }

    @Override
    public void onRequestPermission(int position) {
        PreRequestPermissionItemInfo itemInfo = preRequestPermissionItemInfos.get(position);
        FocusInternalManager.getInstance().requestPermissions(this, new String[]{itemInfo.getPermission()}, new OnNormalPermissionListener() {
            @Override
            public void onFinish(List<String> deniedPermissionList) {
                //更新UI
                boolean grant = true;
                if (deniedPermissionList!=null && deniedPermissionList.contains(itemInfo.getPermission())){
                    grant = false;
                }
                updatePermissionItemUI(itemInfo, grant);
            }
        });
    }

    private void updatePermissionItemUI(){
        if (preRequestPermissionListViewAdapter!=null) {
            preRequestPermissionListViewAdapter.notifyDataSetChanged();
        }
        boolean allGrant = true;
        for (int i = 0; i < preRequestPermissionItemInfos.size(); i++) {
            PreRequestPermissionItemInfo itemInfo1 = preRequestPermissionItemInfos.get(i);
            if (!itemInfo1.isGrant()){
                allGrant = false;
                break;
            }
        }
        if (onMustPermissionListener!=null && !allGrant){
            return;
        }
        if (!allGrant){
            if (!clickOneGrant && onNormalPermissionListener!=null) {
                return;
            }
        }

        button_oneGrant.setText("确定");
    }

    private void updatePermissionItemUI(PreRequestPermissionItemInfo itemInfo, boolean grant){
        if (itemInfo.isGrant() == grant){
            //和上次一样，不更新UI
            return;
        }
        itemInfo.setGrant(grant);
        updatePermissionItemUI();
    }

    private void onClickOneGrantButton(){
        List<String> deniedList = new ArrayList<>();
        for (int i = 0; i < preRequestPermissionItemInfos.size(); i++) {
            PreRequestPermissionItemInfo itemInfo1 = preRequestPermissionItemInfos.get(i);
            if (!itemInfo1.isGrant()){
                deniedList.add(itemInfo1.getPermission());
            }
        }
        if (deniedList.size() > 0){
            if (onMustPermissionListener!=null){
                FocusInternalManager.getInstance().mustRequestPermissions(this, permissions, new OnMustPermissionListener() {
                    @Override
                    public void onFinish() {
                        //更新UI
                        for (int i = 0; i < preRequestPermissionItemInfos.size(); i++) {
                            PreRequestPermissionItemInfo itemInfo1 = preRequestPermissionItemInfos.get(i);
                            itemInfo1.setGrant(true);
                        }
                        updatePermissionItemUI();
                    }

                    @Override
                    public String getDontAskDeniedPermissionDialogTip() {
                        return onMustPermissionListener.getDontAskDeniedPermissionDialogTip();
                    }
                });
            }

            if (onNormalPermissionListener!=null){
                FocusInternalManager.getInstance().requestPermissions(this, permissions, new OnNormalPermissionListener() {
                    @Override
                    public void onFinish(List<String> deniedPermissionList) {
                        //更新UI
                        for (int i = 0; i < preRequestPermissionItemInfos.size(); i++) {
                            PreRequestPermissionItemInfo itemInfo1 = preRequestPermissionItemInfos.get(i);
                            if (deniedPermissionList!=null&&deniedPermissionList.contains(itemInfo1.getPermission())){
                                itemInfo1.setGrant(false);
                            }
                            else {
                                itemInfo1.setGrant(true);
                            }
                        }
                        updatePermissionItemUI();

                    }
                });
            }
            if (onMustPermissionListener!=null || !clickOneGrant){
                clickOneGrant = true;
                return;
            }
        }
        if (onMustPermissionListener!=null){
            onMustPermissionListener.onFinish();
        }
        if (onNormalPermissionListener!=null){
            onNormalPermissionListener.onFinish(deniedList);
        }
        finish();
    }
}
