package Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.www.fish.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import Views.EditTextWithDel;
import utils.PreferencesObjectUtil;


public class LoginFragment extends Fragment implements View.OnClickListener {
    View mView;
    private CheckBox mCheckBoxShowPwd;
    private int mIntShwoPwd = 1;  //1 显示密码  0 隐藏密码

    private EditTextWithDel mEdtLogname;
    private EditTextWithDel mEdtLogpwd;
    private Button mBtLog;
    private RelativeLayout mRelativeLayoutCheckbox;
    TextView mShifouzhuceTv;

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String mUrl = "http://103.208.33.33:9999/CheckRegistCode/registCode/checkCode.action";
    private String mUrlGetUser = "http://103.208.33.33:9999/CheckRegistCode/registCode/getTermInfo.action";
    private String mUrlSaveUser = "http://103.208.33.33:9999/CheckRegistCode/registCode/saveTermInfo.action";
    private String mIsLogin = "";

    private String mUserName = "";
    private String mUserPwd = "";
    String ANDROID_ID = "";

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_blank, container, false);
        ANDROID_ID = Settings.System.getString(getActivity().getContentResolver(), Settings.System.ANDROID_ID);
        initView();
        getUserData();
        return mView;
    }

    private void initView() {
        mRequestQueue = Volley.newRequestQueue(getActivity());
        mShifouzhuceTv = mView.findViewById(R.id.shifouzhuce);
        mRelativeLayoutCheckbox = mView.findViewById(R.id.login_relayout_cb);
        mCheckBoxShowPwd = mView.findViewById(R.id.login_imgv_pwd);
        mEdtLogname = mView.findViewById(R.id.login_edt_username);
        mEdtLogpwd = mView.findViewById(R.id.login_edt_userpwd);
        mBtLog = mView.findViewById(R.id.login_bt_login);
        mBtLog.setOnClickListener(this);
        mRelativeLayoutCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mIntShwoPwd) {
                    case 1:
                        mIntShwoPwd = 0;
                        mCheckBoxShowPwd.setChecked(true);
                        break;
                    case 0:
                        mIntShwoPwd = 1;
                        mCheckBoxShowPwd.setChecked(false);
                        break;
                }
            }
        });

        mCheckBoxShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    //隐藏
                    mEdtLogpwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    //显示
                    mEdtLogpwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                mEdtLogpwd.setSelection(mEdtLogpwd.getText().toString().length());
            }
        });
        //密码域隐藏
        mEdtLogpwd.setTransformationMethod(PasswordTransformationMethod
                .getInstance());
        mEdtLogpwd.setSelection(mEdtLogpwd.getText().toString().length());
//        mEdtLogpwd.postInvalidate();
        mEdtLogpwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!mEdtLogpwd.getText().toString().trim().equals("")) {
                    mBtLog.setBackgroundResource(R.drawable.lsh_tv_pressed_blue_style);
                    mBtLog.setTextColor(Color.parseColor("#ffffff"));
                } else {
                    mBtLog.setBackgroundResource(R.drawable.btn_login_pressed_false_shape);
                    mBtLog.setTextColor(Color.parseColor("#ffffff"));
                }
            }
        });

    }

    public void getUserData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("termId", ANDROID_ID);
            getUserMessObject(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_bt_login:
                if (!TextUtils.isEmpty(mEdtLogpwd.getText().toString().trim()) &&
                        !TextUtils.isEmpty(mEdtLogname.getText().toString().trim())) {

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("termId", ANDROID_ID);
                        jsonObject.put("userAccount", mEdtLogname.getText().toString().trim());
                        jsonObject.put("code", mEdtLogpwd.getText().toString().trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    getJsonObject(jsonObject);
                } else {
                    Toast.makeText(getActivity(), "请输入账号或注册码", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void getUserMessObject(JSONObject params) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, mUrlGetUser, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                System.out.println("WWW---" + jsonObject);
                try {
                    String mSuccess = jsonObject.getString("success");
                    String mMesg = jsonObject.getString("message");
                    Toast.makeText(getActivity(), mMesg, Toast.LENGTH_SHORT).show();
                    if (mSuccess.equals("true")) {
                        String mUserAccount = jsonObject.getString("userAccount");
                        String mUserCode = jsonObject.getString("code");
                        mEdtLogname.setText(mUserAccount);
                        mEdtLogpwd.setText(mUserCode);
                        mShifouzhuceTv.setText("已注册");
                        mBtLog.setEnabled(false);
                        PreferencesObjectUtil.saveObject("true", "isLogin", getActivity());
                    } else {
                        mShifouzhuceTv.setText("未注册");
                        mBtLog.setEnabled(true);
                        PreferencesObjectUtil.saveObject("false", "isLogin", getActivity());
                    }
//                    setText(mSuccess);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                PreferencesObjectUtil.saveObject("false", "isLogin", getActivity());
                Toast.makeText(getActivity(), "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequest.setTag("UserObjectRequest");
        mRequestQueue.add(jsonObjectRequest);
    }

    public void getJsonObject(JSONObject params) {
        JsonObjectRequest jsonObjectRequest = new CharsetJsonRequest(Request.Method.POST, mUrlSaveUser, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                System.out.println("WWW---" + jsonObject);
                try {
                    String mSuccess = jsonObject.getString("success");
                    String mMesg = jsonObject.getString("message");
//                    PreferencesObjectUtil.saveObject(mEdtLogname.getText().toString().trim(), "mUserName", getActivity());
//                    PreferencesObjectUtil.saveObject(mEdtLogpwd.getText().toString().trim(), "mUserPwd", getActivity());
                    Toast.makeText(getActivity(), mMesg, Toast.LENGTH_SHORT).show();
                    setText(mSuccess);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(), "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequest.setTag("jsonObjectRequest");
        mRequestQueue.add(jsonObjectRequest);
    }

    private void setText(String isSuccess) {
        if (isSuccess.equals("true")) {
            PreferencesObjectUtil.saveObject("true", "isLogin", getActivity());
            mShifouzhuceTv.setText("已注册");
        }
    }

    public class CharsetJsonRequest extends JsonObjectRequest {

        public CharsetJsonRequest(String url, JSONObject jsonRequest,
                                  Listener<JSONObject> listener, ErrorListener errorListener) {
            super(url, jsonRequest, listener, errorListener);
        }

        public CharsetJsonRequest(int method, String url, JSONObject jsonRequest,
                                  Listener<JSONObject> listener, ErrorListener errorListener) {
            super(method, url, jsonRequest, listener, errorListener);
        }

        @Override
        protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

            try {
                String jsonString = new String(response.data, "UTF-8");
                return Response.success(new JSONObject(jsonString),
                        HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            } catch (JSONException je) {
                return Response.error(new ParseError(je));
            }
        }

    }
}
