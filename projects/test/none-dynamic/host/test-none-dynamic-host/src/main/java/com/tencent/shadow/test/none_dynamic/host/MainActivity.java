/*
 * Tencent is pleased to support the open source community by making Tencent Shadow available.
 * Copyright (C) 2019 THL A29 Limited, a Tencent company.  All rights reserved.
 *
 * Licensed under the BSD 3-Clause License (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.tencent.shadow.test.none_dynamic.host;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import static com.tencent.shadow.test.none_dynamic.host.HostApplication.BASE;
import static com.tencent.shadow.test.none_dynamic.host.HostApplication.PART_1;
import static com.tencent.shadow.test.none_dynamic.host.HostApplication.PART_2;
import static com.tencent.shadow.test.none_dynamic.host.HostApplication.PART_MAIN;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.TestHostTheme);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        throw new RuntimeException("必须赋予权限.");
                    }
                }
            }
        }
    }

    public void startPlugin(View view) {
        final HostApplication application = (HostApplication) getApplication();
        application.loadPlugin(PART_MAIN, new Runnable(){
            @Override
            public void run() {
                Intent pluginIntent = new Intent();
                pluginIntent.setClassName(getPackageName(), "com.tencent.shadow.test.plugin.general_cases.lib.usecases.activity.TestListActivity");
                pluginIntent.putStringArrayListExtra("activities", TestComponentManager.sActivities);
                Intent intent = application.getPluginLoader().getMComponentManager().convertPluginActivityIntent(pluginIntent);
                startActivity(intent);
            }
        });
        application.loadPlugin(BASE, new Runnable() {
            @Override
            public void run() {

            }
        });
        application.loadPlugin(PART_1, new Runnable(){
            @Override
            public void run() {

                Intent pluginIntent1 = new Intent();
                pluginIntent1.setClassName(getPackageName(), "com.tencent.shadow.none_dynamic_room_plugin1.MainAct");
                pluginIntent1.putStringArrayListExtra("activities", TestComponentManager.sActivities);
                Intent intent1 = application.getPluginLoader().getMComponentManager().convertPluginActivityIntent(pluginIntent1);
                startActivity(intent1);

            }
        });
        application.loadPlugin(PART_2, new Runnable(){
            @Override
            public void run() {

                Intent pluginIntent1 = new Intent();
                pluginIntent1.setClassName(getPackageName(), "com.tencent.shadow.none_dynamic_room_plugin2.MainAct");
                pluginIntent1.putStringArrayListExtra("activities", TestComponentManager.sActivities);
                Intent intent1 = application.getPluginLoader().getMComponentManager().convertPluginActivityIntent(pluginIntent1);
                startActivity(intent1);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        //可以从App Inspection 观察到数据库正常
        CDatabaseObject.INSTANCE.getCDatabase(this).getDataCDao().save(new DataC(8, System.currentTimeMillis() + ""));
    }
}
