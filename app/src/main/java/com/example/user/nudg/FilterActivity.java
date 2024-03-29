package com.example.user.nudg;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        mNudg = new NudgProgram(FilterActivity.this);
        mList = (ListView) findViewById(android.R.id.list);
        mSearch = (SearchView) findViewById(R.id.searcher);
        mSearch.setQueryHint("click here to filter nudgs");
        mSearch.clearFocus();
        setButtons();
        mData = mNudg.getmNudger().getNudgs();
        mAdapter = new MyListAdapter(FilterActivity.this,mData);
        mList.setAdapter(mAdapter);
        if(!mData.isEmpty()){
        mAdapter.getFilter().filter("");
        }
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NudgMaster nudg = mAdapter.getItem(position);
                Intent intent = new Intent(FilterActivity.this, DisplayActivity.class);
                intent.putExtra("tags", nudg.getTags().toString());
                intent.putExtra("text", nudg.getText());
                intent.putExtra("note", nudg.getNote().toString());
                startActivity(intent);
            }
        });

        mList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                NudgMaster nudg = mAdapter.getItem(position);
                if (nudg.getComparisonTags().contains("#DONE")) {
                    nudg.removeTag("#DONE");
                    mNudg.getmNudger().removeSingleTag("#DONE", FilterActivity.this);
                    Toast.makeText(FilterActivity.this, "#DONE tag removed", Toast.LENGTH_SHORT).show();
                } else {
                    nudg.addTag("#DONE");
                    mNudg.getmNudger().processSingleTag("#DONE", FilterActivity.this);
                    Toast toast = Toast.makeText(FilterActivity.this, "Marked Done.\nFilter by #DONE to find all finished Nudgs", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                mNudg.getmNudger().save(FilterActivity.this);
                Intent intent = new Intent(FilterActivity.this, FilterActivity.class);
                startActivity(intent);
                return false;
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


    public void setButtons(){
        Button home = (Button) findViewById(R.id.button_home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FilterActivity.this, "Going to Home", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FilterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout scroller = (LinearLayout) findViewById(R.id.button_scroller);
        ArrayList<String> mTags = mNudg.getmNudger().getCleanedTags();

        for (int i = 0; i <= mTags.size()-1; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.bottomMargin = 2;
            Button btn = new Button(this);
            btn.setId(i);
            final int id_ = btn.getId();
            String tag = mTags.get(i);
            String buttonText = tag + " ("+mNudg.getmNudger().getCountofTag(tag) + ")";
            btn.setText(buttonText);
//            btn.setText(mTags.get(i));
            btn.setTextSize(10);

            btn.setBackgroundResource(R.drawable.button);
            scroller.addView(btn, params);
            Button btn1 = ((Button) findViewById(id_));
            btn1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    setSearchFromButton(view);
                }
            });
        }
    }

    public void setSearchFromButton(View view){
        Button button = (Button) view;
        String tag = button.getText().toString();
        String cutTag = tag;
        int spacePos = tag.indexOf(" ");
        if (spacePos > 0) {
            cutTag= tag.substring(0, spacePos);
        }
        mSearch.setQuery(cutTag, true);
    }

    public void setSearchFromDefaultButton(View view){
        NudgMaster tempNudg = new TextNudg("Temp","temp");
        Button button = (Button) view;
        Log.d("Default Clicked", button.getText().toString());
        String textToSet = tempNudg.dateCheck(button.getText().toString().substring(2));
        Log.d("dateCheck", textToSet);
        mSearch.setQuery(textToSet, true);
    }

}