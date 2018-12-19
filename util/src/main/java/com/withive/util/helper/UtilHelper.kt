package com.withive.util.helper

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.support.v7.app.AlertDialog
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.orhanobut.logger.Logger


object UtilHelper {
    /** 显示一个对话框，确定与取消
     * @param ctx 上下文
     * @param title 标题
     * @param message 内容
     * @param positiveText 确定提示文字
     * @param negativeText 取消提示文字
     * @param positiveListener 确定事件
     * @param negativeListener 取消事件
     */
    @JvmOverloads
    @JvmStatic
    fun dialog(ctx: Context,
               title: String = "消息",
               message: String,
               positiveText: String = "确定",
               negativeText: String = "取消",
               positiveListener: DialogInterface.OnClickListener? = null,
               negativeListener: DialogInterface.OnClickListener? = null) {

        val builder = AlertDialog.Builder(ctx)

        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveText, positiveListener)
                .setNegativeButton(negativeText, negativeListener)

        builder.create().show()
    }

    /**
     * 显示一个只有确定的对话框，需要提供确定事件
     * @param ctx 上下文
     * @param title 标题
     * @param message 内容
     * @param positiveListener 确定事件
     */
    @JvmOverloads
    @JvmStatic
    fun dialogOk(ctx: Context,
                 title: String,
                 message: String,
                 positiveText: String = "确定",
                 positiveListener: DialogInterface.OnClickListener? = null) {
        val builder = AlertDialog.Builder(ctx)

        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveText, positiveListener)

        builder.create().show()
    }

//    /**
//     * 居中提示文字
//     * @param context 上下文
//     * @param message 内容
//     * @param duration 提示时间
//     * @param showLargeTextSize 是否增加字体大小
//     */
//    @JvmOverloads
//    @JvmStatic
//    fun toastCenter(context: Context,
//                    message: String,
//                    duration: Int = Toast.LENGTH_LONG,
//                    showLargeTextSize: Boolean = false) {
//        val toast = Toast.makeText(context, message, duration)
//        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
//        if (showLargeTextSize) {
//            val linearLayout = toast.view as LinearLayout
//            val messageTextView = linearLayout.getChildAt(0) as TextView
//            messageTextView.textSize = 20f
//        }
//        toast.show()
//    }

    /**
     * Activity 之间切换
     * @param packageContext 源活动
     * @param toCls 目标活动
     * @param finishFrom 是否关闭源活动
     */
    @JvmOverloads
    @JvmStatic
    fun fromTo(packageContext: Context, toCls: Class<*>, finishFrom: Boolean = false) {
        val activity = packageContext as Activity

        val intent = Intent()
        intent.setClass(packageContext, toCls)

        // Check if we're running on Android 5.0 or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Apply activity transition
//            activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
//            activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        } else {
            // Swap without transition
//            activity.startActivity(intent)
        }

        activity.startActivity(intent)

        if (finishFrom) {
            activity.finish()
        }
        Logger.d("UtilHelper.From %s to %s", packageContext.javaClass.simpleName, toCls.simpleName)
    }

}