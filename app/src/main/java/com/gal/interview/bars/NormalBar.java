package com.gal.interview.bars;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import com.gal.interview.DoodleJumpActivity;
import com.gal.interview.R;

public class NormalBar {
	public static final int TYPE_NORMAL = 0;
	//Platform coordinate
	public float TLCoorX;
	public float TLCoorY;

	public int type;
	private Bitmap normal_bar;


	//Normal bar for implimenting bar movement in the future
	public NormalBar(float CoorX, float CoorY, DoodleJumpActivity context){
		this.TLCoorX = CoorX;
		this.TLCoorY = CoorY;
		this.type = TYPE_NORMAL;
		normal_bar = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.p_green)).getBitmap();
	}
	//Creating the platform over the canvas
	public void drawSelf(Canvas canvas) {
		canvas.drawBitmap(normal_bar, TLCoorX, TLCoorY, null);
	}


	//Check if the doodle is touching the platform - need improvments
	public boolean IsBeingStep(float CoorX, float CoorY) {
		if(CoorX >= TLCoorX - 35 * DoodleJumpActivity.width_mul && CoorX <= TLCoorX + 35 * DoodleJumpActivity.width_mul && CoorY + 35 * DoodleJumpActivity.height_mul <= TLCoorY && TLCoorY - CoorY <= 35 * DoodleJumpActivity.height_mul){
			return true;
		}
		return false;
	}




}
