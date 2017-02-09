package com.example.lj.ljplayerhd.media.comment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.lj.ljplayerhd.base.BaseSingleFragmentActivity;
import com.example.lj.ljplayerhd.config.IDatas;

/**
 * Created by wh on 2016/12/28.
 */

public class CommentActivity extends BaseSingleFragmentActivity {

    public static Intent newIntent(Context context, String mark, String vId,String name){
        Intent intent=new Intent(context,CommentActivity.class);
        intent.putExtra(IDatas.JsonKey.MARK,mark);
        intent.putExtra(IDatas.JsonKey.ID,vId);
        intent.putExtra(IDatas.JsonKey.NAME,name);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        String mark=getIntent().getStringExtra(IDatas.JsonKey.MARK);
        String vId=getIntent().getStringExtra(IDatas.JsonKey.ID);
        String name=getIntent().getStringExtra(IDatas.JsonKey.NAME);
        CommentFragment fragment=CommentFragment.newInstance(mark,vId,name);
        new CommentPresenter(fragment);
        return fragment;
    }
}
