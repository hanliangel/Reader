package com.reader.hanli.reader.read;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.reader.hanli.baselibrary.base.BaseFragment;
import com.reader.hanli.reader.R;
import com.reader.hanli.reader.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hanli on 2018/2/24.
 */

public class ReadFragment extends BaseFragment implements ReadContract.View {

    private ReadContract.Presenter mPresenter;

    @BindView(R2.id.tv_content)
    TextView tv_content;

    @BindView(R2.id.sv)
    ScrollView sv;

    @BindView(R2.id.bt_last)
    Button bt_last;

    @BindView(R2.id.bt_next)
    Button bt_next;

    @Override
    public void setPresenter(ReadContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Nullable
    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read , null);
        ButterKnife.bind(this , view);

        bt_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.lastChapter();
            }
        });

        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.nextChapter();
            }
        });
        return view;
    }

    @Override
    public void showContent(String content) {
        tv_content.setText(content);

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }
}
