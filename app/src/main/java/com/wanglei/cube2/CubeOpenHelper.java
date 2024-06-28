package com.wanglei.cube2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * @author wanglei
 */
public class CubeOpenHelper extends SQLiteOpenHelper {
	public CubeOpenHelper(Context context) {
		super(context, CubeData.DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS " + CubeData.TABLE_CUBE + " (" + BaseColumns._ID
				+ " integer primary key autoincrement, " + CubeData.KEY_STATE + " integer, " + CubeData.KEY_ACTION
				+ " integer, " + CubeData.KEY_ACTION_LENGTH + " integer, " + CubeData.KEY_GENERATED + " integer);");
		db.execSQL("CREATE UNIQUE INDEX cube_I ON " + CubeData.TABLE_CUBE + "(" + CubeData.KEY_STATE + ");");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + CubeData.TABLE_CUBE);
		onCreate(db);
	}
}