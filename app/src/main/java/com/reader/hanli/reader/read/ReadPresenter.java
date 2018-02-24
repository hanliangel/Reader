package com.reader.hanli.reader.read;

import android.text.TextUtils;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.reader.hanli.reader.data.bean.Book;
import com.reader.hanli.reader.data.engine.EngineHelper;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hanli on 2018/2/24.
 */

public class ReadPresenter implements ReadContract.Presenter {

    private ReadContract.View mView;

    private Book mBook;

    private int mCurrentChapterId;

    public ReadPresenter(ReadContract.View mView, Book mBook , int chapterId) {
        this.mView = mView;
        this.mBook = mBook;
        this.mCurrentChapterId = chapterId;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        switchChapter(mCurrentChapterId);
    }

    @Override
    public void nextChapter() {
        switchChapter(mCurrentChapterId ++);
    }

    @Override
    public void lastChapter() {
        switchChapter(mCurrentChapterId --);
    }

    private void switchChapter(int chapterId){
        final int finalChapterId = chapterId;
        loadChapter(chapterId).subscribe(new Observer<Book.Chapter>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Book.Chapter chapter) {
                mCurrentChapterId = finalChapterId;
                mView.showContent(chapter.getContent());
            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.showShort(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
        loadChapter(chapterId ++).subscribe(new Consumer<Book.Chapter>() {
            @Override
            public void accept(Book.Chapter chapter) throws Exception {

            }
        });
    }

    private Observable<Book.Chapter> loadChapter(final int chapterId){
        return Observable.create(new ObservableOnSubscribe<Book.Chapter>() {
            @Override
            public void subscribe(ObservableEmitter<Book.Chapter> e) throws Exception {
                Book.Chapter chapter = getChapter(chapterId);
                if(ObjectUtils.isNotEmpty(chapter)){
                    if(TextUtils.isEmpty(chapter.getContent())){
                        chapter = EngineHelper.getInstance().getBookEngine().initChapter(chapter);
                    }
                    e.onNext(chapter);
                }else {
                    e.onError(new Throwable("没有下一个章了"));
                }
            }
        }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    }

    private Book.Chapter getChapter(int chapterId){
        if(chapterId >= mBook.getChapters().size() || chapterId < 0){
            return null;
        }
        return mBook.getChapters().get(chapterId);
    }
}