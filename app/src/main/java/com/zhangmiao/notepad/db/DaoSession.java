package com.zhangmiao.notepad.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.zhangmiao.notepad.bean.RecordDataBean;

import com.zhangmiao.notepad.db.RecordDataBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 *
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig recordDataBeanDaoConfig;

    private final RecordDataBeanDao recordDataBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        recordDataBeanDaoConfig = daoConfigMap.get(RecordDataBeanDao.class).clone();
        recordDataBeanDaoConfig.initIdentityScope(type);

        recordDataBeanDao = new RecordDataBeanDao(recordDataBeanDaoConfig, this);

        registerDao(RecordDataBean.class, recordDataBeanDao);
    }

    public void clear() {
        recordDataBeanDaoConfig.getIdentityScope().clear();
    }

    public RecordDataBeanDao getRecordDataBeanDao() {
        return recordDataBeanDao;
    }

}
