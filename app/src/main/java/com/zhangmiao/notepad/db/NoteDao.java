package com.zhangmiao.notepad.db;

import android.util.Log;

import com.zhangmiao.notepad.application.BaseApplication;
import com.zhangmiao.notepad.bean.MoodDataBean;
import com.zhangmiao.notepad.bean.NoteDataBean;

import java.util.Date;
import java.util.List;

/**
 * Author: zhangmiao
 * Date: 2017/12/1
 */
public class NoteDao {

    //插入数据
    public static void insertMood(NoteDataBean noteDataBean) {
        BaseApplication.getDaoInstant().getNoteDataBeanDao().insert(noteDataBean);
    }

    //删除数据
    public static void deleteMood(long id) {
        BaseApplication.getDaoInstant().getNoteDataBeanDao().deleteByKey(id);
    }

    //更新数据
    public static void updateMood(NoteDataBean noteDataBean) {
        BaseApplication.getDaoInstant().getNoteDataBeanDao().update(noteDataBean);
    }

    /**
     * 查询所有数据
     *
     * @return
     */
    public static List<NoteDataBean> queryAll() {
        return BaseApplication.getDaoInstant().getNoteDataBeanDao().loadAll();
    }

    /**
     * 查询当天的数据
     *
     * @return
     */
    public static List<NoteDataBean> questTodayData() {
        Date date = new Date();
        Log.d("MoodDao", "date:" + new Date((date.getTime() / 1000 / 60 / 60 / 12)));
        long time = (date.getTime() / 1000 / 60 / 60 / 12);
        return BaseApplication.getDaoInstant().getNoteDataBeanDao().queryBuilder().where(MoodDataBeanDao.Properties.Date.ge(time)).list();
    }

    /**
     * 查询特定数量的数据
     *
     * @param num 查询数据的数量
     * @return
     */
    public static List<NoteDataBean> questDataLimit(int num) {
        return BaseApplication.getDaoInstant().getNoteDataBeanDao().queryBuilder().limit(num).list();
    }

}
