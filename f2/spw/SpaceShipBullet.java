package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

// Bullet for SpaceShip
public class SpaceShipBullet extends Sprite{
	public static final int Y_TO_FADE = 400;
	public static final int Y_TO_DIE = 0;
	
	private int step = 30;
	private boolean alive = true;

	Color enemyColor = new Color (255, 204, 204);
	
	public SpaceShipBullet(int x, int y) {
		super(x, y, 5, 10);
		
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