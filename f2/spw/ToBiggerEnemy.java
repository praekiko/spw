package f2.spw;

import java.awt.Color;
import java.awt.Graphics2D;

public class ToBiggerEnemy extends Enemy{

	Color enemyColor = new Color (255, 204, 51);
	
	public ToBiggerEnemy(int x, int y) {
		super(x, y);		
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(enemyColor);
		g.fillOval(x, y, 20, 20);
	}

}