package com.example.user.nudg;

import android.content.Context;

/**
 * Created by user on 18/08/2016.
 */
public class NudgProgram {

    private User mUser;
    private NudgManager mNudger;

    public NudgProgram(User user, Context context){
        mUser = user;
        Archive archive = new Archive();
        TagManager tagManager = new TagManager(context);
        mNudger =  new NudgManager(tagManager, archive, context);
    }

    public NudgManager getmNudger(){
        return mNudger;
    }

    public String getUserName(){
        return mUser.getName();
    }
}
