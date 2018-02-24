package com.reader.hanli.reader.bookdetail;

import com.reader.hanli.baselibrary.base.BasePresenter;
import com.reader.hanli.baselibrary.base.BaseView;
import com.reader.hanli.reader.data.bean.Book;

/**
 * Created by hanli on 2018/2/24.
 */

public interface BookDetailContract {

    interface Presenter extends BasePresenter{

        /**
         * 获得书籍详情
         */
        Book getBookDetail();

        void startRead(int position);
    }

    interface View extends BaseView<Presenter>{

        void showBook(Book book);

        void showLoading();

        void dismissLoading();

        void gotoReadActivity(Book book , int chapterId);
    }
}
