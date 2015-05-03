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
		ge.ability.enableGenerateBulletForNeedle = true;  // Enable to generate
		ge.ability.enableTimerForNeedle = true; // enable timer
		ge.ability.timerOneSecond.start();
		if(ge.ability.numOfNeedle < 3){ 	// Max at 3
			ge.ability.numOfNeedle++;
			ge.ability.timePerOneNeedle += 10;	
		}
		else {
			ge.ability.numOfNeedle = 3;
		}
		System.out.println("Needle = " + ge.ability.numOfNeedle);
	}


}