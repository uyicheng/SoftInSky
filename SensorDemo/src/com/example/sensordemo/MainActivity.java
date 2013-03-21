package com.example.sensordemo;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity implements SensorEventListener {

	private SensorManager sensorManager;
	private Sensor accelerometer = null;
	private Sensor orientation = null;

	//TextView textView = null;
	DrawView drawView = null; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

		List<Sensor> sensorList;
		sensorList = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
		if (sensorList.size() > 0) {
			accelerometer = (Sensor) sensorList.get(0);
		}

		sensorList = sensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
		if (sensorList.size() > 0) {
			orientation = (Sensor) sensorList.get(0);
		}
		
		int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
		int screenHeight = getWindowManager().getDefaultDisplay().getHeight(); 
		
		drawView = new DrawView(this, screenWidth, screenHeight);
		drawView.setBackgroundColor(Color.WHITE);
		setContentView(drawView);
		
		
		//setContentView(R.layout.activity_main);
		//textView = (TextView) findViewById(R.id.textAccelerometer);
	}

	@Override
	protected void onResume() {

		super.onResume();

		if (accelerometer != null) {
			sensorManager.registerListener(this, accelerometer,
					SensorManager.SENSOR_DELAY_NORMAL);
		}

		if (orientation != null) {
			sensorManager.registerListener(this, orientation,
					SensorManager.SENSOR_DELAY_FASTEST);
		}

	}

	@Override
	protected void onStop() {

		sensorManager.unregisterListener(this);
		super.onStop();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {

		float total = 0f;
		
		for (int i = 0; i < event.values.length; i++) {
			
			total = total + event.values[i];
		}
				
		if (event.sensor == accelerometer) {
			drawView.setAPosition((int)event.values[SensorManager.DATA_X] , (int)event.values[SensorManager.DATA_Y], (int)event.values[SensorManager.DATA_Z] );
		}
		
		if (event.sensor == orientation) {
			drawView.setOPosition((int)event.values[SensorManager.DATA_X] , (int)event.values[SensorManager.DATA_Y], (int)event.values[SensorManager.DATA_Z] );
		}
		
		drawView.invalidate();

	}
}
