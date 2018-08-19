package com.zhangmiao.notepad.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.zhangmiao.notepad.R;
import com.zhangmiao.notepad.bean.DataBean;
import com.zhangmiao.notepad.bean.MoodContentBean;
import com.zhangmiao.notepad.bean.RecordDataBean;
import com.zhangmiao.notepad.db.RecordDao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.security.auth.DestroyFailedException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 写心情界面
 * Author: zhangmiao
 * Date: 2017/9/29
 */
public class WriteMoodActivity extends Activity {

    private static final String TAG = WriteMoodActivity.class.getSimpleName();

    private static final int DATA_WEATHER = 1;

    @BindView(R.id.activity_write_mood_content_text)
    EditText mContentEditText;

    private RecordDataBean bean;

    private boolean isSave;

    private int mMoodWeather;
    private int mMoodType;
    private boolean mMoodLock;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_mood);
        ButterKnife.bind(this);
        isSave = false;
        bean = new RecordDataBean();
        mMoodWeather = DataBean.WEATHER_FINE;
        mMoodType = DataBean.MOOD_HAPPY;
    }

    @OnClick(R.id.activity_write_mood_toolbar_back_iv)
    public void back() {
        if (isSave == false) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("是否不保存返回？")
                    .setNegativeButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setPositiveButton("否", null);
            builder.show();
        } else {
            finish();
        }
    }

    @OnClick(R.id.activity_write_mood_toolbar_save_iv)
    public void save() {
        isSave = true;
        String content = mContentEditText.getText().toString();
        if (content.isEmpty()) {
            bean.setContent("无内容");
        } else {
            bean.setContent(content);
        }
        bean.setType(DataBean.DATA_TYPE_MOOD);

        MoodContentBean moodContentBean = new MoodContentBean();
        moodContentBean.setWeather(DataBean.WEATHER_FINE);
        moodContentBean.setMood(DataBean.MOOD_HAPPY);
        moodContentBean.setIslock(false);
        moodContentBean.setArticle(content);

        bean.setContent(moodContentBean.toJson());
        long time = (new Date()).getTime();
        bean.setUpdateDate(time);
        bean.setCrateDate(time);
        bean.setId(UUID.randomUUID().toString());
        Log.d(TAG, "bean:" + bean);
        RecordDao.insertNote(bean);
        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.activity_write_mood_toolbar_undo_iv)
    public void undo() {

    }

    @OnClick(R.id.activity_write_mood_toolbar_redo_iv)
    public void redo() {

    }

    @OnClick(R.id.activity_write_mood_toolbar_font_iv)
    public void font() {
        if (mContentEditText.getVisibility() == View.VISIBLE) {
            Toast.makeText(this, "当前就是文字输入模式", Toast.LENGTH_SHORT).show();
        } else {
            mContentEditText.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.activity_write_mood_toolbar_camera_iv)
    public void camera() {

    }

    @OnClick(R.id.activity_write_mood_date_iv)
    public void setDate() {

    }

    @OnClick(R.id.activity_write_mood_location_iv)
    public void setLocation() {

    }

    @OnClick(R.id.activity_write_mood_weather_iv)
    public void setWeather() {
        String[] weathers = {"晴", "阴", "雨", "雪"};
        showDialogList(DATA_WEATHER, "天气", weathers);
    }

    @OnClick(R.id.activity_write_mood_mood_iv)
    public void setMood() {

    }

    @OnClick(R.id.activity_write_mood_lock_iv)
    public void clock() {

    }

    private void showDialogList(final int dataType, String title, String[] dataArray) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setItems(dataArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Log.d(TAG, "setItems onClick which:" + which);
                switch (dataType) {
                    case DATA_WEATHER:
                        switch (which) {
                            case 0:
                                mMoodWeather = DataBean.WEATHER_FINE;

                                break;
                            case 1:
                                mMoodWeather = DataBean.WEATHER_SHADE;
                                break;
                            case 2:
                                mMoodWeather = DataBean.WEATHER_RAIN;
                                break;
                            case 3:
                                mMoodWeather = DataBean.WEATHER_SNOW;
                                break;
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
            }
        });
        builder.create().show();
    }

}
