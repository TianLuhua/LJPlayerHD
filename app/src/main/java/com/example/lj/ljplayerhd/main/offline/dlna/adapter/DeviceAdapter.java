package com.example.lj.ljplayerhd.main.offline.dlna.adapter;

import android.content.Context;
import android.graphics.Color;

import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.main.offline.dlna.proxy.AllShareProxy;
import com.example.lj.ljplayerhd.main.offline.dlna.utils.UpnpUtil;

import org.cybergarage.upnp.Device;

import java.util.List;

public class DeviceAdapter extends MBaseAdapter {

    private List<Device> devices;
    private AllShareProxy proxy;

    public DeviceAdapter(Context context, List<Device> devices) {
        super(context);
        this.devices = devices;
        proxy = AllShareProxy.getInstance(context);
    }

    public void refreshData(List<Device> devices) {
        this.devices = devices;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Device item = (Device) getItem(position);
        holder.img.setTag(item);
        holder.img.setImageResource(R.drawable.device_img);
        if (UpnpUtil.isMediaRenderDevice(item)) {
            if (item == proxy.getDMRSelectedDevice()) {
                holder.txt.setTextColor(Color.RED);
            } else {
                holder.txt.setTextColor(Color.GREEN);
            }
        } else if (UpnpUtil.isMediaServerDevice(item)) {
            if (item == proxy.getDMSSelectedDevice()) {
                holder.txt.setTextColor(Color.RED);
            } else {
                holder.txt.setTextColor(Color.GREEN);
            }
        }
        holder.txt.setText(item.getFriendlyName());
    }

    @Override
    public Object getItemData(int position) {
        return devices.get(position);
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

}
