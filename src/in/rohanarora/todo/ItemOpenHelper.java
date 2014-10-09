package in.rohanarora.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;

public class ItemOpenHelper extends SQLiteOpenHelper {
	
	public static final String DATABASE_NAME = "todoItems";
	public static final int VERSION = 1;
	public static final String ITEM_TABLE_NAME = "items";
	public static final String ARCHIVED_ITEM_TABLE_NAME = "archivedItems";
	public static final String COLUMN_COMPANY_ID = "id";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_DESC = "description";
	public static final String COLUMN_DATE = "date";

	public ItemOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE " + ITEM_TABLE_NAME + " ( " + COLUMN_COMPANY_ID + " INTEGER PRIMARY KEY, " + COLUMN_TITLE + " TEXT, " + COLUMN_DESC + " TEXT, " + COLUMN_DATE + " TEXT)");
		db.execSQL("CREATE TABLE " + ARCHIVED_ITEM_TABLE_NAME + " ( " + COLUMN_COMPANY_ID + " INTEGER PRIMARY KEY, " + COLUMN_TITLE + " TEXT, " + COLUMN_DESC + " TEXT, " + COLUMN_DATE + " TEXT)");
		ContentValues values = new ContentValues();
		values.put(COLUMN_TITLE, "New TO-DO Item");
		values.put(COLUMN_DESC, "Make your own TO-DO item.Create a new item by clicking on add button");
		values.put(COLUMN_DATE, "Today");
		db.insert(ITEM_TABLE_NAME, null, values);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	
	public static void addToDatabase(SQLiteDatabase db ,String tableName , TodoItem item){
		ContentValues values = new ContentValues();
		values.put(ItemOpenHelper.COLUMN_TITLE, item.title);
		values.put(ItemOpenHelper.COLUMN_DESC, item.description);
		values.put(ItemOpenHelper.COLUMN_DATE, item.date);
		db.insert(tableName, null, values);
	}

}
