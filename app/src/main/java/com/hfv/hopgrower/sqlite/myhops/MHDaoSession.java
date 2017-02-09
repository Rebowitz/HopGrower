package com.hfv.hopgrower.sqlite.myhops;import android.database.sqlite.SQLiteDatabase;import java.util.Map;import de.greenrobot.dao.AbstractDao;import de.greenrobot.dao.AbstractDaoSession;import de.greenrobot.dao.identityscope.IdentityScopeType;import de.greenrobot.dao.internal.DaoConfig;// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT./** * {@inheritDoc} * * @see de.greenrobot.dao.AbstractDaoSession */public class MHDaoSession extends AbstractDaoSession {    private final DaoConfig myHopsDaoConfig;    private final DaoConfig growLogDaoConfig;    private final DaoConfig schedulerDaoConfig;    private final DaoConfig notesDaoConfig;    private final MyHopsDao myHopsDao;    private final GrowLogDao growLogDao;    private final SchedulerDao schedulerDao;    private final NotesDao notesDao;    public MHDaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>            daoConfigMap) {        super(db);        myHopsDaoConfig = daoConfigMap.get(MyHopsDao.class).clone();        myHopsDaoConfig.initIdentityScope(type);        growLogDaoConfig = daoConfigMap.get(GrowLogDao.class).clone();        growLogDaoConfig.initIdentityScope(type);        schedulerDaoConfig = daoConfigMap.get(SchedulerDao.class).clone();        schedulerDaoConfig.initIdentityScope(type);        notesDaoConfig = daoConfigMap.get(NotesDao.class).clone();        notesDaoConfig.initIdentityScope(type);        myHopsDao = new MyHopsDao(myHopsDaoConfig, this);        growLogDao = new GrowLogDao(growLogDaoConfig, this);        schedulerDao = new SchedulerDao(schedulerDaoConfig, this);        notesDao = new NotesDao(notesDaoConfig, this);        registerDao(MyHops.class, myHopsDao);        registerDao(GrowLog.class, growLogDao);        registerDao(Scheduler.class, schedulerDao);        registerDao(Notes.class, notesDao);    }    public void clear() {        myHopsDaoConfig.getIdentityScope().clear();        growLogDaoConfig.getIdentityScope().clear();        schedulerDaoConfig.getIdentityScope().clear();        notesDaoConfig.getIdentityScope().clear();    }    public MyHopsDao getMyHopsDao() {        return myHopsDao;    }    public GrowLogDao getGrowLogDao() {        return growLogDao;    }    public SchedulerDao getSchedulerDao() {        return schedulerDao;    }    public NotesDao getNotesDao() {        return notesDao;    }}