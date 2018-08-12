package com.zhangmiao.notepad.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.Toast;

import com.zhangmiao.notepad.R;
import com.zhangmiao.notepad.bean.NoteContentBean;
import com.zhangmiao.notepad.bean.RecordDataBean;
import com.zhangmiao.notepad.db.RecordDao;

import java.util.Date;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 记笔记界面
 * Author: zhangmiao
 * Date: 2017/9/29
 */
public class RecordNoteActivity extends Activity {

    @BindView(R.id.record_note_title)
    EditText mTitleEditText;

    @BindView(R.id.record_note_content_text)
    EditText mContentEditText;

    RecordDataBean dataBean;
    NoteContentBean contentBean;

    boolean isSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_note);
        ButterKnife.bind(this);
        isSave = false;
        dataBean = new RecordDataBean();
        contentBean = new NoteContentBean();
    }

    @OnClick(R.id.record_note_toolbar_back)
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

    @OnClick(R.id.record_note_toolbar_save)
    public void save() {
        isSave = true;
        String title = mTitleEditText.getText().toString();
        if (title.isEmpty()) {
            contentBean.setTitle("无标题");
        } else {
            contentBean.setTitle(title);
        }
        String content = mContentEditText.getText().toString();
        if (content.isEmpty()) {
            contentBean.setArticle("无内容");
        } else {
            contentBean.setArticle(content);
        }
        dataBean.setType(RecordDataBean.TYPE_NOTE);
        dataBean.setCrateDate((new Date()).getTime());
        dataBean.setId(UUID.randomUUID().toString());
        dataBean.setContent(contentBean.toJson());
        RecordDao.insertNote(dataBean);
        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.record_note_toolbar_redo)
    public void redo() {

    }

    @OnClick(R.id.record_note_toolbar_font)
    public void font() {
    }

    @OnClick(R.id.record_note_toolbar_camera)
    public void camera() {
    }

}
