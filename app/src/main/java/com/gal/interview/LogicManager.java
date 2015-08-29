package com.gal.interview;

import android.graphics.Canvas;
import com.gal.interview.android.AbstractAndroid;
import com.gal.interview.android.Android;
import com.gal.interview.bars.NormalBar;
import com.gal.interview.views.GameView;


public class LogicManager {

	private static final int SLEEP_TIME = 40;
	private OBjectsManager objectsManager;
	private AbstractAndroid android;
	private boolean game_started = false;
	public static boolean isrunning;
	DoodleJumpActivity context;
	
	public LogicManager(DoodleJumpActivity context){
		this.context = context;
	    objectsManager = new OBjectsManager(context);
		android = Android.AndroidFactory(context);
		isrunning = true;
		new Thread(new LogicThread()).start();
	}
	
	public void Clear(){
		isrunning = false;
		android = null;
		objectsManager.Clear();
	}

	
	public void DrawAndroidAndBars(Canvas canvas){
		objectsManager.DrawBars(canvas);
		android.DrawSelf(canvas);
	}
	
	public void SetAndroid_HSpeed(float horizonal_speed){
		android.horizonal_speed = - horizonal_speed;
	}
	
	private class LogicThread implements Runnable {

		@Override
		public void run() {
			while(isrunning && !GameView.isGameOver){
				try {
					Thread.sleep(SLEEP_TIME);
				} catch (Exception e) {
					
				}
				finally{
					if(!GameView.isGameOver) {
						android.Move();
						android.CheckAndroidCoor(objectsManager);
						CheckVerticalSpeed();
					}

				}
			}
		}
		//Check the vertical movement of the Doodle and the bars
		private void CheckVerticalSpeed() {
			if(android.current_state == Android.STATE_GO_UP){
				android.CoorY += android.vertical_speed;
				if(game_started){
					objectsManager.MoveBarsDown(android.vertical_speed);
				}
				if(android.vertical_speed >= 0){
					android.vertical_speed = 0;
					android.current_state = Android.STATE_GO_DOWN;
					game_started = true;
				}
			
			}
		    if(android.current_state == Android.STATE_GO_DOWN && !GameView.isGameOver) {
		    	if(android.vertical_speed >= Android.MAX_VERTICAL_SPEED)
			    		android.vertical_speed = Android.MAX_VERTICAL_SPEED;
		    	float temp =  android.vertical_speed;
		    	for(float i=0; i<=temp; i += 0.5){
		    		android.CoorY += 0.5;
			    	if(objectsManager.isTouchBars(android.CoorX, android.CoorY)){
			    		if(objectsManager.isrepeated){
			    			android.vertical_speed = Android.INITIAL_VERTICAL_SPEED;
			    		}
			    		else{ 
			    			if(objectsManager.touch_bar_type == NormalBar.TYPE_NORMAL){
			    				android.vertical_speed = Android.INITIAL_VERTICAL_SPEED;
			    			}
			    		}
			    		android.current_state = Android.STATE_GO_UP;
			    		break;
			    	}
					else if (android.CoorY >= DoodleJumpActivity.screen_height - DoodleJumpActivity.height_mul-50){
							GameView.isGameOver = true;
							context.handler.sendEmptyMessage(DoodleJumpActivity.GAME_OVER);


						}
					}
		    	}
		      
			}
		}
	}

