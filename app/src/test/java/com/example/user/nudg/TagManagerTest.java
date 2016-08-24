package com.example.user.nudg;

import android.test.AndroidTestCase;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by user on 24/08/2016.
 */
public class TagManagerTest extends AndroidTestCase {
    TagManager tagManager;

    @Before
    public void before(){
        tagManager = new TagManager(mContext);
    }

    @Test
    public void canProcessSingleTag(){
        tagManager.processSingleTag("#test");
        assertEquals(1,tagManager.getTags().size());
    }

    @Test
    public void canProcessMultipleTags(){
        ArrayList<String> tags =new ArrayList<>();
        tags.add("#test1");
        tags.add("#test2");
        tags.add("#test3");

        tagManager.process(tags, mContext);
        assertEquals(3, tagManager.getTags().size());
    }

    @Test
    public void canStackMatchingTags(){
        tagManager.processSingleTag("#test");
        tagManager.processSingleTag("#test");
        int result = tagManager.mTags.get("#test");
        assertEquals(2, tagManager.getTags().size());
        assertEquals(2,result);
    }

    @Test
    public void canRemovesSingleTag(){
        ArrayList<String> tags =new ArrayList<>();
        tags.add("#test1");
        tags.add("#test2");
        tags.add("#test3");
        tagManager.process(tags, mContext);
        tagManager.removeSingleTag("#test2");
        assertEquals(2,tagManager.getTags().size());
    }

    @Test
    public void canRemoveTagOnceStackDepleted(){
        ArrayList<String> tags =new ArrayList<>();
        tags.add("#test1");
        tags.add("#test1");
        tags.add("#test3");

        tagManager.process(tags, mContext);
        tagManager.removeSingleTag("#test1");
        tagManager.removeSingleTag("#test1");
        assertEquals(1,tagManager.getTags().size());
    }

    @Test
    public void canAddDays(){
        tagManager.addDays();
        assertEquals(9,tagManager.getTags().size());
    }

    @Test
    public void canStripDaysFromResponse(){
        ArrayList<String> tags =new ArrayList<>();
        tags.add("#test1");
        tags.add("#test1");
        tags.add("#test3");

        tagManager.process(tags, mContext);
        tagManager.addDays();
        assertEquals(2,tagManager.getCleanedTags().size());
    }



}
