package fr.utt.topuv.sqlite;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import fr.utt.topuv.model.Note;

public class CommentDb {
	
	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "topuv_comments.db";
 
	private static final String TABLE_COMMENTS = "table_comments";
	
	private static final String COL_COMMENT_ID = "id";
	private static final int NUM_COL_COMMENT_ID = 0;
	
	private static final String COL_COMMENT_ID_USER = "id_user";
	private static final int NUM_COL_COMMENT_ID_USER = 1;
	
	private static final String COL_COMMENT_ID_UV = "id_uv";
	private static final int NUM_COL_COMMENT_ID_UV = 2;
	
	private static final String COL_COMMENT_NOTE = "note";
	private static final int NUM_COL_COMMENT_NOTE = 3;
	
	private static final String COL_COMMENT_COMMENT = "comment";
	private static final int NUM_COL_COMMENT_COMMENT = 4;
	
	private static final String COL_COMMENT_DATE = "date";
	private static final int NUM_COL_COMMENT_DATE = 5;
	
	private static final String COL_COMMENT_FIRST_NAME = "first_name";
	private static final int NUM_COL_COMMENT_FIRST_NAME = 6;
	
	private static final String COL_COMMENT_LAST_NAME = "last_name";
	private static final int NUM_COL_COMMENT_LAST_NAME = 7;
	
	private String[] allColumns = 
	{ 
		COL_COMMENT_ID,
		COL_COMMENT_ID_USER, 
		COL_COMMENT_ID_UV, 
		COL_COMMENT_NOTE, 
		COL_COMMENT_COMMENT, 
		COL_COMMENT_DATE,
		COL_COMMENT_FIRST_NAME,
		COL_COMMENT_LAST_NAME
	};
 
	private SQLiteDatabase bdd;
 
	private SQLiteBaseComment sQLiteBaseComment;
 
	public CommentDb(Context context)
	{
		sQLiteBaseComment = new SQLiteBaseComment(context, NOM_BDD, null, VERSION_BDD);
	}
 
	public void open() throws SQLException
	{
		bdd = sQLiteBaseComment.getWritableDatabase();
	}
	
	public void read() throws SQLException
	{
		bdd = sQLiteBaseComment.getReadableDatabase();
	}
 
	public void close()
	{
		bdd.close();
	}
	
	public void onUpgrade()
	{
		sQLiteBaseComment.deleteAllAndRebuild(bdd);
	}
 
	public SQLiteDatabase getBDD()
	{
		return bdd;
	}
 
	public long insertComment(Note note)
	{
		ContentValues values = new ContentValues();

		values.put(COL_COMMENT_ID_USER, note.getIdUser());
		values.put(COL_COMMENT_ID_UV, note.getIdUv());
		values.put(COL_COMMENT_NOTE, note.getNote());
		values.put(COL_COMMENT_COMMENT, note.getComment());
		values.put(COL_COMMENT_DATE, note.getDate());
		values.put(COL_COMMENT_FIRST_NAME, note.getFirstName());
		values.put(COL_COMMENT_LAST_NAME, note.getLastName());

		return bdd.insert(TABLE_COMMENTS, null, values);
	}
	
	public ArrayList<Note> getCommentByCodeUv(int idUv) 
	{
	    ArrayList<Note> arrayListComments = new ArrayList<Note>();

	    Cursor cursor = bdd.query(TABLE_COMMENTS, allColumns, COL_COMMENT_ID_UV + " = " + idUv, null, null, null, null);

	    cursor.moveToFirst();
	    
	    while (!cursor.isAfterLast()) 
	    {
	    	Note note = cursorToNote(cursor);
	    	arrayListComments.add(note);
	    	cursor.moveToNext();
	    }
	    
	    cursor.close();
	    return arrayListComments;
	}

	private Note cursorToNote(Cursor c) 
	{
	    Note note = new Note();
	    note.setId(c.getInt(NUM_COL_COMMENT_ID));
		note.setIdUser(c.getInt(NUM_COL_COMMENT_ID_USER));
		note.setIdUv(c.getInt(NUM_COL_COMMENT_ID_UV));
		note.setNote(c.getInt(NUM_COL_COMMENT_NOTE));
		note.setComment(c.getString(NUM_COL_COMMENT_COMMENT));
		note.setDate(c.getString(NUM_COL_COMMENT_DATE));
		note.setFirstName(c.getString(NUM_COL_COMMENT_FIRST_NAME));
		note.setLastName(c.getString(NUM_COL_COMMENT_LAST_NAME));
	    return note;
	}
}
