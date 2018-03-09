package com.reader.hanli.reader.data.engine.impl;

import com.reader.hanli.reader.MyApplication;
import com.reader.hanli.reader.data.bean.Book;
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
}
