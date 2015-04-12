package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

// Bullet for SpaceShip
public class BulletSpaceShip extends Sprite{
	public static final int Y_TO_FADE = 400;
	public static final int Y_TO_DIE = 0;
	
	private int step = 20;
	private boolean alive = true;

	Color enemyColor = new Color (102, 255, 255);
	
	public BulletSpaceShip(int x, int y) {
		super(x, y, 5, 20);
		
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(enemyColor);
		g.fillRect(x, y, width, height);
	}

	public void proceed(){
		y -= step;
		if(y < Y_TO_DIE){
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