package com.reader.hanli.reader.data.engine;

import com.reader.hanli.reader.data.bean.Book;

import java.util.List;

/**
 * Created by hanli on 2018/2/11.
 * 书籍的数据引擎
 */
public interface BookEngine {

    /**
     * 获得当前用户的书架
     * @return
     */
    List<Book> getBookshelf();

    /**
     * 根据关键字搜索书籍
     * @param search
     * @return
     */
    List<Book> searchBook(String search);

    /**
     * 初始化章节列表
     * @param book
     * @return
     */
    Book initBookChapters(Book book);

    /**
     * 初始化获取章节内容
     * @param chapter
     * @return
     */
    Book.Chapter initChapter(Book.Chapter chapter);
}
