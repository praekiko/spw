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
	private ArrayList<BulletEnemy> enemyBullets = new ArrayList<BulletEnemy>(); // BulletEnemy
	private ArrayList<BulletSpaceShip> spaceshipBullets = new ArrayList<BulletSpaceShip>(); // BulletSpaceShip
	private ArrayList<Item> items = new ArrayList<Item>(); // Items

	protected SpaceShip v;	
	
	private Timer timer;
	private Timer timerBonusTime;
	
	protected long score = 0;
	private double difficulty = 0.05;
	protected long damagePerOneBullet = 10;

	protected int heartScore = 5; 	// heart count
	protected int pillCount = 0; // count number of pill in bonus time

	protected boolean generateBulletForNeedle = false;
	protected int numOfNeedle = 0;

	private boolean healthy = true;
	
	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;		
		
		gp.sprites.add(v);
		
		timer = new Timer(50, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				enemyProcess(); // original process
				itemProcess();
				bulletProcess();
				bonusProcess();
			}
		});
		timer.setRepeats(true);

		timerBonusTime = new Timer(10000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {				
				healthy = false;  // change map
				gp.showMessage("Sick Time");
				System.out.println("Bonus Time");
			}
		});
		// timerBonusTime.setRepeats(true);
	}
	
	public void start(){
		timer.start();
		timerBonusTime.start();
	}
	
	// For Enemy
	private void generateEnemy(){	
		int numOfItems = 2;
		int randomCase = (int)(Math.random() * (numOfItems + 1));
		if(healthy){
			switch (randomCase) {
			case 1:
			{
				Enemy e = new Enemy((int)(Math.random()*390), 30);
				gp.sprites.add(e);
				enemies.add(e);
			}
			break;
			case 2:
			{
				EnemyShootBody es = new EnemyShootBody((int)(Math.random()*390), 30);
				gp.sprites.add(es);
				enemies.add(es);
			}
			break;
			}
		}
		else {
			switch (randomCase) {
			case 1:
			{				
				EnemySickPill esp = new EnemySickPill((int)(Math.random()*390), 30);
				gp.sprites.add(esp);
				enemies.add(esp);
			}
			break;
			case 2:
			{
				EnemySickPillShoot esps = new EnemySickPillShoot((int)(Math.random()*390), 30);
				gp.sprites.add(esps);
				enemies.add(esps);
			}
			break;
			}
		}
		
	}

	// Generate Items all in one
	private void generateItem(){
		int numOfItems = 3;
		int randomCase = (int)(Math.random() * (numOfItems + 1));
		switch (randomCase) {
			case 1:
			{
				ItemToBigger itb = new ItemToBigger((int)(Math.random()*390), 30);
				gp.sprites.add(itb);
				items.add(itb);
			}
			break;
			case 2:
			{
				ItemToSmaller its = new ItemToSmaller((int)(Math.random()*390), 30);
				gp.sprites.add(its);
				items.add(its);
			}
			break;
			case 3:
			{
				ItemNeedle in = new ItemNeedle((int)(Math.random()*390), 30);
				gp.sprites.add(in);
				items.add(in);
			}
			break;
		}		
	}

	private void bonusProcess(){


		if(pillCount == 5){
			timerBonusTime.stop();
			healthy = true;
		}

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
			// look up for Enemy that has implements HasBullet?
			if(e instanceof HasBullet){
				HasBullet hb = (HasBullet) e;
				hb.generateBullet(this, e.x, e.y);
			}
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				if(healthy){
					score += 50;
				}
			}
		}
		
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr)){	
				e.doWhenCrash(this);			
				e.setToDie();
				System.out.println("Heart = " + heartScore);
				if(heartScore == 0){
					die();
					return;
				}
			}
		}
	}

	// For Items
	private void itemProcess(){		
		if((Math.random() < difficulty) && healthy){
			generateItem();
		}

		// Items remove
		Iterator<Item> it_iter = items.iterator();
		while (it_iter.hasNext()) {
			Item i = it_iter.next();
			i.proceed();
			if (!i.isAlive()) {
				it_iter.remove();
				gp.sprites.remove(i);
			}
		}

		// Items when intersect
		Rectangle2D.Double vrOfit = v.getRectangle();
		Rectangle2D.Double it;
		for(Item i : items){
			it = i.getRectangle();
			if(it.intersects(vrOfit)){
				i.doWhenCollect(this);
				i.setToDie();				
			}
		}


	}


	private void bulletProcess(){
		// Shooting Enemy Bullet
		Iterator<BulletEnemy> b_iter = enemyBullets.iterator();
		while (b_iter.hasNext()) {
			BulletEnemy bl = b_iter.next();
			bl.proceed();
			if (!bl.isAlive()) {
				b_iter.remove();
				gp.sprites.remove(bl);
			}
		}

		// SSBullet remove
		Iterator<BulletSpaceShip> ssb_iter = spaceshipBullets.iterator();
		while (ssb_iter.hasNext()) {
			BulletSpaceShip sb = ssb_iter.next();
			sb.proceed();
			if (!sb.isAlive()) {
				ssb_iter.remove();
				gp.sprites.remove(sb);
			}
		}
		
		gp.updateGameUI(this);

		// BulletEnemy when intersect
		Rectangle2D.Double vrOfem = v.getRectangle();
		Rectangle2D.Double em;
		for(BulletEnemy e : enemyBullets){
			em = e.getRectangle();
			if(em.intersects(vrOfem)){
				score -= damagePerOneBullet;
				e.setToDie();	
				// Show Damage
				gp.showDamage(this);					
			}
		}

		// SSBullet intersect
		Rectangle2D.Double vrOfe;
		Rectangle2D.Double vrOfse;
		Rectangle2D.Double ssb;
		for(BulletSpaceShip sb : spaceshipBullets){
			ssb = sb.getRectangle();

			for(Enemy e : enemies){
				vrOfe = e.getRectangle();
				if(vrOfe.intersects(ssb)){
					// remove that Enemy
					e.setToDie();
					score += 50;
				}
			}			
		}
	}

	// Generate Enemy Bullet
	protected void generateBulletEnemy(int x, int y){
		BulletEnemy em = new BulletEnemy(x, y);
		gp.sprites.add(em);
		enemyBullets.add(em);
	}

	// Generate Bullet by intersect Needle Enemy
	private void generateBulletSpaceShip(){
		if(generateBulletForNeedle){
			switch (getNumOfNeedle()) {
			case 3:
				{
					BulletSpaceShip ssb = new BulletSpaceShip(getCurrentXOfSS(), getCurrentYOfSS());
					gp.sprites.add(ssb);
					spaceshipBullets.add(ssb);
				}
			case 2:
				{
					BulletSpaceShip ssb = new BulletSpaceShip(getCurrentXOfSS() + v.width, getCurrentYOfSS());
					gp.sprites.add(ssb);
					spaceshipBullets.add(ssb);
				}
			case 1:
				{
					BulletSpaceShip ssb = new BulletSpaceShip(getCurrentXOfSS() + v.width / 2, getCurrentYOfSS());
					gp.sprites.add(ssb);
					spaceshipBullets.add(ssb);
				}
				break;
			}
		}
	}

	//  Timer for Bullet of Needle  //
	private Timer timerBulletforNeedle = new Timer(10000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("TimeCount = " + timerBulletforNeedle.getDelay());				
				timerBulletforNeedle.stop();
				System.out.println("Stop");
				generateBulletForNeedle = false;
				numOfNeedle = 0;
			}
	});

	public void startCountTime(){	
		timerBulletforNeedle.start();
		System.out.println("Start");
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
			generateBulletSpaceShip();
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

	public long getDamage(){
		return damagePerOneBullet;
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
