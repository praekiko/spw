package f2.spw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;


public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;
		
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();	
	private ArrayList<ShootEnemy> shootEnemies = new ArrayList<ShootEnemy>();	// ShoootEnemy
	private ArrayList<EnemyBullet> enemyBullets = new ArrayList<EnemyBullet>(); // EnemyBullet
	private ArrayList<ToBiggerEnemy> toBiggerEnemies = new ArrayList<ToBiggerEnemy>(); // ToBiggerEnemy
	private ArrayList<ToSmallerEnemy> toSmallerEnemies = new ArrayList<ToSmallerEnemy>(); // ToSmallerEnemy


	private SpaceShip v;	
	
	private Timer timer;
	
	private long score = 0;
	private double difficulty = 0.05;

	private int heartScore = 5; 	// heart count
	private int elementOfEnemyCount = 0; // For count arraylist
	
	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;		
		
		gp.sprites.add(v);
		
		timer = new Timer(50, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				enemyProcess(); // original process
				shootEnemyProcess();
				itemProcess();
			}
		});
		timer.setRepeats(true);
		
	}
	
	public void start(){
		timer.start();
	}
	
	// For Enemy
	private void generateEnemy(){
		Enemy e = new Enemy((int)(Math.random()*390), 30);
		gp.sprites.add(e);
		enemies.add(e);
	}
	// To Generate ShootEnemy
	private void generateShootEnemy(){
		ShootEnemy se = new ShootEnemy((int)(Math.random()*390), 30);
		gp.sprites.add(se);
		shootEnemies.add(se);
	}
	// Generate Enemy Bullet
	private void generateEnemyBullet(int x, int y){
		EnemyBullet em = new EnemyBullet(x, y);
		gp.sprites.add(em);
		enemyBullets.add(em);
	}
	// Generate To Bigger Bar Enemy
	private void generateToBiggerEnemy(){
		ToBiggerEnemy tbe = new ToBiggerEnemy((int)(Math.random()*390), 30);
		gp.sprites.add(tbe);
		toBiggerEnemies.add(tbe);
	}
	// Generate To Smaller Bar Enemy
	private void generateToSmallerEnemy(){
		ToSmallerEnemy tse = new ToSmallerEnemy((int)(Math.random()*390), 30);
		gp.sprites.add(tse);
		toSmallerEnemies.add(tse);
	}
	
	// Original Process
	private void enemyProcess(){
		if(Math.random() < difficulty){
			generateEnemy();
		}
		
		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				score += 50;
			}
		}
		
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr)){
				elementOfEnemyCount++;
				if(elementOfEnemyCount >= 4){
					heartScore--;
					score -= 50;
					elementOfEnemyCount = 0;
				}				
				System.out.println(heartScore + " ");
				if(heartScore < 1){
					die();
					return;
				}
			}
		}
	}

	// For ShootEnemy
	private void shootEnemyProcess(){		
		if(Math.random() < difficulty / 2){
			generateShootEnemy();
		}
		
		Iterator<ShootEnemy> se_iter = shootEnemies.iterator();
		while(se_iter.hasNext()){
			ShootEnemy se = se_iter.next();
			se.proceed();
			generateEnemyBullet(se.x + 7, se.y); // generate Enemy Bullet with each x,y of ShootEnemy

			if(!se.isAlive()){
				se_iter.remove();
				gp.sprites.remove(se);
				score += 50;
			}

		}

		Iterator<EnemyBullet> b_iter = enemyBullets.iterator();
		while (b_iter.hasNext()) {
			EnemyBullet bl = b_iter.next();
			bl.proceed();
			if (!bl.isAlive()) {
				b_iter.remove();
				gp.sprites.remove(bl);
			}
		}
		
		gp.updateGameUI(this);
		
		// ShootEnemy Body when intersect
		Rectangle2D.Double vrOfse = v.getRectangle();
		Rectangle2D.Double se;
		for(ShootEnemy s : shootEnemies){
			se = s.getRectangle();
			if(se.intersects(vrOfse)){
				elementOfEnemyCount++;
				if(elementOfEnemyCount >= 4){
					heartScore--;
					score -= 50;
					elementOfEnemyCount = 0;
				}				
				if(heartScore < 1){
					die();
					return;
				}
			}
		}
		// EnemyBullet when intersect
		Rectangle2D.Double vrOfem = v.getRectangle();
		Rectangle2D.Double em;
		for(EnemyBullet e : enemyBullets){
			em = e.getRectangle();
			if(em.intersects(vrOfem)){
				elementOfEnemyCount++;
				if(elementOfEnemyCount >= 4){
					score -= 50;	
					// Show Damage
					gp.showDamage(this);
					elementOfEnemyCount = 0;
				}					
					
			}
		}
	}

	// For Item
	private void itemProcess(){		
		if(Math.random() < difficulty / 5){
			generateToBiggerEnemy();
		}
		if(Math.random() < difficulty / 5){
			generateToSmallerEnemy();
		}

		// To Bigger remove
		Iterator<ToBiggerEnemy> tbe_iter = toBiggerEnemies.iterator();
		while (tbe_iter.hasNext()) {
			ToBiggerEnemy tb = tbe_iter.next();
			tb.proceed();
			if (!tb.isAlive()) {
				tbe_iter.remove();
				gp.sprites.remove(tb);
			}
		}

		Iterator<ToSmallerEnemy> tse_iter = toSmallerEnemies.iterator();
		while (tse_iter.hasNext()) {
			ToSmallerEnemy ts = tse_iter.next();
			ts.proceed();
			if (!ts.isAlive()) {
				tse_iter.remove();
				gp.sprites.remove(ts);
			}
		}
		
		gp.updateGameUI(this);
		
		// To Bigger when intersect -> update size of spaceship
		Rectangle2D.Double vrOftbe = v.getRectangle();
		Rectangle2D.Double tbe;
		for(ToBiggerEnemy tb : toBiggerEnemies){
			tbe = tb.getRectangle();
			if(tbe.intersects(vrOftbe)){
				// update size of spaceship
				elementOfEnemyCount++;
				if(elementOfEnemyCount >= 4){
					v.increaseSize();
					elementOfEnemyCount = 0;
				}				
			}
		}

		// To Smaller when intersect
		Rectangle2D.Double vrOftse = v.getRectangle();
		Rectangle2D.Double tse;
		for(ToSmallerEnemy ts : toSmallerEnemies){
			tse = ts.getRectangle();
			if(tse.intersects(vrOftse)){
				// update size of spaceship
				v.resetSize();
			}
		}
	}

	public void die(){
		timer.stop();
	}
	
	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			v.moveX(-1);
			break;
		case KeyEvent.VK_D:
			v.moveX(1);
			break;
		case KeyEvent.VK_W:
			v.moveY(-1);
			break;
		case KeyEvent.VK_S:
			v.moveY(1);
			break;
		case KeyEvent.VK_UP:
			difficulty += 0.1;
			break;
		}
	}

	public long getScore(){
		return score;
	}

	public int getHeartScore(){
		return heartScore;
	}

	// get Current X,Y axis of spaceship
	public int getCurrentXOfSS(){
		return v.x;
	}

	public int getCurrentYOfSS(){
		return v.y;
	}

	public int getDamage(){
		return 10;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		controlVehicle(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//do nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing		
	}
}
