package com.reader.hanli.reader.search;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.reader.hanli.reader.R;
import com.reader.hanli.reader.data.engine.BookEngine;
import com.reader.hanli.reader.data.engine.EngineHelper;

import java.util.List;

/**
 * Created by hanli on 2018/5/18.
 * 改变搜索引擎的dialog
 */
public class ChangeSearchEngineDialog extends Dialog {

    private OnEngineSelectListener mEngineSelectListener;

    protected ChangeSearchEngineDialog(@NonNull Context context) {
        super(context , R.style.commonDialogTheme);
        init();
    }

    protected ChangeSearchEngineDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    private void init(){
        setContentView(R.layout.dialog_change_search_engine);
        List<BookEngine> allBookEngine = EngineHelper.getInstance().getAllBookEngine();
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll_container);
        for(BookEngine engine : allBookEngine){
            addItem(engine , ll);
        }
    }

    private void addItem(final BookEngine engine , ViewGroup group){
        TextView tv = (TextView) View.inflate(getContext() , R.layout.dialog_change_search_engine_item , null);
        tv.setText(engine.getEngineAlias());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEngineSelectListener != null){
                    mEngineSelectListener.onEngineSelected(engine.getEngineName());
                    dismiss();
                }
            }
        });
        group.addView(tv);
    }

    public void setEngineSelectListener(OnEngineSelectListener engineSelectListener){
        this.mEngineSelectListener = engineSelectListener;
    }

    /**
     * 选择引擎的监听
     */
    public interface OnEngineSelectListener{
        /**
         * 当引擎被选择的回调方法
         * @param engineName 引擎的名称
         */
        void onEngineSelected(String engineName);
    }
}
