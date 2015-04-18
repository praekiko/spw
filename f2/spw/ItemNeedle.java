package f2.spw;

import java.awt.Graphics2D;

public class ItemNeedle extends Item{

	public ItemNeedle(int x, int y) {
		super(x, y);
		super.setImage("f2/spw/image/thpinkneedle.gif");		
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(getImage(), x, y, null);
	}

	@Override
	public void	doWhenCollect(GameEngine ge){
		ge.generateBulletForNeedle = true;  // Enable to generate
		ge.enableTimerForNeedle = true; // enable timer
		ge.timerOneSecond.start();
		if(ge.numOfNeedle < 3){ 	// Max at 3
			ge.numOfNeedle++;
			ge.timePerOneNeedle += 10;	
		}
		else {
			ge.numOfNeedle = 3;
		}
		System.out.println("Needle = " + ge.numOfNeedle);
	}


}