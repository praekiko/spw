package f2.spw;

import java.awt.Color;
import java.awt.Graphics2D;

public class EnemySickPill extends Enemy{
	Color enemyColor = new Color (0, 204, 204);
	
	public EnemySickPill(int x, int y) {
		super(x, y);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(enemyColor);
		g.fillRoundRect(x, y, 20, 10, 5, 5);
	}

	public void doWhenCrash(GameEngine ge){
		ge.score += 100;
		ge.pillCount++;
		System.out.println("Pill = " + ge.pillCount);
	}

}