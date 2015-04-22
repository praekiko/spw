package f2.spw;

import java.awt.Color;
import java.awt.Graphics2D;

public class EnemyShootBody extends Enemy implements HasBullet{
	public static final int X_LEFT = 50;
	public static final int X_RIGHT = 300;

	private boolean moveLeft = false;
	private int moveSpeed = 5;
	private int startX;

	Color enemyColor = new Color (76, 153, 0);
	
	public EnemyShootBody(int x, int y) {
		super(x, y);
		this.startX = x;
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
		if((x >= startX + 100) && (x >= X_RIGHT)){
			moveLeft = true;
		}	
		else if (x <= startX){
			moveLeft = false;
		}

		if(moveLeft){
			x -= moveSpeed * (int)(Math.sin(0.5 * Math.PI));			
		}
		else {
			x += moveSpeed * (int)(Math.sin(0.5 * Math.PI));
		}		
	}
}