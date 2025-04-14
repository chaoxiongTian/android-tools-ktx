package com.r_jeff.android_tools_ktx.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.StringRes;

import java.lang.reflect.Field;

public class SimplexToast {
    private static Toast mToast;
    private static int yOffset;
    private static final Handler handler = new Handler(Looper.getMainLooper());

    private SimplexToast(Context context) {

    }

    synchronized private static Toast init(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context should not be null!!!");
        }
        if (mToast == null) {
            mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
            yOffset = mToast.getYOffset();
        }
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.BOTTOM, 0, yOffset);
        mToast.setMargin(0, 0);
        return mToast;
    }

    private static void show(String content) {
        show(content, Toast.LENGTH_SHORT);
    }

    private static void show(String content, int duration) {
        show(null, content, Gravity.BOTTOM, duration);
    }

    public static void show(Context context, @StringRes int rid) {
        show(context, context.getResources().getString(rid));
    }

    public static void show(Context context, String content) {
        show(context, content, Gravity.BOTTOM);
    }

    public static void show(Context context, String content, int gravity) {
        show(context, content, gravity, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, String content, int gravity, int duration) {
        if (content != null && content.contains("Animators may only be run on Looper threads")) {
            return;
        }
        if (Looper.myLooper() != Looper.getMainLooper()) {
            handler.post(() -> show(context, content, gravity, duration));
            return;
        }
        if (!TextUtils.isEmpty(content)) {
            if (mToast == null) {
                try {
                    init(context.getApplicationContext());
                } catch (Exception e) {
                    // 对于 Android 5 的部分机型,出现创建 toast Crash. 捕获该异常,不做弹窗
                    return;
                }
            }
            mToast.setText(content);
            mToast.setDuration(duration);
            mToast.setGravity(gravity, 0, yOffset);
            // notice: android 10 以上无法发射 mTN, 所以会失败.
            hookToast(mToast);
            WindowManager mWM = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            View toastView = mToast.getView();
            if (toastView != null && toastView.isAttachedToWindow()) {
                mWM.removeView(toastView);
            }
            mToast.show();
        }
    }

    /**
     * 反射替换handler
     *
     * @param toast
     */
    public static void hookToast(Toast toast) {
        try {
            //获取mTN成员变量
            Field mField_mTN = toast.getClass().getDeclaredField("mTN");
            mField_mTN.setAccessible(true);
            //获取mHandler成员变量
            Object TN = mField_mTN.get(toast);
            Field mField_mHandler = TN.getClass().getDeclaredField("mHandler");
            mField_mHandler.setAccessible(true);
            Handler mHandler = (Handler) mField_mHandler.get(TN);
            if (!(mHandler instanceof ToastNewHandler)) {
                //替换handler
                mField_mHandler.set(TN, new ToastNewHandler(mHandler));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * handler中的crash捕获
     */
    private static class ToastNewHandler extends Handler {

        private Handler impl;

        public ToastNewHandler(Handler impl) {
            this.impl = impl;
        }

        @Override
        public void handleMessage(Message msg) {
            try {
                impl.handleMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
