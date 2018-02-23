package com.reader.hanli.reader.search;

import com.reader.hanli.baselibrary.base.BasePresenter;
import com.reader.hanli.baselibrary.base.BaseView;

/**
 * Created by hanli on 2018/2/23.
 */

public interface SearchContract {

    interface Presenter extends BasePresenter{
        void search(String searchText);
    }

    interface View extends BaseView<Presenter>{


        void showSearchView();

        void hideSearchView();
    }

}
