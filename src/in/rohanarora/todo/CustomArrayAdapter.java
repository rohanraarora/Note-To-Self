package in.rohanarora.todo;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
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
				if(isChecked == true){

					done.setChecked(true);
					item.done = true;
					Toast t = Toast.makeText(getContext(), "Task Completed", Toast.LENGTH_LONG);
					t.show();
				}
				else{
		     		done.setChecked(false);
					item.done = false;
				}
			}
		});
		return output;
	}
}
