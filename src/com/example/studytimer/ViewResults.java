package com.example.studytimer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class ViewResults extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_results);
		// Show the Up button in the action bar.
		setupActionBar();
 
        //pass context and data to the custom adapter
        MyArrayAdapter adapter = new MyArrayAdapter(this, generateData());
 
        // Get ListView from view
        ListView listView = (ListView) findViewById(R.id.listView1);
 
        // setListAdapter
        listView.setAdapter(adapter); 
	}
	
	private ArrayList<Model> generateData(){
		ArrayList<Subjects> subjects = new ArrayList<Subjects>();
        ArrayList<Model> models = new ArrayList<Model>();
        ArrayList<Model> totals = new ArrayList<Model>();
        int intTime = 0, intTotalTime = 0;
        String color = " ", icon = " ", title = " ", time = " ";
        String totalsTime = "0";
        boolean found = false;
   
        try {
			//Open file and display it to screen
		    BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("subject_file")));
		    String line;
		    
		    while ((line = br.readLine()) != null ) {
		    	title = line;
		    	color = br.readLine();
		    	icon = br.readLine();
		    	subjects.add(new Subjects(color, icon, title));
		    }
		    br.close();
		}
		catch (IOException e) {
		    //You'll need to add proper error handling here
		}
        try {
			//Open file and display it to screen
		    BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("data_file")));
		    String line;
		    
		    while ((line = br.readLine()) != null ) {
		    	title = line;
		    	time = br.readLine();
		    	br.readLine(); // flush time stamp
		    	for (int i = 0; i < subjects.size(); i++) {
		    		String item = subjects.get(i).getTitle();
		    		if (item.equals(title) ) {
		    			color = subjects.get(i).getColor();
		    			icon = subjects.get(i).getIcon();
		    		}
		    	}
		    	models.add(new Model(color, icon, title, time));
		    }
		    br.close();
		}
		catch (IOException e) {
		    //You'll need to add proper error handling here
		} 
        totals.add(new Model(models.get(0).getColor(), models.get(0).getIcon(), 
        		models.get(0).getTitle(), models.get(0).getCounter() ));
        for (int i = 1; i < models.size(); i++) {
        	found = false;
        	title = models.get(i).getTitle();
        	time = models.get(i).getCounter();
        	try {
        	    intTime = Integer.parseInt(time);
        	} catch(NumberFormatException nfe) {
        		//handle(error)
        	}
        	for(int j = 0; j < totals.size(); j++) {
        		String totalTitle = totals.get(j).getTitle();
        		if (totalTitle.equals(title)) 	{
        			totalsTime = totals.get(j).getCounter();
        			try {
                	    intTotalTime = Integer.parseInt(totalsTime);
                	} catch(NumberFormatException nfe) {
                		//handle(error)
                	}
            		intTime = intTime + intTotalTime;
            		totals.get(j).setCounter(String.valueOf(intTime));
            		found = true;
        		}
        	}
        	if (found == false) {
        		totals.add(new Model(models.get(i).getColor(), models.get(i).getIcon(), 
                		models.get(i).getTitle(), models.get(i).getCounter() ));
        	}
        }
        return totals;//models
    }

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_results, menu);
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

}
