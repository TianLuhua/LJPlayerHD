package com.example.lj.ljplayerhd.main.personal.Login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.config.IDatas;
import com.example.lj.ljplayerhd.main.MainActivity;
import com.example.lj.ljplayerhd.main.MainContract;
import com.example.lj.ljplayerhd.rxjava.RxBus;
import com.example.lj.ljplayerhd.rxjava.RxNoHttp;
import com.example.lj.ljplayerhd.utils.SLogUtil;
import com.example.lj.ljplayerhd.utils.SPUtil;
import com.example.lj.ljplayerhd.utils.ToastUtil;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.RestRequest;
import com.yolanda.nohttp.rest.StringRequest;

import java.util.HashMap;
import java.util.Map;

import rx.functions.Action1;

/**
 * Created by Administrator on 2016/12/1 0001.
 */

public class LoginPresenter implements LoginContract.LogFragmentPresenter {

    public static final String USERNAME = "userName";
    public static String PASSWORD = "passWord";
    private Context mContext;
    private LoginContract.LogFragmentView llv;
    private String userName = null;
    private String passWord = null;
    private boolean rememberPassword;

    public LoginPresenter(LoginContract.LogFragmentView llv, Context mContext) {
        this.llv = llv;
        this.mContext = mContext;
    }


    //    ---------登录----------
//    用户名密码不能为空---- 0
//    用户不存在,或者被删除 ----- -1
//    用户名或密码错误 ------ -2
//    连接超时 ------- 空
//    未定义错误------ 其他
//    userName:466469918@
//    passWord:tll.46646
    @Override
    public void login() {

        if (llv != null) {
            userName = llv.getUserName();
            passWord = llv.getPassword();
        }
        if (rememberPassword && userName != null && passWord != null) {
            SPUtil.put(mContext, USERNAME, userName);
            SPUtil.put(mContext, PASSWORD, passWord);
        }

        //TODO: 2016/12/2 0002  这里实现登录
        SLogUtil.e("info", "rememberPassword:" + rememberPassword + "\n" + "userName:" + userName + "\n" + "passWord:" + passWord);
        final String registerUrl = IDatas.WebService.WEB_LOGIN;
        RestRequest<String> registerRequest = new StringRequest(registerUrl, RequestMethod.POST);
        Map registerMap = new HashMap<String, String>();
        registerMap.put("username", llv.getUserName());
        registerMap.put("password", llv.getPassword());
        registerRequest.add(registerMap);
        llv.showProgress(true);
        RxNoHttp.request(llv.getFragmentLifecycleProvider(), registerRequest, new Action1<Response<String>>() {
                    @Override
                    public void call(Response<String> objectResponse) {
                        String registerCode = objectResponse.get();
                        SLogUtil.e("info", registerCode + "\n" + registerUrl);
                        switch (registerCode) {
                            case "0":
                                ToastUtil.showShort(R.string.usernamenotnull);
                                llv.showProgress(false);
                                break;
                            case "-1":
                                ToastUtil.showShort(R.string.usernotexist);
                                llv.showProgress(false);
                                break;
                            case "-2":
                                ToastUtil.showShort(R.string.passwordwrong);
                                llv.showProgress(false);
                                break;
                            case "":
                                ToastUtil.showShort(R.string.connectwrong);
                                llv.showProgress(false);
                                break;
                            default:
                                ToastUtil.showShort("登录ok了！");
                                llv.showProgress(false);
                                break;
                        }
                    }
                }
        );
    }

    @Override
    public void rememberPassword(boolean rememberPassword) {
        this.rememberPassword = rememberPassword;
    }

    @Override
    public void shoewRegisterFragment() {
        SLogUtil.e("info", "shoewRegisterFragment---:" + IDatas.Messages.START_REGISTER);
        RxBus.getInstance().send(IDatas.Messages.START_REGISTER);
    }

}
