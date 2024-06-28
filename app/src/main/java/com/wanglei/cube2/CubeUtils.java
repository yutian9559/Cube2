package com.wanglei.cube2;

import java.util.ArrayList;

public class CubeUtils {
	public static final String PACKAGE = "com.wanglei.cube2";
	private static final long[] POWER9 = new long[12];

	static {
		for (int i = 0; i < POWER9.length; i++) {
			if (i == 0) {
				POWER9[i] = 1;
			} else {
				POWER9[i] = POWER9[i - 1] * 9;
			}
		}
	}

	public static ArrayList<Byte> getActions(long data, int length) {
		ArrayList<Byte> result = new ArrayList<>();
		long temp = data;
		for (int i = 0; i < length; i++) {
			byte action = (byte) (temp % CubeState.ACTION_TOTAL);
			result.add(action);
			temp = temp / CubeState.ACTION_TOTAL;
		}
		return result;
	}

	public static long getActionData(ArrayList<Byte> actions) {
		long result = 0;
		int length = actions.size();
		for (int i = 0; i < length; i++) {
			result += actions.get(i) * POWER9[i];
		}
		return result;
	}

	public static String getActionString(ArrayList<Byte> actions) {
		StringBuilder sb = new StringBuilder();
		int size = actions.size();
		if (size > 0) {
			for (Byte action : actions) {
				switch (action) {
				case CubeState.ACTION_U:
					sb.append("U");
					break;
				case CubeState.ACTION_U2:
					sb.append("U2");
					break;
				case CubeState.ACTION_U3:
					sb.append("U‘");
					break;
				case CubeState.ACTION_F:
					sb.append("F");
					break;
				case CubeState.ACTION_F2:
					sb.append("F2");
					break;
				case CubeState.ACTION_F3:
					sb.append("F'");
					break;
				case CubeState.ACTION_R:
					sb.append("R");
					break;
				case CubeState.ACTION_R2:
					sb.append("R2");
					break;
				case CubeState.ACTION_R3:
					sb.append("R'");
					break;
				}
			}
		}
		return sb.toString();
	}

	public static String getActionStringRevert(ArrayList<Byte> actions) {
		StringBuilder sb = new StringBuilder();
		int size = actions.size();
		if (size > 0) {
			for (int i = size - 1; i >= 0; i--) {
				byte action = actions.get(i);
				switch (action) {
				case CubeState.ACTION_U:
					sb.append("U'");
					break;
				case CubeState.ACTION_U2:
					sb.append("U2");
					break;
				case CubeState.ACTION_U3:
					sb.append("U");
					break;
				case CubeState.ACTION_F:
					sb.append("F'");
					break;
				case CubeState.ACTION_F2:
					sb.append("F2");
					break;
				case CubeState.ACTION_F3:
					sb.append("F");
					break;
				case CubeState.ACTION_R:
					sb.append("R'");
					break;
				case CubeState.ACTION_R2:
					sb.append("R2");
					break;
				case CubeState.ACTION_R3:
					sb.append("R");
					break;
				}
			}
		}
		return sb.toString();
	}
}