package f2.spw;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
 
public class ScrollingBackground extends Canvas implements Runnable {
 
    // Two copies of the background image to scroll
    private Background bgOne;
    private Background bgTwo;
 
    private BufferedImage bg;
 
    public ScrollingBackground() {
        bgOne = new Background();
        bgTwo = new Background(0, bgOne.getImageHeight());
 
        new Thread(this).start();
        setVisible(true);
    }
 
    @Override
    public void run() {
        try {
            while (true) {
                Thread.currentThread().sleep(5);
                repaint();
            }
        }
        catch (Exception e) {}
    }
 
    @Override
    public void update(Graphics gp) {
        paint(gp);
    }
 
    public void paint(Graphics gp) {
        Graphics2D g = (Graphics2D)gp;
 
        if (bg == null){
            bg = (BufferedImage)(createImage(getWidth(), getHeight()));
        }
            
        // Create a buffer to draw to
        Graphics buffer = bg.createGraphics();
 
        // Put the two copies of the background image onto the buffer
        bgOne.draw(buffer);
        bgTwo.draw(buffer);
 
        // Draw the image onto the window
        g.drawImage(bg, null, 0, 0);
 
    }
 
}