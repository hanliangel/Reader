package com.reader.hanli.reader.search;

/**
 * Created by hanli on 2018/2/23.
 */

public class SearchPresenter implements SearchContract.Presenter {

    private SearchContract.View mView;

    public SearchPresenter(SearchContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void search(String searchText) {

    }
}
