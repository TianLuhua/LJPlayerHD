package com.example.lj.ljplayerhd.main.personal.Login;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.Switch;

import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.config.IDatas;
import com.example.lj.ljplayerhd.rxjava.RxBus;
import com.example.lj.ljplayerhd.rxjava.RxNoHttp;
import com.example.lj.ljplayerhd.utils.SLogUtil;
import com.example.lj.ljplayerhd.utils.ToastUtil;
import com.trello.rxlifecycle.FragmentLifecycleProvider;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.RestRequest;
import com.yolanda.nohttp.rest.StringRequest;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import rx.functions.Action1;

/**
 * Created by Administrator on 2016/12/2 0002.
 */

public class RegisterPresenter implements LoginContract.RegisterFragmentPresenter {


    private LoginContract.RegisterFragmentView lrv;
    private Context mContext;

    public RegisterPresenter(LoginContract.RegisterFragmentView lrv, Context mContext) {
        this.lrv = lrv;
        this.mContext = mContext;
    }


    //    ----注册----
//    用户名密码不能为空---- 0
//    两次输入的密码不一致---- 0.1
//    用户名不合法 ----- -1
//    包含不允许注册的词语 ----- -2
//    用户名已经存在 ------- -3
//     10分钟内不能再次注册 ------ -7
//    用户名或密码太短 ------ -8
//    连接超时 ------- 空
//    未定义错误------ 其他

    @Override
    public void register() {
        SLogUtil.e("info", "register");
        lrv.showProgress(true);
        final String registerUrl = IDatas.WebService.WEB_REGISTER;
        RestRequest<String> registerRequest = new StringRequest(registerUrl, RequestMethod.POST);
        Map registerMap = new HashMap<String, String>();
        registerMap.put("username", lrv.getUserName());
        registerMap.put("password", lrv.getPassword());
        registerRequest.add(registerMap);
        RxNoHttp.request(lrv.getFragmentLifecycleProvider(), registerRequest, new Action1<Response<String>>() {
                    @Override
                    public void call(Response<String> objectResponse) {
                        String registerCode = objectResponse.get();
                        SLogUtil.e("info", registerCode + "\n" + registerUrl);
                        switch (registerCode) {
                            case "0":
                                ToastUtil.showShort(R.string.usernamenotnull);
                                lrv.showProgress(false);
                                break;
                            case "0.1":
                                ToastUtil.showShort(R.string.repasswordwrong);
                                lrv.showProgress(false);
                                break;
                            case "-1":
                                ToastUtil.showShort(R.string.usernameuninvalide);
                                lrv.showProgress(false);
                                break;
                            case "-2":
                                ToastUtil.showShort(R.string.involve);
                                lrv.showProgress(false);
                                break;
                            case "-3":
                                ToastUtil.showShort(R.string.usernameexist);
                                lrv.showProgress(false);
                                break;
                            case "-7":
                                ToastUtil.showShort(R.string.canntregister);
                                lrv.showProgress(false);
                                break;
                            case "-8":
                                ToastUtil.showShort(R.string.usernametooshort);
                                lrv.showProgress(false);
                                break;
                            case "":
                                ToastUtil.showShort(R.string.connectwrong);
                                lrv.showProgress(false);
                                break;
                            default:
                                ToastUtil.showShort(R.string.registersuccess);
                                lrv.showProgress(false);

                                break;
                        }

                    }
                }

        );
        //TODO: 2016/12/2 0002  这里实现注册
    }


    @Override
    public void regiterReturnLogin() {
        RxBus.getInstance().send(IDatas.Messages.RETRUN_LOGIN);
    }


}
