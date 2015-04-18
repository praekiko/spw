package f2.spw;

import java.awt.Color;
import java.awt.Graphics2D;

public class EnemyShootBody extends Enemy implements HasBullet{
	public static final int X_LEFT = 0;
	public static final int X_RIGHT = 350;

	private boolean moveRight = false;
	private int moveSpeed = 5;

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
	public void generateBullet(GameEngine ge){
		ge.generateBulletEnemy(x + 7, y);
		
	}

	public void doWhenCrash(GameEngine ge){
		ge.heartScore--;
	}

	public void movableEnemyX(){
		if(x >= X_RIGHT){
			moveRight = true;
		}	
		else if (x <= X_LEFT){
			moveRight = false;
		}

		if(moveRight){
			x -= moveSpeed;
		}
		else {
			x += moveSpeed;
		}
	}
}