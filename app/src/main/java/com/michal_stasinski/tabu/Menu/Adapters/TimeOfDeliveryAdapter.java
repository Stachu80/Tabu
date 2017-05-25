package com.michal_stasinski.tabu.Menu.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.michal_stasinski.tabu.Menu.Models.MenuItemProduct;
import com.michal_stasinski.tabu.Menu.Models.TimeListItem;
import com.michal_stasinski.tabu.R;

import java.util.ArrayList;

/**
 * Created by win8 on 23.05.2017.
 */

public class TimeOfDeliveryAdapter extends BaseAdapter {

    private ArrayList<TimeListItem> arr;
    private ArrayList<Integer> markSignArr;
    private Context mContext;
    private String mark = "";
    private int positionInMenuListView;
    private LayoutInflater mInflater;

    public TimeOfDeliveryAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.arr = new ArrayList<TimeListItem>();
    }

    public void addItem(final TimeListItem item) {
        this.arr.add(item);
        Log.i("informacja", "TimeOfDeliveryAdapter " + item.getTime());
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int position) {
        return arr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolderItem viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.pop_up_row, null);

            viewHolder = new ViewHolderItem();
            viewHolder.hour = (TextView)  convertView.findViewById(R.id.pizza_size_text);
            viewHolder.check = (TextView) convertView.findViewById(R.id.checkmark);
            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolderItem) convertView.getTag();
        }
        Log.i("informacja", "fffff  "+convertView);
         viewHolder.hour.setText(this.arr.get(position).getTime());

        /*if (this.markSignArr.get(position) == 1) {
            viewHolder.check.setText("\u2713");
            viewHolder.hour.setTextColor(Color.GRAY);
            viewHolder.check.setTextColor(Color.rgb(255, 126, 0));
        } else {
            viewHolder.check.setText("");
            viewHolder.hour.setTextColor(Color.BLACK);
            viewHolder.check.setTextColor(Color.BLACK);
        }*/
       return convertView;
    }

    static class ViewHolderItem {

        TextView hour;
        TextView check;
    }

    public ArrayList<Integer> getMarkSignArr() {
        return markSignArr;
    }

    public void setMarkSignArr(ArrayList<Integer> markSignArr) {
        this.markSignArr = markSignArr;
    }
}

