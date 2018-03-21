package com.reader.hanli.reader.data.engine.impl;

import com.blankj.utilcode.util.ObjectUtils;
import com.reader.hanli.reader.MyApplication;
import com.reader.hanli.reader.data.bean.Book;
import com.reader.hanli.reader.data.bean.BookDao;
import com.reader.hanli.reader.data.bean.Chapter;
import com.reader.hanli.reader.data.bean.ChapterDao;
import com.reader.hanli.reader.data.engine.BookEngine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

/**
 * Created by hanli on 2018/2/24.
 */

public abstract class BaseEngineImpl implements BookEngine {

    protected Document getDocument(String url) throws IOException {
        Document document = Jsoup.connect(url)
                //需要加上userAgent才能伪装成浏览器而不会被网站屏蔽IP
                //(这种做法可能也会被某些网站拉黑IP一段时间，由于不太稳定到底是不是代码的问题，还在测试中...)
                .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36")
                //加上cookie信息
                .cookie("auth", "token")
                //设置超时
                .timeout(30000)
                //用get()方式请求网址，也可以post()方式
                .get();
        return document;
    }

    /**
     * 从获得的包含各种html标签的内容中获得格式化好的内容
     * @param rawContent
     * @return
     */
    protected String getContentFromHtml(String rawContent){
        rawContent = rawContent.replaceAll("<br>" , "");
        rawContent = rawContent.replaceAll("&nbsp;" , "");
        rawContent = rawContent.replaceAll("<.*>.*</[\\w-\\W-]*>", "");
        return rawContent;
    }


    @Override
    public List<Book> getBookshelf() {
        return MyApplication.getInstance().getDaoSession().getBookDao().loadAll();
    }

    @Override
    public void collectBook(Book book) {
        BookDao bookDao = MyApplication.getInstance().getDaoSession().getBookDao();
        if(ObjectUtils.isEmpty(getCollectBook(book.getBookUrl()))){
            // 没有收藏过，才会进行收藏
            bookDao.insert(book);
            book.refresh();
        }
    }

    @Override
    public void unCollectBook(Book book) {
        BookDao bookDao = MyApplication.getInstance().getDaoSession().getBookDao();
        List<Book> list = bookDao.queryBuilder().where(BookDao.Properties.BookUrl.eq(book.getBookUrl())).list();
        for(Book queryBook : list){
            bookDao.delete(queryBook);
        }
    }

    @Override
    public Book getCollectBook(String bookUrl) {
        BookDao bookDao = MyApplication.getInstance().getDaoSession().getBookDao();
        List<Book> list = bookDao.queryBuilder().where(BookDao.Properties.BookUrl.eq(bookUrl)).list();
        if(ObjectUtils.isEmpty(list)){
            return null;
        }
        return list.get(0);
    }
}
