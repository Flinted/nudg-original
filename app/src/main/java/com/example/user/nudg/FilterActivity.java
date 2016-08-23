package com.example.user.nudg;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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
        mSearch.setQueryHint("Click here to filter Nudgs");
        mSearch.clearFocus();

        Intent intent = getIntent();
        mNudg = new NudgProgram(FilterActivity.this);

        mData = mNudg.getmNudger().getNudgs();

        mAdapter = new MyListAdapter(FilterActivity.this,mData);
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NudgMaster nudg = mAdapter.getItem(position);
                Intent intent = new Intent(FilterActivity.this, DisplayActivity.class);
                intent.putExtra("tags", nudg.getTags().toString());
                intent.putExtra("text", nudg.getText());
                intent.putExtra("note",nudg.getNote().toString());
                startActivity(intent);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mainactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.menu_clear ){
            SharedPrefRunner.clear(this,"tags");
            SharedPrefRunner.clear(this,"nudgs");

            Toast.makeText(FilterActivity.this, "ALL ITEMS CLEARED", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(item.getItemId() == R.id.menu_home ){

            Toast.makeText(FilterActivity.this,"Going to Home",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(FilterActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        if(item.getItemId() == R.id.menu_list ){
            Toast.makeText(FilterActivity.this,"Already viewing Nudgs",Toast.LENGTH_SHORT).show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}