package f2.spw;

import java.awt.Color;
import java.awt.Graphics2D;

public class SpaceShip extends Sprite{

	private int step = 10;
	private final int resetWidth;

	private Color buttonColor = new Color (255, 153, 153);
	
	public SpaceShip(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.resetWidth = width;
		super.setImage("f2/spw/image/ch3.gif");		
	}


	@Override
	public void draw(Graphics2D g) {
	// 	g.setColor(buttonColor);
	// 	g.fillRect(x, y, width, height);
		g.drawImage(getImage(), x, y, width, height, null);
	}

	public void moveX(int direction){
		x += (step * direction);
		if(x < 0){
			x = 0;
		}
		if(x > GamePanel.WIDTH - width){
			x = GamePanel.WIDTH - width;
		}			
	}

	public void moveY(int direction){
		y += (step * direction);
		if(y < 0){
			y = 0;
		}
		if(y > GamePanel.HEIGHT - width){
			y = GamePanel.HEIGHT - width;
		}
	}

	public void increaseSize(){
		width += 20;
	}

	public void resetSize(){
		width = resetWidth;
	}

}
