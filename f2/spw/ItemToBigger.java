package f2.spw;

import java.awt.Color;
import java.awt.Graphics2D;

public class ItemToBigger extends Item{

	Color enemyColor = new Color (255, 204, 51);
	
	public ItemToBigger(int x, int y) {
		super(x, y);		
		super.setImage("f2/spw/image/plus.png");
	}

	@Override
	public void draw(Graphics2D g) {
		// g.setColor(enemyColor);
		// g.fillOval(x, y, 20, 20);
		g.drawImage(getImage(), x, y, null);
	}

	@Override
	public void	doWhenCollect(GameEngine ge){
		ge.v.increaseSize();
	}

}