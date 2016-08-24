package com.example.user.nudg;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 22/08/2016.
 */

public class MyListAdapter extends ArrayAdapter<NudgMaster> implements Filterable {
    private Context mContext;
    private ArrayList<NudgMaster> mNudgs;
    private CustomFilter mFilter;
    private ArrayList<NudgMaster> mFiltered;


    public MyListAdapter(Context ctx, ArrayList<NudgMaster> data){
        super(ctx,R.layout.searchlistitem,data);
        dataCheck(data);
        mContext = ctx;
        mFiltered = mNudgs;
    }

    public void dataCheck(ArrayList<NudgMaster> data){
        if(data == null){
            mNudgs = new ArrayList<>();
            Log.d("null", "hit");
        }else{
            mNudgs = data;
            Log.d("notnull", "hit");
        }
    }

    @Override
    public NudgMaster getItem(int pos){
        return mNudgs.get(pos);
    }

    @Override
    public int getCount(){
        return mNudgs.size();
    }

    @Override
    public View getView(int position, View convertView,ViewGroup parent){
        View itemView = convertView;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (itemView == null){
            itemView = inflater.inflate(R.layout.searchlistitem, parent, false);
        }
        NudgMaster currentNudg = mNudgs.get(position);
//
        TextView nudg = (TextView) itemView.findViewById(R.id.item_nudg);
        nudg.setText(currentNudg.getText());
        TextView notes = (TextView) itemView.findViewById(R.id.item_notes);
        notes.setText(currentNudg.getNote());
        TextView tags = (TextView) itemView.findViewById(R.id.item_tags);
        tags.setText(currentNudg.getDisplayTags());
        return itemView;
    }

    @Override
    public Filter getFilter(){
        if(mFilter == null){
            mFilter = new CustomFilter();
        }
        return mFilter;
    }

    class CustomFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint){
            FilterResults results = new FilterResults();
            if(constraint != null && constraint.length() > 0){
                constraint = constraint.toString().toUpperCase();
                ArrayList<NudgMaster> filters = new ArrayList<>();

                for(int i=0; i<mFiltered.size(); i++){
                    if(!mFiltered.get(i).getComparisonTags().toUpperCase().contains("#DONE")){
                    if(mFiltered.get(i).getComparisonTags().toUpperCase().contains(constraint)){

                        filters.add(mFiltered.get(i));
                    }
                }else if("#DONE".contains(constraint)){
                        filters.add(mFiltered.get(i));
                    }
                }

                results.count = filters.size();
                results.values = filters;
            }else{
                ArrayList<NudgMaster> filters = new ArrayList<>();
                for(int i=0; i<mFiltered.size(); i++){
                    if(!mFiltered.get(i).getComparisonTags().toUpperCase().contains("#DONE")) {
                        filters.add(mFiltered.get(i));
                    }
                        results.count = filters.size();
                        results.values = filters;
            }}
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results){
            mNudgs = (ArrayList<NudgMaster>) results.values;
            notifyDataSetChanged();
        }

    }


}


