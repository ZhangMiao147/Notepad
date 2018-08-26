package com.zhangmiao.notepad.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.zhangmiao.notepad.R;
import com.zhangmiao.notepad.bean.DataBean;
import com.zhangmiao.notepad.bean.NoteContentBean;
import com.zhangmiao.notepad.bean.RecordDataBean;
import com.zhangmiao.notepad.db.RecordDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public static final String TAG = RecordNoteActivity.class.getSimpleName();


    @BindView(R.id.record_note_title)
    EditText mTitleEditText;

    @BindView(R.id.record_note_content_text)
    EditText mContentEditText;

    private RecordDataBean dataBean;
    private NoteContentBean contentBean;

    private boolean isSave; //是否已保存
    private boolean isUpdate; //是否是更新
    private int mFontSize;


    private List<EditData> mEditList;
    private List<EditData> mEditDeleteList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_note);
        ButterKnife.bind(this);
        isSave = false;
        Intent intent = getIntent();
        RecordDataBean recordDataBean = (RecordDataBean) intent.getSerializableExtra("edit");
        if (recordDataBean == null) {
            isUpdate = false;
            dataBean = new RecordDataBean();
            contentBean = new NoteContentBean();
            mFontSize = DataBean.FONT_MIDDLE;
            mContentEditText.setTextSize(mFontSize);
            mTitleEditText.setTextSize(mFontSize);
        } else {
            isUpdate = true;
            dataBean = recordDataBean;
            contentBean = NoteContentBean.jsonToBean(dataBean.getContent());
            mFontSize = contentBean.getFont();
            mContentEditText.setText(contentBean.getArticle());
            mContentEditText.setTextSize(mFontSize);
            mTitleEditText.setText(contentBean.getTitle());
            mTitleEditText.setTextSize(mFontSize);
        }

        mEditList = new ArrayList<>();
        mEditDeleteList = new ArrayList<>();
        mTitleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    EditData editData = new EditData();
                    editData.setType(EditData.EDIT_TYPE_TITLE);
                    editData.setContent(s.toString());
                    mEditList.add(editData);
                }
            }
        });
        mContentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    EditData editData = new EditData();
                    editData.setType(EditData.EDIT_TYPE_CONTENT);
                    editData.setContent(s.toString());
                    mEditList.add(editData);
                }
            }
        });
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
        contentBean.setFont(mFontSize);

        long time = (new Date()).getTime();
        dataBean.setCrateDate(time);
        dataBean.setUpdateDate(time);
        dataBean.setType(DataBean.DATA_TYPE_NOTE);
        dataBean.setContent(contentBean.toJson());
        dataBean.setIsWastebasket(false);
        if (isUpdate) {
            RecordDao.updateNote(dataBean);
        } else {
            dataBean.setId(UUID.randomUUID().toString());
            RecordDao.insertNote(dataBean);
        }
        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.record_note_toolbar_undo)
    public void undo() {
        if (mEditList != null && !mEditList.isEmpty()) {
            EditData editData = mEditList.get(mEditList.size() - 1);
            String type = editData.getType();
            String content = editData.getContent();
            if (EditData.EDIT_TYPE_TITLE.equals(type)) {
                String titleStr = mTitleEditText.getText().toString();
                int index = titleStr.lastIndexOf(content);
                mTitleEditText.setText(titleStr.substring(0, index));
            } else {
                String contentStr = mContentEditText.getText().toString();
                int index = contentStr.lastIndexOf(content);
                mContentEditText.setText(contentStr.substring(0, index));
            }
            mEditList.remove(mEditList.size() - 1);
            mEditDeleteList.add(editData);
        }
    }

    @OnClick(R.id.record_note_toolbar_redo)
    public void redo() {
        if (mEditDeleteList != null && !mEditDeleteList.isEmpty()) {
            EditData editData = mEditDeleteList.get(mEditDeleteList.size() - 1);
            String type = editData.getType();
            String content = editData.getContent();
            if (EditData.EDIT_TYPE_TITLE.equals(type)) {
                mTitleEditText.append(content);
            } else {
                mContentEditText.append(content);
            }
            mEditDeleteList.remove(mEditList.size() - 1);
        }
    }


    @OnClick(R.id.record_note_toolbar_font)
    public void font() {
        switch (mFontSize) {
            case DataBean.FONT_SMILE:
                mFontSize = DataBean.FONT_MIDDLE;
                break;
            case DataBean.FONT_MIDDLE:
                mFontSize = DataBean.FONT_BIG;
                break;
            case DataBean.FONT_BIG:
                mFontSize = DataBean.FONT_SMILE;
                break;
            default:
                break;
        }
        mTitleEditText.setTextSize(mFontSize);
        mContentEditText.setTextSize(mFontSize);
    }

    @OnClick(R.id.record_note_toolbar_camera)
    public void camera() {
    }

    public class EditData {
        public static final String EDIT_TYPE_TITLE = "title";
        public static final String EDIT_TYPE_CONTENT = "content";

        private String type;
        private String content;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return "EditData{" +
                    "type='" + type + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }

}
