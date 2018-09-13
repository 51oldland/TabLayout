package applica;

import android.app.Application;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.www.fish.AppMainActivity;
import com.www.fish.R;
import com.yhao.floatwindow.FloatWindow;
import com.yhao.floatwindow.MoveType;
import com.yhao.floatwindow.PermissionListener;
import com.yhao.floatwindow.ViewStateListener;

import utils.DensityUtil;

/**
 * Created by wwp
 * DATE: 2018/5/8:14:40
 * Copyright: 中国自主招生网 All rights reserved
 * Description:
 */

public class BaseApplication extends Application {
    private static final String TAG = "FloatWindow";

    @Override
    public void onCreate() {
        super.onCreate();
        View mViewOne = View.inflate(this, R.layout.scrolltextview, null);
        View mViewTwo = View.inflate(this, R.layout.confirm, null);
        View mViewThree = View.inflate(this, R.layout.luanma, null);
        FloatWindow
                .with(getApplicationContext())
                .setView(mViewOne)
                .setWidth(DensityUtil.dip2px(getApplicationContext(), 200)) //设置悬浮控件宽高
                .setHeight(DensityUtil.dip2px(getApplicationContext(), 250))
                .setX(DensityUtil.dip2px(getApplicationContext(), 90))
                .setY(DensityUtil.dip2px(getApplicationContext(), 200))
                .setTag("one")
//                .setMoveType(MoveType.slide,100,-100)
                .setMoveType(MoveType.active)
                .setMoveStyle(500, new BounceInterpolator())
                .setFilter(true, AppMainActivity.class)
                .setViewStateListener(mViewStateListener)
                .setPermissionListener(mPermissionListener)
                .setDesktopShow(true)
                .build();
        FloatWindow
                .with(getApplicationContext())
                .setView(mViewTwo)
                .setWidth(DensityUtil.dip2px(getApplicationContext(), 150)) //设置悬浮控件宽高
                .setHeight(DensityUtil.dip2px(getApplicationContext(), 150))
                .setX(DensityUtil.dip2px(getApplicationContext(), 90))
                .setY(DensityUtil.dip2px(getApplicationContext(), 200))
                .setTag("two")
//                .setMoveType(MoveType.slide,100,-100)
                .setMoveType(MoveType.active)
                .setFilter(true, AppMainActivity.class)
                .build();
        FloatWindow
                .with(getApplicationContext())
                .setView(mViewThree)
                .setWidth(DensityUtil.dip2px(getApplicationContext(), 120)) //设置悬浮控件宽高
                .setHeight(DensityUtil.dip2px(getApplicationContext(), 140))
                .setX(DensityUtil.dip2px(getApplicationContext(), 110))
                .setY(DensityUtil.dip2px(getApplicationContext(), 220))
                .setTag("three")
//                .setMoveType(MoveType.slide,100,-100)
                .setMoveType(MoveType.active)
                .setFilter(true, AppMainActivity.class)
                .build();

    }

    private PermissionListener mPermissionListener = new PermissionListener() {
        @Override
        public void onSuccess() {
            Log.d(TAG, "onSuccess");
        }

        @Override
        public void onFail() {
            Log.d(TAG, "onFail");
        }
    };

    private ViewStateListener mViewStateListener = new ViewStateListener() {
        @Override
        public void onPositionUpdate(int x, int y) {
            Log.d(TAG, "onPositionUpdate: x=" + x + " y=" + y);
        }

        @Override
        public void onShow() {
            Log.d(TAG, "onShow");
        }

        @Override
        public void onHide() {
            Log.d(TAG, "onHide");
        }

        @Override
        public void onDismiss() {
            Log.d(TAG, "onDismiss");
        }

        @Override
        public void onMoveAnimStart() {
            Log.d(TAG, "onMoveAnimStart");
        }

        @Override
        public void onMoveAnimEnd() {
            Log.d(TAG, "onMoveAnimEnd");
        }

        @Override
        public void onBackToDesktop() {
            Log.d(TAG, "onBackToDesktop");
        }
    };
}
