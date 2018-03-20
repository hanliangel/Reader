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
     * 根据关键字从网络搜索书籍
     * @param search
     * @return
     */
    List<Book> searchBook(String search);

    /**
     * 从网络初始化章节列表
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

    /**
     * 收藏一本书，如果在数据库查询到url和该书一致的已存在收藏书籍，则不重复收藏
     * @param book
     */
    void collectBook(Book book);

    /**
     * 取消收藏，根据url来取消对应的书籍
     * @param book
     */
    void unCollectBook(Book book);

    /**
     * 该书是否已经收藏，以url为判断依据，相同url视为一本书
     * @param book
     * @return
     */
    boolean isCollect(Book book);
}
