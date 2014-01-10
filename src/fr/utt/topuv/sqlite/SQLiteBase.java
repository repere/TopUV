package fr.utt.topuv.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteBase extends SQLiteOpenHelper 
{
	 
	private static final String TABLE_UVS = "table_uvs";
	private static final String COL_ID = "id";
	private static final String COL_CODE = "code";
	private static final String COL_DESIGNATION = "designation";
	private static final String COL_CREDIT = "credit";
	private static final String COL_DESCRIPTION = "description";
	private static final String COL_NOTE = "note";
	private static final String COL_CATEGORIE = "categorie";

 
	private static final String CREATE_BDD = "CREATE TABLE " + TABLE_UVS + " ("
	+ COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
	+ COL_CODE + " TEXT, "
	+ COL_DESIGNATION + " TEXT, "
	+ COL_CREDIT + " INTEGER, "
	+ COL_DESCRIPTION + " TEXT, "
	+ COL_NOTE + " REAL, "
	+ COL_CATEGORIE + " TEXT);";
 
	public SQLiteBase(Context context, String name, CursorFactory factory, int version) 
	{
		super(context, name, factory, version);
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		db.execSQL(CREATE_BDD);
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		db.execSQL("DROP TABLE " + TABLE_UVS + ";");
		onCreate(db);
	}
	
	public void deleteAllAndRebuild(SQLiteDatabase db)
	{
		db.execSQL("DROP TABLE " + TABLE_UVS + ";");
		onCreate(db);
	}
	 
}
