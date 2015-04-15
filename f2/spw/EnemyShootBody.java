package f2.spw;

import java.awt.Color;
import java.awt.Graphics2D;

public class EnemyShootBody extends Enemy implements HasBullet{
	Color enemyColor = new Color (76, 153, 0);
	
	public EnemyShootBody(int x, int y) {
		super(x, y);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(enemyColor);
		g.fillRoundRect(x, y, 20, 20, 5, 5);
	}

	// Override generateBullet in HasBullet 
	public void generateBullet(GameEngine ge, int currentX, int currentY){
		ge.generateBulletEnemy(currentX + 7, currentY);
	}

	public void doWhenCrash(GameEngine ge){
		ge.heartScore--;
	}
}