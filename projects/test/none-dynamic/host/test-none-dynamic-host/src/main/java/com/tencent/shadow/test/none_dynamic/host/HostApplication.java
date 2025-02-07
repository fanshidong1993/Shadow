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

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Parcel;
import android.os.StrictMode;

import com.tencent.shadow.core.common.InstalledApk;
import com.tencent.shadow.core.common.LoggerFactory;
import com.tencent.shadow.core.load_parameters.LoadParameters;
import com.tencent.shadow.core.loader.ShadowPluginLoader;
import com.tencent.shadow.core.runtime.container.ContentProviderDelegateProviderHolder;
import com.tencent.shadow.core.runtime.container.DelegateProviderHolder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class HostApplication extends Application {
    private static Application sApp;

    public final static String PART_MAIN = "partMain";
    public final static String PART_1 = "part1";
    public final static String PART_2 = "part2";
    public final static String BASE = "base";

    private static final PreparePluginApkBloc sPluginPrepareBloc
            = new PreparePluginApkBloc(
            "plugin.apk"
    );
    private static final PreparePluginApkBloc sPluginPrepareBloc1
            = new PreparePluginApkBloc(
            "plugin1.apk"
    );
    private static final PreparePluginApkBloc sPluginPrepareBloc2
            = new PreparePluginApkBloc(
            "plugin2.apk"
    );
    private static final PreparePluginApkBloc sPluginPrepareBlocBase
            = new PreparePluginApkBloc(
            "base.apk"
    );

    static {
        detectNonSdkApiUsageOnAndroidP();

        LoggerFactory.setILoggerFactory(new SLoggerFactory());
    }

    private ShadowPluginLoader mPluginLoader;

    private final Map<String, InstalledApk> mPluginMap = new HashMap<>();

    public void loadPlugin(final String partKey, final Runnable completeRunnable) {
        InstalledApk installedApk = mPluginMap.get(partKey);
        if (installedApk == null) {
            throw new NullPointerException("partKey == " + partKey);
        }

        if (mPluginLoader.getPluginParts(partKey) == null) {
            // 插件访问宿主类的白名单
            String[] hostWhiteList = new String[]{
                    "androidx.test.espresso",//这个包添加是为了general-cases插件中可以访问测试框架的类
                    "com.tencent.shadow.test.lib.plugin_use_host_code_lib.interfaces"//测试插件访问宿主白名单类
            };
            String[] dependsOn = null;
            if(partKey.equals(PART_1) || partKey.equals(PART_2))
                dependsOn = new String[]{BASE};
            LoadParameters loadParameters =  new LoadParameters(null,
                    partKey,
                    dependsOn,
                    hostWhiteList);


            Parcel parcel = Parcel.obtain();
            loadParameters.writeToParcel(parcel, 0);
            final InstalledApk plugin = new InstalledApk(
                    installedApk.apkFilePath,
                    installedApk.oDexPath,
                    installedApk.libraryPath,
                    parcel.marshall()
            );
            parcel.recycle();

            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    ShadowPluginLoader pluginLoader = mPluginLoader;
                    Future<?> future = null;
                    try {
                        future = pluginLoader.loadPlugin(plugin);
                        future.get(10, TimeUnit.SECONDS);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException("加载失败", e);
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    mPluginLoader.callApplicationOnCreate(partKey);
                    completeRunnable.run();
                }
            }.execute();
        } else {
            completeRunnable.run();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;

        CDatabaseObject.INSTANCE.getCDatabase(this).getDataCDao().save(new DataC(8,"8"));

        ShadowPluginLoader loader = mPluginLoader = new TestPluginLoader(getApplicationContext());
        loader.onCreate();
        DelegateProviderHolder.setDelegateProvider(loader.getDelegateProviderKey(), loader);
        ContentProviderDelegateProviderHolder.setContentProviderDelegateProvider(loader);

        InstalledApk installedApk = sPluginPrepareBloc.preparePlugin(this.getApplicationContext());
        InstalledApk installedApk1 = sPluginPrepareBloc1.preparePlugin(this.getApplicationContext());
        InstalledApk installedApk2 = sPluginPrepareBloc2.preparePlugin(this.getApplicationContext());
        InstalledApk base = sPluginPrepareBlocBase.preparePlugin(this.getApplicationContext());
        mPluginMap.put(PART_MAIN, installedApk);
        mPluginMap.put(BASE, base);
        mPluginMap.put(PART_1, installedApk1);
        mPluginMap.put(PART_2, installedApk2);
    }

    private static void detectNonSdkApiUsageOnAndroidP() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            return;
        }
        boolean isRunningEspressoTest;
        try {
            Class.forName("android.support.test.espresso.Espresso");
            isRunningEspressoTest = true;
        } catch (Exception ignored) {
            isRunningEspressoTest = false;
        }
        if (isRunningEspressoTest) {
            return;
        }
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        builder.detectNonSdkApiUsage();
        StrictMode.setVmPolicy(builder.build());
    }

    public static Application getApp() {
        return sApp;
    }

    public ShadowPluginLoader getPluginLoader() {
        return mPluginLoader;
    }
}
