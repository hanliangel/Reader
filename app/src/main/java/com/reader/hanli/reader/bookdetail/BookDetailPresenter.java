package com.reader.hanli.reader.bookdetail;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.reader.hanli.reader.MyApplication;
import com.reader.hanli.reader.data.bean.Book;
import com.reader.hanli.reader.data.bean.BookDao;
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
    /**
     * 当前页面对应的书
     */
    private Book mBook;

    private BookDetailContract.View mView;

    /**
     * 是否已经执行过start，这里start只应该执行一次
     */
    private boolean mHasStart;

    /**
     * 加载书籍的时候的中断控制，如果页面结束那么中断加载过程
     */
    private Disposable mBookDetailDisposable;

    public BookDetailPresenter(Book mBook, BookDetailContract.View mView) {
        this.mBook = mBook;
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public Book getBookDetail() {
        mView.showLoading();
        Observable.create(new ObservableOnSubscribe<Book>() {
            @Override
            public void subscribe(ObservableEmitter<Book> e) throws Exception {
                Book book = EngineHelper.getInstance().getBookEngine().initBookChapters(mBook);
                if(ObjectUtils.isNotEmpty(book)){
                    e.onNext(book);
                }else{
                    e.onError(new Throwable("book is null"));
                }
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<Book>() {
            @Override
            public void onSubscribe(Disposable d) {
                mBookDetailDisposable = d;
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
                mView.dismissLoading();
            }
        });
        return mBook;
    }

    @Override
    public void startRead(int position) {
        mView.gotoReadActivity(mBook , position);
    }

    @Override
    public boolean collect() {
        EngineHelper.getInstance().getBookEngine().collectBook(mBook);
        mView.showBook(mBook);
        return true;
    }

    @Override
    public boolean unCollect() {
        EngineHelper.getInstance().getBookEngine().unCollectBook(mBook);
        mView.showBook(mBook);
        return true;
    }

    @Override
    public boolean isCollect() {
        return EngineHelper.getInstance().getBookEngine().isCollect(mBook);
    }

    @Override
    public void start() {
        if(mHasStart){
            return ;
        }
        mView.showBook(mBook);
        getBookDetail();
        mHasStart = true;
    }

    @Override
    public void destroy() {
        if(mBookDetailDisposable != null && !mBookDetailDisposable.isDisposed()){
            mBookDetailDisposable.dispose();
        }
    }
}
