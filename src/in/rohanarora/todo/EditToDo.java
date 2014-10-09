package in.rohanarora.todo;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
		ItemOpenHelper ioh = new ItemOpenHelper(getApplicationContext());
		SQLiteDatabase db = ioh.getWritableDatabase();
		TodoItem item = OnStart.map.get(titleView);
		OnStart.list.remove(item);
		OnStart.map.remove(item.title);
		String[] titles = {item.title};
		db.delete(ItemOpenHelper.ITEM_TABLE_NAME, ItemOpenHelper.COLUMN_TITLE + " = ?" ,titles );
		TodoItem addItem = new TodoItem(title.getText().toString(), description.getText().toString(),datePick.getText().toString(), Color.WHITE);
		OnStart.list.add(addItem);
		OnStart.map.put(title.getText().toString(), addItem);
		OnStart.ad.notifyDataSetChanged();
		ItemOpenHelper.addToDatabase(db,ItemOpenHelper.ITEM_TABLE_NAME,item);
		this.finish();
	}
}
