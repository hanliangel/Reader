package com.reader.hanli.reader.bookdetail;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.reader.hanli.baselibrary.base.BaseAdapter;
import com.reader.hanli.baselibrary.base.BaseHolder;
import com.reader.hanli.reader.R;
import com.reader.hanli.reader.data.bean.Book;

import java.util.List;


/**
 * Created by hanli on 2018/2/24.
 */

public class ChapterListAdapter extends BaseAdapter<Book.Chapter , ChapterListAdapter.Holder> {


    public ChapterListAdapter(Context context, List<Book.Chapter> list) {
        super(context, list);
    }

    @Override
    protected void onBindHolder(Holder holder, int position) {
        holder.tv.setText(getItemBean(position).getName());
    }

    @Override
    protected Holder onCreateHolder(int position, ViewGroup parent) {
        View item = View.inflate(mContext , R.layout.item_chapter , null);
        return new Holder(item);
    }

    public class Holder extends BaseHolder{

        TextView tv;

        public Holder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }

    }
}
