package com.example.studytimer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.Spinner;
import android.widget.TextView;

public class TimerPage extends Activity implements OnItemSelectedListener{
	
   	private long time_elapsed;  //keeps track of time for pause
   	private boolean timer_running;  //keeps track of if the timer is running or not for pause button
   	private Spinner spinner1; //its the spinner
   	private TextView textView; //textView of currently studying subject
   	private int message;
   	private long start_time;
   	//Chronometer t = (Chronometer)findViewById(R.id.chronometer1);
   	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timer_page);
		// Show the Up button in the action bar.
		setupActionBar();
		// Link the spinner from layout page
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.subjects, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner1.setAdapter(adapter);
		// Add listener to spinner
		spinner1.setOnItemSelectedListener(this);
		// Link textView to textView on layout page
		textView = (TextView) findViewById(R.id.textView2);
	
		//initialize time_elapsed
		time_elapsed = 0; 
		//start the timer
		((Chronometer) findViewById(R.id.chronometer1)).start();
		//initialize that timer is running
		start_time = ((Chronometer) findViewById(R.id.chronometer1)).getBase();
		timer_running = true; 
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		message = extras.getInt("selected");
		spinner1.setSelection(message, false);

	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timer_page, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	//starts the timer
	public void startChronometer(View view) {
        ((Chronometer) findViewById(R.id.chronometer1)).start();
    }
	//stops the timer
    public void stopChronometer(View view) throws IOException {
        ((Chronometer) findViewById(R.id.chronometer1)).stop();
        timer_running = false;
        
        String FILENAME = "hello_file";
        String string = Long.toString(SystemClock.elapsedRealtime() - ((Chronometer) findViewById(R.id.chronometer1)).getBase());
        //String string = ( (Chronometer) findViewById(R.id.chronometer1)).getFormat();
        
        FileOutputStream fos;
		try {
			fos = openFileOutput(FILENAME, Context.MODE_PRIVATE); //MODE_APPEND
			fos.write(string.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
        
    }
    //Pauses or Resumes the timer depending on status of timer_running
    //changes the text on the pause button to resume when paused
    public void pauseChronometer(View view) {
    	if (timer_running) {
    		time_elapsed = SystemClock.elapsedRealtime() - ((Chronometer) findViewById(R.id.chronometer1)).getBase();
            ((Chronometer) findViewById(R.id.chronometer1)).stop();
            timer_running = false;
            TextView buttonText = (TextView) findViewById(R.id.textView1);
            buttonText.setText("Resume");
    	} else {
    		((Chronometer) findViewById(R.id.chronometer1)).setBase(SystemClock.elapsedRealtime() - time_elapsed);
    		((Chronometer) findViewById(R.id.chronometer1)).start();
    		timer_running = true;
    		TextView buttonText = (TextView) findViewById(R.id.textView1);
            buttonText.setText("Pause");
    	}
    }
  
    @Override
    // Changes text based on spinner selection
    public void onItemSelected(AdapterView<?> parent, View view, 
            int pos, long id) {
    	String value = (String) parent.getItemAtPosition(pos);
        textView.setText(value);    
        ((Chronometer) findViewById(R.id.chronometer1)).start();
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
  	// TODO Auto-generated method stub
    }
    
}
