package com.zhangmiao.notepad.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.zhangmiao.notepad.R;
import com.zhangmiao.notepad.bean.RecordDataBean;
import com.zhangmiao.notepad.db.RecordDao;

import java.util.Date;
import java.util.UUID;

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

    @BindView(R.id.write_mood_title)
    EditText mTitleEditText;

    @BindView(R.id.write_mood_content_text)
    EditText mContentEditText;

    @BindView(R.id.write_mood_content_image)
    ImageView mContextImage;

    RecordDataBean bean;

    boolean isSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_mood);
        ButterKnife.bind(this);
        isSave = false;
        bean = new RecordDataBean();
    }

    @OnClick(R.id.write_mood_toolbar_back)
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

    @OnClick(R.id.write_mood_toolbar_save)
    public void save() {
        isSave = true;
        String title = mTitleEditText.getText().toString();
        if (title.isEmpty()) {
            bean.setTitle("无标题");
        } else {
            bean.setTitle(title);
        }
        String content = mContentEditText.getText().toString();
        if (content.isEmpty()) {
            bean.setContent("无内容");
        } else {
            bean.setContent(content);
        }
        bean.setType(RecordDataBean.TYPE_MOOD);
        bean.setDate((new Date()).getTime());
        bean.setLock(false);
        bean.setId(UUID.randomUUID().toString());
        Log.d(TAG, "bean:" + bean);
        RecordDao.insertNote(bean);
        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.write_mood_toolbar_redo)
    public void redo() {

    }

    @OnClick(R.id.write_mood_toolbar_font)
    public void font() {
        if (mContentEditText.getVisibility() == View.VISIBLE) {
            Toast.makeText(this, "当前就是文字输入模式", Toast.LENGTH_SHORT).show();
        } else {
            if (mContextImage.getVisibility() == View.VISIBLE) {
                mContextImage.setVisibility(View.GONE);
            }
            mContentEditText.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.write_mood_toolbar_camera)
    public void camera() {
        if (mContextImage.getVisibility() == View.VISIBLE) {
            Toast.makeText(this, "当前就是图片输入模式", Toast.LENGTH_SHORT).show();
        } else {
            if (mContentEditText.getVisibility() == View.VISIBLE) {
                mContentEditText.setVisibility(View.GONE);
            }
            mContextImage.setVisibility(View.VISIBLE);
        }
    }

}
