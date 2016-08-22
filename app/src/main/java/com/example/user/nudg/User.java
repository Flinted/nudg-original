package com.example.user.nudg;

/**
 * Created by user on 18/08/2016.
 */
public class User {
    private String mName;
    private Settings mSettings;

    public User(String name){
        mName = name;
        mSettings = new Settings();
    }

    public String getName(){
        return mName;
    }
}
