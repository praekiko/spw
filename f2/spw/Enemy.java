package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Enemy extends Sprite{
	public static final int Y_TO_FADE = 400;
	public static final int Y_TO_DIE = 600;
	
	protected int step = 11; // that Bullet can modify it
	protected boolean alive = true; // when intersect in GameEngine -> they can modify
	
	public Enemy(int x, int y) {
		super(x, y, 10, 20);		
	}

	@Override
	public void draw(Graphics2D g) {
		// if(y < Y_TO_FADE)
		// 	g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		// else{
		// 	g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 
		// 			(float)(Y_TO_DIE - y)/(Y_TO_DIE - Y_TO_FADE)));
		// }
	}

	public void proceed(){
		y += step;
		if(y > Y_TO_DIE){
			alive = false;
		}
	}

	// when press B
	public void speedUp(){
		y += 2 * step;
	}
	
	public boolean isAlive(){
		return alive;
	}

	public void setToDie(){
		alive = false;
	}

	public abstract void doWhenCrash(GameEngine ge);

	public void movableEnemyX(){
		
	}

}