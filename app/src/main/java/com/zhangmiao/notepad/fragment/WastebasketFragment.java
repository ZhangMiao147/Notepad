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
import com.zhangmiao.notepad.bean.RecordDataBean;
import com.zhangmiao.notepad.db.RecordDao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 废纸篓的界面
 * Author: zhangmiao
 * Date: 2017/10/8
 */
public class WastebasketFragment extends Fragment {

    private static final String TAG = WastebasketFragment.class.getSimpleName();

    @BindView(R.id.fragment_wastebasket_recycler_rv)
    RecyclerView mYearRecyclerView;

    @BindView(R.id.fragment_wastebasket_no_data_prompt_tv)
    TextView tv_noDataPrompt;

    Context mContext;

    Map<String, List<RecordDataBean>> mDataMap;
    RecordYearAdapter mRecordYearAdapter;

    int mType;

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wastebasket, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mYearRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        List<String> dateList = getData();
        if (dateList == null || (dateList != null && dateList.size() == 0)) {
            tv_noDataPrompt.setVisibility(View.VISIBLE);
            mYearRecyclerView.setVisibility(View.GONE);
            tv_noDataPrompt.setText("废纸篓里没有东西哦！");
        } else {
            tv_noDataPrompt.setVisibility(View.GONE);
            mYearRecyclerView.setVisibility(View.VISIBLE);
        }

        mRecordYearAdapter = new RecordYearAdapter(mContext, dateList, mDataMap);
        mYearRecyclerView.setAdapter(mRecordYearAdapter);
    }

    private List<String> getData() {
        List<String> dateList = new ArrayList<>();
        mDataMap = new HashMap<>();
        List<RecordDataBean> recordDataBeanList = RecordDao.queryDataIsWastebasket();
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
        if (recordDataBeanList != null) {
            for (int i = 0; i < recordDataBeanList.size(); i++) {
                RecordDataBean bean = recordDataBeanList.get(i);
                long time = bean.getUpdateDate();
                String timeText = format.format(new Date(time));
                if (!dateList.contains(timeText)) {
                    dateList.add(timeText);
                    List<RecordDataBean> beanList = new ArrayList<>();
                    beanList.add(bean);
                    mDataMap.put(timeText, beanList);
                } else {
                    List<RecordDataBean> dataBeanList = mDataMap.get(timeText);
                    dataBeanList.add(bean);
                }
            }
        }
        return dateList;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext = null;
        mDataMap.clear();
    }
}
