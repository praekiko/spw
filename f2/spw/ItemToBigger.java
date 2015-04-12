package f2.spw;

import java.awt.Color;
import java.awt.Graphics2D;

public class ItemToBigger extends Item{

	Color enemyColor = new Color (255, 204, 51);
	
	public ItemToBigger(int x, int y) {
		super(x, y);		
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(enemyColor);
		g.fillOval(x, y, 20, 20);
	}

	@Override
	public void	doWhenCollect(GameEngine ge){
		ge.v.increaseSize();
	}

}