package com.zhangmiao.notepad.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhangmiao.notepad.R;
import com.zhangmiao.notepad.adapter.RecordYearAdapter;
import com.zhangmiao.notepad.bean.RecordDataBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: zhangmiao
 * Date: 2017/10/8
 */
public class RecordListFragment extends Fragment {

    private static final String TAG = RecordListFragment.class.getSimpleName();

    RecyclerView mYearRecyclerView;
    Context mContext;

    Map<String, List<RecordDataBean>> mDataMap;

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

        View view = inflater.inflate(R.layout.fragment_my_note, container, false);
        mYearRecyclerView = (RecyclerView) view.findViewById(R.id.my_note_recycler);
        mYearRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        RecordYearAdapter adapter = new RecordYearAdapter(mContext, getNoteData(), mDataMap);
        mYearRecyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private List<String> getNoteData() {
        List<String> dateList = new ArrayList<>();
        mDataMap = new HashMap<>();
        List<RecordDataBean> dataBeanList1 = new ArrayList<>();
        RecordDataBean bean1 = new RecordDataBean();
        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date());
        bean1.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ca.getTime()));
        bean1.setType(mType);
        bean1.setLock(false);
        bean1.setTitle("课堂笔记");
        bean1.setContent("在listView的item中或者是特殊的业务需求中，会要求TextView的内容不完全显示，只有通过一个指定的操作后才显示所有的，比如说一个按钮或者是其它的什么控件。");
        Log.d(TAG, "bean1 date:" + bean1);
        dataBeanList1.add(bean1);

        RecordDataBean bean2 = new RecordDataBean();
        ca.add(Calendar.HOUR, 3);
        bean2.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ca.getTime()));
        bean2.setType(mType);
        bean2.setLock(false);
        bean2.setTitle("部门会议");
        bean2.setContent("八点在会议室召开全体大会！");
        Log.d(TAG, "bean2 date:" + bean2);
        dataBeanList1.add(bean2);

        RecordDataBean bean3 = new RecordDataBean();
        ca.add(Calendar.HOUR, 3);
        bean3.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ca.getTime()));
        bean3.setType(mType);
        bean3.setLock(true);
        bean3.setTitle("读书笔记");
        bean3.setContent("从技术的角度来说，像E、List<E>和List<String>这样的类型应称作不可具体化的(nonreifiable)类型。直观地说，不可具体化的(non-reifiable)类型是指其运行时表示法包含的信息比它的编译时表示法包含的信息更少的类型。唯一可具体化的(reifiable)参数化类型是无限制的通配符类型，如List<?>和Map<?,?>，虽然不常用，但是创建无限制通配符类型的数组是合法的。");
        Log.d(TAG, "bean3 date:" + bean3);
        dataBeanList1.add(bean3);
        mDataMap.put(new SimpleDateFormat("yyyy年MM月").format(ca.getTime()), dataBeanList1);
        dateList.add(new SimpleDateFormat("yyyy年MM月").format(ca.getTime()));

        List<RecordDataBean> dataBeanList2 = new ArrayList<>();
        RecordDataBean bean4 = new RecordDataBean();
        ca.add(Calendar.MONTH, -1);
        ca.add(Calendar.HOUR, 3);
        bean4.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ca.getTime()));
        bean4.setType(mType);
        bean4.setLock(false);
        bean4.setTitle("工作安排");
        bean4.setContent("对上周的学习、工作进行总结，安排计划这周的学习任务。");
        Log.d(TAG, "bean4 date:" + bean4);
        dataBeanList2.add(bean4);
        mDataMap.put(new SimpleDateFormat("yyyy年MM月").format(ca.getTime()), dataBeanList2);
        dateList.add(new SimpleDateFormat("yyyy年MM月").format(ca.getTime()));
        return dateList;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext = null;
        mDataMap.clear();
    }
}
