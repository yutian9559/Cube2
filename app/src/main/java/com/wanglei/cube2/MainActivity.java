package com.wanglei.cube2;

import com.wanglei.widget.Utils;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends ListActivity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Cursor c = getContentResolver().query(CubeData.CONTENT_URI_CUBE, null,
				null/* CubeData.KEY_ACTION_LENGTH + " = " + 11 */, null, null/* BaseColumns._ID + " limit 1024" */);
		startManagingCursor(c);
		setListAdapter(new CubeAdapter(this, c));
		findViewById(R.id.generate).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		final int id = v.getId();
        if (id == R.id.generate) {
            Utils.getSubThreadHandler().post(() -> CubeData.buildData(getApplicationContext()));
        }
	}
}
