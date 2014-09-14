package in.rohanarora.todo;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddToDo extends Activity {
	
	EditText title;
	EditText description;
//	String dateAdd;
//	String titleAdd;
//	String descriptionAdd;
	public static Button datePick; 
	TextView header;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_to_do);
		title = (EditText) findViewById(R.id.title);
		description = (EditText) findViewById(R.id.description);
		header = (TextView) findViewById(R.id.textAddNewItem);
		datePick = (Button) findViewById(R.id.datePick);
		Calendar c = Calendar.getInstance();
		datePick.setText(c.get(Calendar.DAY_OF_MONTH) + "/" + (c.get(Calendar.MONTH)+1) + "/" + c.get(Calendar.YEAR));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_to_do, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void pickDate(View view){
		DialogFragment datePicker = new DatePickerFragment();
		datePicker.show(getFragmentManager(), "datePicker");
		
	}
	
	public void discard(View view){
		this.finish();
	}
	public void save(View view){
		OnStart.sp = getSharedPreferences("TODO Items", Context.MODE_PRIVATE);
		Set<String> currentItem = OnStart.sp.getStringSet(title.getText().toString(), null);
		Set<String> newItem = new LinkedHashSet<String>();
		Set<String> currentTitles = OnStart.sp.getStringSet("title", null);
		Set<String> newTitle = new LinkedHashSet<String>();
		Set<String> doneTitles = OnStart.sp.getStringSet("done", null);
		Set<String> newDone = new LinkedHashSet<String>();
		if(doneTitles != null)
			newDone.addAll(doneTitles);
//		Set<String> currentDecription = OnStart.sp.getStringSet("description", null);
//		Set<String> newDecription = new LinkedHashSet<String>();
//		Set<String> currentDate = OnStart.sp.getStringSet("date", null);
//		Set<String> newDate = new LinkedHashSet<String>();
//		int currentCount = OnStart.sp.getInt("count",0);
//		int count = currentCount;
		SharedPreferences.Editor editor = OnStart.sp.edit();
		if(currentTitles != null){
			newTitle.addAll(currentTitles);
		}
//		if(currentDecription != null){
//			newDecription.addAll(currentDecription);
//		}
//		if(currentDate != null){
//			newDate.addAll(currentDate);
//		}
		if(!newTitle.contains(title.getText().toString()) && !newDone.contains(title.getText().toString())){
			
//			count++;
			newTitle.add(title.getText().toString());
			newItem.add("1" + title.getText().toString() );
			newItem.add("2" + description.getText().toString());
			newItem.add("3" + datePick.getText().toString());
//			newDecription.add(description.getText().toString());
//			newDate.add(count + datePick.getText().toString());
			TodoItem addItem = new TodoItem(title.getText().toString(), description.getText().toString(), datePick.getText().toString(), Color.WHITE);
			OnStart.list.add(addItem);
			OnStart.map.put(title.getText().toString(), addItem);
			OnStart.ad.notifyDataSetChanged();
			editor.putStringSet("title", newTitle);
//			editor.putStringSet("description", newDecription);
//			editor.putStringSet("date", newDate);
//			editor.putInt("count", count);
			editor.putStringSet(title.getText().toString(), newItem);
			editor.commit();
			this.finish();
		}
		else{
			Toast t = Toast.makeText(this, "Item with same Title Exist", Toast.LENGTH_LONG);
			t.show();
		}
	}
}
