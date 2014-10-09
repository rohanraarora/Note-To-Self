package in.rohanarora.todo;
import java.util.Calendar;

import android.app.Activity;
import android.app.DialogFragment;
import android.database.sqlite.SQLiteDatabase;
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
		TodoItem item = new TodoItem(title.getText().toString(),description.getText().toString(),datePick.getText().toString(), Color.WHITE);
		if( !OnStart.map.containsKey(item.title) && !Archived.mapArchived.containsKey(item.title) ){
			OnStart.list.add(item);
			OnStart.map.put(item.title, item);
			OnStart.ad.notifyDataSetChanged();
			ItemOpenHelper ioh = new ItemOpenHelper(getApplicationContext());
			SQLiteDatabase db = ioh.getWritableDatabase();
			ItemOpenHelper.addToDatabase(db,ItemOpenHelper.ITEM_TABLE_NAME,item);
			this.finish();
		}
		else{
			Toast t = Toast.makeText(getApplicationContext(), "Item already present", Toast.LENGTH_SHORT);
			t.show();
		}
	}
}
