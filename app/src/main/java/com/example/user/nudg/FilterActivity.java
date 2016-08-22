package com.example.user.nudg;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FilterActivity extends ListActivity {
   private ArrayList<NudgMaster> mData;
    private MyListAdapter mAdapter;
   private  NudgProgram mNudg;
   private ListView mList;
    private SearchView mSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        mList = (ListView) findViewById(android.R.id.list);
        mSearch = (SearchView) findViewById(R.id.searcher);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String user = extras.getString("user");
        mNudg = new NudgProgram(new User(user), FilterActivity.this);

        mData = mNudg.getmNudger().getNudgs();

        mAdapter = new MyListAdapter(FilterActivity.this,mData);
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Log.d("Clicked", mAdapter.getItem(position).getText());
            }
        });

        mSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String constraint) {
                mAdapter.getFilter().filter(constraint);
                return false;
            }
        });
    }
}