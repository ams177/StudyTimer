package com.example.studytimer;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
//import com.hmkcode.android.R;
 
public class SubjectArrayAdapter extends ArrayAdapter<Subjects> {
 
        private final Context context;
        private final ArrayList<Subjects> subjectsArrayList;
 
        public SubjectArrayAdapter(Context context, ArrayList<Subjects> subjectsArrayList) {
 
            super(context, R.layout.target_item, subjectsArrayList);
 
            this.context = context;
            this.subjectsArrayList = subjectsArrayList;
        }
 
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
 
            // 1. Create inflater
            LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
            // 2. Get rowView from inflater
 
            View rowView = null;
           
            rowView = inflater.inflate(R.layout.row_layout, parent, false);
 
            // 3. Get icon,title & counter views from the rowView
            ImageView imgView = (ImageView) rowView.findViewById(R.id.icon);
            ImageView imgView2 = (ImageView) rowView.findViewById(R.id.color);
            TextView titleView = (TextView) rowView.findViewById(R.id.label);
            ImageButton imgButton = (ImageButton) rowView.findViewById(R.id.delete);
            //TextView counterView = (TextView) rowView.findViewById(R.id.counter);
 
            // 4. Set the text for textView
            imgView.setImageResource(getImageId(context, subjectsArrayList.get(position).getIcon()));
            imgView2.setImageResource(getImageId(context, subjectsArrayList.get(position).getColor()));
            titleView.setText(subjectsArrayList.get(position).getTitle());
            imgButton.setTag(position+1);
           // counterView.setText(modelsArrayList.get(position).getCounter());
            
 
            // 5. retrn rowView
            return rowView;
        }
        public static int getImageId(Context context, String imageName) {
    		return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    	 }
}
