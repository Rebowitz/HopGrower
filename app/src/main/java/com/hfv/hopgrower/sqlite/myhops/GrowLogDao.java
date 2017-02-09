package com.hfv.hopgrower.sqlite.myhops;import android.database.Cursor;import android.database.sqlite.SQLiteDatabase;import android.database.sqlite.SQLiteStatement;import de.greenrobot.dao.AbstractDao;import de.greenrobot.dao.Property;import de.greenrobot.dao.internal.DaoConfig;// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT./** * DAO for table GROW_LOG. */public class GrowLogDao extends AbstractDao<GrowLog, Long> {    public static final String TABLENAME = "GROW_LOG";    /**     * Properties of entity GrowLog.<br/>     * Can be used for QueryBuilder and for referencing column names.     */    public static class Properties {        public final static Property Id = new Property(0, Long.class, "id", true, "_id");        public final static Property MhID = new Property(1, long.class, "mhID", false, "MH_ID");        public final static Property LogDate = new Property(2, java.util.Date.class, "LogDate", false, "LOG_DATE");        public final static Property Category = new Property(3, String.class, "Category", false, "CATEGORY");        public final static Property Item = new Property(4, String.class, "Item", false, "ITEM");        public final static Property Description = new Property(5, String.class, "Description", false, "DESCRIPTION");    }    public GrowLogDao(DaoConfig config) {        super(config);    }    public GrowLogDao(DaoConfig config, MHDaoSession daoSession) {        super(config, daoSession);    }    /**     * Creates the underlying database table.     */    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {        String constraint = ifNotExists ? "IF NOT EXISTS " : "";        db.execSQL("CREATE TABLE " + constraint + "'GROW_LOG' (" + //                "'_id' INTEGER PRIMARY KEY ASC AUTOINCREMENT ," + // 0: id                "'MH_ID' INTEGER NOT NULL ," + // 1: mhID                "'LOG_DATE' INTEGER NOT NULL ," + // 2: LogDate                "'CATEGORY' TEXT," + // 3: Category                "'ITEM' TEXT," + // 4: Item                "'DESCRIPTION' TEXT);"); // 5: Description    }    /**     * Drops the underlying database table.     */    public static void dropTable(SQLiteDatabase db, boolean ifExists) {        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'GROW_LOG'";        db.execSQL(sql);    }    /**     * @inheritdoc     */    @Override    protected void bindValues(SQLiteStatement stmt, GrowLog entity) {        stmt.clearBindings();        Long id = entity.getId();        if (id != null) {            stmt.bindLong(1, id);        }        stmt.bindLong(2, entity.getMhID());        stmt.bindLong(3, entity.getLogDate().getTime());        String Category = entity.getCategory();        if (Category != null) {            stmt.bindString(4, Category);        }        String Item = entity.getItem();        if (Item != null) {            stmt.bindString(5, Item);        }        String Description = entity.getDescription();        if (Description != null) {            stmt.bindString(6, Description);        }    }    /**     * @inheritdoc     */    @Override    public Long readKey(Cursor cursor, int offset) {        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);    }    /**     * @inheritdoc     */    @Override    public GrowLog readEntity(Cursor cursor, int offset) {        GrowLog entity = new GrowLog( //                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id                cursor.getLong(offset + 1), // mhID                new java.util.Date(cursor.getLong(offset + 2)), // LogDate                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // Category                cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // Item                cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // Description        );        return entity;    }    /**     * @inheritdoc     */    @Override    public void readEntity(Cursor cursor, GrowLog entity, int offset) {        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));        entity.setMhID(cursor.getLong(offset + 1));        entity.setLogDate(new java.util.Date(cursor.getLong(offset + 2)));        entity.setCategory(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));        entity.setItem(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));        entity.setDescription(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));    }    /**     * @inheritdoc     */    @Override    protected Long updateKeyAfterInsert(GrowLog entity, long rowId) {        entity.setId(rowId);        return rowId;    }    /**     * @inheritdoc     */    @Override    public Long getKey(GrowLog entity) {        if (entity != null) {            return entity.getId();        } else {            return null;        }    }    /**     * @inheritdoc     */    @Override    protected boolean isEntityUpdateable() {        return true;    }    public Cursor getGLRecords() {        Cursor cursor = db.rawQuery("Select * from GROW_LOG order by _id ASC", null);        return cursor;    }    public int getGLRecCount(long mhID) {        String theId = Long.toString(mhID);        Cursor cursor = db.rawQuery("Select count(*) from GROW_LOG where MH_ID = ?", new String[]{theId});        cursor.moveToFirst();        int recs = cursor.getInt(0);        return recs;    }}