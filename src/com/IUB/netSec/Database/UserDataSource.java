package com.IUB.netSec.Database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UserDataSource {


	// Database fields
	private SQLiteDatabase database;
	private MySQLLiteHelper dbHelper;
	private String[] allColumns = { MySQLLiteHelper.COLUMN_USERNAME,MySQLLiteHelper.COLUMN_PASSWORD };

	public UserDataSource(Context context) {
		dbHelper = new MySQLLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public boolean changePasword(String user, String password){
		ContentValues args = new ContentValues();
		args.put(MySQLLiteHelper.COLUMN_PASSWORD, password);
		
		int row = database.update(MySQLLiteHelper.TABLE_USER, args, MySQLLiteHelper.COLUMN_USERNAME + " = \"" + user+"\" ", null);

		if(row == 1)
			return true;
		return false;
	}
	public boolean authenticate(String user , String password){

		Cursor cursor = database.query(MySQLLiteHelper.TABLE_USER,
				allColumns, null, null, null, null, null);

		if(cursor == null )
			return false;
		cursor.moveToFirst();
		String db_user = cursor.getString(0);
		String db_password = cursor.getString(1);
		// make sure to close the cursor
		cursor.close();
		if(user.equals(db_user)&&password.equals(db_password))
			return true;

		return false;
	}
}
