package com.example.studytimer;

import java.util.ArrayList;
 
//import com.hmkcode.android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class MyArrayAdapter extends ArrayAdapter<Model> {
 
        private final Context context;
        private final ArrayList<Model> modelsArrayList;
 
        public MyArrayAdapter(Context context, ArrayList<Model> modelsArrayList) {
 
            super(context, R.layout.target_item, modelsArrayList);
 
            this.context = context;
            this.modelsArrayList = modelsArrayList;
        }
 
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
 
            // 1. Create inflater
            LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
            // 2. Get rowView from inflater
 
            View rowView = null;
           
            rowView = inflater.inflate(R.layout.target_item, parent, false);
 
            // 3. Get icon,title & counter views from the rowView
            ImageView imgView = (ImageView) rowView.findViewById(R.id.icon);
            ImageView imgView2 = (ImageView) rowView.findViewById(R.id.color);
            TextView titleView = (TextView) rowView.findViewById(R.id.label);
            TextView counterView = (TextView) rowView.findViewById(R.id.counter);
 
            // 4. Set the text for textView
            //imgView.setImageResource(modelsArrayList.get(position).getIcon());
            imgView.setImageResource(getImageId(context, modelsArrayList.get(position).getIcon()));
            imgView2.setImageResource(getImageId(context, modelsArrayList.get(position).getColor()));
            //imgView2.setImageResource(modelsArrayList.get(position).getIcon());
            //iv.setImageResource(getImageId(this, line));
            titleView.setText(modelsArrayList.get(position).getTitle());
            counterView.setText(modelsArrayList.get(position).getCounter());
            
 
            // 5. retrn rowView
            return rowView;
        }
        public static int getImageId(Context context, String imageName) {
    		return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    	 }
}