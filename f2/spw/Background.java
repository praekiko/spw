package f2.spw;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
 
public class Background {
    private BufferedImage image;
 
    private int x;
    private int y;
 
    public Background() {
        this(0,0);
    }
 
    public Background(int x, int y) {
        this.x = x;
        this.y = y;
 
        // Try to open the image file background.png
        try {
            image = ImageIO.read(new File("f2/spw/image/bg1.jpg"));
        }
        catch (Exception e) { 
            System.out.println(e); 
        }
 
    }
 
    public void draw(Graphics window) {
 
        // Draw the image onto the Graphics reference
        window.drawImage(image, getX(), getY(), image.getWidth(), image.getHeight(), null);
 
        // Move the y position left for next time
        this.y += 1;
 
        // Check to see if the image has gone off stage left
        if (this.y >= +1 * image.getHeight()) {
 
            // If it has, line it back up so that its left edge is
            // lined up to the right side of the other background image
            this.y = this.y - image.getHeight() * 2;
        }
 
    }
 
    public void setY(int y) {
        this.y = y;
    }
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public int getImageHeight() {
        return image.getHeight();
    }
 
}