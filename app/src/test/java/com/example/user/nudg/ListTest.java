package com.example.user.nudg;

import org.junit.Before;
import org.junit.Test;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;
/**
 * Created by user on 19/08/2016.
 */
public class ListTest {
    Shelf mShelf;
    ListManager mLister;

    @Before
    public void before(){
        mShelf = new Shelf();
        mLister = new ListManager(mShelf);
    }

    @Test
    public void canMakeList(){
        mLister.newList("date","Monday","mon");
        assertEquals(1, mLister.listCount());
    }

    @Test
    public void canUpdateTagWithHashincluded(){
        mLister.newList("custom","Monday","mon");
        CustomList list = (CustomList) mLister.listFind("Monday");
        list.updateTag("#tue");
        assertEquals("#tue", list.getTag());
    }

    @Test
    public void canUpdateTagWithoutHashincluded(){
        mLister.newList("custom","Monday","mon");
        CustomList list = (CustomList) mLister.listFind("Monday");
        list.updateTag("tue");
        assertEquals("#tue", list.getTag());
    }

    @Test
    public void canUpdateTagWithSpaces(){
        mLister.newList("custom","Monday","mon");
        CustomList list = (CustomList) mLister.listFind("Monday");
        list.updateTag("#tue #wed");
        assertEquals("#tue", list.getTag());
    }

    @Test
    public void canAddNudgToGivenList(){
        NudgMaster nudg = new TextNudg("Test","test notes");
        mLister.newList("custom", "Monday", "#mon");
        mLister.addToList(nudg, "Monday");
        CustomList mondayList = (CustomList) mLister.listFind("Monday");
        assertEquals(1, mondayList.countEntries());
    }

    @Test
     public void canTagFilterAndAddToList(){
        NudgMaster nudg = new TextNudg("Test #mon #happy","test notes");
        mLister.newList("custom", "Monday", "#mon");
        mLister.addToList(nudg,null);
        CustomList mondayList = (CustomList) mLister.listFind("Monday");
        assertEquals(1,mondayList.countEntries());
    }

    @Test
    public void canSendsNudgsToShelfIfWrongTag(){
        NudgMaster nudg = new TextNudg("Test #mon #happy","test notes");
        mLister.newList("custom", "Monday", "#monday");
        mLister.addToList(nudg,null);
        CustomList mondayList = (CustomList) mLister.listFind("Monday");
        assertEquals(0,mondayList.countEntries());
        assertEquals(1,mLister.getShelf().shelfCount(null));
    }

    @Test
    public void canSendsNudgsToShelfIfNoTag(){
        NudgMaster nudg = new TextNudg("Test","test notes");
        mLister.newList("custom", "Monday", "#monday");
        mLister.addToList(nudg, null);
        CustomList mondayList = (CustomList) mLister.listFind("Monday");
        assertEquals(0,mondayList.countEntries());
        assertEquals(1,mLister.getShelf().shelfCount(null));
    }

    @Test
    public void canTagFilterAndAddToListMultipleTimes(){
        NudgMaster nudg = new TextNudg("Test #mon #happy","test notes");
        NudgMaster nudg2 = new TextNudg("Test #tue #happy","test notes");
        NudgMaster nudg3 = new TextNudg("Test #mon #happy #monkey","test notes");
        NudgMaster nudg4 = new TextNudg("Test #tue #happy","test notes");
        NudgMaster nudg5 = new TextNudg("Test #mon","test notes");


        mLister.newList("custom", "Monday", "#mon");
        mLister.newList("custom", "Tuesday", "#tue");
        mLister.newList("custom", "Wednesday", "#wed");

        mLister.addToList(nudg, null);
        mLister.addToList(nudg2,null);
        mLister.addToList(nudg3,null);
        mLister.addToList(nudg4,null);
        mLister.addToList(nudg5,null);

        CustomList mondayList = (CustomList) mLister.listFind("Monday");
        CustomList tuesdayList = (CustomList) mLister.listFind("Tuesday");
        CustomList wednesdayList = (CustomList) mLister.listFind("Wednesday");

        assertEquals(3,mondayList.countEntries());
        assertEquals(2,tuesdayList.countEntries());
        assertEquals(0,wednesdayList.countEntries());
    }
}

