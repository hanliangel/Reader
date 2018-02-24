package com.reader.hanli.reader.bookdetail;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.reader.hanli.reader.data.bean.Book;
import com.reader.hanli.reader.data.engine.EngineHelper;
import com.reader.hanli.reader.read.ReadActivity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hanli on 2018/2/24.
 */

public class BookDetailPresenter implements BookDetailContract.Presenter {

    private Book mBook;

    private BookDetailContract.View mView;

    public BookDetailPresenter(Book mBook, BookDetailContract.View mView) {
        this.mBook = mBook;
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public Book getBookDetail() {
        Observable.create(new ObservableOnSubscribe<Book>() {
            @Override
            public void subscribe(ObservableEmitter<Book> e) throws Exception {
                Book book = EngineHelper.getInstance().getBookEngine().initBookChapters(mBook);
                if(ObjectUtils.isNotEmpty(book)){
                    e.onNext(book);
                }else{
                    e.onError(new Throwable("book is null"));
                }
            }
        }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<Book>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Book book) {
                mView.showBook(mBook);
            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.showShort(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
        return mBook;
    }

    @Override
    public void startRead(int position) {
        mView.gotoReadActivity(mBook , position);
    }

    @Override
    public void start() {
        getBookDetail();
    }
}
