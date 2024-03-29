package com.example.user.nudg;

import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 18/08/2016.
 */
public abstract class NudgMaster {
    private String mText;
    private String mNote;
    private ArrayList<String> mTags;
    private String mImgLink;
    private String mAudioLink;

    public NudgMaster (String text, String notes){
        mText = text;
        mNote = notes;
        mTags = new ArrayList<>();
        siftForTags();
    }

    public void siftForTags(){
        Pattern MY_PATTERN = Pattern.compile("#(\\w+)");
        Matcher matcher = MY_PATTERN.matcher(mText);
        while (matcher.find()) {
            String tag = matcher.group(1);
                mTags.add("#" + tag);
            Log.d("CustomTag", tag);

        }
        Pattern MY_PATTERN2 = Pattern.compile("#/(\\w+)");
        matcher = MY_PATTERN2.matcher(mText);
        while (matcher.find()) {
            String tag = matcher.group(1);
//            mTags.add("#/" + tag);
            String dateTag = dateCheck(tag);
            mTags.add(dateTag);
        }
        if (mTags.size() == 0){
            mTags.add("#noTag");
        }
        stripTags(mText);
    }

    public String getComparisonTags(){
        String returner = "";
        for(String tag: mTags){
            returner = returner + " " + tag;
        }
        return returner;
    }
    public void update(String newText, String newNote, String[] newTags){
        ArrayList<String> tags = new ArrayList<String>(Arrays.asList(newTags));
//        for(String tag:newTags){
//            tags.add(tag);
//        }

        updateText(newText);
        updateNote(newNote);
        updateTags(tags);
    }

    public void addTag(String tag){
        mTags.add(tag);
    }

    public void removeTag(String remtag) {
        int toRemove = 10000;
        for(int i = 0; i<mTags.size();i++) {
            if (mTags.get(i).equalsIgnoreCase(remtag)) {
                toRemove = i;
                Log.d("rem", mTags.toString());
            }
            if(toRemove != 10000){
                mTags.remove(i);

            }
        }
    }

    public void updateText(String newText){
        mText = newText;
    }

    public void updateTags(ArrayList<String> newTags){
           mTags.clear();
            for (String tag : newTags){
                mTags.add(tag);
            }

        if (mTags.size() == 0){
            mTags.add("#noTag");
        }
    }
    public void updateNote(String newNote){

        mNote = newNote;
    }

    public void stripTags(String text){
        text = text.replaceAll("(#/?.+?\\b,?)", "");
        mText= text.trim();
    }

    public String dateCheck(String tag){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        calendar.setTime(date);

        switch (tag){
            case "Monday":
                while(calendar.get(Calendar.DAY_OF_WEEK ) != Calendar.MONDAY )
                    calendar.add( Calendar.DATE, 1 );
                break;
            case "Tuesday":
                while(calendar.get(Calendar.DAY_OF_WEEK ) != Calendar.TUESDAY )
                calendar.add( Calendar.DATE, 1 );
                break;
            case "Wednesday":
                while(calendar.get(Calendar.DAY_OF_WEEK ) != Calendar.WEDNESDAY )
                calendar.add( Calendar.DATE, 1 );
                break;
            case "Thursday":
                while(calendar.get(Calendar.DAY_OF_WEEK ) != Calendar.THURSDAY )
                calendar.add( Calendar.DATE, 1 );
                break;
            case "Friday":
                while(calendar.get(Calendar.DAY_OF_WEEK ) != Calendar.FRIDAY )
                calendar.add( Calendar.DATE, 1 );
                break;
            case "Saturday":
                while(calendar.get(Calendar.DAY_OF_WEEK ) != Calendar.SATURDAY )
                calendar.add( Calendar.DATE, 1 );
                break;
            case "Sunday":
                while(calendar.get(Calendar.DAY_OF_WEEK ) != Calendar.SUNDAY )
                calendar.add( Calendar.DATE, 1 );
                break;
            case "Today":
                break;
            case "Tomorrow":
                calendar.add(Calendar.DATE, 1);
                break;

        }
        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        Integer day = calendar.get(Calendar.DATE);
        return  "#" + day.toString() + month;
    }

    public ArrayList<String> getTags(){
        return mTags;
    }

    public String getDisplayTags(){
        Collections.sort(mTags, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });

        String tags = new String();
        for(String tag : mTags){
            tags = tags + tag + " | ";
        };
        return tags;
    }


    public String getText(){
        return mText;
    }

    public String getNote(){
        return mNote;
    }


}
