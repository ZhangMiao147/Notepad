package com.zhangmiao.notepad.db;

import android.util.Log;

import com.zhangmiao.notepad.application.BaseApplication;
import com.zhangmiao.notepad.bean.RecordDataBean;

import java.util.Date;
import java.util.List;

/**
 * Author: zhangmiao
 * Date: 2017/12/1
 */
public class RecordDao {

    private static final String TAG = RecordDao.class.getSimpleName();

    //插入数据
    public static void insertNote(RecordDataBean recordDataBean) {
        BaseApplication.getDaoInstant().getRecordDataBeanDao().insert(recordDataBean);
    }

    //删除数据
    public static void deleteNote(long id) {
        BaseApplication.getDaoInstant().getRecordDataBeanDao().deleteByKey(id + "");
    }

    //更新数据
    public static void updateNote(RecordDataBean recordDataBean) {
        BaseApplication.getDaoInstant().getRecordDataBeanDao().update(recordDataBean);
    }

    /**
     * 查询所有数据
     *
     * @return
     */
    public static List<RecordDataBean> queryAll() {
        return BaseApplication.getDaoInstant().getRecordDataBeanDao().loadAll();
    }

    /**
     * 查询当天的数据
     *
     * @return
     */
    public static List<RecordDataBean> queryTodayData() {
        Date date = new Date();
        Log.d(TAG, "date:" + new Date((date.getTime() / 1000 / 60 / 60 / 12)));
        long time = (date.getTime() / 1000 / 60 / 60 / 12);
        return BaseApplication.getDaoInstant().getRecordDataBeanDao().queryBuilder().where(RecordDataBeanDao.Properties.Date.ge(time)).list();
    }

    /**
     * 查询日期大于time的数据
     *
     * @return
     */
    public static List<RecordDataBean> queryDataByTime(long time) {
        return BaseApplication.getDaoInstant().getRecordDataBeanDao().queryBuilder().where(RecordDataBeanDao.Properties.Date.ge(time)).list();
    }

    /**
     * 查询特定数量的数据
     *
     * @param num 查询数据的数量
     * @return
     */
    public static List<RecordDataBean> queryDataLimit(int num) {
        return BaseApplication.getDaoInstant().getRecordDataBeanDao().queryBuilder().limit(num).list();
    }


    public static List<RecordDataBean> queryDataByType(int type) {
        return BaseApplication.getDaoInstant().getRecordDataBeanDao().queryBuilder().where(RecordDataBeanDao.Properties.Type.eq(type)).list();
    }

}
