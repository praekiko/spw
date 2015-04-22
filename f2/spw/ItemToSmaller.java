package f2.spw;

import java.awt.Color;
import java.awt.Graphics2D;

public class ItemToSmaller extends Item{

	Color enemyColor = new Color (255, 51, 51);
	
	public ItemToSmaller(int x, int y) {
		super(x, y);		
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(enemyColor);
		g.fillRoundRect(x, y, 20, 8, 5, 5);
	}

	@Override
	public void	doWhenCollect(GameEngine ge){
		ge.v.resetSize();
	}

}