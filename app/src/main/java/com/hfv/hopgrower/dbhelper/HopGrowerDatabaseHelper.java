package com.hfv.hopgrower.dbhelper;import android.content.Context;import android.database.sqlite.SQLiteDatabase;import android.util.Log;import com.hfv.hopgrower.dbadapter.HopGrowerDatabaseAdapter;import com.hfv.hopgrower.sqlite.DaoMaster;import com.hfv.hopgrower.sqlite.DaoMaster.DevOpenHelper;import com.hfv.hopgrower.sqlite.DaoSession;import com.hfv.hopgrower.sqlite.VarietiesDao;import java.io.IOException;//import com.hfv.hopgrower.ApplicationContextProvider;//import com.hfv.hopgrower.SharedVariables;//import com.hfv.hopgrower.sqlite.DaoMaster.OpenHelper;public class HopGrowerDatabaseHelper {//public class HopGrowerDatabaseHelper extends DevOpenHelper{	    public DaoSession daoSession;    public SQLiteDatabase var_db;    //	public OpenHelper var_database_helper_obj;    public DevOpenHelper var_database_helper_obj;    public DaoMaster daoMaster;    private static final String name = "variety";    private static final int dbase_version = 1;    private Context context;    private static HopGrowerDatabaseHelper singleton;    /**     * @param context     * @param name     * @param factory     */    public HopGrowerDatabaseHelper(Context context) {        super();        this.context = context;        // TODO Auto-generated constructor stub    }    /*        public HopGrowerDatabaseHelper getInstance(){                return singleton;        }    */    public void onCreate() {        singleton = this;        setupDatabase();    }    private void setupDatabase() {        Log.d("HopGrower", "Running setup database method");//		HopGrowerDatabaseAdapter eadDB = new HopGrowerDatabaseAdapter(singleton.context);        HopGrowerDatabaseAdapter eadDB = new HopGrowerDatabaseAdapter(context);        try {            eadDB.createDataBase();        } catch (IOException ioe) {            Log.d("HopGrower", "Failed setupDatabase " + ioe.toString());            throw new Error("Unable to create database");        }        var_database_helper_obj = new DevOpenHelper(context, "varieties", null);        var_db = var_database_helper_obj.getWritableDatabase();        if (var_db != null) {            Log.d("HopGrower", "The database value var_db returns true");        }        daoMaster = new DaoMaster(var_db);        daoSession = daoMaster.newSession();    }    public VarietiesDao getVarietiesDaoSession() {        VarietiesDao varietyDao = daoSession.getVarietiesDao();        return varietyDao;    }    //varietyDao.select();//	      clsCustomerDao customerDao =daoSession.getClsCustomerDao(); //	customerDao.insert(customerclassObject); //insert...//	customerDao.delete(customerclassObject); //delete...//	customerDao.update(customerclassObject); //update...//	 customerArrayList=customerDao.queryBuilder().where(Properties.Id.eq(1)).list(); //Query...        public void closeDaoSession() {        daoSession.clear();        var_db.close();        var_database_helper_obj.close();    }    /* (non-Javadoc)     * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)     */    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {        // TODO Auto-generated method stub    }}