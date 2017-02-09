package com.example.lj.ljplayerhd.main.firstpager.live;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.renderscript.RenderScript;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.base.BaseFragment;
import com.example.lj.ljplayerhd.bean.LiveBean;
import com.example.lj.ljplayerhd.databinding.FragmentLiveBinding;
import com.example.lj.ljplayerhd.media.player.FullScreenPlayActivity;
import com.trello.rxlifecycle.FragmentLifecycleProvider;

import java.util.List;

/**
 * Created by wh on 2016/12/29.
 */

public class LiveFragment extends BaseFragment implements LiveContract.View, SwipeRefreshLayout.OnRefreshListener {

    private FragmentLiveBinding liveBinding;
    private LiveAdapter mAdapter;
    private LivePresenter mPresenter;
    private boolean isVisible;
    private boolean isInit;
    private LocationManager locationManager;

    public LiveFragment() {
    }

    public static LiveFragment newInstance() {
        LiveFragment fragment = new LiveFragment();
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            startLoadData();
        } else {
            isVisible = false;
        }
    }

    private void startLoadData() {
        if (isVisible && isInit) {
            initData();
            isInit = false;
        }
    }

    private void initData() {
        showProgress();
        loadData();
    }

    private void loadData() {
//        Location location = getLocation();
        mPresenter.loadLiveData(0, 0);
    }

    private Location getLocation() {
        //获取所有可用的位置提供器
        List<String> providers = locationManager.getProviders(true);
        String locationProvider;
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            return null;
        }
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        //获取Location
        Location location = locationManager.getLastKnownLocation(locationProvider);
        return location;
    }

    private void showProgress() {
        liveBinding.progress.setVisibility(View.VISIBLE);
        liveBinding.recycler.setVisibility(View.GONE);
    }

    private void hideProgress() {
        liveBinding.progress.setVisibility(View.GONE);
        liveBinding.recycler.setVisibility(View.VISIBLE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_live;
    }

    @Override
    public void initViews() {
        mPresenter = new LivePresenter(this);
        liveBinding = FragmentLiveBinding.bind(root);
        liveBinding.recycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mAdapter = new LiveAdapter(mPresenter);
        liveBinding.recycler.setAdapter(mAdapter);
        liveBinding.swipe.setOnRefreshListener(this);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void initEvents() {
        isInit = true;
        startLoadData();
    }

    @Override
    public void updateLiveData(List<LiveBean.RoomBean> bean) {
        hideProgress();
        liveBinding.swipe.setRefreshing(false);
        if (bean != null)
            mAdapter.setData(bean);
    }

    @Override
    public FragmentLifecycleProvider getLifecycleProvider() {
        return this;
    }

    @Override
    public void goPlayerActivity(LiveBean.RoomBean bean) {
        getActivity().startActivity(FullScreenPlayActivity.newIntent(getActivity(), bean.getStream_addr()));
    }

    @Override
    public void onRefresh() {
        liveBinding.swipe.setRefreshing(true);
        loadData();
    }
}
