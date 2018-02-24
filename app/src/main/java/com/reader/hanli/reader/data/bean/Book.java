package com.reader.hanli.reader.data.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by hanli on 2018/2/11.
 */

public class Book implements Serializable{
    /**
     * 封面
     */
    private String coverUri;

    /**
     * 书名
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 作者
     */
    private String author;

    /**
     * 最后更新时间
     */
    private String latestTime;

    /**
     * 最新章节
     */
    private String latestChapter;

    /**
     * 对应的引擎name和链接
     */
    private Map.Entry<String , String> bookUrl;

    private List<Chapter> chapters;

    public String getCoverUri() {
        return coverUri;
    }

    public void setCoverUri(String coverUri) {
        this.coverUri = coverUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public String getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(String latestTime) {
        this.latestTime = latestTime;
    }

    public String getLatestChapter() {
        return latestChapter;
    }

    public void setLatestChapter(String latestChapter) {
        this.latestChapter = latestChapter;
    }

    public Map.Entry<String, String> getBookUrl() {
        return bookUrl;
    }

    public void setBookUrl(Map.Entry<String, String> bookUrl) {
        this.bookUrl = bookUrl;
    }

    public static class Chapter implements Serializable{

        private int id;

        private String name;

        private String content;

        /**
         * 对应的引擎name和链接
         */
        private Map.Entry<String , String> chapterUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
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

        public Map.Entry<String, String> getChapterUrl() {
            return chapterUrl;
        }

        public void setChapterUrl(Map.Entry<String, String> chapterUrl) {
            this.chapterUrl = chapterUrl;
        }
    }

    @Override
    public String toString() {
        return "Book{" +
                "coverUri='" + coverUri + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", latestTime='" + latestTime + '\'' +
                ", latestChapter='" + latestChapter + '\'' +
                ", bookUrl=" + bookUrl +
                ", chapters=" + chapters +
                '}';
    }
}
