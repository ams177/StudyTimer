package com.example.studytimer;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class EditSubjects extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_subjects);
		// Show the Up button in the action bar.
		setupActionBar();
		//readFile();
		//pass context and data to the custom adapter
        SubjectArrayAdapter adapter = new SubjectArrayAdapter(this, generateData());
 
        // Get ListView from view
        ListView listView = (ListView) findViewById(R.id.listView1);
 
        // setListAdapter
        listView.setAdapter(adapter); 
	}
	
	private ArrayList<Subjects> generateData(){
		ArrayList<Subjects> subjects = new ArrayList<Subjects>();
        String color = " ", icon = " ", title = " ";
   
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
        return subjects;
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
	//This method is called by the delete button, it pops up the confirmation
	public void deleteItem(final View view) {
		AlertDialog.Builder builder = new AlertDialog.Builder(EditSubjects.this);
        builder.setTitle("Warning");
        builder.setMessage("Deleting the Subject will clear all the time data associated with that Subject");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
            	String itemToDelete = view.getTag().toString();
                actuallyDeleteItems(itemToDelete);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
            }
        });

        builder.show(); //To show the AlertDialog
	}
	
	//This method deletes the subject from subject_file and then calls deleteData 
	private void actuallyDeleteItems(String itemToDelete) {    
		
		String itemName = "";
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
	    			itemName = line; // this should be the name of the subject to delete
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
	    	br.close();
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
	    	br.close();
	    } catch (IOException e) {
		    //You'll need to add proper error handling here
		}
	    deleteData(itemName);
	    
	    Intent intent = new Intent(this, EditSubjects.class);
		startActivity(intent);
	}
	
	//this method deletes the time data that was associated with the deleted subject
	private void deleteData(String itemName) {
		String FILENAME = "temp_file";
        String line;
        FileOutputStream fos;
		// this makes a temp_file with all of subject_file except the one to delete
	    try {
	    	BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("data_file")));
	    	fos = openFileOutput(FILENAME, Context.MODE_PRIVATE); //MODE_APPEND
	    	while ((line = br.readLine()) != null) {
	    		if (itemName.equals(line)) {
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
	    	}
	    	fos.close();
	    	br.close();
	    } catch (IOException e) {
		    //You'll need to add proper error handling here
		}
	    
	    FILENAME = "data_file";
	    try {
	    	BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("temp_file")));
	    	fos = openFileOutput(FILENAME, Context.MODE_PRIVATE); //MODE_APPEND
	    	while ((line = br.readLine()) != null) {
	    		line = line+'\n';
	    		fos.write(line.getBytes());
	    		line = br.readLine()+'\n';
	    		fos.write(line.getBytes());
	    		line = br.readLine()+'\n';
	    		fos.write(line.getBytes());
	    	}
	    	fos.close();
	    	br.close();
	    } catch (IOException e) {
		    //You'll need to add proper error handling here
		}
	}
	
	public void createNewSubject(View view) {
		Intent intent = new Intent(this, CreateNewSubject.class);
		startActivity(intent);
	}
	/* These are no longer used but they can still be useful so saving them here
	private static int getImageId(Context context, String imageName) {
		return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
	}
	private static int getResourceId(Context context, String imageName) {
		return context.getResources().getIdentifier("@+id/" + imageName, null, context.getPackageName());
	} */
}
