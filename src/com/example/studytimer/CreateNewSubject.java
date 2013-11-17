package com.example.studytimer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CreateNewSubject extends Activity {
	private TextView textview;
	private ImageView color;
	private ImageView icon;
	private String newColor = "color_purple";
	private String newIcon = "icon_book";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_new_subject);
		// Show the Up button in the action bar.
		setupActionBar();
		textview = (TextView) findViewById(R.id.editText1);
		color = (ImageView) findViewById(R.id.color_box);
		icon = (ImageView) findViewById(R.id.icon_box);
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
		getMenuInflater().inflate(R.menu.create_new_subject, menu);
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
	
	 public void addSubject(View view) throws IOException {
	        
		 String FILENAME = "subject_file";
	     String string = textview.getText().toString()+'\n';
	     if (string.matches("\n")) {
	     	Toast.makeText(this, "You did not enter a subject", Toast.LENGTH_SHORT).show();
	     	return;
	     }
	     FileOutputStream fos;
	     try {
	    	 newColor = newColor + '\n';
	    	 newIcon = newIcon + '\n';
	    	 fos = openFileOutput(FILENAME, Context.MODE_APPEND); //MODE_APPEND
	    	 fos.write(string.getBytes());
	    	 fos.write(newColor.getBytes());
	    	 fos.write(newIcon.getBytes());
	    	 fos.close();
	     } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Intent intent = new Intent(this, EditSubjects.class);
			startActivity(intent);   
	    }
	 //this method changes the color icon depending on which is selected
	 public void changeColor(View view) {
		 newColor = view.getTag().toString();
		 color.setImageResource(getImageId(this, newColor));
	 }
	 //this method changes the icon picture depending on which is selected
	 public void changeIcon(View view) {
		 newIcon = view.getTag().toString();
		 icon.setImageResource(getImageId(this, newIcon));
	 }
	 //helper method to return resource id for changeColor and changeId
	 public static int getImageId(Context context, String imageName) {
		return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
	 }
}
