package com.example.user.nudg;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.MultiAutoCompleteTextView;

import java.util.Calendar;


/**
 * Created by user on 22/08/2016.
 */
    class mDateSetListener implements DatePickerDialog.OnDateSetListener {
        MultiAutoCompleteTextView mInput;
    public mDateSetListener(MultiAutoCompleteTextView input){
        mInput =input;
    }
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            int mDay = dayOfMonth;
            int mMonth = monthOfYear;
            String[] months={"January","February","March","April","May","June","July","August","September","October", "November", "December"};

            mInput.setText(new StringBuilder()
                    // Month is 0 based so add 1
                    .append(mInput.getText().toString()).append("#").append(mDay).append(months[mMonth]).append(","));
            Log.d("datePicker", mInput.getText().toString());
        }


    }


