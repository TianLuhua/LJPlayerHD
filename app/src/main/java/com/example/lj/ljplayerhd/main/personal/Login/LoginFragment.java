package com.example.lj.ljplayerhd.main.personal.Login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.base.BaseFragment;
import com.example.lj.ljplayerhd.config.IDatas;
import com.example.lj.ljplayerhd.databinding.FragmentLoginBinding;
import com.example.lj.ljplayerhd.rxjava.RxBus;
import com.example.lj.ljplayerhd.utils.SLogUtil;
import com.jakewharton.rxbinding.view.RxView;
import com.trello.rxlifecycle.FragmentLifecycleProvider;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * Created by tlh on 2016/12/1 0001.
 */

public class LoginFragment extends BaseFragment implements LoginContract.LogFragmentView {


    private FragmentLoginBinding fragmentLoginBinding;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View email_sign_in_button;
    private View mLoginFormView;
    private Button regiter;
    private View rememberPassword;
    private LoginContract.LogFragmentPresenter llp;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void initViews() {
        llp = new LoginPresenter(this, getContext());
        fragmentLoginBinding = FragmentLoginBinding.bind(root);
        fragmentLoginBinding.setLoginPresenter((LoginPresenter) llp);
        regiter = fragmentLoginBinding.regiter;
        rememberPassword = fragmentLoginBinding.rememberPassword;
        mEmailView = fragmentLoginBinding.email;
        mPasswordView = fragmentLoginBinding.password;
        mLoginFormView = root.findViewById(R.id.progress);
        email_sign_in_button = fragmentLoginBinding.emailSignInButton;
    }

    @Override
    public void initEvents() {


    }


    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }


    @Override
    public String getUserName() {

        String email = mEmailView.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            return null;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            return null;
        }
        return email;
    }

    @Override
    public String getPassword() {
        String password = mPasswordView.getText().toString();
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            return null;
        } else {
            return password;
        }
    }

    @Override
    public FragmentLifecycleProvider getFragmentLifecycleProvider() {
        return this;
    }

    @Override
    public void showProgress(boolean show) {
        mLoginFormView.setVisibility(show ? View.VISIBLE : View.GONE);
        regiter.setEnabled(show ? false : true);
        email_sign_in_button.setEnabled(show ? false : true);
        rememberPassword.setClickable(show ? false : true);
        mEmailView.setEnabled(show ? false : true);
        mPasswordView.setEnabled(show ? false : true);
    }

}
