package com.example.user.nudg;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

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

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
