package com.zhangmiao.notepad.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhangmiao.notepad.R;
import com.zhangmiao.notepad.activity.RecordContentActivity;
import com.zhangmiao.notepad.activity.RecordNoteActivity;
import com.zhangmiao.notepad.bean.RecordDataBean;
import com.zhangmiao.notepad.view.TimeView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Author: zhangmiao
 * Date: 2017/10/11
 */
public class RecordContentAdapter extends RecyclerView.Adapter<RecordContentAdapter.RecordContentHolder> {

    private static final String TAG = RecordContentAdapter.class.getSimpleName();

    List<RecordDataBean> dataList;
    Context mContext;

    public RecordContentAdapter(Context context,List<RecordDataBean> dataList) {
        this.mContext = context;
        this.dataList = dataList;
    }

    @Override
    public RecordContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecordContentHolder holder = new RecordContentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_record_content, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecordContentHolder holder, int position) {
        if (position == dataList.size() - 1) {
            holder.line.setVisibility(View.INVISIBLE);
        }
        RecordDataBean bean = dataList.get(position);
        String date = bean.getDate();
        SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date defaultDate = defaultFormat.parse(date);
            holder.monthTextView.setText(new SimpleDateFormat("MM月dd日").format(defaultDate));
            holder.timeTextView.setText(new SimpleDateFormat("HH:mm").format(defaultDate));
            Calendar ca = Calendar.getInstance();
            ca.setTime(defaultDate);
            int hour = ca.get(Calendar.HOUR);
            holder.timeView.setmNum(hour);
            if (hour < 3) {
                holder.timeView.setmClockColor(Color.parseColor("#74848c"));
            } else if (hour < 6) {
                holder.timeView.setmClockColor(Color.parseColor("#56b5e2"));
            } else if (hour < 9) {
                holder.timeView.setmClockColor(Color.parseColor("#8c97cb"));
            } else {
                holder.timeView.setmClockColor(Color.parseColor("#e97f6a"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.title.setText(bean.getTitle());
        holder.content.setText(bean.getContent());

        int type = bean.getType();
        boolean lock = bean.getLock();
        if (type == RecordDataBean.TYPE_NOTE) {
            //holder.flagImageView.setBackgroundResource(R.drawable.clock);
            holder.flagImageView.setVisibility(View.INVISIBLE);
        } else if (type == RecordDataBean.TYPE_MOOD) {
            if (lock) {
                holder.flagImageView.setBackgroundResource(R.drawable.lock);
                holder.contentLayout.setBackgroundColor(Color.parseColor("#1bbc9b"));
                holder.content.setVisibility(View.INVISIBLE);
                holder.title.setVisibility(View.INVISIBLE);
            }
        }
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext != null) {
                    mContext.startActivity(new Intent(mContext, RecordContentActivity.class));
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class RecordContentHolder extends RecyclerView.ViewHolder {
        TimeView timeView;
        TextView monthTextView;
        ImageView flagImageView;
        TextView timeTextView;
        View line;
        TextView title;
        TextView content;
        LinearLayout contentLayout;
        RelativeLayout item;

        public RecordContentHolder(View view) {
            super(view);
            timeView = (TimeView) view.findViewById(R.id.recycler_item_record_content_time_view);
            monthTextView = (TextView) view.findViewById(R.id.recycler_item_record_content_month_text);
            flagImageView = (ImageView) view.findViewById(R.id.recycler_item_record_content_flag_image);
            timeTextView = (TextView) view.findViewById(R.id.recycler_item_record_content_time_text);
            line = view.findViewById(R.id.recycler_item_record_content_line);
            title = (TextView) view.findViewById(R.id.recycler_item_record_content_title);
            content = (TextView) view.findViewById(R.id.recycler_item_record_content_content);
            contentLayout = (LinearLayout) view.findViewById(R.id.recycler_item_record_content_content_layout);
            item = (RelativeLayout) view.findViewById(R.id.recycler_item_record_content);
        }
    }
}
