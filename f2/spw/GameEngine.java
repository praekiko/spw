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
	protected ArrayList<BulletEnemy> enemyBullets = new ArrayList<BulletEnemy>(); // BulletEnemy
	protected ArrayList<BulletSpaceShip> spaceshipBullets = new ArrayList<BulletSpaceShip>(); // BulletSpaceShip
	private ArrayList<Item> items = new ArrayList<Item>(); // Items

	protected SpaceShip v;

	
	private Timer timer;
	protected Timer timerStartBonusTime;
	// protected Timer timerOneSecond; // ItemNeedle use it
	
	protected long score = 0;
	private double difficulty = 0.05;
	protected long damagePerOneBullet = 10;

	protected int heartScore = 5; 	// heart count
	protected boolean healthy = true; // check situation

	protected boolean enableStartButton = false;

	Ability ability;
	
	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;		

		ability = new Ability(this, gp, v);	
		
		gp.sprites.add(v);
		
		timer = new Timer(50, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				enemyProcess(); // original process
				itemProcess();
				bulletProcess();
				ability.bonusProcess();
				ability.generateBulletBigNeedle();  //wait for pressing B
			}
		});
		timer.setRepeats(true);

		timerStartBonusTime = new Timer(10000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {		
			    if(healthy == true)	{
			    	ability.enableBonusTime = true;	  
			    	ability.timerOneSecond.start(); 
			    	healthy = false;  // change map
					gp.setEnableBonusTime(true);
					System.out.println("Bonus Time");					
			    }					
			}
		});

	}
	
	public void start(){
		timer.start();
		timerStartBonusTime.start();
	}
	
	// For Enemy
	private void generateEnemy(){	
		int numOfItems = 2;
		int randomCase = (int)(Math.random() * (numOfItems + 1));

		if(healthy){
			switch (randomCase) {
				case 1:
					EnemyBody e = new EnemyBody((int)(Math.random()*390), 30);
					gp.sprites.add(e);
					enemies.add(e);
				break;
				case 2:
					EnemyShootBody es = new EnemyShootBody((int)(Math.random()*390), 30);
					gp.sprites.add(es);
					enemies.add(es);
				break;
			}
		}
		else {
			switch (randomCase) {
				case 1:					
					EnemySickPillShoot esps = new EnemySickPillShoot((int)(Math.random()*390), 30);
					gp.sprites.add(esps);
					enemies.add(esps);
				break;
				case 2:
					ItemSickPill isp = new ItemSickPill((int)(Math.random()*390), 30);
					gp.sprites.add(isp);
					items.add(isp);
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
				ItemToBigger itb = new ItemToBigger((int)(Math.random()*390), 30);
				gp.sprites.add(itb);
				items.add(itb);
			break;
			case 2:
				ItemToSmaller its = new ItemToSmaller((int)(Math.random()*390), 30);
				gp.sprites.add(its);
				items.add(its);
			break;
			case 3:
				ItemNeedle in = new ItemNeedle((int)(Math.random()*390), 30);
				gp.sprites.add(in);
				items.add(in);
			break;
		}
		
	}

	
	///////////////// Enemy /////////////////////
	private void enemyProcess(){
		if(Math.random() < difficulty){
			generateEnemy();
		}
		
		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();

			if(!ability.enableGenerateBulletForBigNeedle){
				e.proceed();				
			}
			else {
				e.speedUp();  // when press B
			}

			// look up for Enemy that has implements HasBullet?
			if(e instanceof HasBullet){
				HasBullet hb = (HasBullet) e;
				hb.generateBullet(this);
			}
			// Moving Enemy
			e.movableEnemyX();
			
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
			
			if(!ability.enableGenerateBulletForBigNeedle){
				i.proceed();
			}
			else {
				i.speedUp();  // when press B
			}


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
			
			if(!ability.enableGenerateBulletForBigNeedle){
				bl.proceed();
			}
			else {
				bl.speedUp();  // when press B
			}

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

	private boolean gameOver = false;

	public boolean isGameOver(){
		return gameOver;
	} 

	public void die(){
		gameOver = true;
		gp.updateGameUI(this);
		timer.stop();
		timerStartBonusTime.stop();
		ability.timerOneSecond.stop();
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
			ability.generateBulletSpaceShip();
			break;
		case KeyEvent.VK_B:
			ability.bigNeedleProcess();
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
