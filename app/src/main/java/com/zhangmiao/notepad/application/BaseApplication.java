package com.zhangmiao.notepad.application;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.zhangmiao.notepad.db.DaoMaster;
import com.zhangmiao.notepad.db.DaoSession;

/**
 * Author: zhangmiao
 * Date: 2017/12/1
 */
public class BaseApplication extends Application {

    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        //配置数据库
        setupDatabase();
    }

    /**
     * 配置数据库
     */
    private void setupDatabase() {
        //创建数据库notepad.db
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,
                "notepad.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }

}
