package com.IUB.netSec.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLLiteHelper extends SQLiteOpenHelper{
	public static final String TABLE_USER = "user";
	  //public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_USERNAME = "username";
	  public static final String COLUMN_PASSWORD = "password";

	  private static final String DATABASE_NAME = "user.db";
	  private static final int DATABASE_VERSION = 3;

	  // Database creation sql statement
	  private static final String DATABASE_CREATE = "create table "
	      + TABLE_USER + "(" + COLUMN_USERNAME
	      + " text primary key , " + COLUMN_PASSWORD
	      + " text not null);";

	  public MySQLLiteHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	  @Override
	  public void onCreate(SQLiteDatabase database) {
	    database.execSQL(DATABASE_CREATE);
	    String userInsert = "INSERT INTO " + TABLE_USER + " ("
	              + COLUMN_USERNAME + ", "
	              + COLUMN_PASSWORD + ") Values ('Rohit', 'Password')";
	     database.execSQL(userInsert);
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(MySQLLiteHelper.class.getName(),
	        "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
	    onCreate(db);
	  }


}
