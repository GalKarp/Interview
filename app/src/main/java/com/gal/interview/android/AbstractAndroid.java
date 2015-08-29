package com.gal.interview.android;

import android.graphics.Canvas;

import com.gal.interview.DoodleJumpActivity;
import com.gal.interview.OBjectsManager;

public abstract class AbstractAndroid {

	public static final float DEFAULT_VERTICAL_ACCELERATE       = 1 * DoodleJumpActivity.height_mul;
	protected static final float INITIAL_COORX                  = 145 * DoodleJumpActivity.width_mul; //middle of the screen
	protected static final float INITIAL_COORY                  = DoodleJumpActivity.screen_height-300;
	public static final float INITIAL_VERTICAL_SPEED            = -19 * DoodleJumpActivity.height_mul;
	public static final float MAX_VERTICAL_SPEED                = 19 * DoodleJumpActivity.height_mul;
	public static final int   STATE_GO_UP                       = 1;
	public static final int   STATE_GO_DOWN                     = 2;

	public static float CoorX;
	public static float CoorY;

	public float accelerometer;
	public float horizonal_speed;
	public float vertical_speed;

	public int current_state;
	public int bitmap_index;

	public abstract void DrawSelf(Canvas canvas);
	public abstract void Move();
	public abstract void CheckAndroidCoor(OBjectsManager oBjectsManager);
}
