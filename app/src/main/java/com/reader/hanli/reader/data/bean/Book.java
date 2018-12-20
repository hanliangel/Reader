package com.reader.hanli.reader.data.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by hanli on 2018/2/11.
 */
@Entity
public class Book implements Serializable{

    private static final long serialVersionUID = 1;

    public static final String DEFAULT_COVER = "http://www.jindianim.com/uploads/pictureDefaultD.jpg";

    @Id(autoincrement = true)
    private Long bookId;

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
     * 对应的引擎名称
     */
    private String engineName;

    /**
     * 对应的书的url
     */
    private String bookUrl;

    /**
     * 当前正在读读章节的url，给greenDao使用
     */
    private String readingChapterUrl;

    /**
     * 当前正在读读章节
     */
    @ToOne(joinProperty = "readingChapterUrl")
    private Chapter readingChapter;

    @ToMany(referencedJoinProperty = "bookId")
    private List<Chapter> chapters;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1097957864)
    private transient BookDao myDao;

    @Generated(hash = 993621563)
    private transient String readingChapter__resolvedKey;

    @Generated(hash = 1045407155)
    public Book(Long bookId, String coverUri, String name, String description, String author,
            String latestTime, String latestChapter, String engineName, String bookUrl,
            String readingChapterUrl) {
        this.bookId = bookId;
        this.coverUri = coverUri;
        this.name = name;
        this.description = description;
        this.author = author;
        this.latestTime = latestTime;
        this.latestChapter = latestChapter;
        this.engineName = engineName;
        this.bookUrl = bookUrl;
        this.readingChapterUrl = readingChapterUrl;
    }

    @Keep
    public Book() {
        coverUri = DEFAULT_COVER;
    }

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

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Keep
    public List<Chapter> getChapters() {
        if (chapters == null && this.daoSession != null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ChapterDao targetDao = daoSession.getChapterDao();
            List<Chapter> chaptersNew = targetDao._queryBook_Chapters(bookId);
            synchronized (this) {
                if (chapters == null) {
                    chapters = chaptersNew;
                }
            }
        }
        return chapters;
    }

    @Keep
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

    public String getBookUrl() {
        return bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    public String getEngineName() {
        return engineName;
    }

    public void setEngineName(String engineName) {
        this.engineName = engineName;
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

    public Long getBookId() {
        return this.bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 936914273)
    public synchronized void resetChapters() {
        chapters = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 920449393)
    public Chapter getReadingChapter() {
        String __key = this.readingChapterUrl;
        if (readingChapter__resolvedKey == null || readingChapter__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ChapterDao targetDao = daoSession.getChapterDao();
            Chapter readingChapterNew = targetDao.load(__key);
            synchronized (this) {
                readingChapter = readingChapterNew;
                readingChapter__resolvedKey = __key;
            }
        }
        return readingChapter;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2006479482)
    public void setReadingChapter(Chapter readingChapter) {
        synchronized (this) {
            this.readingChapter = readingChapter;
            readingChapterUrl = readingChapter == null ? null : readingChapter.getChapterUrl();
            readingChapter__resolvedKey = readingChapterUrl;
        }
    }

    public String getReadingChapterUrl() {
        return this.readingChapterUrl;
    }

    public void setReadingChapterUrl(String readingChapterUrl) {
        this.readingChapterUrl = readingChapterUrl;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1115456930)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getBookDao() : null;
    }
}
