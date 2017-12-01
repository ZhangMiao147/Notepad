package com.zhangmiao.notepad.bean;

/**
 * 数据库使用GreenDao3.0
 * http://www.jianshu.com/p/4986100eff90
 */

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Author: zhangmiao
 * Date: 2017/10/17
 */
@Entity
public class NoteDataBean {
    @Id(autoincrement = true) //自增
    @Unique
    private long id;

    //时间
    @Unique
    @OrderBy
    private long date;

    //题目
    private String title;

    //内容
    private String content;

    @Generated(hash = 1698860274)
    public NoteDataBean(long id, long date, String title, String content) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.content = content;
    }

    @Generated(hash = 576205436)
    public NoteDataBean() {
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

    @Override
    public String toString() {
        return "RecordDataBean{" +
                "date='" + date + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

}
