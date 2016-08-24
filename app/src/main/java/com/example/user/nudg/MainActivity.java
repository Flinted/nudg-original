package com.example.user.nudg;

import android.graphics.Bitmap;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
/**
 * Created by user on 18/08/2016.
 */
public class MainActivity extends AppCompatActivity{
    ImageButton mText;
    ImageButton mImage;
    Button mGoFilter;
    ImageButton mCalendarGo;
    NudgProgram mNudg;
    MultiAutoCompleteTextView mAuto;
    MyListAdapter mAdapter;

    @Override
    protected void onRestart(){
        super.onRestart();
        setActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNudg = new NudgProgram(MainActivity.this);
        mNudg.setUser(MainActivity.this, "Chris");
        mText = (ImageButton) findViewById(R.id.text);
        mGoFilter = (Button) findViewById(R.id.goFilter);
        mAuto = (MultiAutoCompleteTextView) findViewById(R.id.autoComplete);
        mCalendarGo = (ImageButton) findViewById(R.id.calendarGo);
        setActivity();
        mAuto.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String newString = mAuto.getText() + "#";
                    mAuto.setText(newString);
                return false;
            }
        });
        mImage = (ImageButton) findViewById(R.id.imageGo);
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 10);
            }
        });

        mCalendarGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarGo();
            }
        });

        mGoFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterGo();
            }
        });

        mText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newNudgGo();
            }
        });
    }


    public void toastConfirm(){
        String toastMessage = "Nudg: " + mAuto.getText() + " added.";
        Toast toast = Toast.makeText(MainActivity.this,toastMessage,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    public void toastFail(){
        String toastMessage = "No text, Nudg not added.";
        Toast toast = Toast.makeText(MainActivity.this,toastMessage,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
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
            AlertWindow alert = new AlertWindow();
            alert.show(getFragmentManager(), "clearalert");
            return true;
        }
        if(item.getItemId() == R.id.menu_home ){
            Toast.makeText(MainActivity.this,"Already Home",Toast.LENGTH_SHORT).show();
            return true;
        }
        if(item.getItemId() == R.id.menu_list ){
            Toast.makeText(MainActivity.this,"Going to view Nudgs",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, FilterActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        mImageView.setImageResource(R.mipmap.ic_launcher);
        if(resultCode == RESULT_OK){
            if(requestCode == 10){
                Log.d("hitting", "hitting on return");
                Bitmap cameraImage=(Bitmap) data.getExtras().get("data");
//                mImageView.setImageBitmap(cameraImage);
            }
        }

    }

    public void calendarGo(){
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, new mDateSetListener(mAuto), mYear, mMonth, mDay);
        dialog.show();
    }

    public void filterGo(){
        Intent intent = new Intent(MainActivity.this, FilterActivity.class);
        intent.putExtra("user", mNudg.getUserName());
        startActivity(intent);
    }

    public void newNudgGo(){
        String nudgData = mAuto.getEditableText().toString();
        if (nudgData.equalsIgnoreCase("")){
            toastFail();
            return;
        }
        mNudg.getmNudger().newEntry(nudgData, "", MainActivity.this);
        toastConfirm();
        mAuto.setText("");
        setAuto(mNudg.getmNudger());
        setTodayList();
    }

    public void setActivity(){
        getSupportActionBar().setTitle("Welcome back " + mNudg.getUserName());
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#b5b5b7"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        setAuto(mNudg.getmNudger());
        setTodayList();
    }


    public void setAuto (NudgManager nudgManager){
        List<String> stringList = nudgManager.getTags();
        AutoSuggestAdapter adapter = new AutoSuggestAdapter(this, android.R.layout.simple_list_item_1, stringList);
        mAuto.setAdapter(adapter);
        mAuto.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        mAuto.setThreshold(1);
    }
    public void setTodayList(){
        ArrayList<NudgMaster> data = mNudg.getmNudger().returnTodaysNudgs();
        ListView dayList = (ListView) findViewById(R.id.today);
        TextView placeholder= (TextView) findViewById(R.id.placeholder);
        if(!data.isEmpty()) {
            mAdapter = new MyListAdapter(MainActivity.this, data);
            dayList.setAdapter(mAdapter);
            dayList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    NudgMaster nudg = mAdapter.getItem(position);
                    Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                    intent.putExtra("tags", nudg.getTags().toString());
                    intent.putExtra("text", nudg.getText());
                    intent.putExtra("note", nudg.getNote());
                    startActivity(intent);
                }
            });
            dayList.setVisibility(View.VISIBLE);
            placeholder.setVisibility(View.INVISIBLE);

        }else{
            dayList.setVisibility(View.INVISIBLE);
            placeholder.setVisibility(View.VISIBLE);
        }
    }
}
