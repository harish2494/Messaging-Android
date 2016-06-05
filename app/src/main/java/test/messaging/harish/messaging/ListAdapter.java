package test.messaging.harish.messaging;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by vinayagar on 6/5/2016.
 */

public class ListAdapter extends ArrayAdapter<SMSData> implements Filterable{
    private ArrayList<SMSData> mOriginalValues;
    private ArrayList<SMSData> mDisplayedValues;
    LayoutInflater inflater;
    private final Context context;
    private final List<SMSData> smsList;

    public ListAdapter(Context context, List<SMSData> smsList) {
        super(context, 0, smsList);
        this.context = context;
        this.smsList = smsList;
        this.mOriginalValues = (ArrayList<SMSData>) smsList;
        this.mDisplayedValues = (ArrayList<SMSData>) smsList;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        SMSData data = getItem(position);
        View rowView = inflater.inflate(R.layout.sms_data, parent, false);
        TextView senderNumber = (TextView) rowView.findViewById(R.id.sNo);
        TextView senderDate = (TextView) rowView.findViewById(R.id.date);
        senderNumber.setText(data.getNumber());
        senderDate.setText(data.getDate());
        return rowView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                mDisplayedValues = (ArrayList<SMSData>) results.values;
                Intent result = new Intent(getContext(), ResultActivity.class);
                result.putExtra("results",(Serializable) mDisplayedValues);
                result.putExtra("all",(Serializable) mOriginalValues);
                context.startActivity(result);
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<SMSData> FilteredArrList = new ArrayList<SMSData>();

                if (mOriginalValues == null) {
                    mOriginalValues = new ArrayList<SMSData>(mDisplayedValues);
                }

                if (constraint == null || constraint.length() == 0) {
                    results.count = mOriginalValues.size();
                    results.values = mOriginalValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mOriginalValues.size(); i++) {
                        String data = mOriginalValues.get(i).getNumber();
                        data = data + mOriginalValues.get(i).getDate().toString();
                        for(String s:mOriginalValues.get(i).getBody()){
                            data = data + s;
                        }
                        if (data.toLowerCase().contains(constraint.toString())) {
                            SMSData s = new SMSData();
                            s.setNumber(mOriginalValues.get(i).getNumber());
                            s.setDate(mOriginalValues.get(i).getDate());
                            FilteredArrList.add(s);
                        }
                    }
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                Log.e("RESU",results.toString());
                return results;
            }
        };
        return filter;
    }
}