import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Enemy {
	
	private int scorePerEnemy;
	private int enemyStrength;
	private int EnemyStep;
	private int enemyWidth;
	private int enemyHeight;
	private int enemyHealth;
	private int enemyX;
	private int enemyY;
	private int breakX;
	private int breakInterval = 0;
	private Image enemyImage;
	private boolean firable = false;
	private int gunY;
	
	public Enemy() {
		
	}
	
	public void draw(Graphics g) {
		//g.setColor(Color.green);
		//g.fillRect(enemyX,enemyY,enemyWidth,enemyHeight);
		g.drawImage(enemyImage, enemyX,enemyY, null);
	}
	
	public void takeHit(int strength) {
		enemyHealth-= strength;
	}
	
	public int getEnemyStep() {
		if(getEnemyX()==breakX && breakInterval>0) {
			breakInterval--;
			return 0;
		}
		else return EnemyStep;
	}
	
	public void setEnemyStep(int EnemyStep) {this.EnemyStep = EnemyStep;}
	public void setInterval(int breakInterval) {this.breakInterval = breakInterval;}
	public void setBreakX(int breakX) {this.breakX = breakX;}
	public void setEnemyHeight(int enemyHeight) {this.enemyHeight = enemyHeight;}
	public void setEnemyWidth(int enemyWidth) {this.enemyWidth = enemyWidth;}
	public void setImage(String path) {enemyImage = new ImageIcon(path).getImage();}
	public void setEnemyX(int enemyX) {this.enemyX = enemyX;}
	public void setEnemyY(int enemyY) {this.enemyY = enemyY;}
	public void setEnemyHealth(int enemyHealth) {this.enemyHealth = enemyHealth;}
	public void setScorePerEnemy(int scorePerEnemy) {this.scorePerEnemy = scorePerEnemy;}
	public void setEnemyStrength(int enemyStrength) {this.enemyStrength = enemyStrength;}
	public void setFirable(boolean fireable) { this.firable = fireable;}
	public void setGunY(int gunY) {this.gunY = gunY;}
	
	
	public int getStrength() {
		return enemyStrength;
	}
	
	public int getScorePerEnemy() {
		return scorePerEnemy;
	}
	
	public int getEnemyHealth() {
		return enemyHealth;
	}
	
	public int getEnemyX() {
		return enemyX;
	}
	public int getEnemyY() {
		return enemyY;
	}
	
	public int getEnemyHeight() {
		return enemyHeight;
	}
	public int getEnemyWidth() {
		return enemyWidth;
	}
	public int getEnemyStepV() {
		return 0;
	}
	
	public int getBreakX() {
		return breakX;
	}
	public int getGunY() {
		return gunY;
	}
	public int getBreakInterval() {
		return breakInterval;
	}
	public boolean isFirable() {
		return firable;
	}
	public boolean isAlive() {
		if(getEnemyHealth()<=0) return false;
		else if(getEnemyX() <= 0) return false;
 		return true;
	}
}
