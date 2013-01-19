package com.example.firstapp;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.util.color.Color;

public class Bullet {

	public Rectangle sprite;
	public Bullet(){
		sprite= new Rectangle(0, 0, 20, 20, BaseActivity.getSharedInstance().getVertexBufferObjectManager());
		sprite.setColor(new Color(Color.RED));
	}
	
}
