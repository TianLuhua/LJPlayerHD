package com.example.lj.ljplayerhd.media.definition;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.example.lj.ljplayerhd.R;
import com.example.lj.ljplayerhd.adapter.DefinitionChoiceAdapter;
import com.example.lj.ljplayerhd.base.BaseDialogFragment;
import com.example.lj.ljplayerhd.bean.DefinitionBean;
import com.example.lj.ljplayerhd.config.IDatas;
import com.example.lj.ljplayerhd.databinding.DialogDefinitionChoiceBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wh on 2017/1/9.
 */

public class DefinitionChoiceDialog extends BaseDialogFragment implements DefinitionChoiceContract.View {

    private DialogDefinitionChoiceBinding choiceBinding;
    private DefinitionChoicePresenter mPresenter;
    private DefinitionChoiceAdapter mAdapter;

    public static DefinitionChoiceDialog newInstance(ArrayList<DefinitionBean> data) {
        Bundle args = new Bundle();
        args.putSerializable(IDatas.JsonKey.DEFINITION, data);
        DefinitionChoiceDialog dialog = new DefinitionChoiceDialog();
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_definition_choice;
    }

    @Override
    public void initViews() {
        mPresenter = new DefinitionChoicePresenter(this);
        choiceBinding = DialogDefinitionChoiceBinding.bind(root);
        choiceBinding.recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void initEvents() {
        List<DefinitionBean> data = (ArrayList<DefinitionBean>) getArguments().getSerializable(IDatas.JsonKey.DEFINITION);
        mAdapter = new DefinitionChoiceAdapter(mPresenter);
        if (data != null) {
            mAdapter.setData(data);
        }
        choiceBinding.recycler.setAdapter(mAdapter);
    }

    @Override
    public void onDefinitionChoice(String url) {
        Intent intent = new Intent();
        intent.putExtra(IDatas.JsonKey.ADDRESS, url);
        if (getTargetFragment() != null)
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        dismiss();
    }
}
