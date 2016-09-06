package com.csy.navitionview.app;

import im.fir.sdk.FIR;

/**
 * Created by chensy160806 on 2016/9/6.
 */
public class Application  extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FIR.init(this);
    }
}


