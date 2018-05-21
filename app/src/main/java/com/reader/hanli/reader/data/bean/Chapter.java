package com.reader.hanli.reader.data.bean;

import org.greenrobot.greendao.annotation.Entity;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class Chapter implements Serializable {

    private static final long serialVersionUID = 1;

    /**
     * 章节顺序id，第一章为0，往后递增
     */
    private Integer id;

    /**
     * 章节名称
     */
    private String name;

    /**
     * 章节内容
     */
    private String content;

    /**
     * 他所属的书本的id，暂时没用
     */
    private Long bookId;

    /**
     * 对应的引擎类名称
     */
    private String engineName;

    /**
     * 章节url
     */
    @Id
    private String chapterUrl;

    @Generated(hash = 794289371)
    public Chapter(Integer id, String name, String content, Long bookId,
            String engineName, String chapterUrl) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.bookId = bookId;
        this.engineName = engineName;
        this.chapterUrl = chapterUrl;
    }

    @Generated(hash = 393170288)
    public Chapter() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getChapterUrl() {
        return chapterUrl;
    }

    public void setChapterUrl(String chapterUrl) {
        this.chapterUrl = chapterUrl;
    }

    public String getEngineName() {
        return engineName;
    }

    public void setEngineName(String engineName) {
        this.engineName = engineName;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}