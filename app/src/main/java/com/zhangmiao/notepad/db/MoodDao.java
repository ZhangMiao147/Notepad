package com.zhangmiao.notepad.db;

import android.util.Log;

import com.zhangmiao.notepad.application.BaseApplication;
import com.zhangmiao.notepad.bean.MoodDataBean;

import java.util.Date;
import java.util.List;

/**
 * Author: zhangmiao
 * Date: 2017/12/1
 */
public class MoodDao {
    //插入数据
    public static void insertMood(MoodDataBean moodDataBean) {
        BaseApplication.getDaoInstant().getMoodDataBeanDao().insert(moodDataBean);
    }

    //删除数据
    public static void deleteMood(long id) {
        BaseApplication.getDaoInstant().getMoodDataBeanDao().deleteByKey(id);
    }

    //更新数据
    public static void updateMood(MoodDataBean moodDataBean) {
        BaseApplication.getDaoInstant().getMoodDataBeanDao().update(moodDataBean);
    }

    /**
     * 查询所有数据
     *
     * @return
     */
    public static List<MoodDataBean> queryAll() {
        return BaseApplication.getDaoInstant().getMoodDataBeanDao().loadAll();
    }

    /**
     * 查询当天的数据
     *
     * @return
     */
    public static List<MoodDataBean> questTodayData() {
        Date date = new Date();
        Log.d("MoodDao", "date:" + new Date((date.getTime() / 1000 / 60 / 60 / 12)));
        long time = (date.getTime() / 1000 / 60 / 60 / 12);
        return BaseApplication.getDaoInstant().getMoodDataBeanDao().queryBuilder().where(MoodDataBeanDao.Properties.Date.ge(time)).list();
    }

    /**
     * 查询特定数量的数据
     *
     * @param num 查询数据的数量
     * @return
     */
    public static List<MoodDataBean> questDataLimit(int num) {
        return BaseApplication.getDaoInstant().getMoodDataBeanDao().queryBuilder().limit(num).list();
    }


}
