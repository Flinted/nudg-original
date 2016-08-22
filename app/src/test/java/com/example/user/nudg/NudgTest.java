package com.example.user.nudg;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
}
