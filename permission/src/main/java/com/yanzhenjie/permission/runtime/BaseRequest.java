/*
 * Copyright 2019 Zhenjie Yan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yanzhenjie.permission.runtime;

import android.os.Build;

import androidx.annotation.NonNull;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;
import com.yanzhenjie.permission.checker.PermissionChecker;
import com.yanzhenjie.permission.source.Source;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created Zhenjie Yan on 2019-10-10.
 */
abstract class BaseRequest implements PermissionRequest {

    private Source mSource;

    private Rationale<List<String>> mRationale;

    private Action<List<String>> mGranted;
    private Action<List<String>> mDenied;
    private Action<List<String>> mAlwaysDenied;
    private String mMessage;

    BaseRequest(Source source) {
        this.mSource = source;
    }

    @Override
    public PermissionRequest rationale(@NonNull Rationale<List<String>> rationale) {
        this.mRationale = rationale;
        return this;
    }

    @Override
    public PermissionRequest onGranted(@NonNull Action<List<String>> granted) {
        this.mGranted = granted;
        return this;
    }

    @Override
    public PermissionRequest onDenied(@NonNull Action<List<String>> denied) {
        this.mDenied = denied;
        return this;
    }

    @Override
    public PermissionRequest onAlwaysDenied(@NonNull Action<List<String>> denied) {
        this.mAlwaysDenied = denied;
        return this;
    }

    @Override
    public PermissionRequest message(String message) {
        this.mMessage = message;
        return this;
    }

    protected boolean showRationaleOrNot(List<String> deniedPermissions) {
        return mRationale != null && !getRationalePermissions(mSource, deniedPermissions).isEmpty();
    }

    protected boolean showAlwaysDeniedOrNot(List<String> deniedPermissions) {
        return mAlwaysDenied != null && AndPermission.hasAlwaysDeniedPermission(mSource.getContext(), deniedPermissions);
    }

    final void callbackSucceed(List<String> grantedList) {
        if (mGranted != null) {
            mGranted.onAction(grantedList);
        }
    }

    final void callbackFailed(List<String> deniedList) {
        if (mDenied != null) {
            mDenied.onAction(deniedList);
        }
    }

    final void callbackRationale(List<String> rationaleList, RequestExecutor executor) {
        if (mRationale != null) {
            mRationale.showRationale(mSource.getContext(), rationaleList, executor);
        }
    }

    final void callbackAlwaysDenied(List<String> deniedList) {
        if (mAlwaysDenied != null) {
            mAlwaysDenied.onAction(deniedList);
        }
    }

    /**
     * Filter the permissions you want to apply; remove unsupported and duplicate permissions.
     */
    public static List<String> filterPermissions(List<String> permissions) {
        permissions = new ArrayList<>(new HashSet<>(permissions));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            permissions.remove(Permission.READ_PHONE_NUMBERS);
            permissions.remove(Permission.ANSWER_PHONE_CALLS);
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            permissions.remove(Permission.ACTIVITY_RECOGNITION);
            permissions.remove(Permission.ACCESS_BACKGROUND_LOCATION);
        }
        return permissions;
    }

    /**
     * Get denied permissions.
     */
    public static List<String> getDeniedPermissions(PermissionChecker checker, Source source, List<String> permissions) {
        List<String> deniedList = new ArrayList<>(1);
        for (String permission : permissions) {
            if (!checker.hasPermission(source.getContext(), permission)) {
                deniedList.add(permission);
            }
        }
        return deniedList;
    }

    /**
     * Get permissions to show rationale.
     */
    public static List<String> getRationalePermissions(Source source, List<String> deniedPermissions) {
        List<String> rationaleList = new ArrayList<>(1);
        for (String permission : deniedPermissions) {
            if (source.isShowRationalePermission(permission)) {
                rationaleList.add(permission);
            }
        }
        return rationaleList;
    }
}
