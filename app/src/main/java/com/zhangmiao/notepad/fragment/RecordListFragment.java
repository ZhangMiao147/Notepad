package com.zhangmiao.notepad.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhangmiao.notepad.R;
import com.zhangmiao.notepad.adapter.RecordYearAdapter;
import com.zhangmiao.notepad.bean.DataBean;
import com.zhangmiao.notepad.bean.MoodContentBean;
import com.zhangmiao.notepad.bean.NoteContentBean;
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
import butterknife.OnClick;

/**
 * 我的笔记，我的心情的界面
 * Author: zhangmiao
 * Date: 2017/10/8
 */
public class RecordListFragment extends Fragment {

    private static final String TAG = RecordListFragment.class.getSimpleName();

    @BindView(R.id.fragment_record_recycler)
    RecyclerView mYearRecyclerView;

    @BindView(R.id.fragment_record_no_data_prompt)
    TextView tv_noDataPrompt;

    @BindView(R.id.fragment_record_search_edit)
    EditText et_searchText;

    @BindView(R.id.fragment_record_search)
    ImageView iv_search;

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

        mType = getArguments().getInt("type");
        View view = inflater.inflate(R.layout.fragment_record, container, false);
        ButterKnife.bind(this, view);
        mYearRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        List<String> dateList = getData();
        if (dateList == null || (dateList != null && dateList.size() == 0)) {
            tv_noDataPrompt.setVisibility(View.VISIBLE);
            mYearRecyclerView.setVisibility(View.GONE);
            if (mType == DataBean.DATA_TYPE_MOOD) {
                tv_noDataPrompt.setText("快去记录你当前的心情吧！");
            } else {
                tv_noDataPrompt.setText("快去记录你多彩的生活吧！");
            }
        } else {
            tv_noDataPrompt.setVisibility(View.GONE);
            mYearRecyclerView.setVisibility(View.VISIBLE);
        }

        mRecordYearAdapter = new RecordYearAdapter(mContext, dateList, mDataMap);
        mYearRecyclerView.setAdapter(mRecordYearAdapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        List<String> dateList = getData();

        if (dateList == null || (dateList != null && dateList.size() == 0)) {
            if (mType == DataBean.DATA_TYPE_MOOD) {
                showPrompt(View.GONE, "快去记录你当前的心情吧！");
            } else {
                showPrompt(View.GONE, "快去记录你多彩的生活吧！");
            }
        } else {
            showPrompt(View.GONE, null);
        }
        mRecordYearAdapter.refreshDate(dateList, mDataMap);
    }

    private List<String> getData() {
        List<String> dateList = new ArrayList<>();
        mDataMap = new HashMap<>();
        List<RecordDataBean> recordDataBeanList = RecordDao.queryDataByType(mType);
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

    @OnClick(R.id.fragment_record_search)
    void search() {
        List<RecordDataBean> searchResult = new ArrayList<>();
        if (et_searchText.getText() != null) {
            String searchText = et_searchText.getText().toString();
            if (!TextUtils.isEmpty(searchText.trim())) {
                List<RecordDataBean> dataBeanList = RecordDao.queryDataByType(mType);
                for (int i = 0; i < dataBeanList.size(); i++) {
                    RecordDataBean bean = dataBeanList.get(i);
                    String content = bean.getContent();
                    if (content.contains(searchText)) {
                        //包含内容
                        searchResult.add(bean);
                    }
                    if (mType == DataBean.DATA_TYPE_NOTE) {
                        NoteContentBean noteContentBean = NoteContentBean.jsonToBean(content);
                        String title = noteContentBean.getTitle();
                        if (!TextUtils.isEmpty(title)) {
                            searchResult.add(bean);
                        } else {
                            String article = noteContentBean.getArticle();
                            if (!TextUtils.isEmpty(article)) {
                                searchResult.add(bean);
                            }
                        }
                    } else if (mType == DataBean.DATA_TYPE_MOOD) {
                        MoodContentBean moodContentBean = MoodContentBean.jsonToBean(content);
                        String article = moodContentBean.getArticle();
                        if (!TextUtils.isEmpty(article)) {
                            searchResult.add(bean);
                        }
                    }
                }
            } else {
                //无输入,显示所有的数据
                showPrompt(View.GONE, null);
                mRecordYearAdapter.refreshDate(getData(), mDataMap);
                return;
            }
        } else {
            //无输入,显示所有的数据
            showPrompt(View.GONE, null);
            mRecordYearAdapter.refreshDate(getData(), mDataMap);
            return;
        }

        if (searchResult != null) {
            if (searchResult.size() == 0) {
                //没有查询到数据
                showPrompt(View.VISIBLE, "没有查询到响应的数据！");
            } else {
                List<String> dateList = new ArrayList<>();
                Map<String, List<RecordDataBean>> searchResultMap = new HashMap<>();
                SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
                for (int i = 0; i < searchResult.size(); i++) {
                    RecordDataBean bean = searchResult.get(i);
                    long time = bean.getUpdateDate();
                    String timeText = format.format(new Date(time));
                    if (!dateList.contains(timeText)) {
                        dateList.add(timeText);
                        List<RecordDataBean> beanList = new ArrayList<>();
                        beanList.add(bean);
                        searchResultMap.put(timeText, beanList);
                    } else {
                        List<RecordDataBean> dataBeanList = mDataMap.get(timeText);
                        dataBeanList.add(bean);
                    }
                }
                showPrompt(View.GONE, null);
                mRecordYearAdapter.refreshDate(dateList, searchResultMap);
            }
        }

    }

    public void showPrompt(int Visibility, String message) {
        if (Visibility == View.VISIBLE) {
            tv_noDataPrompt.setVisibility(View.VISIBLE);
            mYearRecyclerView.setVisibility(View.GONE);
            tv_noDataPrompt.setText(message);
        } else {
            tv_noDataPrompt.setVisibility(View.GONE);
            mYearRecyclerView.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext = null;
        mDataMap.clear();
    }
}
