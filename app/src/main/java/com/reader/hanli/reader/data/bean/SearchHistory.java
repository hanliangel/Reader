package com.reader.hanli.reader.data.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by hanli on 2018/5/21.
 * 搜索历史item
 */
@Entity
public class SearchHistory {

    @Id(autoincrement = true)
    private Long historyId;

    /**
     * 搜索的文本
     */
    private String searchText;

    /**
     * 搜索的时间
     */
    private Long searchTime;

    @Generated(hash = 901145098)
    public SearchHistory(Long historyId, String searchText, Long searchTime) {
        this.historyId = historyId;
        this.searchText = searchText;
        this.searchTime = searchTime;
    }

    @Generated(hash = 1905904755)
    public SearchHistory() {
    }

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public Long getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(Long searchTime) {
        this.searchTime = searchTime;
    }
}
