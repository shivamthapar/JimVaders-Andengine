package com.example.firstapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class SensorListener implements SensorEventListener{
	static SensorListener instance;
	GameScene scene;

	public static SensorListener getSharedInstance() {
	    if (instance == null)
	        instance = new SensorListener();
	    return instance;
	}

	public SensorListener() {
	    instance = this;
	    scene = (GameScene) BaseActivity.getSharedInstance().mCurrentScene;
	}
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		synchronized (this) {
		    switch (event.sensor.getType()) {
		        case Sensor.TYPE_ACCELEROMETER:
		            scene.accelerometerSpeedX = event.values[1];
		            break;
		        default:
		            break;
		    }
		}		
	}
	

}
