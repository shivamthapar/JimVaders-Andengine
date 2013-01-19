package com.example.firstapp;

import org.andengine.util.adt.pool.GenericPool;

public class EnemyPool extends GenericPool<Enemy> {
	public static EnemyPool instance;
	
	public static EnemyPool sharedEnemyPool(){
		if(instance==null)
			instance=new EnemyPool();
		return instance;
	}
	
	private EnemyPool(){
		super();
	}
	@Override
	protected Enemy onAllocatePoolItem() {
		return new Enemy();
	}
	
	protected void onHandleRecycleItem(final Enemy e){
		e.sprite.setVisible(false);
		e.sprite.detachSelf();
		e.clean();
	}

}
