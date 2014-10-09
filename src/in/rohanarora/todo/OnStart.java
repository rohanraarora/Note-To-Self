package in.rohanarora.todo;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.AdapterView.OnItemClickListener;;

public class OnStart extends Activity {
	public static SharedPreferences sp;
	public static HashMap<String , TodoItem> map = new HashMap<String , TodoItem>();
	public static ArrayList<TodoItem> list;
	public static  CustomArrayAdapter ad;
	CheckBox toggle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_on_start);
		list = new ArrayList<TodoItem>();
		ad = new CustomArrayAdapter(this, 0, list);
		final ListView lv = (ListView) findViewById(R.id.list);
		lv.setAdapter(ad);
		ItemOpenHelper ioh = new ItemOpenHelper(this.getApplicationContext());
		SQLiteDatabase db =  ioh.getReadableDatabase();
		Cursor c = db.query(ItemOpenHelper.ITEM_TABLE_NAME, null, null, null, null, null, null);
		while(c.moveToNext()){
			String title = c.getString(c.getColumnIndex(ItemOpenHelper.COLUMN_TITLE));
			String description = c.getString(c.getColumnIndex(ItemOpenHelper.COLUMN_DESC));
			String date = c.getString(c.getColumnIndex(ItemOpenHelper.COLUMN_DATE));
			TodoItem item = new TodoItem(title, description, date, Color.WHITE);
			list.add(item);
			map.put(item.title, item);
			ad.notifyDataSetChanged();
		}
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long lid) {
				// TODO Auto-generated method stub
				TodoItem currentItem = (TodoItem) lv.getItemAtPosition(position);
				Intent itemView = new Intent(getApplicationContext(), ItemView.class);
				itemView.putExtra("title",currentItem.title);
				itemView.putExtra("description",currentItem.description);
				itemView.putExtra("date",currentItem.date);
				itemView.putExtra("color",currentItem.color);
				startActivity(itemView);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.on_start, menu);
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
		else if(id == R.id.add_item){
			Intent add = new Intent(this , AddToDo.class);
			startActivity(add);
		}
		else if(id == R.id.item1){
			Intent archived = new Intent(this , Archived.class);
			startActivity(archived);
		}
		return super.onOptionsItemSelected(item);
	}
}
