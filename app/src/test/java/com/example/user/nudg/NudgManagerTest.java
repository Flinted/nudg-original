package com.example.user.nudg;

import android.content.Context;
import android.test.AndroidTestCase;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by user on 24/08/2016.
 */
public class NudgManagerTest extends AndroidTestCase {
    NudgManager mNudger;

    @Before
    public void before(){
        SharedPrefRunner.clear(mContext,"nudgs");
        SharedPrefRunner.clear(mContext,"tags");
        mNudger = new NudgManager(new TagManager(mContext),mContext);
        mNudger.newEntry("test","test",mContext);
        mNudger.newEntry("#test and some more text","",mContext);
        mNudger.newEntry("#test and some more #text","",mContext);
        mNudger.newEntry("#test and some more #text","",mContext);
    }

    @Test
    public void nudgerInstantiatesCorrectly(){
       NudgMaster nudg = mNudger.getNudgs().get(0);
        assertEquals("test", nudg.getText());
    }

    @Test
    public void nudgerFiltersTagsCorrectly(){
        NudgMaster nudg = mNudger.getNudgs().get(1);
        assertEquals("#test |",nudg.getDisplayTags());
    }

    @Test
    public void nudgerFiltersMulitpleTagsCorrectly(){
        NudgMaster nudg = mNudger.getNudgs().get(2);
        assertEquals("#test | #text |",nudg.getDisplayTags());
    }

    @Test
    public void canRemoveNudgs(){
        NudgMaster nudg = mNudger.getNudgs().get(2);
        mNudger.delete(nudg,mContext);
        assertEquals(3,mNudger.count());
    }

    @Test
    public void canFindSpecificNudg(){
        NudgMaster nudg = mNudger.getNudgs().get(0);
        assertEquals(nudg, mNudger.findNudg("#test and some more","#text"));
    }
}
