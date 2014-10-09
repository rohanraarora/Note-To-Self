package in.rohanarora.todo;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
		ItemOpenHelper ioh = new ItemOpenHelper(getApplicationContext());
		SQLiteDatabase db = ioh.getWritableDatabase();
		TodoItem item = Archived.mapArchived.get(titleView);
		Archived.listArchived.remove(item);
		Archived.mapArchived.remove(titleView);
		Archived.adArchived.notifyDataSetChanged();
		String[] titles = {item.title};
		db.delete(ItemOpenHelper.ARCHIVED_ITEM_TABLE_NAME, ItemOpenHelper.COLUMN_TITLE + " = ?" , titles );
		Toast t = Toast.makeText(this, "Item Deleted", Toast.LENGTH_SHORT);
		t.show();
		this.finish();
	}
	
	public void unarchive(View view){
		ItemOpenHelper ioh = new ItemOpenHelper(getApplicationContext());
		SQLiteDatabase db = ioh.getWritableDatabase();
		TodoItem item = Archived.mapArchived.get(titleView);
		OnStart.list.add(item);
		OnStart.map.put(item.title, item);
		OnStart.ad.notifyDataSetChanged();
		ItemOpenHelper.addToDatabase(db, ItemOpenHelper.ITEM_TABLE_NAME, item);
		Archived.listArchived.remove(item);
		Archived.mapArchived.remove(titleView);
		Archived.adArchived.notifyDataSetChanged();
		String[] titles = {item.title};
		db.delete(ItemOpenHelper.ARCHIVED_ITEM_TABLE_NAME, ItemOpenHelper.COLUMN_TITLE + " = ?" , titles );
		Toast t = Toast.makeText(this, "Item UnArchived", Toast.LENGTH_SHORT);
		t.show();
		this.finish();
	}
}
