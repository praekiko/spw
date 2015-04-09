package f2.spw;

import java.awt.Graphics2D;

public class NeedleEnemy extends Enemy{

	public NeedleEnemy(int x, int y) {
		super(x, y);
		super.setImage("f2/spw/image/thpinkneedle.gif");		
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(image, x, y, null);
	}

}