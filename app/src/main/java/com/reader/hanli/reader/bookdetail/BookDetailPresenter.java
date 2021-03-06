package com.reader.hanli.reader.bookdetail;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.reader.hanli.reader.MyApplication;
import com.reader.hanli.reader.data.bean.Book;
import com.reader.hanli.reader.data.bean.Chapter;
import com.reader.hanli.reader.data.engine.BookEngine;
import com.reader.hanli.reader.data.engine.EngineHelper;

import java.util.Collections;
import java.util.Comparator;
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

    /**
     * 当前这本书对应的bookengine
     */
    private BookEngine mCurrentBookEngine;

    /**
     * 当前的排序类型
     */
    private int mCurrentSort = 1;

    public BookDetailPresenter(Book mBook, BookDetailContract.View mView) {
        this.mBook = mBook;
        this.mView = mView;
        mView.setPresenter(this);
        mCurrentBookEngine = EngineHelper.getInstance().getBookEngine(mBook.getEngineName());
        Book collectBook = mCurrentBookEngine.getCollectBook(mBook.getBookUrl());
        if(ObjectUtils.isNotEmpty(collectBook)){
            this.mBook = collectBook;
        }
    }

    @Override
    public Book getBookDetail() {
        mView.showLoading();
        Observable.create(new ObservableOnSubscribe<Book>() {
            @Override
            public void subscribe(ObservableEmitter<Book> e) throws Exception {
                Book book = EngineHelper.getInstance().getBookEngine(mBook.getEngineName()).initBookChapters(mBook);
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
                mBook = book;
                if(isCollect()){
                    List<Chapter> chapters = book.getChapters();
                    if(ObjectUtils.isNotEmpty(chapters)){
                        MyApplication.getInstance().getDaoSession().getChapterDao().insertOrReplaceInTx(chapters);
                    }
                    book.update();
                }
                sort();
                mView.showBook(book);
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
        mView.gotoReadActivity(mBook , mBook.getChapters().get(position).getId());
    }

    @Override
    public void continueRead() {
        startRead(mBook.getReadingChapter().getId());
    }

    @Override
    public boolean collect() {
        mCurrentBookEngine.collectBook(mBook);
        mView.showBook(mBook);
        return true;
    }

    @Override
    public boolean unCollect() {
        mCurrentBookEngine.unCollectBook(mBook);
        mView.showBook(mBook);
        return true;
    }

    @Override
    public boolean isCollect() {
        return ObjectUtils.isNotEmpty(mCurrentBookEngine.getCollectBook(mBook.getBookUrl()));
    }

    @Override
    public void switchSort() {
        mCurrentSort = -mCurrentSort;
        sort();
    }

    private void sort(){
        if(mBook != null && mBook.getChapters() != null){
            Collections.sort(mBook.getChapters() , new Comparator<Chapter>() {
                @Override
                public int compare(Chapter o1, Chapter o2) {
                    if(o1.getId() > o2.getId()){
                        return mCurrentSort;
                    }else{
                        return -mCurrentSort;
                    }
                }
            });
            mView.showBook(mBook);
        }
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
