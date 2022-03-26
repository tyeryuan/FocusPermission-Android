package com.focus.focuspermission.core;

import com.focus.focuspermission.core.listener.OnMustPermissionListener;
import com.focus.focuspermission.core.listener.OnNormalPermissionListener;

import java.util.List;

public class PermissionRequester {
    private int requestCode;
    private List<String> permissions;
    private RequestorType requestorType;
    private OnMustPermissionListener onMustPermissionListener;
    private OnNormalPermissionListener onNormalPermissionListener;

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public PermissionRequester setPermissions(List<String> permissions) {
        this.permissions = permissions;
        return this;
    }

    public RequestorType getRequestorType() {
        return requestorType;
    }

    public PermissionRequester setRequestorType(RequestorType requestorType) {
        this.requestorType = requestorType;
        return this;
    }

    public OnMustPermissionListener getOnMustPermissionListener() {
        return onMustPermissionListener;
    }

    public PermissionRequester setOnMustPermissionListener(OnMustPermissionListener onMustPermissionListener) {
        this.onMustPermissionListener = onMustPermissionListener;
        return this;
    }

    public OnNormalPermissionListener getOnNormalPermissionListener() {
        return onNormalPermissionListener;
    }

    public PermissionRequester setOnNormalPermissionListener(OnNormalPermissionListener onNormalPermissionListener) {
        this.onNormalPermissionListener = onNormalPermissionListener;
        return this;
    }
}
