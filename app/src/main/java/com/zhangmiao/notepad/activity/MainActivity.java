package com.zhangmiao.notepad.activity;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.zhangmiao.notepad.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 主界面
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.main_add)
    ImageView mAdd;

    @BindView(R.id.main_record_layout)
    LinearLayout mRecordLayout;

    @BindView(R.id.main_left_record)
    ListView mLeftRecordListView;

    @BindView(R.id.main_left_set)
    ListView mLeftSetListView;

    @BindView(R.id.main_drawer_layout)
    DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setToolbar();
        initListView();
    }

    private void initListView() {
        SimpleAdapter recordAdapter = new SimpleAdapter(this, getRecordData(), R.layout.list_item_main_left, new String[]{"img", "title"}, new int[]{R.id.list_item_left_img, R.id.list_item_left_title});
        mLeftRecordListView.setAdapter(recordAdapter);
        mLeftRecordListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    //我的笔记
                    startActivity(new Intent(MainActivity.this, MyNoteActivity.class));
                }
                if (position == 1) {
                    //我的心情
                    startActivity(new Intent(MainActivity.this, MyMoodActivity.class));
                }

                if (position == 2) {
                    //废纸篓
                }
            }
        });
        SimpleAdapter setAdapter = new SimpleAdapter(this, getSetData(), R.layout.list_item_main_left, new String[]{"img", "title"}, new int[]{R.id.list_item_left_img, R.id.list_item_left_title});
        mLeftSetListView.setAdapter(setAdapter);
    }

    private List<Map<String, Object>> getRecordData() {
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();
        map.put("img", R.mipmap.ic_launcher);
        map.put("title", "我的笔记");
        list.add(map);

        map = new HashMap<>();
        map.put("img", R.mipmap.ic_launcher);
        map.put("title", "我的心情");
        list.add(map);

        map = new HashMap<>();
        map.put("img", R.mipmap.ic_launcher);
        map.put("title", "废纸篓");
        list.add(map);

        return list;
    }

    private List<Map<String, Object>> getSetData() {
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();
        map.put("img", R.mipmap.ic_launcher);
        map.put("title", "意见反馈");
        list.add(map);

        map = new HashMap<>();
        map.put("img", R.mipmap.ic_launcher);
        map.put("title", "设置");
        list.add(map);

        return list;
    }


    private void setToolbar() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
    }

    @OnClick(R.id.main_add)
    public void addRecord() {
        if (mRecordLayout.getVisibility() == View.GONE) {
            mRecordLayout.setVisibility(View.VISIBLE);
        } else {
            mRecordLayout.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.main_record_life)
    public void recordLife() {
        startActivity(new Intent(this, RecordNoteActivity.class));
    }

    @OnClick(R.id.main_record_mood)
    public void recordMood() {
        startActivity(new Intent(this, WriteMoodActivity.class));
    }

    //下载图标http://www.open-open.com/lib/view/open1446429625717.html
    @OnClick(R.id.main_toolbar_menu)
    public void showLeftMenu() {
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
