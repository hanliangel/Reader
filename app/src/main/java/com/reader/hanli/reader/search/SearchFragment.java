package com.reader.hanli.reader.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.reader.hanli.baselibrary.base.BaseFragment;
import com.reader.hanli.reader.MainActivity;
import com.reader.hanli.reader.R;
import com.reader.hanli.reader.R2;

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

        mSearchView.setSuggestions(new String[]{"abc" , "bbb" , "aaa" , "aaadddddffff" , "哟哟哟"});
        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
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
