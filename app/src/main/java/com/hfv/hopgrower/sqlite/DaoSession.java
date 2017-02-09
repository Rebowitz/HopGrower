package com.hfv.hopgrower.sqlite;import android.database.sqlite.SQLiteDatabase;import java.util.Map;import de.greenrobot.dao.AbstractDao;import de.greenrobot.dao.AbstractDaoSession;import de.greenrobot.dao.identityscope.IdentityScopeType;import de.greenrobot.dao.internal.DaoConfig;// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT./** * {@inheritDoc} * * @see de.greenrobot.dao.AbstractDaoSession */public class DaoSession extends AbstractDaoSession {    private final DaoConfig varietiesDaoConfig;    private final VarietiesDao varietiesDao;    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>            daoConfigMap) {        super(db);        varietiesDaoConfig = daoConfigMap.get(VarietiesDao.class).clone();        varietiesDaoConfig.initIdentityScope(type);        varietiesDao = new VarietiesDao(varietiesDaoConfig, this);        registerDao(Varieties.class, varietiesDao);    }    public void clear() {        varietiesDaoConfig.getIdentityScope().clear();    }    public VarietiesDao getVarietiesDao() {        return varietiesDao;    }}