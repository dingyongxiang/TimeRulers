package com.yongxiang.timerulers;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by dingyongxiang on 2017/6/6.
 */

public class ExampleApplication extends Application {
    @Override public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...
    }
}
