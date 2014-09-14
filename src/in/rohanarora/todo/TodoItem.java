package in.rohanarora.todo;

import java.util.Date;
public class TodoItem {

	String title;
	String description;
	String date;
	boolean done;
	int color;

	public TodoItem(String title,String description,String date , int color){
		this.title = title;
		this.description = description;
		this.date = date;
		this.done = false;
		this.color = color;
	}
}

