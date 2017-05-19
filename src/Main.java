
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public class Main extends ConstructorClass {
	BufferedImage sample;
	
	Random rand = new Random();
	public void init(int width, int height){
		this.setSize(640,640);
		new MissileTower(0 * 640/9 + 320/9);
		new City(1 * 640/9 + 320/9);
		new City(2 * 640/9 + 320/9);
		new City(3 * 640/9 + 320/9);
		new MissileTower(4 * 640/9 + 320/9);
		new City(5 * 640/9 + 320/9);
		new City(6 * 640/9 + 320/9);
		new City(7 * 640/9 + 320/9);
		new MissileTower(8 * 640/9 + 320/9);
	}
	
	public void draw(Graphics g, int width, int height) {
		this.setSize(640,640);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		
		Missile.updateAll(g);
		
		for(int i = 0; i<Building.all.size(); i++){
			g.setColor(Building.all.get(i).c);
			g.fillRect(Building.all.get(i).x, 580, 20, 20);
		}
	}
	
	public static Image loadImage(String path){
		Image img = null;
		try {
		    img = ImageIO.read(new File(path));
		    if(img == null){
		    }
		} 
		catch (IOException e){} 
		catch(NullPointerException e){}
		
		return img;
	}
	

	public void mousePressed(MouseEvent evt){
		MissileTower.allTowers.get(MissileTower.findLeastDistance(evt)).fireMissile(evt);
		super.mousePressed(evt);
	}
	
	public void keyPressed(KeyEvent evt) {}
}


