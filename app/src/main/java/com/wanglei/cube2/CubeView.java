package com.wanglei.cube2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CubeView extends View {
	private CubeState mCubeState = null;
	private static final int COLOR_INVALID = Color.GRAY;
	private static final int COLOR_U = Color.YELLOW;
	private static final int COLOR_L = 0xffffa500;
	private static final int COLOR_F = Color.BLUE;
	private static final int COLOR_R = Color.RED;
	private static final int COLOR_B = Color.GREEN;
	private static final int COLOR_D = Color.WHITE;
	private static final int GRID_WIDTH = 71;
	private static final int GRID_HEIGHT = 53;
	private static final byte[] GRID = new byte[] { 2, 0, 3, 0, 2, 1, 3, 1, 0, 2, 1, 2, 1, 3, 2, 2, 3, 2, 2, 3, 3, 3, 4,
			2, 5, 2, 4, 3, 5, 3, 6, 2, 7, 2, 6, 3, 2, 4, 3, 4, 3, 5 };
	private static final byte[] GRID_CENTER = new byte[] { 0, 3, 7, 3, 2, 5 };
	private static final byte[] CENTER_STATE = new byte[] { CubeState.STATE_L, CubeState.STATE_B, CubeState.STATE_D };
	private int mWidth;
	private int mHeight;
	private final float[] mBoard = new float[21 * 4];
	private final float[] mCenter = new float[3 * 4];
	private final Paint mPaint = new Paint();

	public CubeView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setCubeState(CubeState cubeState) {
		mCubeState = cubeState;
		invalidate();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mWidth = w;
		mHeight = h;
		updateBoard();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (mCubeState != null) {
			byte[] board = mCubeState.getBoard();
			int length = board.length;
			for (int i = 0; i < length; i++) {
				drawBoard(canvas, mBoard, i, board[i]);
			}
			int center_length = CENTER_STATE.length;
			for (byte i = 0; i < center_length; i++) {
				drawBoard(canvas, mCenter, i, CENTER_STATE[i]);
			}
		}
	}

	private int getColor(byte state) {
		int result = COLOR_INVALID;
		switch (state) {
		case CubeState.STATE_U:
			result = COLOR_U;
			break;
		case CubeState.STATE_L:
			result = COLOR_L;
			break;
		case CubeState.STATE_F:
			result = COLOR_F;
			break;
		case CubeState.STATE_R:
			result = COLOR_R;
			break;
		case CubeState.STATE_B:
			result = COLOR_B;
			break;
		case CubeState.STATE_D:
			result = COLOR_D;
			break;
		}
		return result;
	}

	private void drawBoard(Canvas canvas, float[] board, int index, byte state) {
		int board_index_offset = 4 * index;
		mPaint.setColor(getColor(state));
		canvas.drawRect(board[board_index_offset], board[board_index_offset + 1], board[board_index_offset + 2],
				board[board_index_offset + 3], mPaint);
	}

	private void updateBoard() {
		if (GRID_WIDTH * mHeight < GRID_HEIGHT * mWidth) {
			int offset = mWidth * GRID_HEIGHT - GRID_WIDTH * mHeight;
			int h2 = mHeight * 2;
			float H2 = GRID_HEIGHT * 2;
			float H = GRID_HEIGHT;
			for (int i = 0; i < 21; i++) {
				setBoardH(mBoard, GRID, i, mHeight, H, h2, H2, offset);
			}
			for (int i = 0; i < 3; i++) {
				setBoardH(mCenter, GRID_CENTER, i, mHeight, H, h2, H2, offset);
			}
		} else {
			int offset = mHeight * GRID_WIDTH - GRID_HEIGHT * mWidth;
			int w2 = mWidth * 2;
			float W2 = GRID_WIDTH * 2;
			float W = GRID_WIDTH;
			for (int i = 0; i < 21; i++) {
				setBoardW(mBoard, GRID, i, mWidth, W, w2, W2, offset);
			}
			for (int i = 0; i < 3; i++) {
				setBoardW(mCenter, GRID_CENTER, i, mWidth, W, w2, W2, offset);
			}
		}
	}

	private float getBoard(int grid, int g, float G) {
		return grid * g / G;
	}

	private float getBoardOffset(int grid, int g2, float G2, int offset) {
		return (offset + grid * g2) / G2;
	}

	private void setBoardH(float[] board, byte[] grid, int index, int g, float G, int g2, float G2, int offset) {
		int board_index_offset = 4 * index;
		int grid_index_offset = 2 * index;
		int grid_index_offset_y = grid_index_offset + 1;
		int grid_left = grid[grid_index_offset] * 9;
		int grid_top = grid[grid_index_offset_y] * 9;
		int grid_right = grid_left + 8;
		int grid_bottom = grid_top + 8;
		board[board_index_offset] = getBoardOffset(grid_left, g2, G2, offset);
		board[board_index_offset + 1] = getBoard(grid_top, g, G);
		board[board_index_offset + 2] = getBoardOffset(grid_right, g2, G2, offset);
		board[board_index_offset + 3] = getBoard(grid_bottom, g, G);
	}

	private void setBoardW(float[] board, byte[] grid, int index, int g, float G, int g2, float G2, int offset) {
		int board_index_offset = 4 * index;
		int grid_index_offset = 2 * index;
		int grid_index_offset_y = grid_index_offset + 1;
		int grid_left = grid[grid_index_offset] * 9;
		int grid_top = grid[grid_index_offset_y] * 9;
		int grid_right = grid_left + 8;
		int grid_bottom = grid_top + 8;
		board[board_index_offset] = getBoard(grid_left, g2, G2);
		board[board_index_offset + 1] = getBoardOffset(grid_top, g, G, offset);
		board[board_index_offset + 2] = getBoard(grid_right, g2, G2);
		board[board_index_offset + 3] = getBoardOffset(grid_bottom, g, G, offset);
	}
}