package com.reader.hanli.reader.bookshelf;

import com.blankj.utilcode.util.ToastUtils;
import com.reader.hanli.reader.data.bean.Book;
import com.reader.hanli.reader.data.engine.EngineHelper;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hanli on 2018/2/9.
 */

public class BookshelfPresenter implements BookshelfContract.Presenter {

    private BookshelfContract.View mView;

    /**
     * 书架上的所有书
     */
    private List<Book> mBooks;

    public BookshelfPresenter(BookshelfContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        refreshBookshelf();
    }

    @Override
    public void destroy() {

    }


    @Override
    public void refreshBookshelf() {
        Observable.create(new ObservableOnSubscribe<List<Book>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Book>> e) throws Exception {
                List<Book> list = EngineHelper.getInstance().getBookEngine().getBookshelf();
                e.onNext(list);
            }
        }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<List<Book>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Book> books) {
                mBooks = books;
                mView.showBookshelf(books);
            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.showShort("error:" + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void removeFromShelf(int position) {
        Book book = mBooks.get(position);
        mBooks.remove(book);
        EngineHelper.getInstance().getBookEngine().unCollectBook(book);
        mView.refreshComplete();
    }
}
