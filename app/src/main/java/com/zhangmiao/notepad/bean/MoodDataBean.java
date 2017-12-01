package com.zhangmiao.notepad.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Author: zhangmiao
 * Date: 2017/10/17
 */
//数据库 参考 http://mp.weixin.qq.com/s?__biz=MzAxMTI4MTkwNQ==&mid=2650821932&idx=1&sn=d26c09af7cbbfb1b0a95517bd78cc784&chksm=80b781b2b7c008a4a8dab45756e4d433b1c56e1c61762f98ee3b8a2b89a00756f82d6bb4d6b6&scene=0#rd
@Entity
public class MoodDataBean {

    @Id(autoincrement = true)
    @Unique
    private long id;

    //时间
    //时间
    @Unique
    @OrderBy
    private long date;

    //题目
    private String title;

    //内容
    private String content;

    //是否加锁
    private boolean isLock;


    @Generated(hash = 153925751)
    public MoodDataBean(long id, long date, String title, String content, boolean isLock) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.content = content;
        this.isLock = isLock;
    }

    @Generated(hash = 906135138)
    public MoodDataBean() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "RecordDataBean{" +
                "date='" + date + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", isLock=" + isLock +
                '}';
    }

    public boolean getIsLock() {
        return this.isLock;
    }

    public void setIsLock(boolean isLock) {
        this.isLock = isLock;
    }

}
