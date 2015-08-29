package com.gal.interview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.gal.interview.android.Android;
import com.gal.interview.bars.NormalBar;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OBjectsManager {
    
	
	public static  long sum;
	public int bar_level = 0;
	public int barApperrate = 35;
	private Map<String, NormalBar> barMap;
	private long bar_identifier = 0;
    public boolean isrepeated = false;
	public int touch_bar_type = NormalBar.TYPE_NORMAL;
	public static boolean[] person;
	public static int choose;
	private DoodleJumpActivity context;
	private Paint paint;

	public OBjectsManager(DoodleJumpActivity context){
		this.context = context;
		barMap = new HashMap<String, NormalBar>();
		initBarMap();
		initPaint();
		person = new boolean[2];
     	person[0] = false;
		person[1] = false;
		sum = 0;
	}
	
	
	private void initPaint() {
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.BLACK);
		paint.setTextSize(20* DoodleJumpActivity.height_mul);
	}

	public void Clear(){
		barMap.clear();
	}
	
	public void initBarMap(){
		int count = 0;
		int CoorX;
		while(count < DoodleJumpActivity.screen_height / (20 * DoodleJumpActivity.height_mul)){
			if(hasBar()){
				int range = (int) (DoodleJumpActivity.screen_width - 50 * DoodleJumpActivity.width_mul);
				CoorX = new Random().nextInt(range);
				NormalBar bar = new NormalBar(CoorX, count * (20 * DoodleJumpActivity.height_mul), context);
				barMap.put(""+bar_identifier, bar);
				bar_identifier ++;
			}
			count ++;
		}
	}
	
	private boolean hasBar() {
		int temp = new Random().nextInt(100);
		if(temp > barApperrate)
			return true;
		return false;
	}
	
	
	public void DrawBars(Canvas canvas){
		person[0] = true;
		choose = 1;
		while(person[1]&&choose==1);
		for(String key : barMap.keySet()){
			barMap.get(key).drawSelf(canvas);
		}
		drawHeight(canvas);
		person[0] = false;
	}
	private void drawHeight(Canvas canvas) {
		canvas.drawText(""+sum, 5 * DoodleJumpActivity.width_mul, 20 * DoodleJumpActivity.height_mul, paint);
	}
	
	public boolean isTouchBars(float CoorX, float CoorY){
		
		for(String key : barMap.keySet()){
			if(barMap.get(key).IsBeingStep(CoorX, CoorY)){
				return true;
			}
		}
		return false;
	}
	public void MoveBarsDown(float vertical_speed){
		person[1] = true;
		choose = 0;
		while(person[0]&&choose==0);
		for(String key : barMap.keySet()){
			barMap.get(key).TLCoorY -= vertical_speed*2;
		}
		sum += vertical_speed;
		bar_level += vertical_speed;
		AddNewBars();
		person[1] = false;
	}

	
	public void AddNewBars(){
		CheckLevel();
		NormalBar bar;
		float tempY = GetTopCoorY();
		if(tempY > ( DoodleJumpActivity.height_mul)){
			if(hasBar()){
				int CoorX = new Random().nextInt((int) (DoodleJumpActivity.screen_width - 50));
				bar = new NormalBar(CoorX, 0 , context);
				barMap.put(""+bar_identifier, bar);
				bar_identifier ++;
			}
		}
	}
	private void CheckLevel() {
		if(bar_level >= 2000){
			if(barApperrate < 55)
				barApperrate += 2;
			bar_level = 0;
		}
	}
	private float GetTopCoorY() {
		float tempY = 100;
		for(String key : barMap.keySet()){
			if(barMap.get(key)== null){
				if(barMap.get(key).TLCoorY <= tempY)
					tempY = barMap.get(key).TLCoorY;
			}
			else if(barMap.get(key).TLCoorY - 20 * DoodleJumpActivity.height_mul <= tempY){
				tempY = barMap.get(key).TLCoorY - 20 * DoodleJumpActivity.height_mul;
			}
		}
		return tempY;
	}
}
