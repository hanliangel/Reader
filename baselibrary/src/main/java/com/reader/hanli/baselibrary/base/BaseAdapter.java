package com.reader.hanli.baselibrary.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseAdapter<T , M extends BaseHolder> extends android.widget.BaseAdapter {

    protected Context mContext;
    protected List<T> mList;
    protected LayoutInflater inflater;
    public BaseAdapter(Context context , List<T> list) {
        this.mContext = context;
        setData(list);
    }

    public List<T> getList() {
        return mList;
    }

    public void appendData(List<T> list) {
        if (list == null) {
            return;
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void setData(List<T> list) {
        if (list == null) {
            return;
        }
        mList = list;
        notifyDataSetChanged();
    }

    public void appendToTop(List<T> list) {
        if (list == null) {
            return;
        }
        mList.addAll(0, list);
        notifyDataSetChanged();
    }

    public void appendToTop(T t) {
        if (t == null) {
            return;
        }
        mList.add(0, t);
        notifyDataSetChanged();
    }

    public void append(T t) {
        if (t == null) {
            return;
        }
        mList.add(t);
        notifyDataSetChanged();
    }

    public void removeToList(int position) {
        removeToList(position , true);
    }

    /**
     * 是否通知更改
     * @param position
     * @param notifyDataSetChanged
     */
    public void removeToList(int position , boolean notifyDataSetChanged){
        if (mList.size() <= position) {
            return;
        }
        mList.remove(position);
        if(notifyDataSetChanged){
            notifyDataSetChanged();
        }
    }

    public T getItemBean(int position){
        if(mList != null && !mList.isEmpty()){
            return mList.get(position);
        }
        return null;
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(mList != null){
            return mList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (position > mList.size() - 1) {
            return null;
        }
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 绑定内部点击监听
        M holder = null;
        if(convertView == null){
            holder = onCreateHolder(position, parent);
            convertView = holder.getItemView();
            convertView.setTag(holder);
        }else{
            holder = (M) convertView.getTag();
        }
        onBindHolder(holder , position);
        return convertView;
    }


    protected abstract void onBindHolder(M holder , int position);

    protected abstract M onCreateHolder(int position, ViewGroup parent);

}