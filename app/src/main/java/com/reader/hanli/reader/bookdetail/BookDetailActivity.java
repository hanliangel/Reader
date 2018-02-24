package com.reader.hanli.reader.bookdetail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.reader.hanli.baselibrary.base.BaseActivity;
import com.reader.hanli.reader.R;
import com.reader.hanli.reader.data.bean.Book;

public class BookDetailActivity extends BaseActivity {

    public static final String KEY_BOOK = "key_book";

    private BookDetailFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        mFragment = (BookDetailFragment) FragmentUtils.findFragment(getSupportFragmentManager() , BookDetailFragment.class);
        if(mFragment == null){
            mFragment = new BookDetailFragment();
            FragmentUtils.add(getSupportFragmentManager() , mFragment , R.id.fl_content);
        }

        Book book = (Book) getIntent().getSerializableExtra(KEY_BOOK);
        new BookDetailPresenter(book , mFragment);
    }

    public static void startActivity(Context context , Book book){
        Intent intent = new Intent(context , BookDetailActivity.class);
        intent.putExtra(KEY_BOOK , book);
        ActivityUtils.startActivity(intent);
    }
}
