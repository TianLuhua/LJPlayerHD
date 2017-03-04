package com.example.lj.ljplayerhd.main.offline.local;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.module.ManifestParser;
import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.base.BaseFragment;
import com.example.lj.ljplayerhd.bean.LocalVedio;
import com.example.lj.ljplayerhd.config.IDatas;
import com.example.lj.ljplayerhd.main.MainPresenter;
import com.example.lj.ljplayerhd.main.offline.local.adapter.LocalFragmentAdapter;
import com.example.lj.ljplayerhd.media.player.FullScreenPlayActivity;
import com.example.lj.ljplayerhd.utils.SLogUtil;
import com.example.lj.ljplayerhd.utils.SPUtil;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Tianluhua on 2016/11/20 0020.
 */

public class LocalFragment extends BaseFragment implements LocalFragmentContract.View, EasyPermissions.PermissionCallbacks {


    private static final int LOCALFRAGMENT_WRITE_EXTERNAL_STORAGE = 1;
    private static final int LOCALFRAGMENT_SETTINGS_SCREEN = 2;
    private Context mContext;
    private RecyclerView localRecyclerView;
    private LocalFragmentContract.Presenter presenter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_persoanl_local;
    }

    @Override
    public void initViews() {
        localRecyclerView =
                (RecyclerView) root.findViewById(R.id.fragment_persnoal_local_recyclerview);
        localRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
    }

    @Override
    public void initEvents() {
        this.mContext = getActivity();
        presenter = new LocalFragmentPresenter(this, getActivity());
        rsTask();

    }

    @Override
    public void goPlayerActivity(String path) {
        getActivity().startActivity(FullScreenPlayActivity.newIntent(getActivity(), path));
    }

    @Override
    public void updateData(List<LocalVedio> localVedios) {
        if (localRecyclerView != null)
            localRecyclerView.setAdapter(new LocalFragmentAdapter(localVedios, (LocalFragmentPresenter) presenter));
    }

    @Override
    public void canCelRefresh() {
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        presenter.setRecyclerViewAdapter();
        presenter.canCelRefresh();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this, getString(R.string.rationale_ask_again))
                    .setTitle(getString(R.string.title_settings_dialog))
                    .setPositiveButton(getString(R.string.setting))
                    .setNegativeButton(getString(R.string.cancel), null /* click listener */)
                    .setRequestCode(LOCALFRAGMENT_SETTINGS_SCREEN)
                    .build()
                    .show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOCALFRAGMENT_SETTINGS_SCREEN) {
            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(getActivity(), R.string.returned_from_app_settings_to_activity, Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @AfterPermissionGranted(LOCALFRAGMENT_WRITE_EXTERNAL_STORAGE)
    private void rsTask() {
        if (EasyPermissions.hasPermissions(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // Have permission, do the thing!
            presenter.setRecyclerViewAdapter();
            presenter.canCelRefresh();
        } else {
            // Request one permission
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_sms),
                    LOCALFRAGMENT_WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null)
            presenter = null;
    }
}
