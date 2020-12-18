package com.yanzhenjie.permission.bridge;

interface IBridge {
    /**
     * Request for permissions.
     */
    void requestPermission(in String suffix, in String[] permissions);
}