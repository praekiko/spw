package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class EnemyBody extends Enemy{

	Color enemyColor = new Color (110, 218, 83);
	
	public EnemyBody(int x, int y) {
		super(x, y);		
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(enemyColor);
		g.fillRoundRect(x, y, width, height, 5, 5);
	}


	public void doWhenCrash(GameEngine ge){
		ge.heartScore--;
	}
}