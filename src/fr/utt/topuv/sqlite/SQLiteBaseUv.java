package fr.utt.topuv.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteBaseUv extends SQLiteOpenHelper 
{
	//Uv informations
	private static final String TABLE_UVS = "table_uvs";
	private static final String COL_UV_ID = "id";
	private static final String COL_UV_CODE = "code";
	private static final String COL_UV_DESIGNATION = "designation";
	private static final String COL_UV_CREDIT = "credit";
	private static final String COL_UV_DESCRIPTION = "description";
	private static final String COL_UV_NOTE = "note";
	private static final String COL_UV_CATEGORIE = "categorie";
	
	private static final String CREATE_DB_UV = "CREATE TABLE " + TABLE_UVS + " ("
			+ COL_UV_ID + " INTEGER PRIMARY KEY, " 
			+ COL_UV_CODE + " TEXT, "
			+ COL_UV_DESIGNATION + " TEXT, "
			+ COL_UV_CREDIT + " INTEGER, "
			+ COL_UV_DESCRIPTION + " TEXT, "
			+ COL_UV_NOTE + " REAL, "
			+ COL_UV_CATEGORIE + " TEXT);";
 
	public SQLiteBaseUv(Context context, String name, CursorFactory factory, int version) 
	{
		super(context, name, factory, version);
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		db.execSQL(CREATE_DB_UV);
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_UVS + ";");
		onCreate(db);
	}
	
	public void deleteAllAndRebuild(SQLiteDatabase db)
	{
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_UVS + ";");
		onCreate(db);
	} 
}
