package com.reader.hanli.reader.bookshelf;

import com.reader.hanli.baselibrary.base.BasePresenter;
import com.reader.hanli.baselibrary.base.BaseView;
import com.reader.hanli.reader.data.bean.Book;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by hanli on 2018/2/9.
 */

public interface BookshelfContract {

    interface Presenter extends BasePresenter{

        void refreshBookshelf();

        void removeFromShelf(int position);

    }

    interface View extends BaseView<Presenter>{

        void showBookshelf(List<Book> books);

        void refreshComplete();

    }
}
