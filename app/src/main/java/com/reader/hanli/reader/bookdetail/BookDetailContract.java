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

        /**
         * 收藏，即加入书架
         * @return
         */
        boolean collect();

        /**
         * 取消收藏，移出书架
         * @return
         */
        boolean unCollect();

        /**
         * 是否已经收藏
         * @return
         */
        boolean isCollect();
    }

    interface View extends BaseView<Presenter>{

        void showBook(Book book);

        void showLoading();

        void dismissLoading();

        void gotoReadActivity(Book book , int chapterId);
    }
}
