package com.example.lj.ljplayerhd.main;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.lj.ljplayerhd.LJApplication;
import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.base.BaseMultipleFragmentActivity;
import com.example.lj.ljplayerhd.bean.ConfigBean;
import com.example.lj.ljplayerhd.config.IDatas;
import com.example.lj.ljplayerhd.config.NetBroadcastReceiver;
import com.example.lj.ljplayerhd.main.firstpager.FirstPagerFragment;
import com.example.lj.ljplayerhd.main.offline.OfflineFragment;
import com.example.lj.ljplayerhd.main.offline.download.DownLoadService;
import com.example.lj.ljplayerhd.main.personal.PersonalFragment;
import com.example.lj.ljplayerhd.main.search.SearchFragment;
import com.example.lj.ljplayerhd.rxjava.RxNoHttp;
import com.example.lj.ljplayerhd.utils.DensityUtil;
import com.example.lj.ljplayerhd.utils.SLogUtil;
import com.example.lj.ljplayerhd.utils.ToastUtil;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.trello.rxlifecycle.ActivityLifecycleProvider;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by wh on 2016/11/19.
 */

public class MainActivity extends BaseMultipleFragmentActivity implements FloatingActionMenu.MenuStateChangeListener, MainContract.View {

    private ImageView fabContent;
    private FloatingActionMenu mMenu;
    private FirstPagerFragment firstPagerFragment;
    private SearchFragment searchFragment;
    private OfflineFragment offlineFragment;
    private PersonalFragment personalFragment;
    private BroadcastReceiver myReceiver;
    private MainContract.Presenter mPresenter;
    private Intent startDownLoadService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        registerNetReceiver();
        super.onCreate(savedInstanceState);
        mPresenter = new MainPresenter(this);
        //检查是否有读取内存的权限
//        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED||Build.VERSION.SDK_INT>=22 ) {
//            mPresenter.requestPermissions(MainPresenter.MY_PERMISSIONS_REQUEST_READ_CONTACTS);
//        }
        mPresenter.startDownLoadService(this);

    }

    private void registerNetReceiver() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        myReceiver = new NetBroadcastReceiver();
        this.registerReceiver(myReceiver, filter);
    }

    private void unregisterNetReceiver() {
        if (myReceiver != null)
            this.unregisterReceiver(myReceiver);
    }

    @Override
    protected void onDestroy() {
        unregisterNetReceiver();
        mPresenter.stopDownLoadService(this);
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_singlefragment;
    }

    @Override
    protected int getFragmentId() {
        return R.id.single_fragment_contain;
    }

    @Override
    public List<Fragment> createFragments() {
        List<Fragment> fragments = new ArrayList<Fragment>();
        firstPagerFragment = new FirstPagerFragment();
        searchFragment = new SearchFragment();
        offlineFragment = new OfflineFragment();
        personalFragment = new PersonalFragment();
        fragments.add(firstPagerFragment);
        fragments.add(searchFragment);
        fragments.add(offlineFragment);
        fragments.add(personalFragment);
        return fragments;
    }

    @Override
    protected void initViews() {
        createMenu();
    }

    private void createMenu() {
        fabContent = new ImageView(this);
        fabContent.setImageResource(R.drawable.ic_action_new);
        FloatingActionButton mActionButton = new FloatingActionButton.Builder(this)
                .setTheme(FloatingActionButton.THEME_DARK)
                .setContentView(fabContent)
                .setPosition(FloatingActionButton.POSITION_BOTTOM_RIGHT)
                .setBackgroundDrawable(R.drawable.main_menu_selector)
                .build();

        SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(this)
                .setTheme(SubActionButton.THEME_LIGHT);
        ImageView rlIcon1 = new ImageView(this);
        ImageView rlIcon2 = new ImageView(this);
        ImageView rlIcon3 = new ImageView(this);
        ImageView rlIcon4 = new ImageView(this);

        rlIcon1.setImageResource(R.drawable.firstpager);
        rlIcon2.setImageResource(R.drawable.search);
        rlIcon3.setImageResource(R.drawable.offline);
        rlIcon4.setImageResource(R.drawable.personal);

        SubActionButton firstPagerBt = rLSubBuilder.setContentView(rlIcon1).build();
        SubActionButton searchBt = rLSubBuilder.setContentView(rlIcon2).build();
        SubActionButton offlineBt = rLSubBuilder.setContentView(rlIcon3).build();
        SubActionButton personalBt = rLSubBuilder.setContentView(rlIcon4).build();
        firstPagerBt.setBackgroundResource(R.drawable.main_menu_item_selector);
        searchBt.setBackgroundResource(R.drawable.main_menu_item_selector);
        offlineBt.setBackgroundResource(R.drawable.main_menu_item_selector);
        personalBt.setBackgroundResource(R.drawable.main_menu_item_selector);

        firstPagerBt.setOnClickListener(firstPagerClickListener);
        searchBt.setOnClickListener(searchClickListener);
        offlineBt.setOnClickListener(offlineClickListener);
        personalBt.setOnClickListener(personalClickListener);

        // Set 4 SubActionButtons
        mMenu = new FloatingActionMenu.Builder(this)
                .setStartAngle(-180)
                .setEndAngle(-90)
                .setRadius(DensityUtil.dp2px(this, 150))
                .addSubActionView(firstPagerBt, DensityUtil.dp2px(this, 60), DensityUtil.dp2px(this, 60))
                .addSubActionView(searchBt, DensityUtil.dp2px(this, 60), DensityUtil.dp2px(this, 60))
                .addSubActionView(offlineBt, DensityUtil.dp2px(this, 60), DensityUtil.dp2px(this, 60))
                .addSubActionView(personalBt, DensityUtil.dp2px(this, 60), DensityUtil.dp2px(this, 60))
                .setStateChangeListener(this)
                .attachTo(mActionButton)
                .build();
    }

    @Override
    public void onMenuOpened(FloatingActionMenu floatingActionMenu) {
        PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 135);
        ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabContent, pvhR);
        animation.start();
    }

    @Override
    public void onMenuClosed(FloatingActionMenu floatingActionMenu) {
        PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
        ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabContent, pvhR);
        animation.start();
    }

    private View.OnClickListener firstPagerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (firstPagerFragment != null)
                showFragment(firstPagerFragment);
            mMenu.close(true);
        }
    };
    private View.OnClickListener searchClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (searchFragment != null)
                showFragment(searchFragment);
            mMenu.close(true);
        }
    };
    private View.OnClickListener offlineClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (offlineFragment != null)
                showFragment(offlineFragment);
            mMenu.close(true);
        }
    };
    private View.OnClickListener personalClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (personalFragment != null)
                showFragment(personalFragment);
            mMenu.close(true);
        }
    };

//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        mPresenter.onRequestPermissionsResult(requestCode,
//                permissions, grantResults);
//
//    }

    @Override
    public void gotoBack() {
        this.finish();
    }

    @Override
    public ActivityLifecycleProvider getActLife() {
        return this;
    }

}
