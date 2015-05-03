package f2.spw;

public interface GameReporter {

	long getScore();
	int getHeartScore();

	// int getNumOfNeedle();
	// int getTimePerOneNeedle();

	// get Current X,Y axis of spaceship
	int getCurrentXOfSS();
	int getCurrentYOfSS();
	long getDamage();

	//is Game Over?
	boolean isGameOver();

	// int getCountBonusTime();
	// int getPillCount();

	// boolean getEnableGenerateBulletForBigNeedle();
	// int getTimeForBigNeedle();
}
