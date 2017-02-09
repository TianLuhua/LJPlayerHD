package com.example.lj.ljplayerhd.media.definition;

import com.example.lj.ljplayerhd.bean.DefinitionBean;

/**
 * Created by wh on 2017/1/9.
 */

public class DefinitionChoicePresenter implements DefinitionChoiceContract.Presenter {

    private DefinitionChoiceContract.View mView;

    public DefinitionChoicePresenter(DefinitionChoiceContract.View view) {
        this.mView = view;
    }

    @Override
    public void itemClick(DefinitionBean bean) {
        if (bean != null)
            mView.onDefinitionChoice(bean.getUrl());
    }
}
