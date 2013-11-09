package com.example.studytimer;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class SpinnerActivity extends Activity implements OnItemSelectedListener {
   
    
    public void onItemSelected(AdapterView<?> parent, View view, 
            int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    	TextView studyText = (TextView) findViewById(R.id.textView2);
        studyText.setText("Changed");
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}