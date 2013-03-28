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

	// �Z���T�}�l�[�W��
	private SensorManager sensorManager;

	// �����x�Z���T
	private Sensor accelerometer = null;

	// �����Z���T
	private Sensor orientation = null;

	// �`��pView
	DrawView drawView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// �Z���T�}�l�[�W�����擾����
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

		// �Z���T���X�g
		List<Sensor> sensorList;

		// �����x�Z���T���擾����
		sensorList = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
		if (sensorList.size() > 0) {
			accelerometer = (Sensor) sensorList.get(0);
		}

		// �����Z���T���擾����
		sensorList = sensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
		if (sensorList.size() > 0) {
			orientation = (Sensor) sensorList.get(0);
		}

		// �[���̉��T�C�Y���擾����
		int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
		// �[���̏c�T�C�Y���擾����
		int screenHeight = getWindowManager().getDefaultDisplay().getHeight();

		// �`��pView���\�z����
		drawView = new DrawView(this, screenWidth, screenHeight);
		drawView.setBackgroundColor(Color.WHITE);
		setContentView(drawView);

	}

	@Override
	protected void onResume() {

		super.onResume();

		// �����x�Z���T���[�h
		if (accelerometer != null) {
			sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
		}

		// �����Z���T���[�h
		if (orientation != null) {
			sensorManager.registerListener(this, orientation, SensorManager.SENSOR_DELAY_FASTEST);
		}

	}

	@Override
	protected void onStop() {

		// �Z���T�폜
		sensorManager.unregisterListener(this);
		super.onStop();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * ���x�ς����������
	 */
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	/**
	 * �Z���T�ύX������
	 */
	@Override
	public void onSensorChanged(SensorEvent event) {

		// �Z���T�^�C�v����
		switch (event.sensor.getType()) {

		case Sensor.TYPE_ACCELEROMETER:// �����x�Z���T�̏ꍇ
			// �`��View�̕`��ʒu����
			drawView.setAPosition((int) event.values[SensorManager.DATA_X], (int) event.values[SensorManager.DATA_Y],
					(int) event.values[SensorManager.DATA_Z]);
			break;
		case Sensor.TYPE_ORIENTATION:// �����Z���T�̏ꍇ
			// �`��View�̕`��ʒu����
			drawView.setOPosition((int) event.values[SensorManager.DATA_X], (int) event.values[SensorManager.DATA_Y],
					(int) event.values[SensorManager.DATA_Z]);
			break;
		default:
			break;
		}

		// ��ʍĕ`��
		drawView.invalidate();

	}
}
