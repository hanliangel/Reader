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
import com.reader.hanli.reader.data.bean.Book;

import java.util.List;

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
        holder.tv_description.setText(book.getDescription());
        holder.tv_author.setText(book.getAuthor());
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

        ImageView iv_book_cover;

        TextView tv_name;

        TextView tv_description;

        TextView tv_author;

        public Holder(View itemView , Book book) {
            super(itemView);
            iv_book_cover = (ImageView) itemView.findViewById(R.id.iv_book_cover);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_description = (TextView) itemView.findViewById(R.id.tv_description);
            tv_author = (TextView) itemView.findViewById(R.id.tv_author);

        }
    }
}
