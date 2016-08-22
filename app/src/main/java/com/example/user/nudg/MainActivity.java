package com.example.user.nudg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.validation.Validator;

/**
 * Created by user on 18/08/2016.
 */
public class MainActivity extends AppCompatActivity{
    TextView mWelcome;
    Button mText;
    Button mGoFilter;
    NudgProgram mNudg;
    MultiAutoCompleteTextView mAuto;
    Gson mGson;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNudg = new NudgProgram(new User("Malcolm"), MainActivity.this);

        mWelcome = (TextView) findViewById(R.id.welcomer);
        mText = (Button) findViewById(R.id.text);
        mGoFilter = (Button) findViewById(R.id.goFilter);
        mAuto = (MultiAutoCompleteTextView) findViewById(R.id.autoComplete);
        setAuto(mNudg.getmNudger());
        mWelcome.setText("Welcome back " + mNudg.getUserName());

        mGoFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FilterActivity.class);
                intent.putExtra("user", mNudg.getUserName().toString());
                startActivity(intent);
            }
        });


        mText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nudgData = mAuto.getEditableText().toString();
                if(nudgData.equalsIgnoreCase("")){
                    toastFail();
                    return;
                }
                mNudg.getmNudger().newEntry(nudgData , "", MainActivity.this );
                toastConfirm();
                mAuto.setText("");
                setAuto(mNudg.getmNudger());
                Log.d("New Entry", "now " + mNudg.getmNudger());
            }
        });
    }

    public void toastConfirm(){
        String toastMessage = "Nudg: " + mAuto.getText() + " added.";
        Toast.makeText(MainActivity.this,toastMessage,Toast.LENGTH_SHORT).show();

    }

    public void toastFail(){
        String toastMessage = "No text, Nudg not added.";
        Toast.makeText(MainActivity.this,toastMessage,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mainactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.action_shelf ){
            Log.d("PersistenceExample", "Hello menu item clicked");
            Toast.makeText(MainActivity.this, "temp", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(item.getItemId() == R.id.action_lists ){
            Toast.makeText(MainActivity.this,"temp",Toast.LENGTH_SHORT).show();
            return true;
        }
        if(item.getItemId() == R.id.action_options ){
            Toast.makeText(MainActivity.this,"temp",Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setAuto (NudgManager nudgManager){
        List<String> stringList = nudgManager.getTags();

        AutoSuggestAdapter adapter = new AutoSuggestAdapter(this, android.R.layout.simple_list_item_1, stringList);

        mAuto.setAdapter(adapter);
        mAuto.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        mAuto.setThreshold(1);
    }
}
