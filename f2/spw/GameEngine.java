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
	private ArrayList<NeedleEnemy> needleEnemies = new ArrayList<NeedleEnemy>(); // NeedleEnemy
	private ArrayList<SpaceShipBullet> spaceshipBullets = new ArrayList<SpaceShipBullet>(); // SpaceShipBullet

	private boolean generateBulletForNeedle = false;

	private SpaceShip v;	
	
	private Timer timer;
	
	private long score = 0;
	private double difficulty = 0.05;

	private int heartScore = 5; 	// heart count
	private int numOfNeedle = 0;
	
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
	// Generate To Needle Enemy
	private void generateNeedleEnemy(){
		NeedleEnemy ne = new NeedleEnemy((int)(Math.random()*390), 30);
		gp.sprites.add(ne);
		needleEnemies.add(ne);
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
				heartScore--;			
				e.setToDie();
				System.out.println("Heart = " + heartScore);
				if(heartScore == 0){
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
				heartScore--;	
				score -= 50;
				System.out.println("Heart = " + heartScore);		
				s.setToDie();
				if(heartScore == 0){
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
				score -= 10;
				e.setToDie();	
				// Show Damage
				//gp.showDamage(this);					
			}
		}
	}

	// For Item
	private void itemProcess(){		
		if(Math.random() < difficulty / 7){
			generateToBiggerEnemy();
		}
		if(Math.random() < difficulty / 7){
			generateToSmallerEnemy();
		}
		if(Math.random() < difficulty / 5){
			generateNeedleEnemy();
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
		// To Smaller remove
		Iterator<ToSmallerEnemy> tse_iter = toSmallerEnemies.iterator();
		while (tse_iter.hasNext()) {
			ToSmallerEnemy ts = tse_iter.next();
			ts.proceed();
			if (!ts.isAlive()) {
				tse_iter.remove();
				gp.sprites.remove(ts);
			}
		}
		// Needle remove
		Iterator<NeedleEnemy> ne_iter = needleEnemies.iterator();
		while (ne_iter.hasNext()) {
			NeedleEnemy n = ne_iter.next();
			n.proceed();
			if (!n.isAlive()) {
				ne_iter.remove();
				gp.sprites.remove(n);
			}
		}
		// SSBullet remove
		Iterator<SpaceShipBullet> ssb_iter = spaceshipBullets.iterator();
		while (ssb_iter.hasNext()) {
			SpaceShipBullet sb = ssb_iter.next();
			sb.proceed();
			if (!sb.isAlive()) {
				ssb_iter.remove();
				gp.sprites.remove(sb);
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
				v.increaseSize();
				tb.setToDie();				
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
				ts.setToDie();
			}
		}

		
		// Needle when intersect
		Rectangle2D.Double vrOfne = v.getRectangle();
		Rectangle2D.Double ne;
		for(NeedleEnemy n : needleEnemies){
			ne = n.getRectangle();
			if(ne.intersects(vrOfne)){
				// SS can shoot Bullet
				generateBulletForNeedle = true;
				if(numOfNeedle < 3){ 	// Max at 3
					numOfNeedle++;
				}
				else {
					numOfNeedle = 0;
				}
				System.out.println("Needle = " + numOfNeedle);
				n.setToDie();
			}
		}

		// SSBullet remove
		Rectangle2D.Double vrOfe;
		Rectangle2D.Double vrOfse;
		Rectangle2D.Double ssb;
		for(SpaceShipBullet sb : spaceshipBullets){
			ssb = sb.getRectangle();

			for(Enemy e : enemies){
				vrOfe = e.getRectangle();
				if(vrOfe.intersects(ssb)){
					// remove that Enemy
					e.setToDie();
					score += 50;
				}
			}
			
			for(ShootEnemy se : shootEnemies){
				vrOfse = se.getRectangle();
				if(vrOfse.intersects(ssb)){
					// remove that ShootEnemy
					se.setToDie();
					score += 50;
				}
			}
			
		}

	}

	// Generate Bullet by intersect Needle Enemy
	private void generateSpaceShipBullet(){
		if(generateBulletForNeedle){
			SpaceShipBullet ssb = new SpaceShipBullet(getCurrentXOfSS(), getCurrentYOfSS());
			gp.sprites.add(ssb);
			spaceshipBullets.add(ssb);
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
		case KeyEvent.VK_SPACE:
			generateSpaceShipBullet();
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

	public int getNumOfNeedle(){
		return numOfNeedle;
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
