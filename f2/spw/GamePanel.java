package f2.spw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	private BufferedImage bi;	
	private BufferedImage bi2;	

	public static final int WIDTH = 400;
	public static final int HEIGHT = 600;

	Graphics2D big;
	Graphics2D big2;
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();

	Color backgroundColor = new Color(0, 0, 0, 50);
	Color textBoundColor = new Color(0, 0, 0);
	Color bonusColor = new Color (64, 64, 64, 180);

	ScrollingBackground scrollingBg = new ScrollingBackground();

	private Image heart;
	private Image needle;
	private Image bigNeedle;
	private Image pill;
	private Image gameover;

	private boolean gameOver = false;

	public GamePanel() {
		bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		big = (Graphics2D) bi.getGraphics();
		big.setBackground(backgroundColor);
		bi2 = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		big2 = (Graphics2D) bi2.getGraphics();
		big2.setBackground(backgroundColor);

		try {
			heart = ImageIO.read(new File("f2/spw/image/smallredheart.png"));

			needle = ImageIO.read(new File("f2/spw/image/thpinkneedle.gif"));
			bigNeedle = ImageIO.read(new File("f2/spw/image/bigneedle.gif"));

			pill = ImageIO.read(new File("f2/spw/image/pill.png"));
			gameover = ImageIO.read(new File("f2/spw/image/gameover.png"));
		}
		catch (IOException e) {
         	e.printStackTrace();
        }
	}

	public void updateGameUI(GameReporter reporter){
		
		big.clearRect(0, 0, 400, 600);		
		
		big.setColor(Color.WHITE);	
		if (isEnableBonusTime) {
			big.setBackground(bonusColor);
		}	
		else {
			big.setBackground(backgroundColor);
		}
		
		// heart image
		for(int i = 0; i < reporter.getHeartScore(); i++){
			big.drawImage(heart, 330 - (17 * i), 9, 17, 17, null);
		}
		
		big.drawString(String.format("%01d", reporter.getHeartScore()), 355, 20);


		// when game over
		gameOver = reporter.isGameOver();
		if(reporter.isGameOver()){
			big2.setColor(textBoundColor);
			big2.fillRect(125, 270, 150, 90);
			big2.setBackground(new Color (64, 64, 64, 180));
			big2.setColor(Color.WHITE);		
			big2.drawImage(gameover, 125, 130, 150, 150, null);
			big2.drawString("Let's go to Hospital", WIDTH / 2 - 50, HEIGHT / 2);
		
			big2.drawString("Let's go to Hospital", WIDTH / 2 - 50, HEIGHT / 2);
			big2.drawString("Score", WIDTH / 2 - 15, HEIGHT / 2 + 20);
			big2.drawString(String.format("%8d", reporter.getScore()), WIDTH / 2 - 30, HEIGHT / 2 + 40);
		}		
		else {
			big.setColor(new Color (64, 64, 64, 180));
			big.fillRect(160, 0, 75, 30);
			big.setColor(Color.WHITE);		
			big.drawString(String.format("%08d", reporter.getScore()), 170, 20);
		}
			
		
		for(Sprite s : sprites){
			s.draw(big);
		}
		
		repaint();
	}

	public void updateAbility(AbilityReporter abReporter){
		big.setColor(Color.WHITE);
		///////////// Needle
		for(int i = 0; i < abReporter.getNumOfNeedle(); i++){
			big.drawImage(needle, 10 + (20 * i), 5, null);
			big.drawString(String.format("%01d", abReporter.getTimePerOneNeedle()), 20, 50);
		}
		/////////////BIG NEEDLE
		if(abReporter.getNumOfNeedle() == 3){
				big.drawImage(bigNeedle, 10, 60, 80, 20, null);
				if(!abReporter.getEnableGenerateBulletForBigNeedle()){
					big.drawString("Press B", 40, 100);		
				}
			
				big.drawString(String.format("%01d", abReporter.getTimeForBigNeedle()), 10, 110);	
		}

		///////////// When enter Sicktime
		if (isEnableBonusTime) {
			big.setBackground(bonusColor);
			
			if(abReporter.getCountBonusTime() > 15){
				big.drawString("Sick Time", WIDTH / 2 - 33, HEIGHT / 2);
			}
			else if(abReporter.getCountBonusTime() < 15){
				big.drawString("Collect the PILLS", WIDTH / 2 - 50, HEIGHT / 2);
			}
			big.drawString(String.format("%01d",abReporter.getCountBonusTime()), WIDTH / 2 - 20, 320);
			big.drawImage(pill, WIDTH / 2 - 30, 330, null);
			big.drawString(String.format("%01d", abReporter.getPillCount()), WIDTH / 2, 338);
		}
	}

	private boolean isEnableBonusTime = false;

	public void setEnableBonusTime(boolean isEnableBonusTime){
		this.isEnableBonusTime = isEnableBonusTime;
	}

	public void showDamage(GameReporter reporter){		
		big.clearRect(reporter.getCurrentXOfSS() - 10, reporter.getCurrentYOfSS() - 10, 50, 10);
		big.setColor(Color.WHITE);		
		big.drawString(String.format("-%02d", reporter.getDamage()), reporter.getCurrentXOfSS() + 30, reporter.getCurrentYOfSS() - 5);
		
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;


		// Scrolling BG
		if (scrollingBg.bg == null){
            scrollingBg.bg = (BufferedImage)(createImage(getWidth(), getHeight()));
        }
            
        // Create a buffer to draw to
        Graphics buffer = scrollingBg.bg.createGraphics();
 
        // Put the two copies of the background image onto the buffer
        scrollingBg.bgOne.draw(buffer);
        scrollingBg.bgTwo.draw(buffer);
 
        // Draw the image onto the window
        g2d.drawImage(scrollingBg.bg, null, 0, 0);

        // Others
        if(!gameOver){
        	g2d.drawImage(bi, null, 0, 0);
        }
        else {
        	g2d.drawImage(bi2, null, 0, 0);
        }
	}

}
