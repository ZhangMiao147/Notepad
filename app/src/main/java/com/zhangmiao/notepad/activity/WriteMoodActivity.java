package com.zhangmiao.notepad.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zhangmiao.notepad.R;

import butterknife.ButterKnife;

/**
 * 写心情界面
 * Author: zhangmiao
 * Date: 2017/9/29
 */
public class WriteMoodActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_mood);
        ButterKnife.bind(this);
    }

}
