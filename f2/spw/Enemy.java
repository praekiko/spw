package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class Enemy extends Sprite{
	public static final int Y_TO_FADE = 400;
	public static final int Y_TO_DIE = 600;
	
	protected int step = 11; // that Bullet can modify it
	protected boolean alive = true; // when intersect in GameEngine -> they can modify

	Color enemyColor = new Color (110, 218, 83);
	
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
		g.setColor(enemyColor);
		g.fillRoundRect(x, y, width, height, 5, 5);
	}

	public void proceed(){
		y += step;
		if(y > Y_TO_DIE){
			alive = false;
		}
	}
	
	public boolean isAlive(){
		return alive;
	}

	public void setToDie(){
		alive = false;
	}

}