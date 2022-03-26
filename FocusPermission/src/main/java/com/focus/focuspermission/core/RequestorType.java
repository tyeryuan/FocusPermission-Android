package com.focus.focuspermission.core;

public enum RequestorType {
    /**
     * 通用的请求，就算拒绝了也会收到回调
     */
    RT_NORMAL,

    /**
     * 特殊的请求，必须全部通过才能收到回调
     */
    RT_MUST
}
