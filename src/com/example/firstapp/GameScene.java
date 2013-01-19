package com.example.firstapp;

import java.util.Iterator;
import java.util.LinkedList;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.activity.BaseGameActivity;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;

public class GameScene extends Scene implements IOnSceneTouchListener {
	public Ship ship;
	Camera mCamera;
	public float accelerometerSpeedX;
	SensorManager sensorManager;
	public LinkedList<Bullet> bulletList;
	public int bulletCount;
	public int missCount;

	public GameScene() {
		setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		// attaching an EnemyLayer entity with 12 enemies on it
		attachChild(new EnemyLayer(12));
		mCamera = BaseActivity.getSharedInstance().mCamera;
		ship = Ship.getSharedInstance();
		ship.sprite.detachSelf();

		bulletList = new LinkedList<Bullet>();
		attachChild(ship.sprite);
		ship.sprite.setVisible(true);

		BaseActivity.getSharedInstance().setCurrentScene(this);
		sensorManager = (SensorManager) BaseActivity.getSharedInstance()
				.getSystemService(BaseGameActivity.SENSOR_SERVICE);
		SensorListener.getSharedInstance();
		sensorManager.registerListener(SensorListener.getSharedInstance(),
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_GAME);

		setOnSceneTouchListener(this);

		resetValues();

	}

	// method to reset values and restart the game
	public void resetValues() {
		missCount = 0;
		bulletCount = 0;
		ship.restart();
		EnemyLayer.purgeAndRestart();
		clearChildScene();
		registerUpdateHandler(new GameLoopUpdateHandler());
	}

	public void moveShip() {
		ship.moveShip(accelerometerSpeedX);
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		if (!CoolDown.getSharedInstance().checkValidity())
			return false;

		synchronized (this) {
			ship.shoot();
		}
		return true;
	}

	public void detach() {
		Log.v("Jimvaders", "GameScene onDetached()");
		clearUpdateHandlers();
		for (Bullet b : bulletList) {
			BulletPool.sharedBulletPool().recyclePoolItem(b);
		}
		bulletList.clear();
		detachChildren();
		Ship.instance = null;
		EnemyPool.instance = null;
		BulletPool.instance = null;

	}

	public void cleaner() {
		synchronized (this) {
			// if all Enemies are killed
			if (EnemyLayer.isEmpty()) {
				Log.v("Jimvaders", "GameScene Cleaner() cleared");
				setChildScene(new ResultsScene(mCamera));
				clearUpdateHandlers();
			}
			Iterator<Enemy> eIt = EnemyLayer.getIterator();
			while (eIt.hasNext()) {
				Enemy e = eIt.next();
				Iterator<Bullet> it = bulletList.iterator();
				while (it.hasNext()) {
					Bullet b = it.next();
					if (b.sprite.getY() <= -b.sprite.getHeight()) {
						BulletPool.sharedBulletPool().recyclePoolItem(b);
						it.remove();
						missCount++;
						continue;
					}

					if (b.sprite.collidesWith(e.sprite)) {

						if (!e.gotHit()) {							
							EnemyPool.sharedEnemyPool().recyclePoolItem(e);
							eIt.remove();
						}
						BulletPool.sharedBulletPool().recyclePoolItem(b);
						it.remove();
						break;
					}
				}

			}
		}
	}

	
}