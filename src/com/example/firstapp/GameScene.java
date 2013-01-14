package com.example.firstapp;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;

public class GameScene extends Scene{
	public Ship ship;
	Camera mCamera;
	
	public GameScene() {
	    setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
	    mCamera = BaseActivity.getSharedInstance().mCamera;
	    ship = Ship.getSharedInstance();
	    attachChild(ship.sprite);
	}

}
