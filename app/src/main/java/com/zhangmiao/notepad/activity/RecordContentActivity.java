package com.zhangmiao.notepad.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.zhangmiao.notepad.R;
import com.zhangmiao.notepad.bean.RecordDataBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 笔记详情界面
 * Author: zhangmiao
 * Date: 2017/10/11
 */
public class RecordContentActivity extends Activity {

    private RecordDataBean recordDataBean;

    @BindView(R.id.record_content_title)
    TextView tv_contentTitle;

    @BindView(R.id.record_content_content)
    TextView tv_contentContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_content);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        recordDataBean = (RecordDataBean) intent.getSerializableExtra("data");

        if (recordDataBean != null) {
            tv_contentContent.setText(recordDataBean.getContent());
        }
    }

    @OnClick(R.id.record_content_toolbar_back)
    public void back() {
        finish();
    }

}
