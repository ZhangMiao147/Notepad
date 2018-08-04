package com.zhangmiao.notepad.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhangmiao.notepad.R;

import java.util.List;
import java.util.Map;

/**
 * 左边侧拉栏的adapter
 * Author: zhangmiao
 * Date: 2017/9/30
 */
public class LeftListViewAdapter extends BaseAdapter {

    private final static String TAG = LeftListViewAdapter.class.getSimpleName();

    private List<Map<String, Object>> dataList;
    private Context context;

    public LeftListViewAdapter(Context context, List<Map<String, Object>> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        if (dataList != null) {
            return dataList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (dataList != null) {
            dataList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LeftViewHolder holder = null;
        if (convertView == null) {
            holder = new LeftViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_main_left, null);
            convertView.setTag(holder);
            holder.imageView = (ImageView) convertView.findViewById(R.id.list_item_left_img);
            holder.textView = (TextView) convertView.findViewById(R.id.list_item_left_title);

            Map<String, Object> map = dataList.get(position);
            holder.imageView.setImageResource((int) map.get("img"));
            holder.textView.setText((String) map.get("title"));
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick");
                }
            });
        } else {
            holder = (LeftViewHolder) convertView.getTag();
        }
        return convertView;
    }

    public class LeftViewHolder {
        public ImageView imageView;
        public TextView textView;
    }

}
