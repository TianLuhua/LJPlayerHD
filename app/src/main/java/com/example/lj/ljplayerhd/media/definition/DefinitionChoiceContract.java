package com.example.lj.ljplayerhd.media.definition;

import com.example.lj.ljplayerhd.bean.DefinitionBean;

/**
 * Created by wh on 2017/1/9.
 */

public class DefinitionChoiceContract {

    public interface View{
        void onDefinitionChoice(String url);
    }

    public interface Presenter{
        void itemClick(DefinitionBean bean);
    }
}
