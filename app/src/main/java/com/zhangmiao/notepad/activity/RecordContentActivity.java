package com.zhangmiao.notepad.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zhangmiao.notepad.R;

/**
 * Author: zhangmiao
 * Date: 2017/10/11
 */
public class RecordContentActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_content);
    }
}
