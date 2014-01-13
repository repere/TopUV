package fr.utt.topuv.sqlite;

import java.util.ArrayList;

import fr.utt.topuv.model.Uv;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class UvDb {
	 
	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "topuv_sqlite_uvs.db";
 
	private static final String TABLE_UVS = "table_uvs";
	
	private static final String COL_ID = "id";
	private static final int NUM_COL_ID = 0;
	
	private static final String COL_CODE = "code";
	private static final int NUM_COL_CODE = 1;
	
	private static final String COL_DESIGNATION = "designation";
	private static final int NUM_COL_DESIGNATION = 2;
	
	private static final String COL_CREDIT = "credit";
	private static final int NUM_COL_CREDIT = 3;
	
	private static final String COL_DESCRIPTION = "description";
	private static final int NUM_COL_DESCRIPTION = 4;
	
	private static final String COL_NOTE = "note";
	private static final int NUM_COL_NOTE = 5;
	
	private static final String COL_CATEGORIE = "categorie";
	private static final int NUM_COL_CATEGORIE = 6;
	
	private String[] allColumns = 
	{ 
		COL_ID,
		COL_CODE, 
		COL_DESIGNATION, 
		COL_CREDIT, 
		COL_DESCRIPTION, 
		COL_NOTE, 
		COL_NOTE, 
		COL_CATEGORIE 
	};
 
	private SQLiteDatabase bdd;
 
	private SQLiteBaseUv sQLiteBaseUv;
 
	public UvDb(Context context)
	{
		sQLiteBaseUv = new SQLiteBaseUv(context, NOM_BDD, null, VERSION_BDD);
	}
 
	public void open() throws SQLException
	{
		bdd = sQLiteBaseUv.getWritableDatabase();
	}
	
	public void read() throws SQLException
	{
		bdd = sQLiteBaseUv.getReadableDatabase();
	}
 
	public void close()
	{
		bdd.close();
	}
	
	public void onUpgrade()
	{
		sQLiteBaseUv.deleteAllAndRebuild(bdd);
	}
 
	public SQLiteDatabase getBDD()
	{
		return bdd;
	}
 
	public long insertUv(Uv inUv)
	{
		ContentValues values = new ContentValues();

		values.put(COL_ID, inUv.getId());
		values.put(COL_CODE, inUv.getCode());
		values.put(COL_DESIGNATION, inUv.getDesignation());
		values.put(COL_CREDIT, inUv.getCredit());
		values.put(COL_DESCRIPTION, inUv.getDescription());
		values.put(COL_NOTE, inUv.getNote());
		values.put(COL_CATEGORIE, inUv.getCategorie());

		return bdd.insert(TABLE_UVS, null, values);
	}
	
	// In case for the future, if we need to do update
	public long updateUv (int inId, Uv inUv)
	{
		ContentValues values = new ContentValues();
		
		values.put(COL_CODE, inUv.getCode());
		values.put(COL_DESIGNATION, inUv.getDesignation());
		values.put(COL_CREDIT, inUv.getCredit());
		values.put(COL_DESCRIPTION, inUv.getDescription());
		values.put(COL_NOTE, inUv.getNote());
		values.put(COL_CATEGORIE, inUv.getCategorie());

		return bdd.update(TABLE_UVS, values, COL_ID + " = " + inId, null);
	}
	
	public boolean isUvExist (int inId)
	{
		boolean result;
		Cursor cursor = bdd.query(TABLE_UVS, allColumns, COL_ID + " = " + inId, null, null, null, null);
		
		// If cursor is empty return false, else return true (bacause uv exists...)
		if(cursor.moveToFirst() == false)
		{
			result = false;
		}
		else
		{
			result = true;
		}
		
		return result;
	}
	 
	public ArrayList<Uv> getUvByCategory(String categorie) 
	{
	    ArrayList<Uv> arrayListUvs = new ArrayList<Uv>();

	    Cursor cursor = bdd.query(TABLE_UVS, allColumns, COL_CATEGORIE + " LIKE \"" + categorie +"\"", null, null, null, null);

	    cursor.moveToFirst();
	    
	    while (!cursor.isAfterLast()) 
	    {
	    	Uv uv = cursorToUv(cursor);
	    	arrayListUvs.add(uv);
	    	cursor.moveToNext();
	    }
	    
	    cursor.close();
	    return arrayListUvs;
	}
	
	public ArrayList<Uv> getAllUvByOrder(String order) 
	{
	    ArrayList<Uv> arrayListUvs = new ArrayList<Uv>();

	    Cursor cursor = bdd.query(TABLE_UVS, allColumns, null, null, null, null, COL_NOTE + " " + order);

	    cursor.moveToFirst();
	    
	    while (!cursor.isAfterLast()) 
	    {
	    	Uv uv = cursorToUv(cursor);
	    	arrayListUvs.add(uv);
	    	cursor.moveToNext();
	    }
	    
	    cursor.close();
	    return arrayListUvs;
	}
	
	public Uv getUvByUvCode(String code) 
	{
	    Cursor cursor = bdd.query(TABLE_UVS, allColumns, COL_CODE + " LIKE \"" + code +"\"", null, null, null, null);

	    cursor.moveToFirst();

	    return cursorToUv(cursor);
	}

	private Uv cursorToUv(Cursor c) 
	{
	    Uv uv = new Uv();
	    uv.setId(c.getInt(NUM_COL_ID));
		uv.setCode(c.getString(NUM_COL_CODE));
		uv.setDesignation(c.getString(NUM_COL_DESIGNATION));
		uv.setCredit(c.getInt(NUM_COL_CREDIT));
		uv.setDescription(c.getString(NUM_COL_DESCRIPTION));
		uv.setNote(c.getInt(NUM_COL_NOTE));
		uv.setCategorie(c.getString(NUM_COL_CATEGORIE));
	    return uv;
	}
}
