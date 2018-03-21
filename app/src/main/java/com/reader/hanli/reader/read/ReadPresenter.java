package com.reader.hanli.reader.read;

import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.reader.hanli.reader.MyApplication;
import com.reader.hanli.reader.data.bean.Book;
import com.reader.hanli.reader.data.bean.Chapter;
import com.reader.hanli.reader.data.bean.ChapterDao;
import com.reader.hanli.reader.data.engine.BookEngine;
import com.reader.hanli.reader.data.engine.EngineHelper;

import java.util.List;

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

    private boolean mHasStart;

    /**
     * 标示当前的书本是否已收藏，已收藏则需要缓存当前正在阅读章节
     */
    private boolean isCollectedBook;

    /**
     * 当前书本对应的bookengine
     */
    private BookEngine mCurrentBookEngine;

    public ReadPresenter(ReadContract.View mView, Book book, int chapterId) {
        this.mView = mView;
        this.mBook = book;
        this.mCurrentChapterId = chapterId;
        mCurrentBookEngine = EngineHelper.getInstance().getBookEngine(mBook.getEngineName());
        mView.setPresenter(this);
        Book collectedBook = mCurrentBookEngine.getCollectBook(mBook.getBookUrl());
        if(ObjectUtils.isNotEmpty(collectedBook)){
            this.mBook = collectedBook;
            isCollectedBook = true;
        }
    }

    @Override
    public void start() {
        if(mHasStart){
            return ;
        }
        switchChapter(mCurrentChapterId);
        mHasStart = true;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void nextChapter() {
        switchChapter(mCurrentChapterId + 1);
    }

    @Override
    public void lastChapter() {
        switchChapter(mCurrentChapterId - 1);
    }

    private void switchChapter(int chapterId) {
        final int finalChapterId = chapterId;
        loadChapter(chapterId).subscribe(new Observer<Chapter>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Chapter chapter) {
                mCurrentChapterId = finalChapterId;
                if(isCollectedBook){
                    mBook.setReadingChapter(chapter);
                    MyApplication.getInstance().getDaoSession().getBookDao().update(mBook);
                }
                mView.showContent(chapter.getContent());
                mView.dismissLoading();
            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.showShort(e.getMessage());
            }

            @Override
            public void onComplete() {
                // cache未读取到内容，开始网络读取
                LogUtils.iTag("read" , "加载内容onComplete 执行");
                mView.showLoading();
            }
        });
        loadChapter(chapterId + 1).subscribe(new Consumer<Chapter>() {
            @Override
            public void accept(Chapter chapter) throws Exception {

            }
        });
    }

    private Observable<Chapter> loadChapter(final int chapterId) {

        Observable<Chapter> cacheData = Observable.create(new ObservableOnSubscribe<Chapter>() {
            @Override
            public void subscribe(ObservableEmitter<Chapter> e) throws Exception {
                e.onComplete();
            }
        });

        Observable<Chapter> networkData = Observable.create(new ObservableOnSubscribe<Chapter>() {
            @Override
            public void subscribe(ObservableEmitter<Chapter> e) throws Exception {
                Chapter chapter = getChapter(chapterId);
                if (ObjectUtils.isNotEmpty(chapter)) {
                    if (TextUtils.isEmpty(chapter.getContent())) {
                        chapter = mCurrentBookEngine.initChapter(chapter);
                    }
                    if(isCollectedBook){
                        ChapterDao chapterDao = MyApplication.getInstance().getDaoSession().getChapterDao();
                        chapterDao.update(chapter);
                    }
                    e.onNext(chapter);
                } else {
                    e.onError(new Throwable("没有下一个章了"));
                }
            }
        });
        return Observable.concat(cacheData , networkData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 从当前的书本中取出对应id的章节
     * @param chapterId
     * @return
     */
    private Chapter getChapter(int chapterId) {
        if (chapterId >= mBook.getChapters().size() || chapterId < 0) {
            return null;
        }
        return mBook.getChapters().get(chapterId);
    }
}
