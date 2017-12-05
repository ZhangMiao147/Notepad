package com.zhangmiao.notepad.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.zhangmiao.notepad.R;
import com.zhangmiao.notepad.adapter.LeftListViewAdapter;
import com.zhangmiao.notepad.bean.RecordDataBean;
import com.zhangmiao.notepad.fragment.MainFragment;
import com.zhangmiao.notepad.fragment.RecordListFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

//下载图标http://blog.csdn.net/frank__it/article/details/53148723

/**
 * 主界面
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

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

    @BindView(R.id.main_toolbar_back)
    ImageView iv_toolbarBack;

    private boolean isBack;

    FragmentManager mManager;

    MainFragment mMainFragment;

    RecordListFragment mRecordListFragment;

    Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setDefaultFragment();
        setToolbar();
        initListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setDefaultFragment() {
        if (mManager == null) {
            mManager = getSupportFragmentManager();
        }
        FragmentTransaction transaction = mManager.beginTransaction();
        mMainFragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", RecordDataBean.TYPE_MAIN);
        mMainFragment.setArguments(bundle);
        transaction.replace(R.id.main_fragment, mMainFragment);
        transaction.commit();
        iv_toolbarBack.setVisibility(View.GONE);
    }

    private void initListView() {
        LeftListViewAdapter recordAdapter = new LeftListViewAdapter(this, getRecordData());
        mLeftRecordListView.setAdapter(recordAdapter);

        LeftListViewAdapter setAdapter = new LeftListViewAdapter(this, getSetData());
        mLeftSetListView.setAdapter(setAdapter);
    }

    @OnItemClick(R.id.main_left_record)
    public void LeftRecordItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "mLeftRecordListView position = " + position);
        if (mManager == null) {
            mManager = getSupportFragmentManager();
        }
        back();
        if (position == 0) {
            //我的笔记
            Log.d(TAG, "count = " + mManager.getBackStackEntryCount());

            FragmentTransaction transaction = mManager.beginTransaction();
            mRecordListFragment = new RecordListFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", RecordDataBean.TYPE_NOTE);
            mRecordListFragment.setArguments(bundle);
            transaction.replace(R.id.main_fragment, mRecordListFragment);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(null);
            transaction.commit();
            iv_toolbarBack.setVisibility(View.VISIBLE);
            if (mMenu == null) {
                mMenu = mToolbar.getMenu();
            }
            mMenu.findItem(R.id.menu_main_search).setVisible(true);
            mToolbar.setTitle("我的笔记");
            isBack = true;
        }
        if (position == 1) {
            //我的心情
            FragmentTransaction transaction = mManager.beginTransaction();
            mRecordListFragment = new RecordListFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", RecordDataBean.TYPE_MOOD);
            mRecordListFragment.setArguments(bundle);
            transaction.replace(R.id.main_fragment, mRecordListFragment);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(null);
            transaction.commit();
            iv_toolbarBack.setVisibility(View.VISIBLE);
            if (mMenu == null) {
                mMenu = mToolbar.getMenu();
            }
            mMenu.findItem(R.id.menu_main_search).setVisible(true);
            mToolbar.setTitle("我的心情");
            isBack = true;
        }

        if (position == 2) {
            //废纸篓
        }
        mDrawerLayout.closeDrawer(Gravity.LEFT);
        if (mRecordLayout.getVisibility() == View.VISIBLE) {
            mRecordLayout.setVisibility(View.GONE);
        }
    }

    @OnItemClick(R.id.main_left_set)
    public void LeftSetItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "LeftSetItemClick position = " + position);
        //意见反馈与设置
        mDrawerLayout.closeDrawer(Gravity.LEFT);
        if (mRecordLayout.getVisibility() == View.VISIBLE) {
            mRecordLayout.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.main_left_person)
    public void showPersonMessage() {
        Log.d(TAG, "main_left_person");
    }

    @OnClick(R.id.main_toolbar_back)
    public void back() {
        Fragment fragment = mManager.findFragmentById(R.id.main_fragment);
        if (fragment instanceof RecordListFragment) {
            mManager.popBackStack();
        }
        mToolbar.setTitle("");
        iv_toolbarBack.setVisibility(View.GONE);
    }

    private List<Map<String, Object>> getRecordData() {
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();
        map.put("img", R.drawable.note);
        map.put("title", "我的笔记");
        list.add(map);

        map = new HashMap<>();
        map.put("img", R.drawable.mood);
        map.put("title", "我的心情");
        list.add(map);

        map = new HashMap<>();
        map.put("img", R.drawable.wastepaper);
        map.put("title", "废纸篓");
        list.add(map);

        return list;
    }

    private List<Map<String, Object>> getSetData() {
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();
        map.put("img", R.drawable.feedback);
        map.put("title", "意见反馈");
        list.add(map);

        map = new HashMap<>();
        map.put("img", R.drawable.set);
        map.put("title", "设置");
        list.add(map);

        return list;
    }

    private void setToolbar() {
        mToolbar.setTitle("");
        mToolbar.setNavigationIcon(R.drawable.menu);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });

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
        if (mRecordLayout.getVisibility() == View.VISIBLE) {
            mRecordLayout.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.main_record_mood)
    public void recordMood() {
        startActivity(new Intent(this, WriteMoodActivity.class));
        if (mRecordLayout.getVisibility() == View.VISIBLE) {
            mRecordLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mMenu = menu;
        mMenu.findItem(R.id.menu_main_search).setVisible(false);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (mManager == null) {
            mManager = getSupportFragmentManager();
        }

        if (isBack) {
            mManager.popBackStack();
            mToolbar.setTitle("");
            iv_toolbarBack.setVisibility(View.GONE);
            isBack = false;
        } else {
            super.onBackPressed();
        }

    }
}
