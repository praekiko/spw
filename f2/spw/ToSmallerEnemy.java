package f2.spw;

import java.awt.Color;
import java.awt.Graphics2D;

public class ToSmallerEnemy extends Enemy{

	Color enemyColor = new Color (255, 51, 51);
	
	public ToSmallerEnemy(int x, int y) {
		super(x, y);		
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(enemyColor);
		g.fillRoundRect(x, y, 20, 20, 5, 5);
	}

}