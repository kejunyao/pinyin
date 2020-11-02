package com.kejunyao.lecture;

import android.app.Application;
import android.content.Context;

/**
 * $类描述$
 *
 * @author kejunyao
 * @since 2020年10月08日
 */
public class App extends Application {

    private static Context sContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        sContext = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static Context getContext() {
        return sContext;
    }
}
