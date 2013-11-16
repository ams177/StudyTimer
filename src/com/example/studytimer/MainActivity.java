package com.example.studytimer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends Activity implements OnItemSelectedListener {
	private Spinner spinner1; //its the spinner
	private int selected_value;
	public final static String EXTRA_MESSAGE = "1";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//This is the spinner
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		
		List<String> SpinnerArray =  new ArrayList<String>();
		
		try {
			//Open file and display it to screen
		    BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("subject_file")));
		    String line;

		    while ((line = br.readLine()) != null) {
		    	SpinnerArray.add(line);
		    }
		    br.close();
		}
		catch (IOException e) {
		    //You'll need to add proper error handling here
		}
		
		// link up to the spinner
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SpinnerArray);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner1.setAdapter(adapter);
		
	    //Start the listener on the spinner
		spinner1.setOnItemSelectedListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void startTimer(View view) {
		Intent intent = new Intent(this, TimerPage.class);
		intent.putExtra("selected", (int) selected_value);
		startActivity(intent); 
	}
	
	public void editSubjects(View view) {
		Intent intent = new Intent(this, EditSubjects.class);
		startActivity(intent);
	}
	
	public void viewResults(View view) {
		Intent intent = new Intent(this, ViewResults.class);
		startActivity(intent);
	}
    // Changes text based on spinner selection
	@Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
    	selected_value =  pos;
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
  	// TODO Auto-generated method stub
    }
}
