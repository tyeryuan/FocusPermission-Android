package com.focus.focuspermission.core.listener;

import java.util.List;

public interface OnNormalPermissionListener {
    void onFinish(List<String> deniedPermissionList);
}
