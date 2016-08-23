package com.example.user.nudg;

import android.content.Context;

/**
 * Created by user on 18/08/2016.
 */
public class NudgProgram {

    private String mUser;
    private NudgManager mNudger;

    public NudgProgram(Context context){
        mUser = "User";
        Archive archive = new Archive(context);
        TagManager tagManager = new TagManager(context);
        mNudger =  new NudgManager(tagManager, archive, context);
        checkUser(context);
    }

    public NudgManager getmNudger(){
        return mNudger;
    }

    public String getUserName(){
        return mUser;
    }

    public void setUser(Context context,String user){
        mUser = user;
        SharedPrefRunner.setStoredText("user",context,mUser);
    }

    private void checkUser(Context context){
        String stored = SharedPrefRunner.getStoredText("user",context);
        if(stored != null){
            mUser = stored;
        }
    }
}
