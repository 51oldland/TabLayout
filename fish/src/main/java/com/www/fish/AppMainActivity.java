package com.www.fish;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhao.floatwindow.FloatWindow;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgePagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Fragments.ContactFragment;
import Fragments.LoginFragment;
import Fragments.SettingFragment;
import Fragments.UseDesFragment;
import adapter.ExamplePagerAdapter;
import adapter.TabFragmentPagerAdapter;

public class AppMainActivity extends AppCompatActivity implements View.OnClickListener {
    private MagicIndicator mMagicIndicator = null;
    private static final String[] CHANNELS = new String[]{"软件开通中心", "软件设置区", "使用说明", "客服中心"};
//    private static final String[] CHANNELS = new String[]{"开通中心", "软件设置", "使用说明", "客服中心"};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private ExamplePagerAdapter mExamplePagerAdapter = new ExamplePagerAdapter(mDataList);
    private ViewPager mViewPager;
    private TextView mGuanbi;
    private List<Fragment> mFragmentlist = new ArrayList<>();
    TabFragmentPagerAdapter mTabFragmentPagerAdapter;
    LoginFragment mLoginFragment = new LoginFragment();
    UseDesFragment mUseDesFragment = new UseDesFragment();
    SettingFragment mSettingFragment = new SettingFragment();
    ContactFragment mContactFragment = new ContactFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mGuanbi = findViewById(R.id.guanbi);
        initView();
    }

    private void initView() {
        mViewPager.setOffscreenPageLimit(3);
        mGuanbi.setOnClickListener(this);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ViewGroup decorViewGroup = (ViewGroup) window.getDecorView();
        View statusBarView = new View(window.getContext());
        int statusBarHeight = getStatusBarHeight(window.getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, statusBarHeight);
        params.gravity = Gravity.TOP;
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(getResources().getColor(R.color.color_0000aa));
        decorViewGroup.addView(statusBarView);

        mFragmentlist.add(mLoginFragment);
        mFragmentlist.add(mSettingFragment);
        mFragmentlist.add(mUseDesFragment);
        mFragmentlist.add(mContactFragment);
        mTabFragmentPagerAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), mFragmentlist);
        mViewPager.setAdapter(mTabFragmentPagerAdapter);
        initMagicIndicator4();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FloatWindow.get("one").hide();
        FloatWindow.get("two").hide();
        FloatWindow.get("three").hide();
    }

    private static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    private void initMagicIndicator4() {
        MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator4);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                BadgePagerTitleView badgePagerTitleView = new BadgePagerTitleView(context);

                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);//设置为可渐变的View
//                simplePagerTitleView.setWidth(ScreenUtil.getScreenWidth(context) / 4);
//                simplePagerTitleView.setPadding(0, 0, 0, 0);
                simplePagerTitleView.setNormalColor(Color.parseColor("#333333"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#3699ff"));
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setTextSize(15);//设置字体大小
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                badgePagerTitleView.setInnerPagerTitleView(simplePagerTitleView);

                return badgePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setLineHeight(UIUtil.dip2px(context, 2));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(Color.parseColor("#3699ff"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE); //设置tab的显示模式，SHOW_DIVIDER_MIDDLE为居中
//        titleContainer.setDividerDrawable(new ColorDrawable() {
//            @Override
//            public int getIntrinsicWidth() {
//                return UIUtil.dip2px(AppMainActivity.this, 30);//tab的宽度
//            }
//        });
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.guanbi:
                finish();
                break;
        }
    }
}
