package Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.www.fish.R;
import com.yhao.floatwindow.FloatWindow;

import utils.PreferencesObjectUtil;

public class SettingFragment extends Fragment implements View.OnClickListener {
    View mView;
    TextView mXiayulv;
    MaterialDialog.Builder mMaterialDialog;
    SeekBar mSeekBar;
    TextView mProgressValueTv;
    TextView mStartUpTv;
    private int mProgressValue = 10;
    private boolean isShowLuanma = false;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initView() {
        TextView mConfirm = FloatWindow.get("two").getView().findViewById(R.id.confirm);
        TextView mStop = FloatWindow.get("three").getView().findViewById(R.id.stop);
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatWindow.get("one").hide();
                FloatWindow.get("two").hide();
                FloatWindow.get("three").show();
                isShowLuanma = true;
            }
        });
        mStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(), "停止", Toast.LENGTH_SHORT).show();
                if (getActivity() != null) {
                    getActivity().finish();
                }
                isShowLuanma = false;
                FloatWindow.get("three").hide();
            }
        });
        mXiayulv = mView.findViewById(R.id.xiayulv);
        mStartUpTv = mView.findViewById(R.id.start_up);
        mXiayulv.setOnClickListener(this);
        mStartUpTv.setOnClickListener(this);
        mMaterialDialog = new MaterialDialog.Builder(getActivity())
                .title("调整命中率")
//                        .content("一个简单的dialog,高度会随着内容改变，同时还可以嵌套RecyleView")
//                        .iconRes(R.mipmap.ic_launcher_round)
                .positiveColor(Color.parseColor("#3699ff"))
                .negativeColor(Color.parseColor("#3699ff"))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        Toast.makeText(getActivity(), "mSeekBar.getProgress():" + mSeekBar.getProgress(), Toast.LENGTH_SHORT).show();
//                        if (mSeekBar != null&&mProgressValue!=null)
                        mXiayulv.setText(mProgressValue + "%");
                    }
                })
                .positiveText("确定")
                .negativeText("取消")
                .customView(R.layout.progress, false);

        View customeView = mMaterialDialog.build().getCustomView();

        mSeekBar = customeView.findViewById(R.id.progress);
        mProgressValueTv = customeView.findViewById(R.id.text1);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 当拖动条的滑块位置发生改变时触发该方法,在这里直接使用参数progress，即当前滑块代表的进度值
//                mProgressValue.setText(Integer.toString(progress));
                int MIN = 10;
                if (progress < MIN) {
                    mProgressValueTv.setText(mProgressValue + "%");
                } else {
                    mProgressValue = progress;
                }
                mProgressValueTv.setText(mProgressValue + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.e("------------", "开始滑动！");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.e("------------", "停止滑动！");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_setting, container, false);
        initView();
        initFloatWindow();
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        FloatWindow.get("one").hide();
        FloatWindow.get("two").hide();
        FloatWindow.get("three").hide();
        if (isShowLuanma) {
            FloatWindow.get("three").show();
        } else {
            FloatWindow.get("three").hide();
        }
    }

    private void initFloatWindow() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.xiayulv:
                mMaterialDialog.show();
                break;
            case R.id.start_up:
                if (mStartUpTv.getText().equals("启动")) {
                    if (PreferencesObjectUtil.readObject("isLogin", getActivity()) != null) {
                        String isScuess = (String) PreferencesObjectUtil.readObject("isLogin", getActivity());
                        if (!TextUtils.isEmpty(isScuess) && isScuess.equals("true")) {
                            Intent home = new Intent(Intent.ACTION_MAIN);
                            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            home.addCategory(Intent.CATEGORY_HOME);
                            startActivity(home);
                            FloatWindow.get("one").show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    FloatWindow.get("one").hide();
                                    FloatWindow.get("two").show();
                                    mStartUpTv.setText("已启动");
                                }
                            }, 22 * 1000);
                        } else {
                            Toast.makeText(getActivity(), "未注册", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "未注册", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "已启动", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
