package com.focus.focuspermission.ui.PreRequestPermission;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.focus.focuspermission.core.Utils;

public class PreRequestPermissionItemViewInfo {
    private int position;

    public PreRequestPermissionItemViewInfo(int position){
        this.position = position;
    }

    public TextView getTextView_permission() {
        return textView_permission;
    }

    public void setTextView_permission(TextView textView_permission) {
        this.textView_permission = textView_permission;
    }

    public TextView getTextView_description() {
        return textView_description;
    }

    public void setTextView_description(TextView textView_description) {
        this.textView_description = textView_description;
    }

    public Button getBtn_grant() {
        return btn_grant;
    }

    public void setBtn_grant(Button btn_grant) {
        this.btn_grant = btn_grant;
        this.btn_grant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (preRequestPermissionItemInfo!=null){
                    preRequestPermissionItemInfo.getOnClickGrantBtnCallback().onRequestPermission(position);
                }
            }
        });
    }

    public PreRequestPermissionItemInfo getPreRequestPermissionItemInfo() {
        return preRequestPermissionItemInfo;
    }

    public void setPreRequestPermissionItemInfo(PreRequestPermissionItemInfo preRequestPermissionItemInfo) {
        this.preRequestPermissionItemInfo = preRequestPermissionItemInfo;
    }

    private PreRequestPermissionItemInfo preRequestPermissionItemInfo;
    private TextView textView_permission;
    private TextView textView_description;
    private Button btn_grant;


    public void updateGrantStateUI(){
        if (getPreRequestPermissionItemInfo().isGrant()){
            btn_grant.setText("已授权");
            Utils.changeShapeRoundButtonColor(btn_grant, "#2fff00");
        }
        else {
            btn_grant.setText("授权");
            Utils.changeShapeRoundButtonColor(btn_grant, "#b2afae");
        }

    }

}
