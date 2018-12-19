package com.withive.util.progress;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.orhanobut.logger.Logger;

/**
 * 进度提示处理类
 */
public class ProgressBarHandler extends Handler {
    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;
    public static int heightPixels = -1;

    private Context mContext;
    private ProgressBar mProgressBar;
    private Boolean mDelayHideProgressBar;

    public ProgressBarHandler(Context mContext, int styleId, boolean delayHideProgressBar) {
        this.mContext = mContext;
        this.mDelayHideProgressBar = delayHideProgressBar;
        Logger.d("styleId: [%s], [%s]", styleId, styleId == android.R.attr.progressBarStyle);
        initProgressBar(styleId);
    }

    void initProgressBar(int styleId) {

        Activity activity = ((Activity) this.mContext);

        if (heightPixels == -1) {
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

            heightPixels = dm.heightPixels * 50 / 100;
        }

        switch (styleId) {
            case android.R.attr.progressBarStyle:
                /**
                 * 在页面中间显示
                 */

//                int padding_in_dp = 100;
//                final float scale = mContext.getResources().getDisplayMetrics().density;
//                int padding_in_px = (int) (padding_in_dp * scale + 0.5f);

                mProgressBar = new ProgressBar(mContext, null, android.R.attr.progressBarStyle);
                mProgressBar.setIndeterminate(true);
                mProgressBar.setVisibility(View.INVISIBLE);

                mProgressBar.setPadding(0, heightPixels, 0, 0);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                params.addRule(RelativeLayout.CENTER_IN_PARENT);
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.addRule(RelativeLayout.CENTER_VERTICAL);

                activity.addContentView(mProgressBar, params);

                break;

            case android.R.attr.progressBarStyleHorizontal:
                /**
                 * 在页面顶端显示
                 */

                mProgressBar = new ProgressBar(mContext, null, android.R.attr.progressBarStyleHorizontal);
                mProgressBar.setIndeterminate(true);
                mProgressBar.setVisibility(View.INVISIBLE);
                mProgressBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                ((Activity) this.mContext).addContentView(mProgressBar, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                break;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                mProgressBar.setVisibility(View.VISIBLE);
                break;
            case DISMISS_PROGRESS_DIALOG:
                /**
                 * 调试时延迟隐藏
                 */
                if (mDelayHideProgressBar) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setVisibility(View.INVISIBLE);
                        }
                    }, 500);
                } else {
                    mProgressBar.setVisibility(View.INVISIBLE);
                }

                break;
        }
    }
}
