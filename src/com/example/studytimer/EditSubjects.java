package com.example.studytimer;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class EditSubjects extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_subjects);
		// Show the Up button in the action bar.
		setupActionBar();
		readFile();
	}

	private void readFile() {
		//StringBuilder text = new StringBuilder();
		
		try {
			//Open file and display it to screen
		    BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("subject_file")));
		    String line;
		    int count = 1;
		    TextView tv;
		    ImageView iv;
		    int id;
		    
		    while ((line = br.readLine()) != null && count <= 5) {
		    	String textnumber = "text"+count;
		    	String iconbox = "iconbox"+count;
		    	String icon_box = "icon_box"+count;
		    	String delete = "delete"+count;

		        id = getResources().getIdentifier(textnumber, "id", getPackageName());
		        tv = (TextView) findViewById(id); 
		        tv.setText(line);
		        tv.setVisibility(0);
		        line = br.readLine();
		        id = getResources().getIdentifier(iconbox, "id", getPackageName());
		        iv = (ImageView)findViewById(id);
		        iv.setImageResource(getImageId(this, line));
		        iv.setVisibility(0);
		        line = br.readLine();
		        id = getResources().getIdentifier(icon_box, "id", getPackageName());
		        iv = (ImageView)findViewById(id);
		        iv.setImageResource(getImageId(this, line));
		        iv.setVisibility(0);
		        id = getResources().getIdentifier(delete, "id", getPackageName());
		        iv = (ImageView)findViewById(id);
		        iv.setVisibility(0);
		        count++;
		    }
		    br.close();
		}
		catch (IOException e) {
		    //You'll need to add proper error handling here
		}

		//Find the view by its id
		//TextView tv = (TextView)findViewById(R.id.textView1);
		//Set the text
		//tv.setText(text);
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
		getMenuInflater().inflate(R.menu.edit_subjects, menu);
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
	public void deleteItem(View view) {
		String itemToDelete = view.getTag().toString();
		int count = 1;
		int itemNum = 0;  //This is the number of the subject to delete
		try {
		    itemNum = Integer.parseInt(itemToDelete);
		} catch(NumberFormatException nfe) {
		}
		String FILENAME = "temp_file";
        String line;
        FileOutputStream fos;
		// this makes a temp_file with all of subject_file except the one to delete
	    try {
	    	BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("subject_file")));
	    	fos = openFileOutput(FILENAME, Context.MODE_PRIVATE); //MODE_APPEND
	    	while ((line = br.readLine()) != null) {
	    		if (count == itemNum) {
	    			line = br.readLine();
	    			line = br.readLine();
	    		} else {
	    			line = line+'\n';
	    			fos.write(line.getBytes());
	    			line = br.readLine();
	    			line = line+'\n';
	    			fos.write(line.getBytes());
	    			line = br.readLine();
	    			line = line+'\n';
	    			fos.write(line.getBytes());
	    		}
	    		count++;
	    	}
	    	fos.close();
	    } catch (IOException e) {
		    //You'll need to add proper error handling here
		}
	    
	    FILENAME = "subject_file";
	    try {
	    	BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("temp_file")));
	    	fos = openFileOutput(FILENAME, Context.MODE_PRIVATE); //MODE_APPEND
	    	while ((line = br.readLine()) != null) {
	    		line = line+'\n';
	    		fos.write(line.getBytes());
	    		line = br.readLine();
	    		line = line+'\n';
	    		fos.write(line.getBytes());
	    		line = br.readLine();
	    		line = line+'\n';
	    		fos.write(line.getBytes());
	    	}
	    	fos.close();
	    } catch (IOException e) {
		    //You'll need to add proper error handling here
		}
	    Intent intent = new Intent(this, EditSubjects.class);
		startActivity(intent);
	}
	public void turnInvisible() {
		
	}
	public void createNewSubject(View view) {
		Intent intent = new Intent(this, CreateNewSubject.class);
		startActivity(intent);
	}
	public static int getImageId(Context context, String imageName) {
		return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
	 }
	public static int getResourceId(Context context, String imageName) {
		return context.getResources().getIdentifier("@+id/" + imageName, null, context.getPackageName());
	 }
}
