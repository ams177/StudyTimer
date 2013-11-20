package com.example.studytimer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
import android.widget.Toast;

public class TimerPage extends Activity implements OnItemSelectedListener{
	
   	private long time_elapsed;  //keeps track of time for pause
   	private boolean timer_running;  //keeps track of if the timer is running or not for pause button
   	private Spinner spinner1; //its the spinner
   	private TextView textView; //textView of currently studying subject
   	private int message;  //gets the subject to be studied from Main Activity page
   	private Chronometer timer; //this is the timer
   	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timer_page);
		// Show the Up button in the action bar.
		setupActionBar();
		// Link the spinner from layout page
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		
		List<String> SpinnerArray =  new ArrayList<String>();
		
		try {
			//Open file and display it to screen
		    BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("subject_file")));
		    String line;

		    while ((line = br.readLine()) != null) {
		    	SpinnerArray.add(line);
		    	line = br.readLine();  //flush out the icon
		    	line = br.readLine();  //flush out the color
		    }
		    br.close();
		}
		catch (IOException e) {
		    //You'll need to add proper error handling here
			//handle(error)
		}
		
		// link up to the spinner
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SpinnerArray);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner1.setAdapter(adapter);
		
	    //Start the listener on the spinner
		spinner1.setOnItemSelectedListener(this);
		
		// Add listener to spinner
		spinner1.setOnItemSelectedListener(this);
		// Link textView to textView on layout page
		textView = (TextView) findViewById(R.id.textView2);
		timer = (Chronometer) findViewById (R.id.chronometer1);
		//initialize time_elapsed
		time_elapsed = 0; 
		//start the timer
		timer.start();
		//initialize that timer is running
		timer_running = true; 
		//Get data from MainAvtivity page
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
        timer.start();
    }
	//stops the timer, saves the time studied, subject studied and a time stamp to an internal file
    public void stopChronometer(View view) throws IOException {
        timer.stop();
        timer_running = false;
        saveData();
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
        
    }
    //Pauses or Resumes the timer depending on status of timer_running
    //changes the text on the pause button to resume when paused
    public void pauseChronometer(View view) {
    	if (timer_running) {
    		time_elapsed = SystemClock.elapsedRealtime() - timer.getBase();
            timer.stop();
            timer_running = false;
            TextView buttonText = (TextView) findViewById(R.id.text_pause);
            buttonText.setText("Resume");
    	} else {
    		timer.setBase(SystemClock.elapsedRealtime() - time_elapsed);
    		timer.start();
    		timer_running = true;
    		TextView buttonText = (TextView) findViewById(R.id.text_pause);
            buttonText.setText("Pause");
    	}
    }
  
    @Override
    // Changes text based on spinner selection
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
    	String value = (String) parent.getItemAtPosition(pos);
    	if ((textView.getText()==value) || (((SystemClock.elapsedRealtime() - timer.getBase())/1000) <= 1) ) { //do nothing cause the item selected didnt change.
    		timer.start();
    		textView.setText(value); 
    	} else {	//item selected changed, need to save time and restart timer
    		try {
    			saveData();
    		} catch (IOException e) {
    			// handle error
    		}
    		//get data read for the pop up
    	    long l_time = (SystemClock.elapsedRealtime() - timer.getBase())/1000;
    	    String studyTime = computeTime(l_time);
    	    String congrats = "You studied "+textView.getText()+" for "+studyTime;
    	    Toast.makeText(this, congrats, Toast.LENGTH_SHORT).show();
    	    //reset timer and update textView
    	    textView.setText(value);
    	    timer.setBase(SystemClock.elapsedRealtime());
    		time_elapsed = 0;
    	    timer.start();
    	}
       
    }
    private String computeTime(long l_time){
    	String studyTime = " ";
    	long hours, mins, secs;
    	
    	if (l_time > 3600) {
    		hours = l_time / 3600;
    		l_time = l_time % 3600;
    		mins = l_time / 60;
    		secs = l_time % 60;
    		studyTime = Long.toString(hours) + " hour " + Long.toString(mins) + " minutes and " + Long.toString(secs) + " seconds.";
    	} else if (l_time > 60 ){
    		mins = l_time / 60;
    		secs = l_time % 60;
    		studyTime = Long.toString(mins) + " minutes and " + Long.toString(secs) + " seconds.";
    	} else {
    		studyTime =  Long.toString(l_time) + " seconds.";
    	}
    	return studyTime;
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
  	// TODO Auto-generated method stub
    }
    
    //this method saves the time studied in seconds, the subject name and a timestamp to a file 
    private void saveData( ) throws IOException {
    	String FILENAME = "data_file";
        String string = Long.toString((SystemClock.elapsedRealtime() - timer.getBase())/1000)+","+textView.getText()+","+System.currentTimeMillis()+"\n";
        
        FileOutputStream fos;
		try {
			fos = openFileOutput(FILENAME, Context.MODE_APPEND); //MODE_APPEND
			fos.write(string.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
