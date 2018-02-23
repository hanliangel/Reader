package com.reader.hanli.baselibrary.base;

import android.view.View;

/**
 * Created by hanli on 2017/3/7.
 * baseAdapter的基础holder
 */
public class BaseHolder {

    /**
     * 列表中一个item的视图
     */
    protected View itemView;

    public View getItemView() {
        return itemView;
    }

    public BaseHolder(View itemView) {
        this.itemView = itemView;
    }
}