package com.reader.hanli.reader.bookshelf;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.LogUtils;
import com.reader.hanli.baselibrary.base.BaseActivity;
import com.reader.hanli.reader.R;
import com.reader.hanli.reader.service.KeepLiveService;

public class BookshelfActivity extends BaseActivity {

    private BookshelfFragment mBookshelfFragment;

    private BookshelfPresenter mBookshelfPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookshelf);

        mBookshelfFragment = (BookshelfFragment) FragmentUtils.findFragment(getSupportFragmentManager() , BookshelfFragment.class);
        if(mBookshelfFragment == null){
            mBookshelfFragment = new BookshelfFragment();
            FragmentUtils.add(getSupportFragmentManager() , mBookshelfFragment , R.id.cl_content);
        }

        mBookshelfPresenter = new BookshelfPresenter(mBookshelfFragment);

        startKeepLiveService();
    }

    /**
     * 启动保活服务
     */
    private void startKeepLiveService(){
        startService(new Intent(this, KeepLiveService.class));
    }
}
