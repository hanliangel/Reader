package com.reader.hanli.reader.bookshelf;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.blankj.utilcode.util.ConvertUtils;
import com.reader.hanli.baselibrary.base.BaseFragment;
import com.reader.hanli.reader.R;
import com.reader.hanli.reader.R2;
import com.reader.hanli.reader.data.bean.Book;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by hanli on 2018/2/9.
 */

public class BookshelfFragment extends BaseFragment implements BookshelfContract.View {

    @BindView(R2.id.smlv)
    SwipeMenuListView smlv;

    @BindView(R2.id.ptr)
    PtrFrameLayout ptr;

    private BookshelfContract.Presenter mPresenter;

    private BookListAdapter mAdapter;

    @Nullable
    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookshelf, null);
        ButterKnife.bind(this, view);

        mAdapter = new BookListAdapter(getContext(), new ArrayList<Book>());
        initSwipeMenu();

        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame , content , header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPresenter.refreshBookshelf();
            }
        });
        return view;
    }

    @Override
    public void setPresenter(BookshelfContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void showBookshelf(List<Book> books) {
        mAdapter.setData(books);
    }

    @Override
    public void refreshComplete() {
        ptr.refreshComplete();
    }

    /**
     * 初始化列表item侧滑菜单
     */
    private void initSwipeMenu() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(ConvertUtils.dp2px(90));
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(ConvertUtils.dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_launcher_background);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        // set creator
        smlv.setMenuCreator(creator);
    }
}
