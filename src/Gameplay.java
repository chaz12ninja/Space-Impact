import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Gameplay extends JPanel implements ActionListener, KeyListener{
	
	private boolean play = false;
	private boolean restartReady = false;
	private int borderThickness = 3;
	private int InfoHeight = 50;
	private int score;
	private int shipHealth ;
	
	private Timer timer;
	private int delay = 10;
	
	private int shipX ;
	private int shipY ;
	private int shipStep = 10;
 	private int[] shipLimitsX = {40,500};
 	private int[] shipLimitsY = {InfoHeight+10,Game.windowHeight-(100)};
 	private int[] shipSize = {100,60};
 	
 	private boolean createdEnemies = false;

 	ArrayList<Bullet> bullets = new ArrayList<Bullet>() ;
 	ArrayList<Enemy> enemies = new ArrayList<Enemy>() ;
 	ArrayList<Bullet> enemyBullets = new ArrayList<Bullet>() ;
 	
 	private Image shipImage = new ImageIcon("images/shipDesign.png").getImage();
 	private Image shipBulletImage = new ImageIcon("images/shipbullet.png").getImage();
 	private Image enemyBulletImage = new ImageIcon("images/enemybullet.png").getImage();
 	
 	Random random = new Random();
	
	public Gameplay() {
		//Setting difficulty
		//level = 2;
		setIntitialGameVariables();
		
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		//background
		g.setColor(Color.darkGray);
		g.fillRect(1, 1, Game.windowWidth-8, Game.windowHeight-8);
		
		//borders
		g.setColor(Color.black);
		g.fillRect(0,0,borderThickness,Game.windowHeight-8);
		g.fillRect(0,0,Game.windowWidth,borderThickness);
		g.fillRect(Game.windowWidth-8,0,borderThickness,Game.windowHeight-8);
		g.fillRect(0, InfoHeight, Game.windowWidth-8, borderThickness);
		
		//Scores
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString ("Score "+score,Game.windowWidth-200,32);
		g.drawString ("Bullets "+bullets.size(),Game.windowWidth-350,32);
		g.drawString("Enemies Left "+enemies.size(),Game.windowWidth-600,32);
		g.drawString("Ship Health "+shipHealth,Game.windowWidth-1000,32);
		
		//the ship
		g.drawImage(shipImage, shipX, shipY, null);
		
		
		//Game Over
		if(shipHealth <= 0) {
			gameOver(g);
			moveRight();
		}
		
		//Bullets Fired by Ship
		if(!bullets.isEmpty()) {
			try {
				for(Bullet b:bullets) {
					if(b.getBulletValue()==1) b.fire(g); //Draw the bullet
					else bullets.remove(b); //This is where dead bullets are removed	
				}
			} catch (ConcurrentModificationException e) { }
		}
		
		//Bullets Fired by Enemies
		if(!enemyBullets.isEmpty()) {
			try {
				for(Bullet eb:enemyBullets) {
					if(eb.getBulletValue()==1) eb.fire(g); //Draw the bullet
					else enemyBullets.remove(eb); //This is where dead bullets are removed	
				}
			} catch (ConcurrentModificationException e) { }
		}
		
		//THis will draw the enemies if they are alive;
		if(!enemies.isEmpty()) {
			try {
				for(Enemy e:enemies) {
					if(e.isAlive()) {
						e.setEnemyX(e.getEnemyX()-e.getEnemyStep()); //This will move the enemy horizontally according to the specification
						e.setEnemyY(e.getEnemyY()-e.getEnemyStepV()); //This will move the enemy vertically according to the specification
						e.draw(g); //Draw the enemy
						
						// This will Fire if the enemy is fireable
						if(e.isFirable() ) {
							int rate = 40; // = low-->> high rate
							enemyBulletFire(e, rate);
						}
						
					}
					else enemies.remove(e); //This is where dead enemies are removed
				}
			} catch (ConcurrentModificationException ex) { }
		}
				
		
		g.dispose();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		//Starts Play
		if(play) {
			
			//This will kill the bullets which just flew to the end without impact
			if(!bullets.isEmpty()) {
				for(Bullet b:bullets) {
					if(b.getBulletX()>Game.windowWidth-20) {
						b.killBullet();
					}
				}
			}
			
			//Creating enemies if not created
			if(!createdEnemies || enemies.isEmpty()) {
				Random r = new Random();
				//createEnemies(7);
				createEnemies(r.nextInt((7 - 1) + 1) + 1);
			}
			
			//Checking for impacts of Enemies and Bullets
			if (!enemies.isEmpty() && !bullets.isEmpty()) {
				for(Enemy e1:enemies) {
					for(Bullet b:bullets) {
						Rectangle enemyRect = new Rectangle(e1.getEnemyX(),e1.getEnemyY(),e1.getEnemyWidth(),e1.getEnemyHeight());
						Rectangle bulletRect = new Rectangle(b.getBulletX(),b.getBulletY(),b.getBulletWidth(),b.getBulletHeight());
					
						if(bulletRect.intersects(enemyRect)) {
							e1.takeHit(b.getStrength()); //This will reduce the health of the enemy
							b.killBullet(); //This will kill the used bullet
							if(!e1.isAlive()) score+=e1.getScorePerEnemy(); //This will add the score if the enemy is dead.
						}
					}
				}
			}
			
			//Checking for impacts of Enemy bullets and SpaceShip
			if (!enemyBullets.isEmpty()) {
				for(Bullet eb:enemyBullets) {
					Rectangle enemyBulletRect = new Rectangle(eb.getBulletX(),eb.getBulletY(),eb.getBulletWidth(),eb.getBulletHeight());
					Rectangle ShipRect = new Rectangle(shipX,shipY,shipSize[0],shipSize[1]);
					
					if(ShipRect.intersects(enemyBulletRect)) {
						ShiptakeHit(eb.getStrength()); //This will take a hit from an enemy bullet
						eb.killBullet(); //This will kill the used enemy bullet
					}
				}
			}
			
			//Checking for impacts of Enemies and SpaceShip
			if (!enemies.isEmpty()) {
				for(Enemy e1:enemies) {
					Rectangle enemyRect = new Rectangle(e1.getEnemyX(),e1.getEnemyY(),e1.getEnemyWidth(),e1.getEnemyHeight());
					Rectangle ShipRect = new Rectangle(shipX,shipY,shipSize[0],shipSize[1]);
					
					if(ShipRect.intersects(enemyRect)) {
						e1.setEnemyHealth(0); //This will kill the enemy
						ShiptakeHit(e1.getStrength()); //This will take a hit from the enemy
					}
				}
			}

		}
		repaint();
	}
	

	private void setIntitialGameVariables() {
		shipX = 50;
		shipY = 312;
		shipHealth = 100;
		score = 0;
		play = true;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() ==  KeyEvent.VK_DOWN) {
			if(shipY>=shipLimitsY[1]) shipY = shipLimitsY[1];
			else moveDown();
		}
		if(e.getKeyCode() ==  KeyEvent.VK_UP) {
			if(shipY<=shipLimitsY[0]) shipY = shipLimitsY[0];
			else moveUp();
		}
		if(e.getKeyCode() ==  KeyEvent.VK_LEFT) {
			if(shipX<=shipLimitsX[0]) shipX = shipLimitsX[0];
			else moveLeft();
		}
		if(e.getKeyCode() ==  KeyEvent.VK_RIGHT) {
			if(shipX>=shipLimitsX[1]) shipX = shipLimitsX[1];
			else moveRight();
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			bullets.add(new Bullet(shipX,shipY+22,15,shipBulletImage));
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER && restartReady) {
			setIntitialGameVariables();
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			setIntitialGameVariables();
		}
	}
	
	private void createEnemies(int level) {
		
		switch (level) {
		case 1:
			for (int i = 0; i < 7; i++) {
				enemies.add(new EnemySmall(Game.windowWidth+50, InfoHeight+ 50+ (70*i),Game.windowWidth-300,200));
			}
			break;
		case 2:
			for (int i = 0; i < 7; i++) {
				enemies.add(new EnemySmall(Game.windowWidth+(40*i), InfoHeight+ 50+ (70*i),Game.windowWidth-300+(40*i),200));
			}
			break;
		case 3:
			for (int i = 0; i < 7; i++) {
				enemies.add(new EnemySmall(Game.windowWidth+240-(40*i), InfoHeight+ 50+ (70*i),Game.windowWidth-60-(40*i),200));
			}
			break;
		case 4:
			enemies.add(new EnemySmall(Game.windowWidth,(Game.windowHeight-InfoHeight)/2,Game.windowWidth-400,200));
			enemies.add(new EnemySmall(Game.windowWidth+50,(Game.windowHeight-InfoHeight)/2-70,Game.windowWidth-350,200));
			enemies.add(new EnemySmall(Game.windowWidth+50,(Game.windowHeight-InfoHeight)/2+70,Game.windowWidth-350,200));
			enemies.add(new EnemySmall(Game.windowWidth+100,(Game.windowHeight-InfoHeight)/2-140,Game.windowWidth-300,200));
			enemies.add(new EnemySmall(Game.windowWidth+100,(Game.windowHeight-InfoHeight)/2+140,Game.windowWidth-300,200));
			enemies.add(new EnemySmall(Game.windowWidth+150,(Game.windowHeight-InfoHeight)/2-210,Game.windowWidth-250,200));
			enemies.add(new EnemySmall(Game.windowWidth+150,(Game.windowHeight-InfoHeight)/2+210,Game.windowWidth-250,200));
			break;
		case 5:
			for (int i = 0; i < 4; i++) {
				enemies.add(new EnemyMedium(Game.windowWidth+50, InfoHeight+ 70+ (100*i),Game.windowWidth-300,150,firable()));
			}
			break;
		case 6:
			for (int i = 0; i < 4; i++) {
				enemies.add(new EnemyMedium(Game.windowWidth+(50*i), InfoHeight+ 70+ (100*i),Game.windowWidth-300+(50*i),150,firable()));
			}
			break;
		case 7:
			enemies.add(new EnemyBig1(Game.windowWidth+50, InfoHeight+ 200,Game.windowWidth-400, 400));
			break;
		}
		createdEnemies = true;
	}
	
	private void enemyBulletFire(Enemy e, int rate) {
		if ( e.getBreakX()==e.getEnemyX() && e.getBreakInterval()%rate==0) {
			enemyBullets.add(new Bullet(e.getEnemyX()-2,e.getGunY(),-5,enemyBulletImage));
		}
	}
	private void gameOver(Graphics g) {
		
		play = false;

		//Kill the remaining Enemies
		if(!enemies.isEmpty()) {
			for(Enemy en:enemies) {
				en.setEnemyHealth(0);
			}
		}
		
		//Displaying Score
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 30));
		drawCenteredString("Game Over!",Game.windowWidth,Game.windowHeight-200,g);
		drawCenteredString("Score: "+score,Game.windowWidth,Game.windowHeight-100,g);
		drawCenteredString("Press Enter to Restart ",Game.windowWidth,Game.windowHeight,g);
		
		//Preparing for the next game
		restartReady = true;
	}

	private void moveDown() { shipY+=shipStep; }
	private void moveUp() 	{ shipY-=shipStep; }
	private void moveLeft() { shipX-=shipStep; }
	private void moveRight(){ shipX+=shipStep; }
	
	private void ShiptakeHit(int strength) { shipHealth-= strength; }	

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean firable() {
		int fireInt = random.nextInt(2);
		if (fireInt==1) {
			System.out.println(fireInt);
			return true;
		}
		System.out.println(fireInt);
		return false;
	}

	//Custom drawString Function to center the text
	public void drawCenteredString(String s, int w, int h, Graphics g) {
	    FontMetrics fm = g.getFontMetrics();
	    int x = (w - fm.stringWidth(s)) / 2;
	    int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
	    g.drawString(s, x, y);
	  }

}
