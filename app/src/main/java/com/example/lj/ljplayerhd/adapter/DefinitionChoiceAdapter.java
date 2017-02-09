package com.example.lj.ljplayerhd.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.bean.DefinitionBean;
import com.example.lj.ljplayerhd.databinding.LayoutCardTextviewBinding;
import com.example.lj.ljplayerhd.media.definition.DefinitionChoiceContract;
import com.example.lj.ljplayerhd.media.definition.DefinitionChoicePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wh on 2017/1/9.
 */

public class DefinitionChoiceAdapter extends RecyclerView.Adapter<DefinitionChoiceAdapter.ViewHolder> {

    private List<DefinitionBean> data;
    private DefinitionChoicePresenter mPresenter;

    public DefinitionChoiceAdapter(DefinitionChoicePresenter presenter){
        data=new ArrayList<>();
        this.mPresenter=presenter;
    }

    public void setData(List<DefinitionBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card_textview,parent,false);
        LayoutCardTextviewBinding.bind(itemView);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DefinitionBean bean=data.get(position);
        if(bean==null)
            return;
        LayoutCardTextviewBinding binding= DataBindingUtil.getBinding(holder.itemView);
        if(binding==null)
            return;
        binding.setBean(bean);
        binding.setPresenter(mPresenter);
        binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
