package com.reader.hanli.reader.read;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.reader.hanli.baselibrary.base.BaseActivity;
import com.reader.hanli.reader.R;
import com.reader.hanli.reader.data.bean.Book;

public class ReadActivity extends BaseActivity {

    public static final String KEY_BOOK = "key_book";

    public static final String KEY_CHAPTER_ID = "key_chapter_id";

    private ReadFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        mFragment = (ReadFragment) FragmentUtils.findFragment(getSupportFragmentManager() , ReadFragment.class);
        if(mFragment == null){
            mFragment = new ReadFragment();
            FragmentUtils.add(getSupportFragmentManager() , mFragment , R.id.fl_content);
        }

        Book book = (Book) getIntent().getSerializableExtra(KEY_BOOK);
        int chapterId = getIntent().getIntExtra(KEY_CHAPTER_ID , 0);

        new ReadPresenter(mFragment , book , chapterId);
    }

    public static void startActivity(Context context , Book book){
        startActivity(context , book , 0);
    }

    public static void startActivity(Context context , Book book , int chapterId){
        Intent intent = new Intent(context , ReadActivity.class);
        intent.putExtra(KEY_BOOK , book);
        intent.putExtra(KEY_CHAPTER_ID , chapterId);
        ActivityUtils.startActivity(intent);
    }
}
