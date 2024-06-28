package com.wanglei.cube2;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

public class CubeData {
	private static final String TAG = "CubeData";
	private static final String AUTHORITIES = CubeUtils.PACKAGE;
	public static final String DATABASE_NAME = "Cube.db";
	public static final String TABLE_CUBE = "cube";
	public static final String KEY_STATE = "state";
	public static final String KEY_ACTION = "action";
	public static final String KEY_ACTION_LENGTH = "action_length";
	public static final String KEY_GENERATED = "generated";
	public static final Uri CONTENT_URI_CUBE = Uri.parse("content://" + AUTHORITIES + "/" + TABLE_CUBE);

	private static void insert(Context context, long state, long action, int actionLength) {
		ContentResolver resolver = context.getContentResolver();
		ContentValues value = new ContentValues();
		value.put(KEY_STATE, state);
		value.put(KEY_ACTION, action);
		value.put(KEY_ACTION_LENGTH, actionLength);
		value.put(KEY_GENERATED, 0);
		resolver.insert(CONTENT_URI_CUBE, value);
	}

	private static Cursor queryNoGenerated(Context context) {
		ContentResolver resolver = context.getContentResolver();
		String selection = KEY_GENERATED + " = " + 0;
		return resolver.query(CONTENT_URI_CUBE, null, selection, null, BaseColumns._ID + " limit 1024");
	}

	public static void buildData(Context context) {
		CubeState state = new CubeState();
		insert(context, state.toValue(), CubeUtils.getActionData(new ArrayList<>()), 0);
		while (true) {
            try (Cursor cursor = queryNoGenerated(context)) {
                int count = 0;
                if (cursor != null) {
                    count = cursor.getCount();
                }
                Log.i(TAG, "WL_DEBUG buildData count = " + count);
                if (count > 0) {
                    int index_id = cursor.getColumnIndex(BaseColumns._ID);
                    int index_state = cursor.getColumnIndex(KEY_STATE);
                    int index_action = cursor.getColumnIndex(KEY_ACTION);
                    int index_action_length = cursor.getColumnIndex(KEY_ACTION_LENGTH);
                    for (int i = 0; i < count; i++) {
                        cursor.moveToPosition(i);
                        long id = cursor.getLong(index_id);
                        long stateData = cursor.getLong(index_state);
                        long actionData = cursor.getLong(index_action);
                        int actionLength = cursor.getInt(index_action_length);
                        Log.i(TAG, "WL_DEBUG buildData id = " + id + ", stateData = " + stateData + ", actionData = "
                                + actionData + ", actionLength = " + actionLength);
                        ArrayList<Byte> actions = CubeUtils.getActions(actionData, actionLength);
                        state = new CubeState(stateData);
                        List<Byte> actionList = state.getActions();
                        for (Byte action : actionList) {
                            ArrayList<Byte> actions2 = (ArrayList<Byte>) actions.clone();
                            actions2.add(action);
                            CubeState result = state.getResult(action);
                            insert(context, result.toValue(), CubeUtils.getActionData(actions2), actions2.size());
                        }
                        updateGenerated(context, id);
                    }
                } else {
                    break;
                }
            } catch (Exception e) {
                Log.e(TAG, "WL_DEBUG insert e = " + e, e);
            }
		}
		/*
		 * create table cube2 as select _id, state, action, action_length from cube;
		 * drop table if exists cube; alter table cube2 rename to cube;
		 */
	}

	private static void updateGenerated(Context context, long id) {
		ContentResolver resolver = context.getContentResolver();
		String where = BaseColumns._ID + " = " + id;
		ContentValues value = new ContentValues();
		value.put(KEY_GENERATED, 1);
		resolver.update(CONTENT_URI_CUBE, value, where, null);
	}
}