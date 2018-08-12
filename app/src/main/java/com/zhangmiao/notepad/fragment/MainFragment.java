package com.zhangmiao.notepad.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhangmiao.notepad.R;
import com.zhangmiao.notepad.adapter.RecordYearAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主界面布局
 * Author: zhangmiao
 * Date: 2017/10/8
 */
public class MainFragment extends Fragment {

    private static final String TAG = MainFragment.class.getSimpleName();

//    Map<String, List<RecordDataBean>> mDataMap;
    private Context mContext;

    RecordYearAdapter mRecordYearAdapter;

    @BindView(R.id.main_fragment_rv)
    RecyclerView mMainFragmentRecyclerView;

    @BindView(R.id.main_fragment_no_data_prompt)
    TextView tv_noDataPrompt;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        mMainFragmentRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        List<String> dateList = getNoteData();
        if (dateList == null || (dateList != null && dateList.size() == 0)) {
            //无数据
            tv_noDataPrompt.setVisibility(View.VISIBLE);
            mMainFragmentRecyclerView.setVisibility(View.GONE);
        } else {
            tv_noDataPrompt.setVisibility(View.GONE);
            mMainFragmentRecyclerView.setVisibility(View.VISIBLE);
        }
//        mRecordYearAdapter = new RecordYearAdapter(mContext, dateList, mDataMap);
        mMainFragmentRecyclerView.setAdapter(mRecordYearAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        List<String> dateList = getNoteData();
        if (dateList == null || (dateList != null && dateList.size() == 0)) {
            //无数据
            tv_noDataPrompt.setVisibility(View.VISIBLE);
            mMainFragmentRecyclerView.setVisibility(View.GONE);
        } else {
            tv_noDataPrompt.setVisibility(View.GONE);
            mMainFragmentRecyclerView.setVisibility(View.VISIBLE);
        }
//        mRecordYearAdapter.refreshDate(dateList, mDataMap);
    }

    private List<String> getNoteData() {
        List<String> dateList = new ArrayList<>();
//        mDataMap = new HashMap<>();
//        List<RecordDataBean> recordDataBeanList = RecordDao.queryTodayData();
//        Log.d(TAG, "recordDataBeanList:" + recordDataBeanList);
//        if (recordDataBeanList != null && recordDataBeanList.size() > 0) {
//            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
//            RecordDataBean bean = recordDataBeanList.get(0);
//            Date date = new Date(bean.getDate());
//            String dateText = format.format(date);
//            dateList.add(dateText);
//            mDataMap.put(dateText, recordDataBeanList);
//        }
        return dateList;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (mDataMap != null) {
//            mDataMap.clear();
//            mDataMap = null;
//        }
        if (mContext != null) {
            mContext = null;
        }

    }
}
