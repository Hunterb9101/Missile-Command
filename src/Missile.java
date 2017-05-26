import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Missile {
	public static ArrayList<Missile> all = new ArrayList<>();
	static Random rand = new Random();
	double dx;
	double dy;
	double x;
	double y;
	int startX;
	int startY;
	int endX;
	int endY;
	int team;
	static double enemySpeedMultiplier = .25;
	
	boolean isExploding = false;
	int explosionAge = 0;

	public Missile(double dx, double dy, int x, int y, int endX, int endY, int team){
		this.dx = dx;
		this.dy = dy;
		this.x = x;
		this.y = y;
		this.team = team;
		startX = x;
		startY = y;
		this.endX = endX;
		this.endY = endY;
		
		all.add(this);
	}
	
	public static void updateAll(Graphics g){
		for(int i = 0; i< all.size(); i++){
			if(all.get(i).isExploding){
				g.setColor(Color.WHITE);
				g.fillArc((int)all.get(i).x - (int)getExplosionSize(all.get(i).explosionAge)/2,(int)all.get(i).y - (int)getExplosionSize(all.get(i).explosionAge)/2,(int)getExplosionSize(all.get(i).explosionAge),(int)getExplosionSize(all.get(i).explosionAge), 0, 360);
				all.get(i).explosionAge++;
				
				if(all.get(i).explosionAge > 75){
					if(all.get(i).team == 1 && all.get(i).y < 550){
						Main.score += 50;
					}
					all.remove(i);
				}
			}
			else{
				if(all.get(i).team == 0){
					g.setColor(Color.BLUE);
				}
				else{
					g.setColor(Color.RED);
				}
				
				for(int j = 0; j< all.size(); j++){
					if(all.get(j).isExploding && (all.get(i).team != all.get(j).team || all.get(j).team == 1) && dist(all.get(i),all.get(j)) < getExplosionSize(all.get(j).explosionAge)/2){
						all.get(i).isExploding = true;
					}
				}
				g.drawLine((int)all.get(i).x, (int)all.get(i).y, all.get(i).startX, all.get(i).startY);
				g.drawRect((int)all.get(i).x - 2, (int)all.get(i).y - 2, 4, 4);
				all.get(i).x += all.get(i).dx;
				all.get(i).y += all.get(i).dy;
				
				if(Math.sqrt(Math.pow(all.get(i).x - all.get(i).endX,2) + Math.pow(all.get(i).y - all.get(i).endY, 2)) < 3){
					all.get(i).isExploding = true;
				}
			}
		}
	}
	
	public static double dist(Missile a, Missile b){
		return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
	}
	
	public static void spawnEnemyMissile(){
		
		int startX = rand.nextInt(640);
		int startY = -5;
		Building target = null;
		
		while(Building.all.size() != Building.destroyed){
			target = Building.all.get(rand.nextInt(9));
			if(!target.isDisabled){
				break;
			}
			
			if(Building.all.size() == Building.destroyed){
				break;
			}
			
		}
		int targetX = target.x + 10;
		double unitVector = Math.sqrt(Math.pow(targetX - startX, 2) + Math.pow(590 - startY, 2));
		double dx = (targetX - startX)/unitVector * enemySpeedMultiplier;
		double dy = (590 - startY)/unitVector * enemySpeedMultiplier;
		
		new Missile(dx,dy,startX, startY, targetX, 590, 1);
	}
	
	public static double getExplosionSize(int explosionAge){
		//return 1.75*Math.pow(explosionAge, .75);
		return 4.364 *Math.pow(explosionAge, .625); // Radius / 22.911 = Coefficient
		//return 6.708*Math.sqrt(explosionAge);
		//return 34.465*Math.log10(explosionAge);
	}
}
