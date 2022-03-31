package com.focus.focuspermission.ui.PreRequestPermission;

import com.focus.focuspermission.core.PermissionInfo;

public class PreRequestPermissionItemInfo {
    private String permission;
    private OnClickGrantBtnCallback onClickGrantBtnCallback;
    private boolean isGrant;
    private PermissionInfo permissionInfo;

    public PermissionInfo getPermissionInfo() {
        return permissionInfo;
    }

    public void setPermissionInfo(PermissionInfo permissionInfo) {
        this.permissionInfo = permissionInfo;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public OnClickGrantBtnCallback getOnClickGrantBtnCallback() {
        return onClickGrantBtnCallback;
    }

    public void setOnClickGrantBtnCallback(OnClickGrantBtnCallback onClickGrantBtnCallback) {
        this.onClickGrantBtnCallback = onClickGrantBtnCallback;
    }

    public boolean isGrant() {
        return isGrant;
    }

    public void setGrant(boolean grant) {
        isGrant = grant;
    }
}
