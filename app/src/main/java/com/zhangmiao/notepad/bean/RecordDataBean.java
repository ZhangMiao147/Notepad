package com.zhangmiao.notepad.bean;

/**
 * Author: zhangmiao
 * Date: 2017/10/8
 */
public class RecordDataBean {

    public static int TYPE_MOOD = 10001;
    public static int TYPE_NOTE = 10002;

    //时间
    private String date;

    //题目
    private String title;

    //内容
    private String content;

    //是否加锁
    private boolean isLock;

    //记录类型，"mood"--心情,"note"--笔记
    private int type;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    @Override
    public String toString() {
        return "RecordDataBean{" +
                "date='" + date + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", isLock=" + isLock +
                ", type=" + type +
                '}';
    }
}
