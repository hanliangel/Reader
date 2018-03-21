package com.reader.hanli.reader.bookdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.MutableBoolean;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.bumptech.glide.Glide;
import com.reader.hanli.baselibrary.base.BaseFragment;
import com.reader.hanli.reader.R;
import com.reader.hanli.reader.R2;
import com.reader.hanli.reader.data.bean.Book;
import com.reader.hanli.reader.data.bean.Chapter;
import com.reader.hanli.reader.read.ReadActivity;
import com.victor.loading.book.BookLoading;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hanli on 2018/2/24.
 */

public class BookDetailFragment extends BaseFragment implements BookDetailContract.View {

    @BindView(R2.id.lv)
    ListView lv;

    @BindView(R2.id.book_loading)
    BookLoading book_loading;

    /**
     * 列表顶部的书籍详情
     */
    View mHeaderView;
    HeaderHolder mHeaderHolder;

    private BookDetailContract.Presenter mPresenter;

    private ChapterListAdapter mAdapter;

    @Nullable
    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_detail ,  null);
        ButterKnife.bind(this , view);
//        lv = (ListView) view.findViewById(R.id.lv);
//        book_loading = (BookLoading) view.findViewById(R.id.book_loading);
        mAdapter = new ChapterListAdapter(getContext() , new ArrayList<Chapter>());
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPresenter.startRead(position);
            }
        });
        mHeaderView = inflater.inflate(R.layout.header_book_detail , null);
        mHeaderHolder = new HeaderHolder(mHeaderView);
        mHeaderHolder.bt_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPresenter.isCollect()){
                    mPresenter.unCollect();
                }else{
                    mPresenter.collect();
                }
            }
        });
        mHeaderHolder.bt_continue_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.continueRead();
            }
        });
        lv.addHeaderView(mHeaderView);

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
        // 显示书籍详情部分
        if(mHeaderHolder != null){
            mHeaderHolder.tv_name.setText(book.getName());
            mHeaderHolder.tv_description.setText(book.getDescription());
            mHeaderHolder.tv_author.setText(book.getAuthor());
            mHeaderHolder.bt_continue_read.setVisibility(View.GONE);
            if(mPresenter.isCollect()){
                mHeaderHolder.bt_collect.setText(getString(R.string.bt_has_collect_book));
                if(ObjectUtils.isNotEmpty(book.getReadingChapter())){
                    mHeaderHolder.bt_continue_read.setText("继续阅读：" + book.getReadingChapter().getName());
                    mHeaderHolder.bt_continue_read.setVisibility(View.VISIBLE);
                }
            }else{
                mHeaderHolder.bt_collect.setText(getString(R.string.bt_collect_book));
            }
            Glide.with(getContext())
                    .load(book.getCoverUri())
                    .into(mHeaderHolder.iv_book_cover);
        }

        // 显示章节列表部分
        mAdapter.setData(book.getChapters());
    }

    @Override
    public void showLoading() {
        book_loading.start();
        book_loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissLoading() {
        if(book_loading.isStart()){
            book_loading.stop();
        }
        book_loading.setVisibility(View.GONE);
    }

    @Override
    public void gotoReadActivity(Book book, int chapterId) {
        ReadActivity.startActivity(getContext() , book , chapterId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    public class HeaderHolder{
        @BindView(R2.id.iv_book_cover)
        ImageView iv_book_cover;

        @BindView(R2.id.tv_name)
        TextView tv_name;

        @BindView(R2.id.tv_description)
        TextView tv_description;

        @BindView(R2.id.tv_author)
        TextView tv_author;

        @BindView(R2.id.bt_collect)
        Button bt_collect;

        @BindView(R2.id.bt_continue_read)
        Button bt_continue_read;

        public HeaderHolder(View headerView){
            ButterKnife.bind(this , headerView);
        }
    }
}
