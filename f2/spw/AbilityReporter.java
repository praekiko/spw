package f2.spw;

public interface AbilityReporter {

	int getNumOfNeedle();
	int getTimePerOneNeedle();

	int getCountBonusTime();
	int getPillCount();

	boolean getEnableGenerateBulletForBigNeedle();
	int getTimeForBigNeedle();
}
