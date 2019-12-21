import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;


public class Bullet {
	
	private int X; 
	private int Y; 
	private int value;
	private int bulletWidth = 20;
	private int bulletHeight = 10;
	private int bulletSpeed;
	private int bulletStrength = 1;
	
	private Image bulletImage;
	
	
	
	public Bullet(int X, int Y, int speed, Image bulletImage) {
		this.X = X+55;
		this.Y = Y;
		value = 1;
		this.bulletImage = bulletImage;
		bulletSpeed = speed; //for Ship bullet, this is positive. For enemy bullet, this should be negative;
	}

	public void fire(Graphics g) {
		moveBullet();
		if(value> 0) {
			//g.setColor(Color.white);
			//g.fillRect(X,Y,bulletWidth,bulletHeight);
			g.drawImage(bulletImage, X, Y, null);
		}
	}
	
	private void moveBullet() {
		X+=bulletSpeed;
	}
	
	public void setBulletValue(int value) {
		this.value = value;
	}
	
	public int getBulletValue() {
		return value;
	}
	
	public void killBullet() {
		value = 0;
	}
	
	public int getStrength() {
		return bulletStrength;
	}
	
	public int getBulletHeight() {
		return bulletHeight;
	}
	
	public int getBulletWidth() {
		return bulletWidth;
	}
	
	public int getBulletX() {
		return X;
	}
	public int getBulletY() {
		return Y;
	}
	
	
}
