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
		ItemOpenHelper ioh = new ItemOpenHelper(getApplicationContext());
		SQLiteDatabase db = ioh.getWritableDatabase();
		TodoItem item = OnStart.map.get(titleView);
		OnStart.list.remove(item);
		OnStart.map.remove(item.title);
		OnStart.ad.notifyDataSetChanged();
		String[] titles = {item.title};
		db.delete(ItemOpenHelper.ITEM_TABLE_NAME, ItemOpenHelper.COLUMN_TITLE + "= ?" , titles );
		Toast t = Toast.makeText(this, "Item Deleted", Toast.LENGTH_SHORT);
		t.show();
		this.finish();
	}
	
	public void done(View view){
		ItemOpenHelper ioh = new ItemOpenHelper(getApplicationContext());
		SQLiteDatabase db = ioh.getWritableDatabase();
		TodoItem item = OnStart.map.get(titleView);
		OnStart.list.remove(item);
		OnStart.map.remove(titleView);
		OnStart.ad.notifyDataSetChanged();
		String[] titles = {item.title};
		db.delete(ItemOpenHelper.ITEM_TABLE_NAME, ItemOpenHelper.COLUMN_TITLE + " = ?" , titles );
		Archived.listArchived.add(item);
		Archived.mapArchived.put(item.title, item);
		Archived.adArchived.notifyDataSetChanged();
		ItemOpenHelper.addToDatabase(db, ItemOpenHelper.ARCHIVED_ITEM_TABLE_NAME, item);
		Toast t = Toast.makeText(this, "Item Archived", Toast.LENGTH_SHORT);
		t.show();
		this.finish();
	}
}
