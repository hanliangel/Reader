package com.reader.hanli.reader.search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.blankj.utilcode.util.FragmentUtils;
import com.reader.hanli.baselibrary.base.BaseActivity;
import com.reader.hanli.reader.R;

public class SearchActivity extends BaseActivity {


    private SearchFragment mSearchFragment;

    private SearchPresenter mSearchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mSearchFragment = (SearchFragment) FragmentUtils.findFragment(getSupportFragmentManager() , SearchFragment.class);
        if(mSearchFragment == null){
            mSearchFragment = new SearchFragment();
            FragmentUtils.add(getSupportFragmentManager() , mSearchFragment , R.id.fl_content);
        }

        mSearchPresenter = new SearchPresenter(mSearchFragment);
    }

    @Override
    public void onBackPressed() {
        if(!FragmentUtils.dispatchBackPress(mSearchFragment)){
            super.onBackPressed();
        }
    }
}
