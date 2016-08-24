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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by user on 18/08/2016.
 */
public class NudgManager {
    private TagManager mTagger;
    private NudgMaster mCurrentNudg;
    private ArrayList<NudgMaster> mNudgs;
    private Gson mGson;

    public NudgManager(TagManager lister, Context context){
       mTagger = lister;
        mGson = new Gson();
        checkStored(context);
    }

    public void newEntry(String text, String notes, Context context){
       newNudg(text, notes);
        ArrayList<String> tags = mCurrentNudg.getTags();
        mTagger.process(tags, context);
        save(context);
    }

    public int count(){
        return mNudgs.size();
    }

    public void delete(NudgMaster nudg, Context context){
        mTagger.removeTags(nudg.getTags(), context);
        mNudgs.remove(nudg);
        save(context);
    }

    public ArrayList<NudgMaster> getNudgs(){
        return mNudgs;
    }

    public void removeTags(ArrayList<String> tags, Context context){
        mTagger.removeTags(tags, context);
    }

    public void newNudg(String text, String notes){
        mCurrentNudg = new TextNudg(text,notes);
        mNudgs.add(mCurrentNudg);
    }

    public ArrayList<String> getTags(){
        return mTagger.getTags();
    }

    public void save(Context context){
        String json = mGson.toJson(mNudgs);
        SharedPrefRunner.setStoredText("nudgs", context, json);
    }

    public void processNewTags(ArrayList<String> newTags, Context context){
        mTagger.process(newTags, context);
    }

    public void processSingleTag(String tag, Context context){
        mTagger.processSingleTag(tag);
        mTagger.save(context);
    }

    public void removeSingleTag(String tag, Context context){
        mTagger.removeSingleTag(tag);
        mTagger.save(context);
    }
    public void checkStored(Context context){
        String jsonReturn = SharedPrefRunner.getStoredText("nudgs", context);
        if(jsonReturn != null) {
            Type type = new TypeToken<ArrayList<TextNudg>>(){}.getType();
            mNudgs =  mGson.fromJson(jsonReturn, (java.lang.reflect.Type) type);
        }else{
            mNudgs = new ArrayList<>();
        }
    }

    public ArrayList<String> getCleanedTags(){
        return mTagger.getCleanedTags();
    }

    public NudgMaster findNudg(String text, String tags){
        for(NudgMaster nudg : mNudgs){
            if(nudg.getText().equalsIgnoreCase(text)){

                if(tags.contains(nudg.getTags().get(0))){
                    return nudg;
                }
            }
        }
        return null;
    }


    public ArrayList<NudgMaster> returnTodaysNudgs(){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        calendar.setTime(date);
        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        Integer day = calendar.get(Calendar.DATE);
        String searchTerm =  "#" + day.toString() + month;

        ArrayList<NudgMaster> returnList = new ArrayList<>();
        for(NudgMaster nudg: mNudgs){
            if(nudg.getTags().toString().contains(searchTerm)){
                returnList.add(nudg);
            }
        }
        return returnList;
    }
//
//    public void removeNudgByFind(NudgMaster nudgToRemove){
//        String text = nudgToRemove.getText();
//        String tags = nudgToRemove.getComparisonTags()
//        for(int i=0; i<mNudgs.size();i++){
//            if(mNudgs.get(i).getText().equalsIgnoreCase(text)){
//
//                if(tags.contains(mNudgs.get(i).getTags().get(0))){
//                    mNudgs.remove(i);
//                }
//            }
//        }
//        return null;
//    }
}
