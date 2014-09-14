package in.rohanarora.todo;

import in.rohanarora.todo.R.layout;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomArrayAdapter extends ArrayAdapter<TodoItem> {
	
	private Context context;
	
	public CustomArrayAdapter(Context context, int resource, List<TodoItem> objects) {
		super(context, resource, objects);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View output = convertView;
		if(output == null){
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			output = inflater.inflate(R.layout.list_item, null);
		}
		final TodoItem item = getItem(position);
		TextView title = (TextView) output.findViewById(R.id.listTitle);
		final CheckBox done = (CheckBox) output.findViewById(R.id.checkBox1);
		title.setText(item.title);
		if(item.done == true){
			done.setChecked(true);
		}
		else{
			done.setChecked(false);
		}
		done.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView , boolean isChecked) {
				//OnStart.sp = getSharedPreferences("TODO Items", Context.MODE_PRIVATE);
				if(isChecked == true){
//					SharedPreferences.Editor editor = OnStart.sp.edit();
//					Set<String> currentTitles = OnStart.sp.getStringSet("title", null);
//					Set<String> newTitle = new LinkedHashSet<String>();
//					Set<String> doneTitles = OnStart.sp.getStringSet("done", null);
//					Set<String> newDone = new LinkedHashSet<String>();
//					if(doneTitles != null)
//					newDone.addAll(doneTitles);
//					newTitle.addAll(currentTitles);
//					newTitle.remove(item.title);
//					newDone.add(item.title);
//					editor.putStringSet("title", newTitle);
//					editor.putStringSet("done", newDone);
//					editor.commit();
//					OnStart.list.remove(item);
//					OnStart.map.remove(item.title);
//					OnStart.ad.notifyDataSetChanged();
//					Archived.listArchived.add(item);
//					Archived.mapArchived.put(item.title, item);
//					Archived.adArchived.notifyDataSetChanged();
					done.setChecked(true);
					item.done = true;
//					SharedPreferences.Editor editor = OnStart.sp.edit();
//					Set<String> completed = OnStart.sp.getStringSet("completed", null);
//					Set<String> newCompleted = new LinkedHashSet<String>();
//					newCompleted.addAll(completed);
//					newCompleted.add(item.title);
//					editor.putStringSet("completed", newCompleted);
//					editor.commit();
					Toast t = Toast.makeText(getContext(), "Task Completed", Toast.LENGTH_LONG);
					t.show();
				}
				else{
//					SharedPreferences.Editor editor = OnStart.sp.edit();
//					Set<String> currentTitles = OnStart.sp.getStringSet("title", null);
//					Set<String> newTitle = new LinkedHashSet<String>();
//					Set<String> doneTitles = OnStart.sp.getStringSet("done", null);
//					Set<String> newDone = new LinkedHashSet<String>();
//					if(doneTitles != null)
//						newDone.addAll(doneTitles);
//					newTitle.addAll(currentTitles);
//					newDone.remove(item.title);
//					newTitle.add(item.title);
//					editor.putStringSet("title", newTitle);
//					editor.putStringSet("done", newDone);
//					editor.commit();
//					OnStart.list.add(item);
//					OnStart.map.put(item.title, item);
//					OnStart.ad.notifyDataSetChanged();
//					Archived.listArchived.remove(item);
//					Archived.mapArchived.remove(item.title);
//					Archived.adArchived.notifyDataSetChanged();
		     		done.setChecked(false);
					item.done = false;
//					Toast t = Toast.makeText(getContext(), "Task Reset", Toast.LENGTH_SHORT);
//					t.show();
					
				}
			}
		});
		return output;
	}
}
