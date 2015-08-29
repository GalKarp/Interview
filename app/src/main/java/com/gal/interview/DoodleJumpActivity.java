package com.gal.interview;


import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.gal.interview.views.GameView;

public class DoodleJumpActivity extends Activity {
    /** Called when the activity is first created. */
	
	public static final int GAME_OVER  = 0;
	public static final int GAME_START = 1;

	private GameView gameView;
	private SensorManager sensorManager;
	private MySensorEventListener sensorEventListener;
	int pre_speed = 0;
	View current_view;
	
	public static boolean isGame_Running = false;
	public static float screen_width;
	public static float screen_height;
	public static float width_mul;
	public static float height_mul;

	//Hadler for catching when i need to start or end game
	public Handler handler = new Handler(){
		 public void handleMessage(Message msg) {
			 if(msg.what == GAME_OVER){
				current_view = null;
				 initWelcomeView();

			 }
			 if(msg.what == GAME_START){
				isGame_Running = true;
			    initGameView();
			 }
		 }



	};

	
	public void initGameView() {
		isGame_Running = true;
		gameView = new GameView(DoodleJumpActivity.this);
		current_view = gameView;
		setContentView(gameView);
	}
	
	public void initWelcomeView(){
		isGame_Running = false;
		gameView = new GameView(DoodleJumpActivity.this);
		setContentView(R.layout.activity_main);

	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        DisplayMetrics dm;
		dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);     
		screen_width = dm.widthPixels;  
		screen_height = dm.heightPixels;
		width_mul = screen_width/320;
		height_mul = screen_height/480;
		if(screen_height >= 800)
			height_mul = (float) 1.5;
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		this.handler.sendEmptyMessage(DoodleJumpActivity.GAME_START);

	}

	@Override
	protected void onResume() {
		Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorEventListener = new MySensorEventListener();
		sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_GAME);
		super.onResume();
	}


	@Override
	protected void onPause() {
		sensorManager.unregisterListener(sensorEventListener);
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
		finish();
	}

	//Responsible for the sensor movement
	private final class MySensorEventListener implements SensorEventListener{

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			if(current_view == gameView){
				float X = event.values[SensorManager.DATA_X];
				pre_speed += X;
				if(X > 0.45 || X < -0.45){
					int temp = X > 0 ? 4 : -4;
					if(pre_speed > 7 || pre_speed < -7)
						pre_speed = pre_speed > 0 ? 7 : -7;
					gameView.logicManager.SetAndroid_HSpeed(pre_speed + temp);
				}
			}
		}
	}
}