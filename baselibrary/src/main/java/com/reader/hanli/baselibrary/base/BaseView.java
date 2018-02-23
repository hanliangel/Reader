package com.reader.hanli.baselibrary.base;

public interface BaseView<T extends BasePresenter> {

    void setPresenter(T presenter);

}
