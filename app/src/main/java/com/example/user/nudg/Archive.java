package com.example.user.nudg;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by user on 18/08/2016.
 */
public class Archive {
    Gson mGson;
    ArrayList<NudgMaster> mNudgArchive;

    public Archive(Context context){
        mGson = new Gson();
        checkStored(context);
    }

        public void checkStored(Context context){
            String jsonReturn = SharedPrefRunner.getStoredText("archive", context);
            if(jsonReturn != null) {
                Type type = new TypeToken<ArrayList<TextNudg>>(){}.getType();
                mNudgArchive =  mGson.fromJson(jsonReturn, (java.lang.reflect.Type) type);
            }else{
                mNudgArchive = new ArrayList<NudgMaster>();
            }
        }

    public void addToArchive(NudgMaster nudg, Context context){
        mNudgArchive.add(nudg);
        save(context);
    }

    public void save(Context context){
        String json = mGson.toJson(mNudgArchive);
        SharedPrefRunner.setStoredText("archive", context, json);
    }
}
