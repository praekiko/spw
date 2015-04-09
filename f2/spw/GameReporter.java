package f2.spw;

public interface GameReporter {

	long getScore();
	int getHeartScore();
	int getNumOfNeedle();

	// get Current X,Y axis of spaceship
	int getCurrentXOfSS();
	int getCurrentYOfSS();
	int getDamage();

}
