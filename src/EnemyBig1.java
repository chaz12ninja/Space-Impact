
public class EnemyBig1 extends Enemy{
	private int breakIntervalY;
	private int breakIntervalOriginal;
	private int breakXC;
	public EnemyBig1(int enemyX, int enemyY, int breakX, int breakInterval) {
		setEnemyHealth(20);
		setScorePerEnemy(50);
		setEnemyStrength(100);
		setBreakX(breakX);
		setInterval(breakInterval);
		setEnemyStep(1);
		setEnemyWidth(400);
		setEnemyHeight(400);
		setEnemyX(enemyX);
		setEnemyY(enemyY);
		setImage("images/enemybig1Design.png");
		setFirable(true);
		setGunY(getEnemyY()+3*(getEnemyHeight()/4));
		breakIntervalY = breakInterval;
		breakIntervalOriginal = breakInterval;
		breakXC = breakX;
	}
	
	
	//This is a custom function for the EnemyBig1
	//This will move the Enemy vertically when breaks were applied in the middle.
	public int getEnemyStepV() {
		if(getEnemyX()==breakXC && breakIntervalY>0) {
			breakIntervalY--;
			if (breakIntervalY>7*(breakIntervalOriginal/8)) return -1;
			if (breakIntervalY>5*(breakIntervalOriginal/8)) return +1;
			if (breakIntervalY>3*(breakIntervalOriginal/8)) return -1;
			if (breakIntervalY>1*(breakIntervalOriginal/8)) return +1;
			if (breakIntervalY>(breakIntervalOriginal/8)) return -2;
		}	
		return 0;
	}
	
	
}
