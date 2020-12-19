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
package com.yanzhenjie.permission.bridge;

/**
 * Created by Zhenjie Yan on 2/13/19.
 */
final class RequestExecutor extends Thread implements Messenger.Callback {

    private BridgeRequest mRequest;
    private Messenger mMessenger;

    public RequestExecutor(BridgeRequest request) {
        this.mRequest = request;
    }

    @Override
    public void run() {
        mMessenger = new Messenger(mRequest.getSource().getContext(), this);
        mMessenger.register(getName());

        String[] permissions = mRequest.getPermissions().toArray(new String[0]);
        BridgeActivity.requestPermission(mRequest.getSource(), getName(), permissions);
    }

    @Override
    public void onCallback() {
        synchronized (this) {
            mMessenger.unRegister();
            mRequest.getCallback().onCallback();
            mMessenger = null;
            mRequest = null;
        }
    }
}