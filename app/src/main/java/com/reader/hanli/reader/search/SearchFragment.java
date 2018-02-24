package com.reader.hanli.reader.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.reader.hanli.baselibrary.base.BaseFragment;
import com.reader.hanli.reader.MainActivity;
import com.reader.hanli.reader.R;
import com.reader.hanli.reader.R2;
import com.reader.hanli.reader.bookdetail.BookDetailActivity;
import com.reader.hanli.reader.bookshelf.BookListAdapter;
import com.reader.hanli.reader.data.bean.Book;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hanli on 2018/2/23.
 */

public class SearchFragment extends BaseFragment implements SearchContract.View , FragmentUtils.OnBackClickListener{

    @BindView(R2.id.search_view)
    MaterialSearchView mSearchView;

    @BindView(R2.id.tool_bar)
    Toolbar mToolbar;

    @BindView(R2.id.lv)
    ListView lv;

    private BookListAdapter mAdapter;

    private SearchContract.Presenter mPresenter;

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Nullable
    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search , null);
        ButterKnife.bind(this , view);

        mAdapter = new BookListAdapter(getContext() , new ArrayList<Book>());
        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPresenter.search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

            }
        });

        if(ObjectUtils.isNotEmpty(mActivity)){
            setHasOptionsMenu(true);
            mActivity.setSupportActionBar(mToolbar);
        }
        lv.setAdapter(mAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = (Book) parent.getItemAtPosition(position);
                LogUtils.iTag("engine" , "点击的book：" + book);
                BookDetailActivity.startActivity(getContext() , book);
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu , menu);
        MenuItem searchMenu = menu.findItem(R.id.action_search);
        mSearchView.setMenuItem(searchMenu);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void showResult(List<Book> list) {
        mAdapter.setData(list);
    }

    @Override
    public void showSearchView() {
        mSearchView.showSearch();
    }

    @Override
    public void hideSearchView() {
        mSearchView.closeSearch();
    }

    @Override
    public boolean onBackClick() {
        if(mSearchView.isSearchOpen()){
            mSearchView.closeSearch();
            return true;
        }
        return false;
    }
}
