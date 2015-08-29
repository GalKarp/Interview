package com.gal.interview.android;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import com.gal.interview.DoodleJumpActivity;
import com.gal.interview.OBjectsManager;
import com.gal.interview.R;

public class Android extends AbstractAndroid {

	public Bitmap[] normal_android;
	
	public Android(DoodleJumpActivity context, int X, int Y){
		this(context);
		this.CoorX = X;
		this.CoorY = Y;
		
	}
	
	public Android(DoodleJumpActivity context){
		this.CoorX = INITIAL_COORX;
		this.CoorY = INITIAL_COORY;
		this.horizonal_speed = 0;
		this.vertical_speed = INITIAL_VERTICAL_SPEED + 5 * DoodleJumpActivity.height_mul;
		this.current_state = STATE_GO_UP;
	    accelerometer = DEFAULT_VERTICAL_ACCELERATE;
	    initBitMap(context);
	}
	//Doodle icons, the right icon and left icon not implemented
	private void initBitMap(DoodleJumpActivity context){
		normal_android = new Bitmap[2];
		normal_android[0] = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.doodlel)).getBitmap();
		normal_android[1] = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.doodler)).getBitmap();
	}

	//For drawing the doodle icon
	@Override
	public void DrawSelf(Canvas canvas) {
			canvas.drawBitmap(normal_android[bitmap_index], CoorX,CoorY-50, null);
	}

	public static AbstractAndroid AndroidFactory(DoodleJumpActivity context){
		return new Android(context);
	}

	//For android(doodle) icon movement
	@Override
	public void Move() {
		vertical_speed += accelerometer *1;
		CoorX += horizonal_speed;
		horizonal_speed = 0;
	}

	//For checking android current position
	@Override
	public void CheckAndroidCoor(OBjectsManager oBjectsManager) {
		if(CoorY > DoodleJumpActivity.screen_height-DoodleJumpActivity.height_mul){
			vertical_speed = Android.INITIAL_VERTICAL_SPEED;
			current_state = Android.STATE_GO_UP;
			oBjectsManager.isrepeated = false;
		}
		if(CoorX <= -40 * DoodleJumpActivity.width_mul) //For android cycle from left to right , -40 for the icon size
			CoorX = DoodleJumpActivity.screen_width - 40 * DoodleJumpActivity.width_mul;
		if(CoorX >= DoodleJumpActivity.screen_width)
			CoorX = 0;
	}


}
