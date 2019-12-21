
public class EnemySmall extends Enemy{
	public EnemySmall(int enemyX, int enemyY, int breakX, int breakInterval) {
		setEnemyHealth(1);
		setScorePerEnemy(5);
		setEnemyStrength(10);
		setBreakX(breakX);
		setInterval(breakInterval);
		setEnemyStep(2);
		setEnemyWidth(50);
		setEnemyHeight(50);
		setEnemyX(enemyX);
		setEnemyY(enemyY);
		setImage("images/enemySmallDesign.png");
	}
}
