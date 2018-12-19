package com.withive.util.helper;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ToastUtil {

    private static Toast toast;
    public static boolean showInCenter = false;
    /**
     * @param context  上下文
     * @param showInfo 内容
     * @param showType Toast 显示时间长短类型  如：Toast.LENGTH_SHORT
     */
    public static void showToast(Context context, String showInfo, int showType) {
        if (context == null) {
            return;
        }

        cancelToast();

        toast = Toast.makeText(context, showInfo, showType);

        if(showInCenter){
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        }

        toast.show();
    }

    /**
     * @param context  上下文
     * @param showInfo 内容
     */
    public static void showToast(Context context, String showInfo) {
        showToast(context, showInfo, Toast.LENGTH_SHORT);
    }

    public static void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }
}
