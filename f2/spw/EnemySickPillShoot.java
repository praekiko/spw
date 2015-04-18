package f2.spw;

import java.awt.Color;
import java.awt.Graphics2D;

public class EnemySickPillShoot extends Enemy implements HasBullet{
	Color enemyColor = new Color (255, 255, 255);
	
	public EnemySickPillShoot(int x, int y) {
		super(x, y);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(enemyColor);
		g.fillOval(x, y, 20, 20);
	}

	// Override generateBullet in HasBullet 
	public void generateBullet(GameEngine ge){
		ge.generateBulletEnemy(x + 7, y);
	}

	public void doWhenCrash(GameEngine ge){
		ge.heartScore--;
	}
}