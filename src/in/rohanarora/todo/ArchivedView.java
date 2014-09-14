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

public class ArchivedView extends Activity {
	
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
		setContentView(R.layout.activity_archived_view);
		title = (TextView) findViewById(R.id.titleViewArchived);
		description = (TextView) findViewById(R.id.descriptionViewArchived);
		date = (TextView) findViewById(R.id.dateViewArchived);
		Intent view = getIntent();
		titleView = view.getStringExtra("titleArchived");
		descriptionView = view.getStringExtra("descriptionArchived");
		dateView = view.getStringExtra("dateArchived");
		color = view.getIntExtra("color", Color.WHITE);
		title.setText(titleView);
		description.setText(descriptionView);
		date.setText(dateView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.archived_view, menu);
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
	
	public void deleteArchived(View view){
		OnStart.sp = getSharedPreferences("TODO Items", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = OnStart.sp.edit();
		Set<String> doneTitles = OnStart.sp.getStringSet("done", null);
		Set<String> newDone = new LinkedHashSet<String>();
		newDone.addAll(doneTitles);
		newDone.remove(titleView);
		editor.putStringSet("done", newDone);
		editor.remove(titleView);
		editor.commit();
		TodoItem item = Archived.mapArchived.get(titleView);
		Archived.listArchived.remove(item);
		Archived.mapArchived.remove(titleView);
		Archived.adArchived.notifyDataSetChanged();
		Toast t = Toast.makeText(this, "Item Deleted", Toast.LENGTH_SHORT);
		t.show();
		this.finish();
	}
	
	public void unarchive(View view){
		OnStart.sp = getSharedPreferences("TODO Items", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = OnStart.sp.edit();
		Set<String> currentTitles = OnStart.sp.getStringSet("title", null);
		Set<String> newTitle = new LinkedHashSet<String>();
		Set<String> doneTitles = OnStart.sp.getStringSet("done", null);
		Set<String> newDone = new LinkedHashSet<String>();
		if(doneTitles != null)
			newDone.addAll(doneTitles);
		newTitle.addAll(currentTitles);
		newDone.remove(titleView);
		newTitle.add(titleView);
		editor.putStringSet("title", newTitle);
		editor.putStringSet("done", newDone);
		editor.commit();
		TodoItem item = Archived.mapArchived.get(titleView);
		OnStart.list.add(item);
		OnStart.map.put(item.title, item);
		OnStart.ad.notifyDataSetChanged();
		Archived.listArchived.remove(item);
		Archived.mapArchived.remove(titleView);
		Archived.adArchived.notifyDataSetChanged();
		Toast t = Toast.makeText(this, "Item UnArchived", Toast.LENGTH_SHORT);
		t.show();
		this.finish();
	}
}
