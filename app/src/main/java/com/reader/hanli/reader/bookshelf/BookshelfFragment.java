package com.reader.hanli.reader.bookshelf;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.blankj.utilcode.util.ConvertUtils;
import com.reader.hanli.baselibrary.base.BaseFragment;
import com.reader.hanli.reader.R;
import com.reader.hanli.reader.R2;
import com.reader.hanli.reader.bookdetail.BookDetailActivity;
import com.reader.hanli.reader.data.bean.Book;
import com.reader.hanli.reader.search.SearchActivity;

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

    @BindView(R2.id.tool_bar)
    Toolbar mToolbar;

    @BindView(R2.id.smlv)
    SwipeMenuListView smlv;

//    @BindView(R2.id.ptr)
//    PtrFrameLayout ptr;

    private BookshelfContract.Presenter mPresenter;

    private BookListAdapter mAdapter;

    @Nullable
    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookshelf, null);
        ButterKnife.bind(this , view);

        mAdapter = new BookListAdapter(getContext(), new ArrayList<Book>());
        initSwipeMenu();
        smlv.setAdapter(mAdapter);
        smlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BookDetailActivity.startActivity(mActivity , (Book) parent.getItemAtPosition(position));
            }
        });

        initToolbar(mToolbar);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_search:
                        SearchActivity.startSearchActivity(mActivity);
                        break;
                    default:
                        break;
                }
                return false;
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
        mAdapter.removeToList(1);
    }

    @Override
    public void refreshComplete() {
        if(mAdapter != null){
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.bookshelf_menu , menu);
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
                openItem.setTitle(getString(R.string.menu_remove_from_shelf));
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

            }
        };

        // set creator
        smlv.setMenuCreator(creator);

        smlv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                mPresenter.removeFromShelf(position);
                return true;
            }
        });
    }
}
