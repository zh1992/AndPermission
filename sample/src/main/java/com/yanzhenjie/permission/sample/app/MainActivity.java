/*
 * Copyright © Zhenjie Yan
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
package com.yanzhenjie.permission.sample.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.yanzhenjie.permission.runtime.PermissionDef;
import com.yanzhenjie.permission.sample.R;
import com.yanzhenjie.permission.sample.RuntimeRationale;

import java.util.List;

/**
 * Created by Zhenjie Yan on 2016/9/17.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_SETTING = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.btn_request_camera).setOnClickListener(this);
        findViewById(R.id.btn_request_contact).setOnClickListener(this);
        findViewById(R.id.btn_request_location).setOnClickListener(this);
        findViewById(R.id.btn_request_calendar).setOnClickListener(this);
        findViewById(R.id.btn_request_microphone).setOnClickListener(this);
        findViewById(R.id.btn_request_storage).setOnClickListener(this);
        findViewById(R.id.btn_request_phone).setOnClickListener(this);
        findViewById(R.id.btn_request_call_log).setOnClickListener(this);
        findViewById(R.id.btn_request_sensors).setOnClickListener(this);
        findViewById(R.id.btn_request_activity_recognition).setOnClickListener(this);
        findViewById(R.id.btn_request_sms).setOnClickListener(this);
        findViewById(R.id.btn_setting).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_request_camera: {
                requestPermission(Permission.Group.CAMERA);
                break;
            }
            case R.id.btn_request_contact: {
                PopupMenu popupMenu = createMenu(v, getResources().getStringArray(R.array.contacts));
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int order = item.getItemId();
                        switch (order) {
                            case 0: {
                                requestPermission(Permission.READ_CONTACTS);
                                break;
                            }
                            case 1: {
                                requestPermission(Permission.WRITE_CONTACTS);
                                break;
                            }
                            case 2: {
                                requestPermission(Permission.GET_ACCOUNTS);
                                break;
                            }
                            case 3: {
                                requestPermission(Permission.Group.CONTACTS);
                                break;
                            }
                        }
                        return true;
                    }
                });
                popupMenu.show();
                break;
            }
            case R.id.btn_request_location: {
                PopupMenu popupMenu = createMenu(v, getResources().getStringArray(R.array.location));
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int order = item.getItemId();
                        switch (order) {
                            case 0: {
                                requestPermission(Permission.ACCESS_FINE_LOCATION);
                                break;
                            }
                            case 1: {
                                requestPermission(Permission.ACCESS_COARSE_LOCATION);
                                break;
                            }
                            case 2: {
                                requestPermission(Permission.Group.LOCATION);
                                break;
                            }
                        }
                        return true;
                    }
                });
                popupMenu.show();
                break;
            }
            case R.id.btn_request_calendar: {
                PopupMenu popupMenu = createMenu(v, getResources().getStringArray(R.array.calendar));
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int order = item.getOrder();
                        switch (order) {
                            case 0: {
                                requestPermission(Permission.READ_CALENDAR);
                                break;
                            }
                            case 1: {
                                requestPermission(Permission.WRITE_CALENDAR);
                                break;
                            }
                            case 2: {
                                requestPermission(Permission.Group.CALENDAR);
                                break;
                            }
                        }
                        return true;
                    }
                });
                popupMenu.show();
                break;
            }
            case R.id.btn_request_microphone: {
                requestPermission(Permission.Group.MICROPHONE);
                break;
            }
            case R.id.btn_request_storage: {
                PopupMenu popupMenu = createMenu(v, getResources().getStringArray(R.array.storage));
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int order = item.getOrder();
                        switch (order) {
                            case 0: {
                                requestPermission(Permission.READ_EXTERNAL_STORAGE);
                                break;
                            }
                            case 1: {
                                requestPermission(Permission.WRITE_EXTERNAL_STORAGE);
                                break;
                            }
                            case 2: {
                                requestPermission(Permission.Group.STORAGE);
                                break;
                            }
                        }
                        return true;
                    }
                });
                popupMenu.show();
                break;
            }
            case R.id.btn_request_phone: {
                PopupMenu popupMenu = createMenu(v, getResources().getStringArray(R.array.phone));
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int order = item.getOrder();
                        switch (order) {
                            case 0: {
                                requestPermission(Permission.READ_PHONE_STATE);
                                break;
                            }
                            case 1: {
                                requestPermission(Permission.CALL_PHONE);
                                break;
                            }
                            case 2: {
                                requestPermission(Permission.READ_PHONE_NUMBERS);
                                break;
                            }
                            case 3: {
                                requestPermission(Permission.ANSWER_PHONE_CALLS);
                                break;
                            }
                            case 4: {
                                requestPermission(Permission.USE_SIP);
                                break;
                            }
                            case 5: {
                                requestPermission(Permission.ADD_VOICEMAIL);
                                break;
                            }
                            case 6: {
                                // ADD_VOICEMAIL is special, not shown here.
                                requestPermission(Permission.READ_PHONE_STATE, Permission.CALL_PHONE, Permission.READ_PHONE_NUMBERS,
                                    Permission.ANSWER_PHONE_CALLS, Permission.USE_SIP);
                                break;
                            }
                        }
                        return true;
                    }
                });
                popupMenu.show();
                break;
            }
            case R.id.btn_request_call_log: {
                PopupMenu popupMenu = createMenu(v, getResources().getStringArray(R.array.call_log));
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int order = item.getOrder();
                        switch (order) {
                            case 0: {
                                requestPermission(Permission.READ_CALL_LOG);
                                break;
                            }
                            case 1: {
                                requestPermission(Permission.WRITE_CALL_LOG);
                                break;
                            }
                            case 2: {
                                requestPermission(Permission.PROCESS_OUTGOING_CALLS);
                                break;
                            }
                            case 3: {
                                requestPermission(Permission.Group.CALL_LOG);
                                break;
                            }
                        }
                        return true;
                    }
                });
                popupMenu.show();
                break;
            }
            case R.id.btn_request_sensors: {
                requestPermission(Permission.Group.SENSORS);
                break;
            }
            case R.id.btn_request_activity_recognition: {
                requestPermission(Permission.Group.ACTIVITY_RECOGNITION);
                break;
            }
            case R.id.btn_request_sms: {
                PopupMenu popupMenu = createMenu(v, getResources().getStringArray(R.array.sms));
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int order = item.getOrder();
                        switch (order) {
                            case 0: {
                                requestPermission(Permission.SEND_SMS);
                                break;
                            }
                            case 1: {
                                requestPermission(Permission.RECEIVE_SMS);
                                break;
                            }
                            case 2: {
                                requestPermission(Permission.READ_SMS);
                                break;
                            }
                            case 3: {
                                requestPermission(Permission.RECEIVE_WAP_PUSH);
                                break;
                            }
                            case 4: {
                                requestPermission(Permission.RECEIVE_MMS);
                                break;
                            }
                            case 5: {
                                requestPermission(Permission.Group.SMS);
                                break;
                            }
                        }
                        return true;
                    }
                });
                popupMenu.show();
                break;
            }
            case R.id.btn_setting: {
                setPermission();
                break;
            }
        }
    }

    /**
     * Request permissions.
     */
    private void requestPermission(@PermissionDef String... permissions) {
        AndPermission.with(this)
            .runtime()
            .permission(permissions)
            .rationale(new RuntimeRationale())
            .onGranted(new Action<List<String>>() {
                @Override
                public void onAction(List<String> permissions) {
                    toast(R.string.successfully);
                }
            })
            .onDenied(new Action<List<String>>() {
                @Override
                public void onAction(@NonNull List<String> permissions) {
                    toast(R.string.failure);
                    if (AndPermission.hasAlwaysDeniedPermission(MainActivity.this, permissions)) {
                        showSettingDialog(MainActivity.this, permissions);
                    }
                }
            })
            .start();
    }

    /**
     * Display setting dialog.
     */
    public void showSettingDialog(Context context, final List<String> permissions) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = context.getString(R.string.message_permission_always_failed,
            TextUtils.join("\n", permissionNames));

        new AlertDialog.Builder(context).setCancelable(false)
            .setTitle(R.string.title_dialog)
            .setMessage(message)
            .setPositiveButton(R.string.setting, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setPermission();
                }
            })
            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            })
            .show();
    }

    /**
     * Set permissions.
     */
    private void setPermission() {
        AndPermission.with(this).runtime().setting().start(REQUEST_CODE_SETTING);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SETTING: {
                Toast.makeText(MainActivity.this, R.string.message_setting_comeback, Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    /**
     * Create menu.
     */
    private PopupMenu createMenu(View v, String[] menuArray) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        Menu menu = popupMenu.getMenu();
        for (int i = 0; i < menuArray.length; i++) {
            String menuText = menuArray[i];
            menu.add(0, i, i, menuText);
        }
        return popupMenu;
    }

    protected void toast(@StringRes int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}