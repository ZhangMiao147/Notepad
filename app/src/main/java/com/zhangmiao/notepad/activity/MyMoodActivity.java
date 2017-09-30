package com.zhangmiao.notepad.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;

import com.zhangmiao.notepad.R;

import butterknife.ButterKnife;

/**
 * 我的心情界面
 * Author: zhangmiao
 * Date: 2017/9/29
 */
public class MyMoodActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_mood);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my_note, menu);
        return true;
    }
}
