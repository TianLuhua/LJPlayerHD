package com.example.lj.ljplayerhd.main.offline.dlna;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.base.BaseFragment;
import com.example.lj.ljplayerhd.databinding.FragmentOfflineDlnaBinding;
import com.example.lj.ljplayerhd.main.offline.dlna.adapter.DeviceAdapter;
import com.example.lj.ljplayerhd.main.offline.dlna.adapter.DirectoryAdapter;
import com.example.lj.ljplayerhd.main.offline.dlna.adapter.MBaseAdapter;
import com.example.lj.ljplayerhd.main.offline.dlna.center.DMSDeviceBrocastFactory;
import com.example.lj.ljplayerhd.main.offline.dlna.center.IBaseEngine;
import com.example.lj.ljplayerhd.main.offline.dlna.center.MediaItemFactory;
import com.example.lj.ljplayerhd.main.offline.dlna.center.MediaServerProxy;
import com.example.lj.ljplayerhd.main.offline.dlna.model.MediaItem;
import com.example.lj.ljplayerhd.main.offline.dlna.proxy.AllShareProxy;
import com.example.lj.ljplayerhd.main.offline.dlna.proxy.BrowseDMSProxy;
import com.example.lj.ljplayerhd.main.offline.dlna.proxy.IDeviceChangeListener;
import com.example.lj.ljplayerhd.main.offline.dlna.proxy.MediaManager;
import com.example.lj.ljplayerhd.main.offline.dlna.utils.CommonUtil;
import com.example.lj.ljplayerhd.main.offline.dlna.utils.DlnaUtils;
import com.example.lj.ljplayerhd.main.offline.dlna.utils.UpnpUtil;

import org.cybergarage.upnp.Device;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/20 0020.
 */

public class DlnaFragment extends BaseFragment implements IDeviceChangeListener, DlnaFragmentContract.View {

    public static final String POSITION = "position";
    private WifiManager.MulticastLock mMulticastLock;
    private ListView deviceFileView;
    private IBaseEngine serverProxy;
    private AllShareProxy playreProxy;
    private DeviceAdapter adapter;
    private DMSDeviceBrocastFactory deviceBrocastFactory;
    private DirectoryAdapter directoryAdapter;
    private DirectoryAdapter fileAdapter;
    private DMRSelectedWindow dmrSelectedWindow;
    private DlnaFragmentContract.Presenter presenter;
    private FragmentOfflineDlnaBinding dlnaBinding;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_offline_dlna;
    }

    @Override
    public void initViews() {
        mMulticastLock = CommonUtil.openWifiBrocast(getActivity());
        deviceFileView = (ListView) root.findViewById(R.id.device_list);
        adapter = new DeviceAdapter(getActivity(), new ArrayList<Device>());
        directoryAdapter = new DirectoryAdapter(getActivity(),
                new ArrayList<MediaItem>());
        fileAdapter = new DirectoryAdapter(getActivity(), new ArrayList<MediaItem>());
        adapter.setOnItemClickListener(deviceItemClickListener);
        directoryAdapter.setOnItemClickListener(directoryItemClickListener);
        fileAdapter.setOnItemClickListener(fileItemClickListener);
        dmrSelectedWindow = new DMRSelectedWindow(getActivity());
    }

    @Override
    public void initEvents() {
        serverProxy = MediaServerProxy.getSingleton(getActivity().getApplicationContext());
        playreProxy = AllShareProxy.getInstance(getActivity().getApplicationContext());
        // deviceUpdateBrocastFactory=new DeviceUpdateBrocastFactory(this);
        // deviceUpdateBrocastFactory.register(this);
        deviceBrocastFactory = new DMSDeviceBrocastFactory(getActivity());
        deviceBrocastFactory.registerListener(this);
        DlnaUtils.setDevName(getActivity(), "LJDlna");
        serverProxy.startEngine();
        playreProxy.startSearch();
        presenter = new DlanPresenter(this);
        dlnaBinding = FragmentOfflineDlnaBinding.bind(root);
        dlnaBinding.setPresenter((DlanPresenter) presenter);

    }


    private MBaseAdapter.OnItemClickListener fileItemClickListener = new MBaseAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            View view = v.findViewById(R.id.device_item_img);
            MediaItem item = (MediaItem) view.getTag();
            if (item == null) {
                return;
            }
            if (UpnpUtil.isAudioItem(item)
                    && playreProxy.getDMRSelectedDevice() != null) {
                Log.e("info", "isAudioItem");
                Toast.makeText(getActivity(), "" + item.getRes(),
                        Toast.LENGTH_SHORT).show();
                goMusicControlActivity(position, item);
            } else if (UpnpUtil.isVideoItem(item)
                    && playreProxy.getDMRSelectedDevice() != null) {
                Log.e("info", "isVideoItem");
                Toast.makeText(getActivity(), "" + item.getRes(),
                        Toast.LENGTH_SHORT).show();
                goVideoControlActivity(position, item);
            } else if (UpnpUtil.isPictureItem(item)
                    && playreProxy.getDMRSelectedDevice() != null) {
                Log.e("info", "isPictureItem");
                Toast.makeText(getActivity(), "" + item.getRes(),
                        Toast.LENGTH_SHORT).show();
                goPictureControlActivity(position, item);
            } else if (UpnpUtil.isStorageFolder(item)) {
                Log.e("info", "isStorageFolder");
                Toast.makeText(getActivity(), "" + item.getStringid(),
                        Toast.LENGTH_SHORT).show();
                requestFile(item);
            }
            if (playreProxy.getDMRSelectedDevice() == null && !UpnpUtil.isStorageFolder(item)) {
                Toast.makeText(getActivity(),
                        "请选择播放器!-" + item.getObjectClass(), Toast.LENGTH_SHORT)
                        .show();
            }
        }
    };

    private MBaseAdapter.OnItemClickListener deviceItemClickListener = new MBaseAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            View view = v.findViewById(R.id.device_item_img);
            Device device = (Device) view.getTag();
            if (device != null) {
                playreProxy.setDMSSelectedDevice(device);
                Toast.makeText(getActivity(),
                        "" + device.getFriendlyName(), Toast.LENGTH_SHORT)
                        .show();
                //请求DMS端的文件列表
                requestDirectory("0");
            }
        }
    };

    private MBaseAdapter.OnItemClickListener directoryItemClickListener = new MBaseAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            View view = v.findViewById(R.id.device_item_img);
            MediaItem item = (MediaItem) view.getTag();
            if (item != null) {
                Toast.makeText(
                        getActivity(),
                        "" + item.getObjectClass() + ",Stringid"
                                + item.getStringid(), Toast.LENGTH_SHORT)
                        .show();
                Log.e("info", "" + item.getObjectClass());
                requestFile(item);
            }
        }
    };

    private void goPictureControlActivity(int index, MediaItem item) {
        MediaManager.getInstance().setPictureList(fileAdapter.getDatas());
        Intent intent = new Intent();
        intent.setClass(getActivity(), ImageControllerActivity.class);
        intent.putExtra(POSITION, index);
        MediaItemFactory.putItemToIntent(item, intent);
        this.startActivity(intent);
    }

    private void goVideoControlActivity(int index, MediaItem item) {
        MediaManager.getInstance().setVideoList(fileAdapter.getDatas());
        Intent intent = new Intent();
        intent.setClass(getActivity(), VideoControllerActivity.class);
        intent.putExtra(POSITION, index);
        MediaItemFactory.putItemToIntent(item, intent);
        this.startActivity(intent);
    }

    private void goMusicControlActivity(int index, MediaItem item) {
        MediaManager.getInstance().setMusicList(fileAdapter.getDatas());
        Intent intent = new Intent();
        intent.setClass(getActivity(), MusicControllerActivity.class);
        intent.putExtra(POSITION, index);
        MediaItemFactory.putItemToIntent(item, intent);
        this.startActivity(intent);
    }

    protected void requestDirectory(String id) {
        //directoryCallback为跟新List的Callback,每次点击Item将结果回调回来
        BrowseDMSProxy.syncGetDirectory(getActivity(), id, directoryCallback);
    }

    private void requestFile(MediaItem item) {
        BrowseDMSProxy.syncGetItems(getActivity(), item.getStringid(), fileCallback);
    }

    private BrowseDMSProxy.BrowseRequestCallback fileCallback = new BrowseDMSProxy.BrowseRequestCallback() {
        @Override
        public void onGetItems(final List<MediaItem> list) {
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    if (list == null) {
                        Toast.makeText(getActivity(), "没有获取到数据!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    deviceFileView.setAdapter(fileAdapter);
                    curradaAdapter = fileAdapter;
                    fileAdapter.refreshData(list);
                }
            });
        }
    };

    private BrowseDMSProxy.BrowseRequestCallback directoryCallback = new BrowseDMSProxy.BrowseRequestCallback() {
        @Override
        public void onGetItems(final List<MediaItem> list) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (list != null) {
                        deviceFileView.setAdapter(directoryAdapter);
                        curradaAdapter = directoryAdapter;
                        directoryAdapter.refreshData(list);
                    }
                }
            });
        }
    };


    private MBaseAdapter curradaAdapter;

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            moveTaskToBack(false);
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }


    @Override
    public void onDestroy() {
        mMulticastLock.release();
        mMulticastLock = null;
        deviceBrocastFactory.unRegisterListener();
        super.onDestroy();
        super.onDestroy();
    }

    private void updateDMSDeviceList() {
        deviceFileView.setAdapter(adapter);
        curradaAdapter = adapter;
        //获取局域网中DMS设备
        List<Device> devices = playreProxy.getDMSDeviceList();
        adapter.refreshData(devices);
    }

    private void updateDMRDeviceList() {
        //获取局域网中DMR设备
        List<Device> devices = playreProxy.getDMRDeviceList();
        dmrSelectedWindow.refreshData(devices);
    }

    // ! 注:此处加载dms与dmr时间不同,因此应该分开
    @Override
    public void onDeviceChange(boolean isSelDeviceChange) {
        updateDMSDeviceList();
        updateDMRDeviceList();
    }


    @Override
    public void refresh() {
        serverProxy.restartEngine();
        playreProxy.resetSearch();
    }

    @Override
    public void player(View v) {
        dmrSelectedWindow.showPopupWindow(v);
    }

    @Override
    public void exit() {
        serverProxy.stopEngine();
        playreProxy.exitSearch();
//        finish();
    }

    @Override
    public void fileBack() {
        if (curradaAdapter == fileAdapter) {
            curradaAdapter = directoryAdapter;
            deviceFileView.setAdapter(directoryAdapter);
        } else if (curradaAdapter == directoryAdapter) {
            curradaAdapter = adapter;
            deviceFileView.setAdapter(adapter);
        }
    }
}
