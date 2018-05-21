package com.reader.hanli.reader.search;

import com.reader.hanli.baselibrary.base.BasePresenter;
import com.reader.hanli.baselibrary.base.BaseView;
import com.reader.hanli.reader.data.bean.Book;

import java.util.List;

/**
 * Created by hanli on 2018/2/23.
 */

public interface SearchContract {

    interface Presenter extends BasePresenter{

        /**
         * 搜索书本
         * @param searchText
         */
        void search(String searchText);

        /**
         * 返回搜索历史记录
         * @return
         */
        List<String> getSearchHistory();

        /**
         * 切换搜索引擎
         * @param engineName
         */
        void switchEngineName(String engineName);
    }

    interface View extends BaseView<Presenter>{

        void showResult(List<Book> list);

        /**
         * 显示标题栏上的搜索输入框
         */
        void showSearchView();

        /**
         * 隐藏标题栏上的搜索输入框
         */
        void hideSearchView();

        /**
         * 显示改变搜索引擎的dialog
         */
        void showChangeSearchEngineDialog();

        /**
         * 刷新标题栏
         */
        void refreshTitle();

        /**
         * 刷新搜索历史
         */
        void refreshSearchHistory();
    }

}
