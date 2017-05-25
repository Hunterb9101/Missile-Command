
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public class Main extends ConstructorClass {
	int wave = 1;
	int bonusMissiles;
	int bonusBuildings;
	int missiles = getMissileCount(1);
	long gracePeriodStart = System.currentTimeMillis();
	boolean gracePeriod = true;
	boolean destroyed = false;
	BufferedImage sample;
	Font f = new Font("serif", Font.PLAIN, 32);
	Random rand = new Random();
	public static int score = 0;
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
		Missile.enemySpeedMultiplier = getSpeedMultiplier(1);

	}
	
	public void draw(Graphics g, int width, int height) {
		this.setSize(640,640);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.setFont(font);
		
		if(!gracePeriod && !destroyed){
			if(rand.nextInt(100) == 13 && Building.all.size() != Building.destroyed && missiles != 0){
				Missile.spawnEnemyMissile();
				missiles --;
			}
			else if(Building.all.size() == Building.destroyed){
				destroyed = true;
			}

			Missile.updateAll(g);
			
			for(int i = 0; i<Missile.all.size(); i++){
				for(int j = 0; j<Building.all.size(); j++){
					if(Missile.all.get(i).isExploding && Missile.all.get(i).team == 1 && Building.dist(Building.all.get(j),Missile.all.get(i)) < 15){
						if(!Building.all.get(j).isDisabled){
							Building.destroyed++;
						}
						Building.all.get(j).isDisabled = true;
					}
				}
			}
			
			if(missiles == 0 && Building.all.size() != Building.destroyed && Missile.all.size() == 0){
				wave++;
				Missile.enemySpeedMultiplier = getSpeedMultiplier(wave);
				missiles = getMissileCount(wave);
				gracePeriod = true;
				gracePeriodStart = System.currentTimeMillis();
				bonusBuildings = 0;
				bonusMissiles = 0;
				for(int i = 0; i<Building.all.size();i++){
					if(Building.all.get(i).isDisabled == false){
						score += 100;
						bonusBuildings += 100;
					}
					Building.all.get(i).isDisabled = false;
				}
				
				for(int i = 0; i<MissileTower.allTowers.size(); i++){
					score += MissileTower.allTowers.get(i).missiles*10;
					bonusMissiles += MissileTower.allTowers.get(i).missiles*10;
					MissileTower.allTowers.get(i).missiles = 20;
				}
			}
		}
		else if(gracePeriod && !destroyed){
			g.setColor(Color.BLUE);
			g.drawString("Wave: " + wave, 300, 300);
			g.drawString("Bonus - Buildings: " + bonusBuildings, 300, 340);
			g.drawString("Bonus - Missiles: " + bonusMissiles, 300, 370);
			
			if(gracePeriodStart + 4000 < System.currentTimeMillis()){
				gracePeriod = false;
			}
		}
		else{
			g.setColor(Color.RED);
			g.drawString("You lose!", 300, 300);
		}
		
		Building.drawAll(g);
		
		g.setColor(Color.BLUE);
		g.drawString("Score: " + score, 15, 35);
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
		MissileTower.allTowers.get(MissileTower.findLeastDistance(evt)).missiles--;
		if(MissileTower.allTowers.get(MissileTower.findLeastDistance(evt)).missiles <= -1){
			MissileTower.allTowers.get(MissileTower.findLeastDistance(evt)).isDisabled = true;
		}
		super.mousePressed(evt);
	}
	
	public void keyPressed(KeyEvent evt) {}
	
	public int getMissileCount(int wave){
		return 10 +2*(int)(Math.sqrt(5*wave));
	}
	
	public double getSpeedMultiplier(int wave){
		return .5*Math.pow(10, -5)*Math.pow(wave, 2) + .25;
	}
}


