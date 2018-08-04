package com.zhangmiao.notepad.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * 笔记心情的数据库Bean
 * Author: zhangmiao
 * Date: 2017/10/8
 */
@Entity
public class RecordDataBean implements Serializable {

    public static int TYPE_MAIN = 10000;
    public static int TYPE_MOOD = 10001;
    public static int TYPE_NOTE = 10002;

    @Id
    private String id;

    //时间
    @Unique
    @OrderBy
    private long date;

    //题目
    private String title;

    //内容
    private String content;

    //是否加锁 mood专用
    private boolean isLock;

    //记录类型，"mood"--心情,"note"--笔记
    private int type;

    @Generated(hash = 217281634)
    public RecordDataBean(int TYPE_MAIN, int TYPE_MOOD, int TYPE_NOTE, String id, long date,
                          String title, String content, boolean isLock, int type) {
        this.TYPE_MAIN = TYPE_MAIN;
        this.TYPE_MOOD = TYPE_MOOD;
        this.TYPE_NOTE = TYPE_NOTE;
        this.id = id;
        this.date = date;
        this.title = title;
        this.content = content;
        this.isLock = isLock;
        this.type = type;
    }

    @Generated(hash = 1002308030)
    public RecordDataBean() {
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getLock() {
        return isLock;
    }

    public void setLock(boolean lock) {
        isLock = lock;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean getIsLock() {
        return this.isLock;
    }

    public void setIsLock(boolean isLock) {
        this.isLock = isLock;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTYPE_NOTE() {
        return this.TYPE_NOTE;
    }

    public void setTYPE_NOTE(int TYPE_NOTE) {
        this.TYPE_NOTE = TYPE_NOTE;
    }

    public int getTYPE_MOOD() {
        return this.TYPE_MOOD;
    }

    public void setTYPE_MOOD(int TYPE_MOOD) {
        this.TYPE_MOOD = TYPE_MOOD;
    }

    public int getTYPE_MAIN() {
        return this.TYPE_MAIN;
    }

    public void setTYPE_MAIN(int TYPE_MAIN) {
        this.TYPE_MAIN = TYPE_MAIN;
    }

    @Override
    public String toString() {
        return "RecordDataBean{" +
                "id=" + id +
                ", date=" + date +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", isLock=" + isLock +
                ", type=" + type +
                '}';
    }
}
