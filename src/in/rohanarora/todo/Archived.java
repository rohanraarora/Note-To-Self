package in.rohanarora.todo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
		OnStart.sp = getSharedPreferences("TODO Items", Context.MODE_PRIVATE);
		Set<String> title = OnStart.sp.getStringSet("done", null);
//		Set<String> completed = OnStart.sp.getStringSet("completed", null);
		if(title != null){
			String[] titles = title.toArray(new String[title.size()]);
			for(int i = 0;i<titles.length;i++){
				Set<String> items = OnStart.sp.getStringSet(titles[i], null);
				String[] todoitem = items.toArray(new String[items.size()]);
				Arrays.sort(todoitem);
				TodoItem item = new TodoItem(todoitem[0].substring(1), todoitem[1].substring(1), todoitem[2].substring(1), Color.WHITE);
//				if(completed.contains(item.title))
//					item.done = true;
				listArchived.add(item);
				mapArchived.put(item.title, item);
				adArchived.notifyDataSetChanged();
			}
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
