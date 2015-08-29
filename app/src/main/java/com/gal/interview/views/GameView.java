package com.gal.interview.views;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.gal.interview.Constants;
import com.gal.interview.DoodleJumpActivity;
import com.gal.interview.LogicManager;

public class GameView extends View {
   
	private static final int SLEEP_TIME = 10;
	public LogicManager logicManager;
	private Paint paint;
	public  static boolean isGameOver;
	DoodleJumpActivity doodleJumpActivity;
	public static boolean ispause = false;
	private boolean isgamerunning = true;
	
	private Animation alphAnimation;
	
	public void startEntryAnim(){
		alphAnimation = new AlphaAnimation(0.0f, 1.0f);
		alphAnimation.setDuration(Constants.ANIMATION_TIME);
		startAnimation(alphAnimation);
	}

	
	public GameView(DoodleJumpActivity context) {
		super(context);
		logicManager = new LogicManager(context);
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.parseColor("#c5c5c5"));
		isGameOver = false;
		this.doodleJumpActivity = context;
		ispause = false;
		new Thread(new GameThread()).start();
		startEntryAnim();
	}

	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.parseColor("#faf0cc"));
		DrawBackground(canvas);
		if(!isGameOver) {
			logicManager.DrawAndroidAndBars(canvas);
			super.onDraw(canvas);
		}
	}
	//Draw X and Y background lines
	private void DrawBackground(Canvas canvas) {
		for(int i=0; i < DoodleJumpActivity.screen_height; i = (int) (i + 10 * DoodleJumpActivity.height_mul))
			canvas.drawLine(0, i, DoodleJumpActivity.screen_width, i, paint);
		for(int i=0; i < DoodleJumpActivity.screen_width; i = (int) (i + 10 * DoodleJumpActivity.height_mul))
			canvas.drawLine(i, 0, i, DoodleJumpActivity.screen_height, paint);
		
	}

	private class GameThread implements Runnable {

		@Override
		public void run() {
		
				while(isgamerunning){
					try {
						Thread.sleep(GameView.SLEEP_TIME);
					} catch (Exception e) {
					}
					postInvalidate();
					if(isGameOver == true){
						isgamerunning = false;
	    				logicManager.Clear();

						doodleJumpActivity.handler.sendEmptyMessage(DoodleJumpActivity.GAME_OVER);
					}
				}
			}
	}
}
