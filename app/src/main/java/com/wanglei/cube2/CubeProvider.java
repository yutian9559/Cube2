package com.wanglei.cube2;

import com.wanglei.widget.DatabaseProvider;

import android.database.sqlite.SQLiteOpenHelper;

public class CubeProvider extends DatabaseProvider {

	@Override
	protected SQLiteOpenHelper CreateOpenHelper() {
		return new CubeOpenHelper(getContext());
	}

}