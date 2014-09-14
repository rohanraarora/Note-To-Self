package in.rohanarora.todo;

import java.util.LinkedHashSet;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ItemView extends Activity {
	
	String titleView ;
	String descriptionView;
	String dateView;
	int color;
	TextView title;
	TextView description;
	TextView date;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_view);
		title = (TextView) findViewById(R.id.titleView);
		description = (TextView) findViewById(R.id.descriptionView);
		date = (TextView) findViewById(R.id.dateView);
		Intent view = getIntent();
		titleView = view.getStringExtra("title");
		descriptionView = view.getStringExtra("description");
		dateView = view.getStringExtra("date");
		color = view.getIntExtra("color", Color.WHITE);
		title.setText(titleView);
		description.setText(descriptionView);
		date.setText(dateView);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.item_view, menu);
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
		else if (id == R.id.edit){
			Intent edit = new Intent(getApplicationContext() , EditToDo.class);
			edit.putExtra("title", titleView);
			edit.putExtra("description", descriptionView);
			edit.putExtra("date", dateView);
			startActivity(edit);
			this.finish();
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void delete(View view){
		OnStart.sp = getSharedPreferences("TODO Items", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = OnStart.sp.edit();
		Set<String> currentTitles = OnStart.sp.getStringSet("title", null);
		Set<String> newTitle = new LinkedHashSet<String>();
		newTitle.addAll(currentTitles);
		newTitle.remove(titleView);
		editor.putStringSet("title", newTitle);
		editor.remove(titleView);
		editor.commit();
		TodoItem item = OnStart.map.get(titleView);
		OnStart.list.remove(item);
		OnStart.map.remove(item.title);
		OnStart.ad.notifyDataSetChanged();
		Toast t = Toast.makeText(this, "Item Deleted", Toast.LENGTH_SHORT);
		t.show();
		this.finish();
	}
	
	public void done(View view){
		OnStart.sp = getSharedPreferences("TODO Items", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = OnStart.sp.edit();
		Set<String> currentTitles = OnStart.sp.getStringSet("title", null);
		Set<String> newTitle = new LinkedHashSet<String>();
		Set<String> doneTitles = OnStart.sp.getStringSet("done", null);
		Set<String> newDone = new LinkedHashSet<String>();
		if(doneTitles != null)
			newDone.addAll(doneTitles);
		newTitle.addAll(currentTitles);
		newTitle.remove(titleView);
		newDone.add(titleView);
		editor.putStringSet("title", newTitle);
		editor.putStringSet("done", newDone);
		editor.commit();
		TodoItem item = OnStart.map.get(titleView);
		OnStart.list.remove(item);
		OnStart.map.remove(titleView);
		OnStart.ad.notifyDataSetChanged();
		Archived.listArchived.add(item);
		Archived.mapArchived.put(item.title, item);
		Archived.adArchived.notifyDataSetChanged();
		Toast t = Toast.makeText(this, "Item Archived", Toast.LENGTH_SHORT);
		t.show();
		this.finish();
	}
}
