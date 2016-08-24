package com.example.user.nudg;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
/**
 * Created by user on 19/08/2016.
 */
public class NudgTest {
    NudgMaster mNudg;


    @Before
    public void before(){

        mNudg = new TextNudg("Test", "test note");
    }

    @Test
    public void nudgInstantiatesCorrectly(){
        assertEquals("Test", mNudg.getText());
        assertEquals("test note", mNudg.getNote());
        Log.d("Test", "test");
    }

    @Test
     public void canUpdateNudgNote(){
        mNudg.updateNote("new test note");
        assertEquals("new test note", mNudg.getNote());
    }

    @Test
    public void canUpdateNudgtext(){
        mNudg.updateText("new test text");
        assertEquals("new test text", mNudg.getText());
    }

    @Test
    public void canUpdateNudgTags(){
        ArrayList<String> tags = new ArrayList<>();
        tags.add("#test");
        tags.add("#test2");
        tags.add("#test3");

        mNudg.updateTags(tags);
        assertEquals(3, mNudg.getTags().size());
    }

    @Test
    public void canGiveDisplayTags(){
        ArrayList<String> tags = new ArrayList<>();
        tags.add("#test");
        tags.add("#test2");
        tags.add("#test3");

        mNudg.updateTags(tags);
        assertEquals("#test | #test2 | #test3 |", mNudg.getDisplayTags());
    }
}
