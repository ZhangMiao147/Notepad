package com.zhangmiao.notepad.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhangmiao.notepad.R;

import java.util.List;
import java.util.Map;

/**
 * 笔记心情年月列表adapter
 * Author: zhangmiao
 * Date: 2017/10/11
 */
public class RecordYearAdapter extends RecyclerView.Adapter<RecordYearAdapter.YearHolder> {

    private static final String TAG = RecordYearAdapter.class.getSimpleName();

    List<String> dataList;
    Context mContext;
//    Map<String, List<RecordDataBean>> mDataMap;

//    public RecordYearAdapter(Context context, List<String> dataList, Map<String, List<RecordDataBean>> dataMap) {
////        this.mContext = context;
////        this.dataList = dataList;
////        this.mDataMap = dataMap;
//    }

//    public void refreshDate(List<String> dataList, Map<String, List<RecordDataBean>> dataMap) {
////        this.dataList = dataList;
////        this.mDataMap = dataMap;
////        notifyDataSetChanged();
//    }

    @Override
    public RecordYearAdapter.YearHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecordYearAdapter.YearHolder holder = new RecordYearAdapter.YearHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_my_note, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecordYearAdapter.YearHolder holder, int position) {
//        String date = dataList.get(position);
//        holder.year.setText(date);
//        holder.contentRecycler.setLayoutManager(new LinearLayoutManager(mContext));
//        RecordContentAdapter adapter = new RecordContentAdapter(mContext, mDataMap.get(date));
//        holder.contentRecycler.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class YearHolder extends RecyclerView.ViewHolder {
        TextView year;
        RecyclerView contentRecycler;

        public YearHolder(View view) {
            super(view);
            year = (TextView) view.findViewById(R.id.recycler_item_my_note_year);
            contentRecycler = (RecyclerView) view.findViewById(R.id.recycler_item_my_note_recycler);
        }
    }
}
