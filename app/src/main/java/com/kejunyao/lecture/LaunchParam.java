package com.kejunyao.lecture;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

/**
 * 统一参数较多的Activity启动入口基类
 */
public abstract class LaunchParam {

    public static final String INTENT_EXTRA_PARAM = "_intent_extra_param_";

    public static <T extends LaunchParam & Parcelable> T obtainParam(Intent intent) {
        if (intent == null) {
            return null;
        }
        return intent.getParcelableExtra(INTENT_EXTRA_PARAM);
    }

    public LaunchParam() {
    }

    public abstract void startActivity(Context context);
}
