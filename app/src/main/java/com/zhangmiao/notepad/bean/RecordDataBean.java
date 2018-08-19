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

    @Id
    private String id;

    //时间
    @Unique
    @OrderBy
    private long crateDate;

    //内容
    private String content;

    //记录类型，"mood"--心情,"note"--笔记
    private int type;

    /**
     * 更新时间
     */
    private long updateDate;

    private boolean isWastebasket;

    @Generated(hash = 1278603916)
    public RecordDataBean(String id, long crateDate, String content, int type,
            long updateDate, boolean isWastebasket) {
        this.id = id;
        this.crateDate = crateDate;
        this.content = content;
        this.type = type;
        this.updateDate = updateDate;
        this.isWastebasket = isWastebasket;
    }

    @Generated(hash = 1002308030)
    public RecordDataBean() {
    }

    @Override
    public String toString() {
        return "RecordDataBean{" +
                "id='" + id + '\'' +
                ", crateDate=" + crateDate +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", updateDate=" + updateDate +
                ", isWastebasket=" + isWastebasket +
                '}';
    }

    public boolean getIsWastebasket() {
        return this.isWastebasket;
    }

    public void setIsWastebasket(boolean isWastebasket) {
        this.isWastebasket = isWastebasket;
    }

    public long getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCrateDate() {
        return this.crateDate;
    }

    public void setCrateDate(long crateDate) {
        this.crateDate = crateDate;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
