/** * */package com.hfv.hopgrower.screens;import android.app.Activity;import android.content.Context;import android.os.Bundle;import android.support.v4.app.ListFragment;import android.util.Log;import android.view.View;import android.widget.LinearLayout;import android.widget.ListView;import com.hfv.hopgrower.R;import com.hfv.hopgrower.adapter.MyHopsListAdapter;import com.hfv.hopgrower.dbadapter.MyHopsDatabaseAdapter;import com.hfv.hopgrower.sqlite.myhops.MHDaoSession;import com.hfv.hopgrower.sqlite.myhops.MyHops;import com.hfv.hopgrower.sqlite.myhops.MyHopsDao;import java.util.HashMap;import java.util.List;import de.greenrobot.dao.query.QueryBuilder;/** * @author Robert B. Andrews *         RBA Internet Services, Inc. Strasburg, Virginia *         Copyright 2014  All Rights Reserved *///import com.hfv.hopgrower.screens.DummyContent;public class GrowLogLeftMainFragment extends ListFragment {    MyHopsDatabaseAdapter mDbAdapter;    int recCount = 0;    Boolean dbExist = null;    ListView listView;    MyHopsListAdapter adapter;    LinearLayout llayout;    int listsize = 0;    Context mCtx;    MHDaoSession mhDaoSess;    boolean mDualPane;    int mCurCheckPosition = 0;    int currId = 0;    @SuppressWarnings("unused")    private static int selectedIndex = 0;    public static void setSelectedIndex(int ind) {        selectedIndex = ind;    }    private static final String STATE_ACTIVATED_POSITION = "activated_position";    private Callbacks mCallbacks = sDummyCallbacks;    private int mActivatedPosition = ListView.INVALID_POSITION;    public interface Callbacks {        void onItemSelected(String id);    }    private static Callbacks sDummyCallbacks = new Callbacks() {        @Override        public void onItemSelected(String id) {        }    };    public GrowLogLeftMainFragment() {    }    @Override    public void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        mCtx = getActivity().getApplicationContext();        //       listView = (ListView) getActivity().findViewById(R.id.list_view1);        mDbAdapter = new MyHopsDatabaseAdapter(mCtx);        mDbAdapter.open();        MyHopsDao mhDao = mDbAdapter.getMyHopsDaoSession();        recCount = mhDao.getMHRecCount();//        Log.d("Hopgrower","mhDao count is: " + recCount);          if (recCount == 0) {            //no myhops records, throw splash screen or toast        }        QueryBuilder<MyHops> qb = mhDao.queryBuilder();        List<MyHops> myHopList = qb.list();        mDbAdapter.closeDaoSession();        listsize = myHopList.size();        Log.d("HopGrower", "List size is: " + listsize);        String[] theIds = new String[100];        String[] theVarieties = new String[100];        String[] theLocations = new String[100];        for (int i = 0; i < listsize; i++) {            MyHops mhObj = myHopList.get(i);            Log.d("HopGrower", "Value of subscript i is: " + i);            theIds[i] = mhObj.getId().toString();            theVarieties[i] = mhObj.getVariety();            theLocations[i] = mhObj.getLocation();        }        adapter = new MyHopsListAdapter(getActivity().getBaseContext(), listsize, theIds, theVarieties, theLocations);        setListAdapter(adapter);    }    @Override    public void onViewCreated(View view, Bundle savedInstanceState) {        super.onViewCreated(view, savedInstanceState);        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {            setActivatedPosition(savedInstanceState                    .getInt(STATE_ACTIVATED_POSITION));        }        listView = this.getListView();        listView.setPadding(10, 10, 10, 10);        listView.setDivider(getResources().getDrawable((R.drawable.list_divider_hopcolor)));        listView.setHeaderDividersEnabled(true);        listView.setFooterDividersEnabled(true);        listView.setDividerHeight(3);        listView.setSelector(R.drawable.list_item_selector);        listView.setBackgroundResource(R.drawable.text_border);    }    @Override    public void onAttach(Activity activity) {        super.onAttach(activity);        if (!(activity instanceof Callbacks)) {            throw new IllegalStateException(                    "Activity must implement fragment's callbacks.");        }        mCallbacks = (Callbacks) activity;    }    @Override    public void onDetach() {        super.onDetach();        mCallbacks = sDummyCallbacks;    }    @Override    public void onListItemClick(ListView listView, View view, int position, long id) {        super.onListItemClick(listView, view, position, id);        @SuppressWarnings("unchecked")        HashMap<String, String> selectedHashMap = (HashMap<String, String>) listView.getAdapter().getItem(position);        String selectedMHId = selectedHashMap.get("mhID");        String selectedVariety = selectedHashMap.get("growvar");        Log.d("HopGrower", "selectedMHId is: " + selectedMHId);        Log.d("HopGrower", "selectedVariety is " + selectedVariety);        Log.d("HopGrower", "");        adapter.setSelectedPosition(position);        mCallbacks.onItemSelected(selectedMHId);    }    @Override    public void onSaveInstanceState(Bundle outState) {        super.onSaveInstanceState(outState);        if (mActivatedPosition != ListView.INVALID_POSITION) {            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);        }    }    public void setActivateOnItemClick(boolean activateOnItemClick) {        getListView().setChoiceMode(                activateOnItemClick ? ListView.CHOICE_MODE_SINGLE                        : ListView.CHOICE_MODE_NONE);    }    private void setActivatedPosition(int position) {        if (position == ListView.INVALID_POSITION) {            getListView().setItemChecked(mActivatedPosition, false);        } else {            getListView().setItemChecked(position, true);        }        mActivatedPosition = position;    }    public void onPause() {        super.onPause();    }}    