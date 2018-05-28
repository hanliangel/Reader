package com.reader.hanli.reader.search;

import com.blankj.utilcode.util.CacheUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.reader.hanli.reader.MyApplication;
import com.reader.hanli.reader.data.bean.Book;
import com.reader.hanli.reader.data.bean.SearchHistory;
import com.reader.hanli.reader.data.bean.SearchHistoryDao;
import com.reader.hanli.reader.data.engine.EngineHelper;

import java.util.ArrayList;
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
 * Created by hanli on 2018/2/23.
 */

public class SearchPresenter implements SearchContract.Presenter {

    private SearchContract.View mView;

    List<SearchHistory> mSearchHistories;


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
        addSearchHistory(searchText);
        mView.refreshSearchHistory();
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

    @Override
    public List<String> getSearchHistory() {
        if(mSearchHistories == null){
            mSearchHistories = MyApplication.getInstance().getDaoSession().getSearchHistoryDao().queryBuilder().orderDesc(SearchHistoryDao.Properties.SearchTime).list();
        }
        List<String> suggestion = new ArrayList<>();
        for(SearchHistory history : mSearchHistories){
            suggestion.add(history.getSearchText());
        }
        return suggestion;
    }

    /**
     * 添加一个历史记录搜索
     * @param searchText
     */
    private void addSearchHistory(String searchText){
        SearchHistoryDao searchHistoryDao = MyApplication.getInstance().getDaoSession().getSearchHistoryDao();
        SearchHistory addHistory = null;
        for(SearchHistory history : mSearchHistories){
            if(history.getSearchText().equals(searchText)){
                addHistory = history;
                break;
            }
        }
        if(addHistory != null){
            searchHistoryDao.update(addHistory);
            mSearchHistories.remove(addHistory);
        }else{
            addHistory = new SearchHistory();
            addHistory.setSearchText(searchText);
            addHistory.setSearchTime(System.currentTimeMillis());
            searchHistoryDao.insert(addHistory);
        }
        mSearchHistories.add(0 , addHistory);
    }

    @Override
    public void switchEngineName(String engineName) {
        EngineHelper.getInstance().switchEngine(engineName);
        mView.refreshTitle();
    }
}
