package in.rohanarora.todo;

import java.util.Calendar;
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

public class EditToDo extends Activity {

	EditText title;
	EditText description;
	Button datePick;
	String titleView;
	String descriptionView;
	String dateView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_to_do);
		title = (EditText) findViewById(R.id.titleEdit);
		description = (EditText) findViewById(R.id.descriptionEdit);
		datePick = (Button) findViewById(R.id.datePickEdit);
		Intent get = getIntent();
		titleView = get.getStringExtra("title");
		title.setText(titleView);
		descriptionView = get.getStringExtra("description");
		description.setText(descriptionView);
		dateView = get.getStringExtra("date");
		datePick.setText(dateView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_to_do, menu);
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
	
	public void pickDateEdit(View view){
		DialogFragment datePicker = new DatePickerFragment();
		datePicker.show(getFragmentManager(), "datePickerEdit");
	}
	
	public void discardEdit(View view){
		this.finish();
	}
	
	public void saveEdit(View view){
		OnStart.sp = getSharedPreferences("TODO Items", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = OnStart.sp.edit();
		Set<String> currentTitles = OnStart.sp.getStringSet("title", null);
		Set<String> newTitle = new LinkedHashSet<String>();
		Set<String> currentItem = OnStart.sp.getStringSet(title.getText().toString(), null);
		Set<String> newItem = new LinkedHashSet<String>();
		newTitle.addAll(currentTitles);
		newTitle.remove(titleView);
		editor.remove(titleView);
		if(!newTitle.contains(title.getText().toString())){
		newTitle.add(title.getText().toString());
		newItem.add("1" + title.getText().toString() );
		newItem.add("2" + description.getText().toString());
		newItem.add("3" + datePick.getText().toString());
		editor.putStringSet("title", newTitle);
		editor.putStringSet(title.getText().toString(), newItem);
		editor.commit();
		TodoItem item = OnStart.map.get(titleView);
		OnStart.list.remove(item);
		OnStart.map.remove(item.title);
		TodoItem addItem = new TodoItem(title.getText().toString(), description.getText().toString(),datePick.getText().toString(), Color.WHITE);
		OnStart.list.add(addItem);
		OnStart.map.put(title.getText().toString(), addItem);
		OnStart.ad.notifyDataSetChanged();
		this.finish();
		}
		else{
			Toast t = Toast.makeText(this, "Item with same Title Exist", Toast.LENGTH_LONG);
			t.show();
		}
		
	}
}
