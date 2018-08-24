package com.zhangmiao.notepad.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.AlphabeticIndex;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhangmiao.notepad.R;
import com.zhangmiao.notepad.activity.RecordContentActivity;
import com.zhangmiao.notepad.bean.DataBean;
import com.zhangmiao.notepad.bean.MoodContentBean;
import com.zhangmiao.notepad.bean.NoteContentBean;
import com.zhangmiao.notepad.bean.RecordDataBean;
import com.zhangmiao.notepad.util.SPUtil;
import com.zhangmiao.notepad.view.TimeView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 笔记心情列表adapter
 * Author: zhangmiao
 * Date: 2017/10/11
 */
public class RecordContentAdapter extends RecyclerView.Adapter<RecordContentAdapter.RecordContentHolder> {

    private static final String TAG = RecordContentAdapter.class.getSimpleName();

    List<RecordDataBean> dataList;
    Context mContext;

    public RecordContentAdapter(Context context, List<RecordDataBean> dataList) {
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
        final RecordDataBean bean = dataList.get(position);
        long date = bean.getUpdateDate();
        SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date defaultDate = new Date(date);
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

        int type = bean.getType();
        String content = bean.getContent();
        switch (type) {
            case DataBean.DATA_TYPE_MOOD:
                MoodContentBean moodContentBean = MoodContentBean.jsonToBean(content);
                Log.d(TAG, "position:" + position + ",moodContentBean.getArticle():" + moodContentBean.getArticle());
                holder.title.setVisibility(View.GONE);
                if (moodContentBean.getIslock()) {
                    holder.content.setText("");
                    holder.flagImageView.setBackgroundResource(R.drawable.lock);
                    holder.contentLayout.setBackgroundColor(Color.parseColor("#1bbc9b"));
                } else {
                    holder.content.setText(moodContentBean.getArticle());
                    holder.flagImageView.setVisibility(View.GONE);
                    holder.contentLayout.setBackgroundColor(Color.WHITE);
                }
                break;
            case DataBean.DATA_TYPE_NOTE:
                NoteContentBean noteContentBean = NoteContentBean.jsonToBean(content);
                holder.title.setVisibility(View.VISIBLE);
                holder.title.setText(noteContentBean.getTitle());
                holder.content.setText(noteContentBean.getArticle());
                break;
            default:
                break;
        }
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean.getType() == DataBean.DATA_TYPE_MOOD) {
                    MoodContentBean moodContentBean = MoodContentBean.jsonToBean(bean.getContent());
                    if (moodContentBean.getIslock()) {
                        handPassword(bean);
                    } else {
                        gotoContent(bean);
                    }
                } else {
                    gotoContent(bean);
                }

            }
        });
    }

    private void gotoContent(RecordDataBean bean) {
        if (mContext != null) {
            Intent intent = new Intent();
            intent.setClass(mContext, RecordContentActivity.class);
            intent.putExtra("data", bean);
            mContext.startActivity(intent);
        }
    }

    private void handPassword(RecordDataBean bean) {
        if (mContext != null) {
            String password = SPUtil.getString(mContext, SPUtil.MOOD_PASSWORD, "");
            if (TextUtils.isEmpty(password)) {
                //显示设置密码的框
                showSetPassword();
            } else {
                //显示输入密码的框
                showDeterminePassword(bean);
            }
        }
    }

    private void showSetPassword() {
        if (mContext == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = View.inflate(mContext, R.layout.dialog_set_password, null);
        final EditText et_first = (EditText) view.findViewById(R.id.dialog_set_password_first_et);
        final EditText et_second = (EditText) view.findViewById(R.id.dialog_set_password_second_et);
        builder.setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String firstStr = et_first.getText().toString().trim();
                        String secondStr = et_second.getText().toString().trim();
                        if (TextUtils.isEmpty(firstStr) || TextUtils.isEmpty(secondStr)) {
                            Toast.makeText(mContext, "输入密码为空，请检查", Toast.LENGTH_SHORT).show();
                        } else {
                            if (!firstStr.equals(secondStr)) {
                                Toast.makeText(mContext, "密码不一致，请检查", Toast.LENGTH_SHORT).show();
                            } else {
                                SPUtil.saveString(mContext, SPUtil.MOOD_PASSWORD, firstStr);
                                dialog.dismiss();
                            }
                        }

                    }
                });
        builder.show();
    }

    private void showDeterminePassword(final RecordDataBean dataBean) {
        if (mContext == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = View.inflate(mContext, R.layout.dialog_determine_password, null);
        final EditText et_determine = (EditText) view.findViewById(R.id.dialog_determine_password_first_et);
        builder.setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String determineStr = et_determine.getText().toString().trim();
                        if (TextUtils.isEmpty(determineStr)) {
                            Toast.makeText(mContext, "输入密码为空，请检查", Toast.LENGTH_SHORT).show();
                        } else {
                            String correctPassword = SPUtil.getString(mContext, SPUtil.MOOD_PASSWORD, "");
                            if (!correctPassword.equals(determineStr)) {
                                Toast.makeText(mContext, "密码错误", Toast.LENGTH_SHORT).show();
                            } else {
                                gotoContent(dataBean);
                                dialog.dismiss();
                            }
                        }
                    }
                });
        builder.show();
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
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
