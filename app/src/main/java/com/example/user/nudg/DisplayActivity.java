package com.example.user.nudg;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by user on 22/08/2016.
 */
public class DisplayActivity extends AppCompatActivity{
    NudgProgram mNudgProgram;
    NudgMaster mNudg;
    String mText;
    String mNote;
    String mTag;
    TextView TextField;
    EditText NoteField;
    TextView TagField;
    Button mSave;
    Button mDelete;
    Button mDiscard;
    Button mDone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitynudgdisplay);
        mNudgProgram = new NudgProgram(DisplayActivity.this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        mText = extras.getString("text");
        mTag = extras.getString("tags");
        mNudg = mNudgProgram.getmNudger().findNudg(mText, mTag);
        setFields();
        setButtons();
    }

    public void setFields(){
        TextField = (TextView) findViewById(R.id.TextField);
        NoteField = (EditText) findViewById(R.id.NoteField);
        TagField = (TextView) findViewById(R.id.TagField);

        TextField.setText(mNudg.getText());
        NoteField.setText(mNudg.getNote());
        TagField.setText(mNudg.getDisplayTags());
    }

    public void setButtons(){
        mSave = (Button) findViewById(R.id.button_save);
        mDelete= (Button) findViewById(R.id.button_delete);
        mDiscard = (Button) findViewById(R.id.button_discard);
        mDone = (Button) findViewById(R.id.button_done);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });
        mDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markDone();
            }
        });
    }


    public void save(){
        String newText = TextField.getText().toString();
        String newNote = NoteField.getText().toString();
        String newTags = TagField.getText().toString();

        String[] splitTags = newTags.split("\\|");
        mNudgProgram.getmNudger().removeTags(mNudg.getTags(), DisplayActivity.this);
        mNudg.update(newText, newNote, splitTags);
        mNudgProgram.getmNudger().processNewTags(mNudg.getTags(), DisplayActivity.this);
        mNudgProgram.getmNudger().save(DisplayActivity.this);
        Intent intent = new Intent(DisplayActivity.this, FilterActivity.class);
        startActivity(intent);
    }

    public void delete(){
        mNudgProgram.getmNudger().delete(mNudg, DisplayActivity.this);
        Intent intent = new Intent(DisplayActivity.this, FilterActivity.class);
        startActivity(intent);
    }

    public void cancel(){
        Intent intent = new Intent(DisplayActivity.this, FilterActivity.class);
        startActivity(intent);
    }

    public void markDone(){
        NudgMaster toStore = mNudg;
        mNudgProgram.getmNudger().delete(mNudg, DisplayActivity.this);

        ArrayList<String> tags = toStore.getTags();
        tags.add("#DONE!");
        toStore.updateTags(tags);

        mNudgProgram.getmNudger().archive(toStore, DisplayActivity.this);
        Intent intent = new Intent(DisplayActivity.this, FilterActivity.class);
        startActivity(intent);
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

            Toast.makeText(DisplayActivity.this, "ALL ITEMS CLEARED", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(item.getItemId() == R.id.menu_home ){

            Toast.makeText(DisplayActivity.this,"Going to Home",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DisplayActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        if(item.getItemId() == R.id.menu_list ){
            Toast.makeText(DisplayActivity.this,"Going to View Nudgs",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DisplayActivity.this, FilterActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
