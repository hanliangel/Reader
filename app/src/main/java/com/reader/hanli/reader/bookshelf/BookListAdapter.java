package com.reader.hanli.reader.bookshelf;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.reader.hanli.baselibrary.base.BaseAdapter;
import com.reader.hanli.baselibrary.base.BaseHolder;
import com.reader.hanli.reader.R;
import com.reader.hanli.reader.R2;
import com.reader.hanli.reader.data.bean.Book;
import com.reader.hanli.reader.data.bean.Chapter;
import com.reader.hanli.reader.data.engine.EngineHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hanli on 2018/2/11.
 */

public class BookListAdapter extends BaseAdapter<Book, BookListAdapter.Holder> {


    public BookListAdapter(Context context, List<Book> list) {
        super(context, list);
    }

    @Override
    protected void onBindHolder(Holder holder, int position) {
        Book book = getItemBean(position);
        holder.tv_name.setText(book.getName());

        int unreadNum = book.getUnreadChapterNum();
        switch (unreadNum){
            case -1 :
                holder.tv_unread_num.setText(mContext.getString(R.string.book_unread_not_start));
                break;
            case 0 :
                holder.tv_unread_num.setText(mContext.getString(R.string.book_unread_read_end));
                break;
            default:
                holder.tv_unread_num.setText(String.format(mContext.getString(R.string.book_unread_num), unreadNum + ""));
                break;
        }
        holder.tv_newest_chapter.setText(mContext.getString(R.string.book_newest_chapter_tip) + (book.getNewestChapter() == null ? "" : book.getNewestChapter().getName()));
        holder.tv_author.setText(book.getAuthor());
        holder.tv_alias.setText(EngineHelper.getInstance().getBookEngine(book.getEngineName()).getEngineAlias());
        Glide.with(mContext)
                .load(book.getCoverUri())
                .into(holder.iv_book_cover);
    }

    @Override
    protected Holder onCreateHolder(int position, ViewGroup parent) {
        View view = View.inflate(mContext , R.layout.book_item_layout , null);
        return new Holder(view , getItemBean(position));
    }

    public class Holder extends BaseHolder {

        @BindView(R2.id.iv_book_cover)
        ImageView iv_book_cover;

        @BindView(R2.id.tv_name)
        TextView tv_name;

        @BindView(R2.id.tv_unread_num)
        TextView tv_unread_num;

        @BindView(R2.id.tv_newest_chapter)
        TextView tv_newest_chapter;

        @BindView(R2.id.tv_author)
        TextView tv_author;

        @BindView(R2.id.tv_engine_alias)
        TextView tv_alias;

        public Holder(View itemView , Book book) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
}
