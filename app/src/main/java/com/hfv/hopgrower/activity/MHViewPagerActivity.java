/** * */package com.hfv.hopgrower.activity;import android.content.Context;import android.content.Intent;import android.os.Bundle;import android.support.v4.app.Fragment;import android.support.v4.app.FragmentManager;import android.support.v4.app.FragmentTransaction;import android.support.v7.app.ActionBarActivity;import android.util.Log;import android.view.View;import android.view.View.OnClickListener;import android.widget.Button;import com.hfv.hopgrower.R;import com.hfv.hopgrower.adapter.MyHopsAdapter;import com.hfv.hopgrower.adapter.MyHopsAddAdapter;import com.hfv.hopgrower.dbadapter.MyHopsDatabaseAdapter;import com.hfv.hopgrower.screens.MyHopsNewRecordFragment;import com.hfv.hopgrower.screens.MyHopsNuRecDialogFragment;import com.hfv.hopgrower.screens.MyHopsPanelFragment;import com.hfv.hopgrower.screens.MyHopsRightFragment;import com.hfv.hopgrower.special.MyHopsViewPager;import com.hfv.hopgrower.sqlite.myhops.GrowLogDao;import com.hfv.hopgrower.sqlite.myhops.MyHopsDao;import com.hfv.hopgrower.sqlite.myhops.SchedulerDao;import java.util.ArrayList;import java.util.List;/** * @author Robert B. Andrews *         RBA Internet Services, Inc. Strasburg, Virginia *         Copyright 2014  All Rights Reserved */public class MHViewPagerActivity extends ActionBarActivity implements MyHopsPanelFragment.Callbacks_mhpanelFrag {    MyHopsDatabaseAdapter mDbAdapter;    MyHopsViewPager mPager;    MyHopsAdapter mPagerAdapter;    MyHopsAddAdapter addPageAdapter;    List<Fragment> mhPanels;    int recCount = 0;    Context mCtx;    MyHopsPanelFragment tempFrag;    FragmentTransaction myhopsFragmentTransaction;    MyHopsRightFragment fragment;//	MyHopsRightMainFragment myRMFragment;    Button button2;    @Override    public void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.my_hops);        getSupportActionBar().setTitle(R.string.app_myhops);        Intent intent = getIntent();        String mhRecID = "";        if (intent.getExtras() != null) {            mhRecID = intent.getStringExtra("mhRecID");        }        //create our viewpager and fragment layout//        View newMHRec = this.findViewById(android.R.id.content);        mCtx = getApplicationContext();        mDbAdapter = new MyHopsDatabaseAdapter(mCtx);        mDbAdapter.open();        MyHopsDao mhDao = mDbAdapter.getMyHopsDaoSession();        recCount = mhDao.getMHRecCount();        Log.d("HopGrower", "mhDao count is: " + recCount);        mDbAdapter.closeDaoSession();        if (recCount == 0) {            MyHopsNewRecordFragment apFrag = new MyHopsNewRecordFragment();            List<MyHopsNewRecordFragment> tmpFrag = new ArrayList<MyHopsNewRecordFragment>();            mPager = (MyHopsViewPager) findViewById(R.id.viewpager);            Log.d("HopGrower", "No records found, throwing add screen");            addPageAdapter = new MyHopsAddAdapter(getSupportFragmentManager(), tmpFrag);            mPager.setAdapter(addPageAdapter);        } else {//          View newMHRec = this.findViewById(android.R.id.content);  //        	Log.d("HopGrower","Setting view for ViewPager");            mPager = (MyHopsViewPager) findViewById(R.id.viewpager);            Log.d("HopGrower", "Generating fragments");            mhPanels = getFragments();            mPager.setOffscreenPageLimit(mhPanels.size() - 1);            Log.d("HopGrower", "Loading adapter");            mPagerAdapter = new MyHopsAdapter(getSupportFragmentManager(), mhPanels);            Log.d("HopGrower", "mhPanels size is: " + mPagerAdapter.getCount());            int ind = mPagerAdapter.getCount();            Log.d("HopGrower", "Assigning adapter to the ViewPager");            mPager.setAdapter(mPagerAdapter);            mPager.setPadding(10, 10, 10, 10);            mPager.setPageMargin(10);            mPager.setBackgroundResource(R.drawable.text_border2);            if (mhRecID.equals("NewRec")) {                mPager.setCurrentItem(ind);            }        }//		button2 = (Button) findViewById(R.id.saveglentry);//		button2.setOnClickListener(listener2);        final Button button = (Button) findViewById(R.id.addEntry);        button.setOnClickListener(new OnClickListener() {            @Override            public void onClick(View v) {                FragmentManager fm = getSupportFragmentManager();                MyHopsNuRecDialogFragment apFrag = MyHopsNuRecDialogFragment.newInstance(mCtx, "anything");                apFrag.show(fm, "dialog");            }        });    }//end on create    @Override    public void onBackPressed() {        if (mPager.getCurrentItem() == 0) {            // If the user is currently looking at the first step, allow the system to handle the            // Back button. This calls finish() on this activity and pops the back stack.            super.onBackPressed();        } else {            // Otherwise, select the previous step.            mPager.setCurrentItem(mPager.getCurrentItem() - 1);        }    }    private List<Fragment> getFragments() {        Log.d("HopGrower", "Entered fragment creation method");        List<Fragment> fragList = new ArrayList<Fragment>();        for (int cnt = 1; cnt < recCount + 1; cnt++) {            Log.d("HopGrower", "Fragments processing #" + cnt);            fragList.add(MyHopsPanelFragment.newInstance(cnt));        }        Log.d("HopGrower", "FragList size is: " + fragList.size());        return fragList;    }    /* (non-Javadoc)     * @see com.hfv.hopgrower.screens.MyHopsPanelFragment.Callbacks_mhpanelFrag#deleteMyHopsPanel(android.os.Bundle)     */    @Override    public void deleteMyHopsPanel(Long recordId) {        Log.d("HopGrower", "Entered deleteMyHopsPanel callback");        mDbAdapter.open();        MyHopsDao mhDao = mDbAdapter.getMyHopsDaoSession();        mhDao.deleteByKey(recordId);        mDbAdapter.closeDaoSession();        mDbAdapter.open();        SchedulerDao schedDao = mDbAdapter.getSchedulerDaoSession();        schedDao.deleteByKey(recordId);        mDbAdapter.closeDaoSession();        mDbAdapter.open();        GrowLogDao glDao = mDbAdapter.getGrowLogDaoSession();        glDao.deleteByKey(recordId);        mDbAdapter.closeDaoSession();        //callback to activity to delete this panel        int position = mPager.getCurrentItem();        Log.d("HopGrower", "Position of current item is: " + position);        mhPanels.remove(position);        Log.d("HopGrower", "Loading adapter");        mPagerAdapter = new MyHopsAdapter(getSupportFragmentManager(), mhPanels);        Log.d("HopGrower", "Assigning adapter to the ViewPager");        mPager.setAdapter(mPagerAdapter);        Log.d("HopGrower", "Removed panel");        mPagerAdapter.notifyDataSetChanged();        //need to delete db records scheduler and growlog    }}