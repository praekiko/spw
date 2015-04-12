package f2.spw;

import java.awt.Color;
import java.awt.Graphics2D;

public class EnemyShootBody extends Enemy{
	Color enemyColor = new Color (76, 153, 0);
	
	public EnemyShootBody(int x, int y) {
		super(x, y);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(enemyColor);
		g.fillRoundRect(x, y, 20, 20, 5, 5);
	}

}