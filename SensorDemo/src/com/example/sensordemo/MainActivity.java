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

	// センサマネージャ
	private SensorManager sensorManager;

	// 加速度センサ
	private Sensor accelerometer = null;

	// 方向センサ
	private Sensor orientation = null;

	// 描画用View
	DrawView drawView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// センサマネージャを取得する
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

		// センサリスト
		List<Sensor> sensorList;

		// 加速度センサを取得する
		sensorList = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
		if (sensorList.size() > 0) {
			accelerometer = (Sensor) sensorList.get(0);
		}

		// 方向センサを取得する
		sensorList = sensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
		if (sensorList.size() > 0) {
			orientation = (Sensor) sensorList.get(0);
		}

		// 端末の横サイズを取得する
		int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
		// 端末の縦サイズを取得する
		int screenHeight = getWindowManager().getDefaultDisplay().getHeight();

		// 描画用Viewを構築する
		drawView = new DrawView(this, screenWidth, screenHeight);
		drawView.setBackgroundColor(Color.WHITE);
		setContentView(drawView);

	}

	@Override
	protected void onResume() {

		super.onResume();

		// 加速度センサロード
		if (accelerometer != null) {
			sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
		}

		// 方向センサロード
		if (orientation != null) {
			sensorManager.registerListener(this, orientation, SensorManager.SENSOR_DELAY_FASTEST);
		}

	}

	@Override
	protected void onStop() {

		// センサ削除
		sensorManager.unregisterListener(this);
		super.onStop();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * 精度変わった時処理
	 */
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	/**
	 * センサ変更時処理
	 */
	@Override
	public void onSensorChanged(SensorEvent event) {

		// センサタイプ判別
		switch (event.sensor.getType()) {

		case Sensor.TYPE_ACCELEROMETER:// 加速度センサの場合
			// 描画Viewの描画位置処理
			drawView.setAPosition((int) event.values[SensorManager.DATA_X], (int) event.values[SensorManager.DATA_Y],
					(int) event.values[SensorManager.DATA_Z]);
			break;
		case Sensor.TYPE_ORIENTATION:// 方向センサの場合
			// 描画Viewの描画位置処理
			drawView.setOPosition((int) event.values[SensorManager.DATA_X], (int) event.values[SensorManager.DATA_Y],
					(int) event.values[SensorManager.DATA_Z]);
			break;
		default:
			break;
		}

		// 画面再描画
		drawView.invalidate();

	}
}
