package f2.spw;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Ability implements AbilityReporter{
	GamePanel gp;
	GameEngine ge;
	SpaceShip v;

	protected Timer timerOneSecond;

	public Ability(GameEngine ge, GamePanel gp, SpaceShip v){
		this.ge = ge;
		this.gp = gp;
		this.v = v;

		timerOneSecond = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {	
				if(enableBonusTime){
					countDownBonusTime();
				}								
				if(enableTimerForNeedle){
					countDownNeedleBullet();
				}			
				if(enableGenerateBulletForBigNeedle){
					countDownBigNeedle();
				}
			}
		});
		timerOneSecond.setRepeats(true);
	}

	//////////////// Bonus Time ////////////////////////\
	protected int pillCount = 0; // count number of pill in bonus time
	private final static int countBonusTimeAtStart = 20; // final!!
	protected int countBonusTime = 20; // 20s
	protected boolean enableBonusTime = false;

	protected void bonusProcess(){
		gp.updateAbility(this);
		if(pillCount == 5){
			ge.timerStartBonusTime.stop();
			ge.healthy = true;  // change map
			pillCount = 0; // reset
			countBonusTime = countBonusTimeAtStart;  // reset to start value
			gp.setEnableBonusTime(false);
			gp.updateAbility(this);
			// start again
			// ge.timerStartBonusTime.start();
		}
	}

	public int getCountBonusTime(){
		return countBonusTime;
	}

	public int getPillCount(){
		return pillCount;
	}

	private void countDownBonusTime(){
		countBonusTime--;
		if(countBonusTime == 0){
			pillCount = 5; // set pill =5
			bonusProcess();
			if(!enableTimerForNeedle){ //check if timer for needle id running?
				timerOneSecond.stop();
			}			
			System.out.println("Out of Time ");					
		}
	}

	////////////////// Needle ////////////////////
	protected int timePerOneNeedle = 0; // 10s/1Needle  +=10 when Needle++
	protected boolean enableTimerForNeedle = false;
	protected boolean enableGenerateBulletForNeedle = false;
	protected int numOfNeedle = 0;

	public int getNumOfNeedle(){
		return numOfNeedle;
	}

	public int getTimePerOneNeedle(){
		return  timePerOneNeedle;
	}	

	private void countDownNeedleBullet(){
		if(timePerOneNeedle > 0){
			timePerOneNeedle--;
		}				
		else if(timePerOneNeedle == 0){
			if(!enableBonusTime){ //check if timer for bonus is running?
				timerOneSecond.stop();
			}	
			// System.out.println("Stop Needle Bullet");
			enableGenerateBulletForNeedle = false;
			numOfNeedle = 0;
		}
	}

	// Generate Bullet by intersect Needle Enemy
	protected void generateBulletSpaceShip(){
		if(enableGenerateBulletForNeedle){
			for(int i = 0; i < numOfNeedle; i++){
				int pos = 2 - i;
				int offset = pos > 0 ? v.width / pos : 0;
				BulletSpaceShip ssb = new BulletSpaceShip(ge.getCurrentXOfSS() + offset, ge.getCurrentYOfSS());
				ge.gp.sprites.add(ssb);
				ge.spaceshipBullets.add(ssb);
			}
		}
	}
	
	///////////////// Speed Up////////////////////
	protected boolean enableGenerateBulletForBigNeedle = false;  //press B to enable
	private final static int startTimeForBigNeedle = 10;
	private int timeForBigNeedle = 10;

	protected void bigNeedleProcess(){
		if((numOfNeedle == 3) & isTimeOkay()){
			enableGenerateBulletForBigNeedle = true;

				gp.scrollingBg.bgOne.stepUp();
				gp.scrollingBg.bgTwo.stepUp();
		}		
		timerOneSecond.start();
	}

	private void countDownBigNeedle(){
		timeForBigNeedle--;
		if(timeForBigNeedle == 0){
			enableGenerateBulletForBigNeedle = false;

				gp.scrollingBg.bgOne.stepDown();
				gp.scrollingBg.bgTwo.stepDown();

			if(!enableTimerForNeedle){ //check if timer for needle id running?
				timerOneSecond.stop();
				timeForBigNeedle = startTimeForBigNeedle;
			}							
		}
	}

	private boolean isTimeOkay(){
		if(timeForBigNeedle == startTimeForBigNeedle){
			return true;
		}
		return false;
	}

	public boolean getEnableGenerateBulletForBigNeedle(){
		return enableGenerateBulletForBigNeedle;
	}

	public int getTimeForBigNeedle(){
		return timeForBigNeedle;
	}

	protected void generateBulletBigNeedle(){
		if(enableGenerateBulletForBigNeedle){
			for(int i = 0; i < 3; i++){  
				int pos = 2 - i;
				int offset = pos > 0 ? v.width / pos : 0;
				BulletSpaceShip ssb = new BulletSpaceShip(ge.getCurrentXOfSS() + offset, ge.getCurrentYOfSS());
				ge.gp.sprites.add(ssb);
				ge.spaceshipBullets.add(ssb);
			}
		}
	}
}
