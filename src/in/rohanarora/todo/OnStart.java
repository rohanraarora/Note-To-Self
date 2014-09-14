package in.rohanarora.todo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;
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
		sp = getSharedPreferences("TODO Items", Context.MODE_PRIVATE);
		TodoItem firstTime = new TodoItem("Make a new ToDo Item" , " Delete this task and make a new ToDo item." , "Today" , Color.WHITE);
		Set<String> title = sp.getStringSet("title", null);
//		Set<String> completed = sp.getStringSet("completed", null);
		if(title != null){
			String[] titles = title.toArray(new String[title.size()]);
				for(int i = 0;i<titles.length;i++){
					Set<String> items = sp.getStringSet(titles[i], null);
					String[] todoitem = items.toArray(new String[items.size()]);
					Arrays.sort(todoitem);
					TodoItem item = new TodoItem(todoitem[0].substring(1), todoitem[1].substring(1), todoitem[2].substring(1), Color.WHITE);
//					if(completed.contains(item.title))
	//						item.done = true;
					list.add(item);
					map.put(item.title, item);
					ad.notifyDataSetChanged();
				}
		}
		else{
			Toast.makeText(this, "Add a new ToDo task", Toast.LENGTH_LONG).show();
//			SharedPreferences.Editor editor = sp.edit();
//			editor.putStringSet("title", null);
//			editor.putStringSet("Done", null);
//			editor.commit();
//			Set<String> currentItem = sp.getStringSet(firstTime.title, null);
//			Set<String> newItem = new LinkedHashSet<String>();
//			Set<String> currentTitles = sp.getStringSet("title", null);
//			Set<String> newTitle = new LinkedHashSet<String>();
//			if(currentTitles != null){
//				newTitle.addAll(currentTitles);
//			}
//			newTitle.add(firstTime.title);
//			newItem.add("1" + firstTime.title );
//			newItem.add("2" +firstTime.description);
//			newItem.add("3" + firstTime.date);
//			list.add(firstTime);
//			map.put(firstTime.title, firstTime);
//			ad.notifyDataSetChanged();
//			editor.putStringSet("title", newTitle);
//			editor.putStringSet(firstTime.title, newItem);
//			editor.commit();
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
