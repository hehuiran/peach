/*
 * Copyright 2017 JessYan
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
package me.jessyan.peach.shop.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.ali.auth.third.core.MemberSDK;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.blankj.utilcode.util.Utils;
import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.integration.cache.IntelligentCache;
import com.jess.arms.utils.ArmsUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;

import butterknife.ButterKnife;
import me.jessyan.peach.shop.BuildConfig;
import timber.log.Timber;

/**
 * ================================================
 * 展示 {@link AppLifecycles} 的用法
 * <p>
 * Created by JessYan on 04/09/2017 17:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class AppLifecyclesImpl implements AppLifecycles {

    private static final String TAG = "AppLifecyclesImpl";

    @Override
    public void attachBaseContext(@NonNull Context base) {
//          MultiDex.install(base);  //这里比 onCreate 先执行,常用于 MultiDex 初始化,插件化框架的初始化
    }

    @Override
    public void onCreate(@NonNull Application application) {
        if (LeakCanary.isInAnalyzerProcess(application)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        if (BuildConfig.LOG_DEBUG) {//Timber初始化
            //Timber 是一个日志框架容器,外部使用统一的Api,内部可以动态的切换成任何日志框架(打印策略)进行日志打印
            //并且支持添加多个日志框架(打印策略),做到外部调用一次 Api,内部却可以做到同时使用多个策略
            //比如添加三个策略,一个打印日志,一个将日志保存本地,一个将日志上传服务器
            Timber.plant(new Timber.DebugTree());
            // 如果你想将框架切换为 Logger 来打印日志,请使用下面的代码,如想切换为其他日志框架请根据下面的方式扩展
//                    Logger.addLogAdapter(new AndroidLogAdapter());
//                    Timber.plant(new Timber.DebugTree() {
//                        @Override
//                        protected void log(int priority, String tag, String message, Throwable t) {
//                            Logger.log(priority, tag, message, t);
//                        }
//                    });
            ButterKnife.setDebug(true);
        }
        //LeakCanary 内存泄露检查
        //使用 IntelligentCache.KEY_KEEP 作为 key 的前缀, 可以使储存的数据永久存储在内存中
        //否则存储在 LRU 算法的存储空间中, 前提是 extras 使用的是 IntelligentCache (框架默认使用)
        ArmsUtils.obtainAppComponentFromContext(application).extras()
                .put(IntelligentCache.getKeyOfKeep(RefWatcher.class.getName())
                        , BuildConfig.USE_CANARY ? LeakCanary.install(application) : RefWatcher.DISABLED);


        Utils.init(application);
        initUM(application);
        initAlibc(application);
    }

    private void initUM(Application application) {
        UMConfigure.setLogEnabled(BuildConfig.LOG_DEBUG);
        UMConfigure.init(application, "5c2b43fdf1f556f1860002d3"
                , "UMENG_APPKEY", UMConfigure.DEVICE_TYPE_PHONE, "");

        PlatformConfig.setWeixin("wx6079f1dafdd1ebd1", "e90182aa51c437457145a49f84ffa9c4");

        /*PlatformConfig.setSinaWeibo("2763906099", "855c8d550a2eca79eb46aad7488e1cc8"
                , "http://sns.whalecloud.com/sina2/callback");*/

        PlatformConfig.setSinaWeibo("2763906099", "855c8d550a2eca79eb46aad7488e1cc8"
                , "http://open.weibo.com/apps/2763906099/info/advanced");
        PlatformConfig.setQQZone("1107926243", "KUDWUWobClTMsb35");

        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(application).setShareConfig(config);
    }

    private void initAlibc(Application application) {
        if (BuildConfig.LOG_DEBUG) {
            MemberSDK.turnOnDebug();
        }
        AlibcTradeSDK.asyncInit(application, new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
                Timber.tag(TAG).d("阿里百川初始化成功");
                /*AlibcTaokeParams params = new AlibcTaokeParams("mm_52605298_11108750_38144362","","");
                AlibcTradeSDK.setTaokeParams(params);*/
            }

            @Override
            public void onFailure(int code, String msg) {
                Timber.tag(TAG).e("阿里百川初始化失败" + "code=" + code + " msg=" + msg);
            }
        });

    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }
}
