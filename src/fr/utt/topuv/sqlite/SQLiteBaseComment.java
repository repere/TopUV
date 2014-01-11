package fr.utt.topuv.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class SQLiteBaseComment extends SQLiteOpenHelper 
{
	private static final String TABLE_COMMENTS = "table_comments";
	private static final String COL_COMMENT_ID = "id";
	private static final String COL_COMMENT_ID_USER = "id_user";
	private static final String COL_COMMENT_ID_UV = "id_uv";
	private static final String COL_COMMENT_NOTE = "note";
	private static final String COL_COMMENT_COMMENT = "comment";
	private static final String COL_COMMENT_DATE = "date";
	private static final String COL_COMMENT_FIRST_NAME = "first_name";
	private static final String COL_COMMENT_LAST_NAME = "last_name";
	
	private static final String CREATE_DB_COMMENT = "CREATE TABLE " + TABLE_COMMENTS + " ("
			+ COL_COMMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ COL_COMMENT_ID_USER + " INTEGER, "
			+ COL_COMMENT_ID_UV + " INTEGER, "
			+ COL_COMMENT_NOTE + " INTEGER, "
			+ COL_COMMENT_COMMENT + " TEXT, "
			+ COL_COMMENT_DATE + " TEXT, "
			+ COL_COMMENT_FIRST_NAME + " TEXT, "
			+ COL_COMMENT_LAST_NAME + " TEXT);";
	
 
	public SQLiteBaseComment(Context context, String name, CursorFactory factory, int version) 
	{
		super(context, name, factory, version);
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		db.execSQL(CREATE_DB_COMMENT);
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS + ";");
		onCreate(db);
	}
	
	public void deleteAllAndRebuild(SQLiteDatabase db)
	{
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS + ";");
		onCreate(db);
	} 
}
