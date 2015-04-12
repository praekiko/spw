package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Item extends Sprite{
	public static final int Y_TO_FADE = 400;
	public static final int Y_TO_DIE = 600;
	
	protected int step = 11; 
	protected boolean alive = true; // when intersect in GameEngine -> they can modify

	Color itemColor = new Color (110, 218, 83);
	
	public Item(int x, int y) {
		super(x, y, 10, 20);		
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(itemColor);
		g.fillRoundRect(x, y, width, height, 5, 5);
	}

	public void proceed(){
		y += step;
		if(y > Y_TO_DIE){
			alive = false;
		}
	}
	
	public boolean isAlive(){
		return alive;
	}

	public void setToDie(){
		alive = false;
	}

	public abstract void doWhenCollect(GameEngine ge);

}