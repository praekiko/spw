package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class EnemyBody extends Enemy{

	Color enemyColor = new Color (102, 204, 0);
	
	public EnemyBody(int x, int y) {
		super(x, y);	
		super.setImage("f2/spw/image/virus.gif");		
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(enemyColor);
		g.fillOval(x, y, 20, 20);
		// g.drawImage(getImage(), x, y, 60, 50, null);
	}


	public void doWhenCrash(GameEngine ge){
		ge.heartScore--;
	}
}