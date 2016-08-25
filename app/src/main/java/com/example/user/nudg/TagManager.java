package com.example.user.nudg;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 21/08/2016.
 */
public class TagManager {
    HashMap<String, Integer> mTags;
    Gson mGson;

    public TagManager(Context context) {
        mGson = new Gson();
        checkStored(context);
    }

    public void process(ArrayList<String> newTags, Context context) {
        for (String tag : newTags) {
            processSingleTag(tag);
        }
        Log.d("processing", newTags.toString());
        save(context);
    }

    public int getCountofTag(String tag){
        return mTags.get(tag);
    }

    public void save(Context context){
        String json = mGson.toJson(mTags);
        SharedPrefRunner.setStoredText("tags", context, json);
    }

    public void processSingleTag(String tag){
        if (!mTags.containsKey(tag)) {
            mTags.put(tag, 1);
        } else {
            mTags.put(tag, mTags.get(tag) + 1);
        }
    }

    public ArrayList<String> getTags() {
        ArrayList<String> tags = new ArrayList<>();
        for (String key : mTags.keySet()) {
            tags.add(key);
        }
        return tags;
    }

    public void removeTags(ArrayList<String> remtags, Context context) {
        for (String tag : remtags) {
            removeSingleTag(tag);
        }

        Log.d("After remTags", mTags.toString());
        save(context);
    }

    public void removeSingleTag(String tag){
        if (mTags.get(tag) == 1) {
            mTags.remove(tag);
        } else {
            mTags.put(tag, mTags.get(tag) - 1);
        }
    }
    public void addDays() {
        mTags.put("#/Monday", 1);
        mTags.put("#/Tuesday", 1);
        mTags.put("#/Wednesday", 1);
        mTags.put("#/Thursday", 1);
        mTags.put("#/Friday", 1);
        mTags.put("#/Saturday", 1);
        mTags.put("#/Sunday", 1);
        mTags.put("#/Today", 1);
        mTags.put("#/Tomorrow", 1);
    }

    public void checkStored(Context context) {
        String jsonReturn = SharedPrefRunner.getStoredText("tags", context);
        if (jsonReturn != null) {
            Type type = new TypeToken<HashMap<String, Integer>>() {
            }.getType();
            mTags = mGson.fromJson(jsonReturn, (java.lang.reflect.Type) type);
            Log.d("mTags from mem", mTags.toString());

        } else {
            mTags = new HashMap<>();
            addDays();
            Log.d("mTags created", mTags.toString());
        }
    }

    public ArrayList<String> getCleanedTags() {
        return stripDays();
    }

    public ArrayList<String> stripDays(){
        ArrayList<String> strippedTags = getTags();
        strippedTags.remove("#/Monday");
        strippedTags.remove("#/Tuesday");
        strippedTags.remove("#/Wednesday");
        strippedTags.remove("#/Thursday");
        strippedTags.remove("#/Friday");
        strippedTags.remove("#/Saturday");
        strippedTags.remove("#/Sunday");
        strippedTags.remove("#/Today");
        strippedTags.remove("#/Tomorrow");
        return strippedTags;
    }
}
