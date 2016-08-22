package com.example.user.nudg;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 18/08/2016.
 */
public class NudgManager {
    private TagManager mTagger;
    private Archive mArchive;
    private NudgMaster mCurrentNudg;
    private ArrayList<NudgMaster> mNudgs;
    private Gson mGson;

    public NudgManager(TagManager lister, Archive archive, Context context){
       mTagger = lister;
        mGson = new Gson();
        mArchive = archive;
        checkStored(context);
    }

    public void newEntry(String text, String notes, Context context){
       newNudg(text, notes);
        ArrayList<String> tags = mCurrentNudg.getTags();
        mTagger.process(tags, context);
        save(context);
    }

    public ArrayList<NudgMaster> getNudgs(){
        return mNudgs;
    }

    public void newNudg(String text, String notes){
        mCurrentNudg = new TextNudg(text,notes);
    }

    public ArrayList<String> getTags(){
        return mTagger.getTags();
    }

    public void save(Context context){
        mNudgs.add(mCurrentNudg);
        String json = mGson.toJson(mNudgs);
        SharedPrefRunner.setStoredText("nudgs", context, json);
    }

    public void checkStored(Context context){
        String jsonReturn = SharedPrefRunner.getStoredText("nudgs", context);
        if(jsonReturn != null) {
            Type type = new TypeToken<ArrayList<TextNudg>>(){}.getType();
            mNudgs =  mGson.fromJson(jsonReturn, (java.lang.reflect.Type) type);

            for(NudgMaster nudg: mNudgs){
                Log.d("nudgTag", nudg.getTags().toString());
            }
        }else{
            mNudgs = new ArrayList<NudgMaster>();
        }
    }
}
