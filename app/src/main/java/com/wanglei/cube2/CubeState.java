package com.wanglei.cube2;

import java.util.ArrayList;
import java.util.List;

public class CubeState {
	public static final byte STATE_U = 0;
	public static final byte STATE_L = 1;
	public static final byte STATE_F = 2;
	public static final byte STATE_R = 3;
	public static final byte STATE_B = 4;
	public static final byte STATE_D = 5;
	private static final byte STATE_TOTAL = 6;

	public static final byte ACTION_U = 0;
	public static final byte ACTION_U2 = 1;
	public static final byte ACTION_U3 = 2;
	public static final byte ACTION_F = 3;
	public static final byte ACTION_F2 = 4;
	public static final byte ACTION_F3 = 5;
	public static final byte ACTION_R = 6;
	public static final byte ACTION_R2 = 7;
	public static final byte ACTION_R3 = 8;
	public static final byte ACTION_TOTAL = 9;

	private static final byte INDEX_U0 = 0;
	private static final byte INDEX_U1 = 1;
	private static final byte INDEX_U2 = 2;
	private static final byte INDEX_U3 = 3;
	private static final byte INDEX_L0 = 4;
	private static final byte INDEX_L1 = 5;
	private static final byte INDEX_L2 = 6;
	private static final byte INDEX_F0 = 7;
	private static final byte INDEX_F1 = 8;
	private static final byte INDEX_F2 = 9;
	private static final byte INDEX_F3 = 10;
	private static final byte INDEX_R0 = 11;
	private static final byte INDEX_R1 = 12;
	private static final byte INDEX_R2 = 13;
	private static final byte INDEX_R3 = 14;
	private static final byte INDEX_B0 = 15;
	private static final byte INDEX_B1 = 16;
	private static final byte INDEX_B2 = 17;
	private static final byte INDEX_D0 = 18;
	private static final byte INDEX_D1 = 19;
	private static final byte INDEX_D2 = 20;

	private static final ArrayList<Byte> ACTIONS = new ArrayList<>();
	private static final long[] POWER6 = new long[21];

	static {
		ACTIONS.add(ACTION_U);
		ACTIONS.add(ACTION_U2);
		ACTIONS.add(ACTION_U3);
		ACTIONS.add(ACTION_F);
		ACTIONS.add(ACTION_F2);
		ACTIONS.add(ACTION_F3);
		ACTIONS.add(ACTION_R);
		ACTIONS.add(ACTION_R2);
		ACTIONS.add(ACTION_R3);

		for (int i = 0; i < POWER6.length; i++) {
			if (i == 0) {
				POWER6[i] = 1;
			} else {
				POWER6[i] = POWER6[i - 1] * 6;
			}
		}
	}

	private final byte[] mBoard = { STATE_U, STATE_U, STATE_U, STATE_U, STATE_L, STATE_L, STATE_L, STATE_F, STATE_F,
			STATE_F, STATE_F, STATE_R, STATE_R, STATE_R, STATE_R, STATE_B, STATE_B, STATE_B, STATE_D, STATE_D,
			STATE_D };

	public CubeState() {

	}

	public CubeState(long value) {
		int length = mBoard.length;
		long temp = value;
		for (int i = 0; i < length; i++) {
			mBoard[i] = (byte) (temp % STATE_TOTAL);
			temp = temp / STATE_TOTAL;
		}
	}

	public List<Byte> getActions() {
		return ACTIONS;
	}

	public CubeState getResult(Byte action) {
		CubeState result = null;
		switch (action) {
		case ACTION_U:
			result = actionU(this);
			break;
		case ACTION_U2:
			result = actionU(actionU(this));
			break;
		case ACTION_U3:
			result = actionU(actionU(actionU(this)));
			break;
		case ACTION_F:
			result = actionF(this);
			break;
		case ACTION_F2:
			result = actionF(actionF(this));
			break;
		case ACTION_F3:
			result = actionF(actionF(actionF(this)));
			break;
		case ACTION_R:
			result = actionR(this);
			break;
		case ACTION_R2:
			result = actionR(actionR(this));
			break;
		case ACTION_R3:
			result = actionR(actionR(actionR(this)));
			break;
		}
		return result;
	}

	private CubeState actionU(CubeState state) {
		CubeState result = new CubeState();
		byte[] stateBoard = state.mBoard;
		byte[] resultBoard = result.mBoard;
		resultBoard[INDEX_U0] = stateBoard[INDEX_U2];
		resultBoard[INDEX_U1] = stateBoard[INDEX_U0];
		resultBoard[INDEX_U2] = stateBoard[INDEX_U3];
		resultBoard[INDEX_U3] = stateBoard[INDEX_U1];
		resultBoard[INDEX_L0] = stateBoard[INDEX_F0];
		resultBoard[INDEX_L1] = stateBoard[INDEX_F1];
		resultBoard[INDEX_L2] = stateBoard[INDEX_L2];
		resultBoard[INDEX_F0] = stateBoard[INDEX_R0];
		resultBoard[INDEX_F1] = stateBoard[INDEX_R1];
		resultBoard[INDEX_F2] = stateBoard[INDEX_F2];
		resultBoard[INDEX_F3] = stateBoard[INDEX_F3];
		resultBoard[INDEX_R0] = stateBoard[INDEX_B0];
		resultBoard[INDEX_R1] = stateBoard[INDEX_B1];
		resultBoard[INDEX_R2] = stateBoard[INDEX_R2];
		resultBoard[INDEX_R3] = stateBoard[INDEX_R3];
		resultBoard[INDEX_B0] = stateBoard[INDEX_L0];
		resultBoard[INDEX_B1] = stateBoard[INDEX_L1];
		resultBoard[INDEX_B2] = stateBoard[INDEX_B2];
		resultBoard[INDEX_D0] = stateBoard[INDEX_D0];
		resultBoard[INDEX_D1] = stateBoard[INDEX_D1];
		resultBoard[INDEX_D2] = stateBoard[INDEX_D2];
		return result;
	}

	private CubeState actionF(CubeState state) {
		CubeState result = new CubeState();
		byte[] stateBoard = state.mBoard;
		byte[] resultBoard = result.mBoard;
		resultBoard[INDEX_U0] = stateBoard[INDEX_U0];
		resultBoard[INDEX_U1] = stateBoard[INDEX_U1];
		resultBoard[INDEX_U2] = stateBoard[INDEX_L2];
		resultBoard[INDEX_U3] = stateBoard[INDEX_L1];
		resultBoard[INDEX_L0] = stateBoard[INDEX_L0];
		resultBoard[INDEX_L1] = stateBoard[INDEX_D0];
		resultBoard[INDEX_L2] = stateBoard[INDEX_D1];
		resultBoard[INDEX_F0] = stateBoard[INDEX_F2];
		resultBoard[INDEX_F1] = stateBoard[INDEX_F0];
		resultBoard[INDEX_F2] = stateBoard[INDEX_F3];
		resultBoard[INDEX_F3] = stateBoard[INDEX_F1];
		resultBoard[INDEX_R0] = stateBoard[INDEX_U2];
		resultBoard[INDEX_R1] = stateBoard[INDEX_R1];
		resultBoard[INDEX_R2] = stateBoard[INDEX_U3];
		resultBoard[INDEX_R3] = stateBoard[INDEX_R3];
		resultBoard[INDEX_B0] = stateBoard[INDEX_B0];
		resultBoard[INDEX_B1] = stateBoard[INDEX_B1];
		resultBoard[INDEX_B2] = stateBoard[INDEX_B2];
		resultBoard[INDEX_D0] = stateBoard[INDEX_R2];
		resultBoard[INDEX_D1] = stateBoard[INDEX_R0];
		resultBoard[INDEX_D2] = stateBoard[INDEX_D2];
		return result;
	}

	private CubeState actionR(CubeState state) {
		CubeState result = new CubeState();
		byte[] stateBoard = state.mBoard;
		byte[] resultBoard = result.mBoard;
		resultBoard[INDEX_U0] = stateBoard[INDEX_U0];
		resultBoard[INDEX_U1] = stateBoard[INDEX_F1];
		resultBoard[INDEX_U2] = stateBoard[INDEX_U2];
		resultBoard[INDEX_U3] = stateBoard[INDEX_F3];
		resultBoard[INDEX_L0] = stateBoard[INDEX_L0];
		resultBoard[INDEX_L1] = stateBoard[INDEX_L1];
		resultBoard[INDEX_L2] = stateBoard[INDEX_L2];
		resultBoard[INDEX_F0] = stateBoard[INDEX_F0];
		resultBoard[INDEX_F1] = stateBoard[INDEX_D1];
		resultBoard[INDEX_F2] = stateBoard[INDEX_F2];
		resultBoard[INDEX_F3] = stateBoard[INDEX_D2];
		resultBoard[INDEX_R0] = stateBoard[INDEX_R2];
		resultBoard[INDEX_R1] = stateBoard[INDEX_R0];
		resultBoard[INDEX_R2] = stateBoard[INDEX_R3];
		resultBoard[INDEX_R3] = stateBoard[INDEX_R1];
		resultBoard[INDEX_B0] = stateBoard[INDEX_U3];
		resultBoard[INDEX_B1] = stateBoard[INDEX_B1];
		resultBoard[INDEX_B2] = stateBoard[INDEX_U1];
		resultBoard[INDEX_D0] = stateBoard[INDEX_D0];
		resultBoard[INDEX_D1] = stateBoard[INDEX_B2];
		resultBoard[INDEX_D2] = stateBoard[INDEX_B0];
		return result;
	}

	public long toValue() {
		long result = 0;
		int length = mBoard.length;
		for (int i = 0; i < length; i++) {
			result += mBoard[i] * POWER6[i];
		}
		return result;
	}

	public byte[] getBoard() {
		return mBoard;
	}
}