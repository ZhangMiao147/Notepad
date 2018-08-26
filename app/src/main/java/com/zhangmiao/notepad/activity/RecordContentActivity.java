package com.zhangmiao.notepad.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhangmiao.notepad.R;
import com.zhangmiao.notepad.bean.DataBean;
import com.zhangmiao.notepad.bean.MoodContentBean;
import com.zhangmiao.notepad.bean.NoteContentBean;
import com.zhangmiao.notepad.bean.RecordDataBean;
import com.zhangmiao.notepad.db.RecordDao;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 笔记详情界面
 * Author: zhangmiao
 * Date: 2017/10/11
 */
public class RecordContentActivity extends Activity {

    private RecordDataBean recordDataBean;

    @BindView(R.id.activity_record_content_note_title_tv)
    TextView tv_noteTitle;

    @BindView(R.id.activity_record_content_article_tv)
    TextView tv_article;

    @BindView(R.id.activity_record_content_mood_layout_ll)
    LinearLayout ll_moodLayout;

    @BindView(R.id.activity_record_content_mood_weather_iv)
    ImageView iv_moodWeather;

    @BindView(R.id.activity_record_content_mood_mood_iv)
    ImageView iv_moodMood;

    @BindView(R.id.activity_record_content_mood_location_iv)
    TextView tv_moodLocation;

    @BindView(R.id.activity_record_content_year_tv)
    TextView tv_year;

    @BindView(R.id.activity_record_content_second_tv)
    TextView tv_second;

    private int mType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_content);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        recordDataBean = (RecordDataBean) intent.getSerializableExtra("data");
        if (recordDataBean == null) {
            Toast.makeText(this, "数据不正确！", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        mType = recordDataBean.getType();
        switch (mType) {
            case DataBean.DATA_TYPE_NOTE:
                tv_noteTitle.setVisibility(View.VISIBLE);
                ll_moodLayout.setVisibility(View.GONE);
                NoteContentBean noteContentBean = NoteContentBean.jsonToBean(recordDataBean.getContent());
                tv_noteTitle.setText(noteContentBean.getTitle());
                tv_article.setText(noteContentBean.getArticle());
                tv_article.setTextSize(noteContentBean.getFont());
                tv_noteTitle.setTextSize(noteContentBean.getFont());
                break;
            case DataBean.DATA_TYPE_MOOD:
                tv_noteTitle.setVisibility(View.GONE);
                ll_moodLayout.setVisibility(View.VISIBLE);
                MoodContentBean moodContentBean = MoodContentBean.jsonToBean(recordDataBean.getContent());
                tv_article.setText(moodContentBean.getArticle());
                tv_article.setTextSize(moodContentBean.getFont());
                switch (moodContentBean.getWeather()) {
                    case DataBean.WEATHER_FINE:
                        iv_moodWeather.setImageResource(R.drawable.fine);
                        break;
                    case DataBean.WEATHER_RAIN:
                        iv_moodWeather.setImageResource(R.drawable.rain);
                        break;
                    case DataBean.WEATHER_SHADE:
                        iv_moodWeather.setImageResource(R.drawable.shade);
                        break;
                    case DataBean.WEATHER_SNOW:
                        iv_moodWeather.setImageResource(R.drawable.snow);
                        break;
                    default:
                        break;
                }

                switch (moodContentBean.getMood()) {
                    case DataBean.MOOD_HAPPY:
                        iv_moodMood.setImageResource(R.drawable.happy);
                        break;
                    case DataBean.MOOD_UNHAPPY:
                        iv_moodMood.setImageResource(R.drawable.unhappy);
                        break;
                    default:
                        break;
                }
                String location = moodContentBean.getLocation();
                if (!TextUtils.isEmpty(location)) {
                    tv_moodLocation.setText(location);
                } else {
                    tv_moodLocation.setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }
        long createDate = recordDataBean.getCrateDate();
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy年MM月dd日");
        tv_year.setText(yearFormat.format(createDate));
        SimpleDateFormat secondFormat = new SimpleDateFormat("HH:mm:ss");
        tv_second.setText(secondFormat.format(createDate));
    }

    @OnClick(R.id.activity_record_content_toolbar_back_iv)
    public void back() {
        finish();
    }

    @OnClick(R.id.activity_record_content_toolbar_edit_iv)
    public void edit() {
        Intent intent = new Intent();
        switch (mType) {
            case DataBean.DATA_TYPE_NOTE:
                intent.setClass(RecordContentActivity.this, RecordNoteActivity.class);
                break;
            case DataBean.DATA_TYPE_MOOD:
                intent.setClass(RecordContentActivity.this, WriteMoodActivity.class);
                break;
            default:
                intent.setClass(RecordContentActivity.this, RecordNoteActivity.class);
                break;
        }
        intent.putExtra("edit", recordDataBean);
        startActivity(intent);
        finish();
    }


    @OnClick(R.id.activity_record_content_toolbar_share_iv)
    public void share() {
        Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.activity_record_content_toolbar_delete_iv)
    public void delete() {
        recordDataBean.setIsWastebasket(true);
        RecordDao.updateNote(recordDataBean);
        finish();
    }

}
