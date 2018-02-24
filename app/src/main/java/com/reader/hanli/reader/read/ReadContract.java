package com.reader.hanli.reader.read;

import com.reader.hanli.baselibrary.base.BasePresenter;
import com.reader.hanli.baselibrary.base.BaseView;

/**
 * Created by hanli on 2018/2/24.
 */

public interface ReadContract {

    interface Presenter extends BasePresenter{
        void nextChapter();

        void lastChapter();
    }

    interface View extends BaseView<Presenter>{
        void showContent(String content);
    }

}
