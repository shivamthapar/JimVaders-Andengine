package com.example.firstapp;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;

public class Ship {
	public Rectangle sprite;
	public static Ship instance;
	Camera mCamera;
	
	public static Ship getSharedInstance(){
		if(instance==null){
			instance=new Ship();
		}
		return instance;
	}
	private Ship(){
		sprite= new Rectangle (0,0,70,30,BaseActivity.getSharedInstance().getVertexBufferObjectManager());
		mCamera= BaseActivity.getSharedInstance().mCamera;
		sprite.setPosition(mCamera.getWidth()/2-sprite.getWidth()/2, mCamera.getHeight()-sprite.getHeight()-10);
		
	}
}
