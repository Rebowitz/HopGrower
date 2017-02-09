package com.hfv.hopgrower.activity;/* * Copyright (C) 2008 Google Inc. *  * Licensed under the Apache License, Version 2.0 (the "License"); you may not * use this file except in compliance with the License. You may obtain a copy of * the License at *  * http://www.apache.org/licenses/LICENSE-2.0 *  * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the * License for the specific language governing permissions and limitations under * the License. *//** * @author Robert B. Andrews * RBA Internet Services, Inc. Strasburg, Virginia * Copyright 2014  All Rights Reserved */import android.content.Context;import android.content.Intent;import android.os.Bundle;import android.support.v7.app.ActionBarActivity;import android.util.Log;import android.view.ContextMenu;import android.view.ContextMenu.ContextMenuInfo;import android.view.Menu;import android.view.MenuItem;import android.view.View;import android.widget.AdapterView;import android.widget.AdapterView.AdapterContextMenuInfo;import android.widget.Button;import android.widget.ListView;import android.widget.TextView;import com.hfv.hopgrower.R;import com.hfv.hopgrower.adapter.NotesListAdapter;import com.hfv.hopgrower.dbadapter.MyHopsDatabaseAdapter;import com.hfv.hopgrower.screens.NotesEdit;import com.hfv.hopgrower.sqlite.myhops.Notes;import com.hfv.hopgrower.sqlite.myhops.NotesDao;import java.util.List;import de.greenrobot.dao.query.QueryBuilder;public class NotesActivity extends ActionBarActivity {//	public class NotesActivity extends Activity{    private static final int ACTIVITY_CREATE = 0;    private static final int ACTIVITY_EDIT = 1;    private static final int ACTIVITY_DISPLAY = 2;    private static final int INSERT_ID = Menu.FIRST;    private static final int DELETE_ID = Menu.FIRST + 1;    NotesDao notesDao;    ListView noteText;    Context mCtx;    TextView noteIdView;    /** Called when the activity is first created. */    @Override    public void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_notes);//        setContentView(R.layout.notes_list);        mCtx = getApplicationContext();        getSupportActionBar().setTitle(R.string.app_notes);        TextView noteTitle = (TextView) this.findViewById(R.id.notes_title);        noteText = (ListView) this.findViewById(R.id.nlist);        noteIdView = (TextView) this.findViewById(R.id.noteid);        Button addButton = (Button) this.findViewById(R.id.note_button_add);        //dress it up a bit        noteText.setDivider(getResources().getDrawable(R.drawable.list_divider_hopcolor));        noteText.setHeaderDividersEnabled(true);        noteText.setFooterDividersEnabled(true);        noteText.setDividerHeight(3);        noteText.setSelector(R.drawable.list_item_selector);        MyHopsDatabaseAdapter mDbAdapter = new MyHopsDatabaseAdapter(mCtx);        mDbAdapter.open();        notesDao = mDbAdapter.getNotesDaoSession();        QueryBuilder<Notes> qb = notesDao.queryBuilder();        List<Notes> myNoteList = qb.list();        mDbAdapter.closeDaoSession();        int recCount = myNoteList.size();        if (recCount == 0) {            //no myhops records, throw splash screen or toast            Log.d("HopGrower", "NO RECORDS FOUND");            Notes emptNote = new Notes();            emptNote.setTitle("No Records");            String temp = "0";            emptNote.setId(Long.valueOf(temp));            myNoteList.add(emptNote);        }        NotesListAdapter notesListAdapter = new NotesListAdapter(mCtx, myNoteList, 0); //0=notes activity layout        noteText.setAdapter(notesListAdapter);        //	noteText.setChoiceMode(ListView.CHOICE_MODE_SINGLE);        addButton.setOnClickListener(new View.OnClickListener() {            public void onClick(View view) {                Intent mhIntent = new Intent();                mhIntent.setClassName("com.hfv.hopgrower", "com.hfv.hopgrower.screens.NotesCreate");                startActivity(mhIntent);            }        });        noteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {            @Override            public void onItemClick(AdapterView<?> arg0, View v,                                    int arg2, long arg3) {                // TODO Auto-generated method stub                TextView textview1 = (TextView) v.findViewById(R.id.text1);                TextView textview2 = (TextView) v.findViewById(R.id.noteid);                String noteId = textview2.getText().toString();                int theId = Integer.parseInt(noteId);                //load up bundle and intent and then call edit note                Intent mhIntent = new Intent();                mhIntent.putExtra("NoteId", theId);                mhIntent.setClassName("com.hfv.hopgrower", "com.hfv.hopgrower.screens.NotesEdit");                startActivityForResult(mhIntent, 1);            }        });    }    @Override    public boolean onCreateOptionsMenu(Menu menu) {        super.onCreateOptionsMenu(menu);        //   getMenuInflater().inflate(R.menu.your_action_bar_name_here, menu);        menu.add(0, INSERT_ID, 0, R.string.menu_insert);        return true;    }    /*    @Override    public boolean onMenuItemSelected(int featureId, MenuItem item) {        switch(item.getItemId()) {            case INSERT_ID:                createNote();                return true;        }        return super.onMenuItemSelected(featureId, item);    }*/    @Override    public void onCreateContextMenu(ContextMenu menu, View v,                                    ContextMenuInfo menuInfo) {        super.onCreateContextMenu(menu, v, menuInfo);        menu.add(0, DELETE_ID, 0, R.string.menu_delete);    }    @Override    public boolean onContextItemSelected(MenuItem item) {        switch (item.getItemId()) {            case DELETE_ID:                AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();                //               notesDao.deleteByKey(key);//                mDbHelper.deleteNote(info.id);//                fillData();                return true;        }        return super.onContextItemSelected(item);    }    private void createNote() {        Intent i = new Intent(this, NotesEdit.class);        startActivityForResult(i, ACTIVITY_CREATE);    }    @Override    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {        super.onActivityResult(requestCode, resultCode, intent);        if (resultCode == RESULT_OK) {            Intent refresh = new Intent(this, NotesActivity.class);            startActivity(refresh);            this.finish();        }    }	/* (non-Javadoc)     * @see android.app.ActionBar.OnNavigationListener#onNavigationItemSelected(int, long)	 */}