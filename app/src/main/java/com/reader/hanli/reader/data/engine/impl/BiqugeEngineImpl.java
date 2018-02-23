package com.reader.hanli.reader.data.engine.impl;

import com.reader.hanli.reader.data.bean.Book;
import com.reader.hanli.reader.data.engine.BookEngine;

import java.util.List;

/**
 * Created by hanli on 2018/2/11.
 */

public class BiqugeEngineImpl implements BookEngine {
    @Override
    public List<Book> getBookshelf() {
        return null;
    }

    @Override
    public List<Book> searchBook(String search) {
        return null;
    }

    @Override
    public Book initBookChapters(Book book) {
        return null;
    }

    @Override
    public Book.Chapter initChapter(Book.Chapter chapter) {
        return null;
    }
}
