
public class EnemyMedium extends Enemy {
	
	public EnemyMedium(int enemyX, int enemyY, int breakX, int breakInterval,boolean firable) {
		setEnemyHealth(3);
		setScorePerEnemy(10);
		setEnemyStrength(20);
		setBreakX(breakX);
		setInterval(breakInterval);
		setEnemyStep(1);
		setEnemyWidth(100);
		setEnemyHeight(70);
		setEnemyX(enemyX);
		setEnemyY(enemyY);
		setFirable(firable);
		if(firable) setGunY(getEnemyY()+(getEnemyHeight()/2));
		
		setImage("images/enemyMediumDesign.png");
	}
}
