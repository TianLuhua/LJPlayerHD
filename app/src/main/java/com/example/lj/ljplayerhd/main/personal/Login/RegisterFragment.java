package com.example.lj.ljplayerhd.main.personal.Login;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.base.BaseFragment;
import com.example.lj.ljplayerhd.databinding.FragmentRegisterBinding;
import com.example.lj.ljplayerhd.rxjava.RxBus;
import com.trello.rxlifecycle.FragmentLifecycleProvider;

/**
 * Created by Administrator on 2016/12/1 0001.
 */

public class RegisterFragment extends BaseFragment implements LoginContract.RegisterFragmentView {


    private LoginContract.RegisterFragmentPresenter lrp;

    private View regiterReturnLogin;
    private View registerButton;
    private EditText registerUserName;
    private EditText registerPassword;
    private EditText registerConfirmPassword;
    private View registerProgress;
    private FragmentRegisterBinding fragmentRegisterBinding;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    public void initViews() {
        fragmentRegisterBinding = FragmentRegisterBinding.bind(root);
        lrp = new RegisterPresenter(this, getActivity());
        fragmentRegisterBinding.setRegisterPresenter((RegisterPresenter) lrp);
        registerUserName = fragmentRegisterBinding.registerEmail;
        registerPassword = fragmentRegisterBinding.registerPassword;
        registerConfirmPassword = fragmentRegisterBinding.registerConfirmPassword;
        registerProgress = root.findViewById(R.id.progress);
        regiterReturnLogin = fragmentRegisterBinding.regiterReturnLogin;
        registerButton = fragmentRegisterBinding.registerButton;

    }

    @Override
    public void initEvents() {

    }

    @Override
    public String getUserName() {

        if (isEmailValid(registerUserName.getText().toString())) {
            registerUserName.setError(null);
            return registerUserName.getText().toString();
        }
        registerUserName.setError(getString(R.string.error_field_required));
        return null;
    }

    @Override
    public String getPassword() {
        String pwd = registerPassword.getText().toString();
        String confirmPassword = registerConfirmPassword.getText().toString();

        if (!isPasswordValid(pwd) && !isPasswordValid(confirmPassword)) {
            registerPassword.setError(getString(R.string.error_invalid_password));
            return null;
        }
        if (!matchePassWord(pwd, confirmPassword)) {
            registerPassword.setError(getString(R.string.error_confirm_password));
            return null;
        }
        registerPassword.setError(null);
        return pwd;
    }

    @Override
    public FragmentLifecycleProvider getFragmentLifecycleProvider() {
        return this;
    }

    @Override
    public void showProgress(boolean show) {
        registerProgress.setVisibility(show ? View.VISIBLE : View.GONE);
        registerUserName.setEnabled(show ? false : true);
        registerPassword.setEnabled(show ? false : true);
        registerConfirmPassword.setEnabled(show ? false : true);
        regiterReturnLogin.setEnabled(show ? false : true);
        registerButton.setEnabled(show ? false : true);

    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }


    public boolean matchePassWord(String pwd, String confirmPwd) {
        return TextUtils.equals(pwd, confirmPwd);
    }
}
