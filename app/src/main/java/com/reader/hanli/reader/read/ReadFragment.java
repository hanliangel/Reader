package com.reader.hanli.reader.read;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.reader.hanli.baselibrary.base.BaseFragment;
import com.reader.hanli.reader.R;
import com.reader.hanli.reader.R2;
import com.victor.loading.book.BookLoading;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hanli on 2018/2/24.
 */

public class ReadFragment extends BaseFragment implements ReadContract.View {

    private ReadContract.Presenter mPresenter;

    @BindView(R2.id.tv_content)
    TextView tv_content;

    @BindView(R2.id.sv)
    ScrollView sv;

    @BindView(R2.id.bt_last)
    Button bt_last;

    @BindView(R2.id.bt_next)
    Button bt_next;

    @BindView(R2.id.book_loading)
    BookLoading book_loading;

    @Override
    public void setPresenter(ReadContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Nullable
    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read , null);
        ButterKnife.bind(this , view);
//        tv_content = (TextView) view.findViewById(R.id.tv_content);
//        sv = (ScrollView) view.findViewById(R.id.sv);
//        bt_last = (Button) view.findViewById(R.id.bt_last);
//        bt_next = (Button) view.findViewById(R.id.bt_next);
//        book_loading = (BookLoading) view.findViewById(R.id.book_loading);

        bt_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.lastChapter();
            }
        });

        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.nextChapter();
            }
        });
        return view;
    }

    @Override
    public void showContent(String content) {
        tv_content.setText(content);
        sv.postDelayed(new Runnable() {
            @Override
            public void run() {
                sv.fullScroll(ScrollView.FOCUS_UP);
            }
        } , 100);
    }

    @Override
    public void showLoading() {
        LogUtils.iTag("read" , "showLoading");
        book_loading.setVisibility(View.VISIBLE);
        book_loading.start();
    }

    @Override
    public void dismissLoading() {
        LogUtils.iTag("read" , "dismissLoading");
        if(book_loading.isStart()){
            book_loading.stop();
        }
        book_loading.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }
}
