package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class EnemyBullet extends Sprite{
	public static final int Y_TO_FADE = 400;
	public static final int Y_TO_DIE = 600;
	
	private int step = 11;
	private boolean alive = true;

	Color enemyColor = new Color (51, 102, 0);
	
	public EnemyBullet(int x, int y) {
		super(x, y, 5, 10);
		
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(enemyColor);
		g.fillRect(x, y, width, height);
	}

	public void proceed(){
		y += 3 * step;
		if(y > Y_TO_DIE){
			alive = false;
		}
	}
	
	public boolean isAlive(){
		return alive;
	}
}