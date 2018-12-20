package com.reader.hanli.reader.data.bean;

/**
 * Created by hanli on 2018/12/20.
 * book可以被操作的各种方法
 */
public interface BookOperator {

    /**
     * 获得未读章节数目
     * @return -1 表示还没有开始阅读
     */
    int getUnreadChapterNum();

}
