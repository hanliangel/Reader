package com.reader.hanli.reader.search;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.reader.hanli.reader.data.bean.Book;
import com.reader.hanli.reader.data.engine.EngineHelper;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hanli on 2018/2/23.
 */

public class SearchPresenter implements SearchContract.Presenter {

    private SearchContract.View mView;

    public SearchPresenter(SearchContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void destroy() {

    }

    @Override
    public void search(final String searchText) {
        Observable.create(new ObservableOnSubscribe<List<Book>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Book>> e) throws Exception {
                List<Book> books = EngineHelper.getInstance().getBookEngine().searchBook(searchText);
                if(ObjectUtils.isNotEmpty(books)){
                    e.onNext(books);
                }else{
                    e.onError(new Throwable("book not found"));
                }
            }
        }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<List<Book>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Book> books) {
                mView.showResult(books);
            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.showShort(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
