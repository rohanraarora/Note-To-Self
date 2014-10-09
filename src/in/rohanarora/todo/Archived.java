package in.rohanarora.todo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class Archived extends Activity {
	public static ArrayList<TodoItem> listArchived;
	public static  CustomArrayAdapter adArchived;
	public static HashMap<String, TodoItem> mapArchived =  new HashMap<String, TodoItem>();
	CheckBox toggle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_archived);
		listArchived = new ArrayList<TodoItem>();
		adArchived = new CustomArrayAdapter(this, 0, listArchived);
		final ListView lvArchived = (ListView) findViewById(R.id.archived);
		lvArchived.setAdapter(adArchived);
		ItemOpenHelper ioh = new ItemOpenHelper(this.getApplicationContext());
		SQLiteDatabase db =  ioh.getReadableDatabase();
		Cursor c = db.query(ItemOpenHelper.ARCHIVED_ITEM_TABLE_NAME, null, null, null, null, null, null);
		while(c.moveToNext()){
			String title = c.getString(c.getColumnIndex(ItemOpenHelper.COLUMN_TITLE));
			String description = c.getString(c.getColumnIndex(ItemOpenHelper.COLUMN_DESC));
			String date = c.getString(c.getColumnIndex(ItemOpenHelper.COLUMN_DATE));
			TodoItem item = new TodoItem(title, description, date, Color.WHITE);
			listArchived.add(item);
			mapArchived.put(item.title, item);
			adArchived.notifyDataSetChanged();
		}
		lvArchived.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long lid) {
				// TODO Auto-generated method stub
				TodoItem currentItem = (TodoItem) lvArchived.getItemAtPosition(position);
				Intent itemView = new Intent(getApplicationContext(), ArchivedView.class);
				itemView.putExtra("titleArchived",currentItem.title);
				itemView.putExtra("descriptionArchived",currentItem.description);
				itemView.putExtra("dateArchived",currentItem.date);
				itemView.putExtra("colorArchived",currentItem.color);
				startActivity(itemView);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.archived, menu);
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
}
