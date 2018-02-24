package com.reader.hanli.reader.bookdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.reader.hanli.baselibrary.base.BaseFragment;
import com.reader.hanli.reader.R;
import com.reader.hanli.reader.R2;
import com.reader.hanli.reader.data.bean.Book;
import com.reader.hanli.reader.read.ReadActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hanli on 2018/2/24.
 */

public class BookDetailFragment extends BaseFragment implements BookDetailContract.View {

    @BindView(R2.id.lv)
    ObservableListView lv;

    private BookDetailContract.Presenter mPresenter;

    private ChapterListAdapter mAdapter;

    @Nullable
    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_detail ,  null);
        ButterKnife.bind(this , view);
        mAdapter = new ChapterListAdapter(getContext() , new ArrayList<Book.Chapter>());
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPresenter.startRead(position);
            }
        });
        return view;
    }

    @Override
    public void setPresenter(BookDetailContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void showBook(Book book) {
        // TODO 显示书籍详情部分

        // 显示章节列表部分
        mAdapter.setData(book.getChapters());
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void gotoReadActivity(Book book, int chapterId) {
        ReadActivity.startActivity(getContext() , book , chapterId);
    }
}