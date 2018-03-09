package com.reader.hanli.baselibrary.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.ObjectUtils;

/**
 * Created by hanli on 2018/2/9.
 */

public abstract class BaseFragment extends Fragment {

    protected BaseActivity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getActivity() instanceof BaseActivity){
            mActivity = (BaseActivity) getActivity();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView(inflater , container , savedInstanceState);
    }

    @Nullable
    public abstract View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    protected void initToolbar(Toolbar toolbar){
        if(ObjectUtils.isNotEmpty(mActivity)){
            setHasOptionsMenu(true);
            mActivity.setSupportActionBar(toolbar);
        }
    }

}
