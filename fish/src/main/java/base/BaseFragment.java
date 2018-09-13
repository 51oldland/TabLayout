package base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by wwp
 * DATE: 2018/5/7:10:58
 * Copyright: 中国自主招生网 All rights reserved
 * Description:
 */

public class BaseFragment extends Fragment {
    public FragmentManager mFragmentManager;
    public String mTag;
    public Activity mActivity;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFragmentManager=this.getFragmentManager();
        mTag=this.getClass().getSimpleName();
        mActivity=this.getActivity();
    }
}
