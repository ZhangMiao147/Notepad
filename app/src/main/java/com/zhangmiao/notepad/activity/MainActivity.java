package com.zhangmiao.notepad.activity;

import android.content.Intent;
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
import android.widget.Toast;

import com.zhangmiao.notepad.R;
import com.zhangmiao.notepad.adapter.LeftListViewAdapter;
import com.zhangmiao.notepad.bean.DataBean;
import com.zhangmiao.notepad.bean.RecordDataBean;
import com.zhangmiao.notepad.fragment.MainFragment;
import com.zhangmiao.notepad.fragment.RecordListFragment;
import com.zhangmiao.notepad.fragment.SetFragment;
import com.zhangmiao.notepad.fragment.WastebasketFragment;

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

    @BindView(R.id.main_toolbar_left_iv)
    ImageView iv_left;

    private boolean isBack;

    private FragmentManager mManager;

    private MainFragment mMainFragment;

    private RecordListFragment mRecordListFragment;

    private WastebasketFragment mWastebasketFragment;

    private SetFragment mSetFragment;

    private Menu mMenu;

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
        mMainFragment.setArguments(bundle);
        transaction.replace(R.id.main_fragment, mMainFragment).commit();
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
        if (position == 0) {
            //我的笔记
            Log.d(TAG, "count = " + mManager.getBackStackEntryCount());
            FragmentTransaction transaction = mManager.beginTransaction();
            mRecordListFragment = new RecordListFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", DataBean.DATA_TYPE_NOTE);
            mRecordListFragment.setArguments(bundle);
            transaction.replace(R.id.main_fragment, mRecordListFragment);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(null);
            transaction.commit();
            if (mMenu == null) {
                mMenu = mToolbar.getMenu();
            }
            mToolbar.setTitle("我的笔记");
            isBack = true;
        }
        if (position == 1) {
            //我的心情
            FragmentTransaction transaction = mManager.beginTransaction();
            mRecordListFragment = new RecordListFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", DataBean.DATA_TYPE_MOOD);
            mRecordListFragment.setArguments(bundle);
            transaction.replace(R.id.main_fragment, mRecordListFragment);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(null);
            transaction.commit();
            if (mMenu == null) {
                mMenu = mToolbar.getMenu();
            }
            mToolbar.setTitle("我的心情");
            isBack = true;
        }

        if (position == 2) {
            //废纸篓
            Log.d(TAG, "count = " + mManager.getBackStackEntryCount());
            FragmentTransaction transaction = mManager.beginTransaction();
            mWastebasketFragment = new WastebasketFragment();
            transaction.replace(R.id.main_fragment, mWastebasketFragment);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(null);
            transaction.commit();
            if (mMenu == null) {
                mMenu = mToolbar.getMenu();
            }
            mToolbar.setTitle("废纸篓");
            isBack = true;
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
        if (position == 0) {
            //意见反馈
            Toast.makeText(this, "意见反馈", Toast.LENGTH_SHORT).show();
        }
        if (position == 1) {
            //设置
            Log.d(TAG, "count = " + mManager.getBackStackEntryCount());
            FragmentTransaction transaction = mManager.beginTransaction();
            mSetFragment = new SetFragment();
            transaction.replace(R.id.main_fragment, mSetFragment);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(null);
            transaction.commit();
            if (mMenu == null) {
                mMenu = mToolbar.getMenu();
            }
            mToolbar.setTitle("设置");
            isBack = true;
        }
        mDrawerLayout.closeDrawer(Gravity.LEFT);
        if (mRecordLayout.getVisibility() == View.VISIBLE) {
            mRecordLayout.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.main_left_person)
    public void showPersonMessage() {
        Log.d(TAG, "main_left_person");
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

    @OnClick(R.id.main_toolbar_left_iv)
    public void showLeftLayout() {
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    private void setToolbar() {
        mToolbar.setTitle("");
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
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRecordLayout.getVisibility() == View.VISIBLE) {
            mRecordLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        if (mManager == null) {
            mManager = getSupportFragmentManager();
        }
        if (isBack) {
            mManager.popBackStack();
            mToolbar.setTitle("");
            isBack = false;
        } else {
            super.onBackPressed();
        }
    }
}
