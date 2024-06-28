package com.wanglei.widget;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

/**
 * @author wanglei
 */
public abstract class ReadOnlyDatabaseProvider extends ContentProvider {
	private static final String TAG = "ReadOnlyDatabaseProvider";
	SQLiteDatabase mDb = null;

	protected abstract String getDatabaseName();

	@Override
	public boolean onCreate() {
		mDb = initAsserts(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		String table = getTable(uri);
		qb.setTables(table);
		Cursor ret = qb.query(mDb, projection, selection, selectionArgs, null, null, sortOrder);

		if (ret == null) {
			Log.e(TAG, "WL_DEBUG query: failed");
		} else {
			ret.setNotificationUri(getContext().getContentResolver(), uri);
		}

		return ret;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	private String getTable(Uri uri) {
		return uri.getPathSegments().get(0);
	}

	private SQLiteDatabase initAsserts(Context context) {
		File dbFile = context.getDatabasePath(getDatabaseName());

		if (!dbFile.exists()) {
			try {
				FileOutputStream out = new FileOutputStream(dbFile);
				InputStream in = context.getAssets().open(getDatabaseName());
				byte[] buffer = new byte[1024];
				int readBytes;
				while ((readBytes = in.read(buffer)) != -1)
					out.write(buffer, 0, readBytes);
				in.close();
				out.close();
			} catch (IOException e) {
				Log.i(TAG, "WL_DEBUG initAsserts e = " + e);
			}
		}

		return SQLiteDatabase.openOrCreateDatabase(dbFile, null);
	}
}