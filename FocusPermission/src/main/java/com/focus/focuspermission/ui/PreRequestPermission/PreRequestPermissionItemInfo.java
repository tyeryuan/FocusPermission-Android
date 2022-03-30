package com.focus.focuspermission.ui.PreRequestPermission;

public class PreRequestPermissionItemInfo {
    private String permission;
    private OnGrantCallback onGrantCallback;
    private boolean isGrant;

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public OnGrantCallback getOnGrantCallback() {
        return onGrantCallback;
    }

    public void setOnGrantCallback(OnGrantCallback onGrantCallback) {
        this.onGrantCallback = onGrantCallback;
    }

    public boolean isGrant() {
        return isGrant;
    }

    public void setGrant(boolean grant) {
        isGrant = grant;
    }
}
