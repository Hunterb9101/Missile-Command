import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Missile {
	public static ArrayList<Missile> all = new ArrayList<>();
	double dx;
	double dy;
	double x;
	double y;
	int startX;
	int startY;
	int endX;
	int endY;
	
	boolean isExploding = false;
	int explosionAge = 0;
	public Missile(double dx, double dy, int x, int y, int endX, int endY){
		this.dx = dx;
		this.dy = dy;
		this.x = x;
		this.y = y;
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
				g.fillArc((int)all.get(i).x - (4 + all.get(i).explosionAge/2)/2, (int)all.get(i).y - (4 + all.get(i).explosionAge/2)/2, 4 + all.get(i).explosionAge/2, 4 + all.get(i).explosionAge/2, 0, 360);
				all.get(i).explosionAge++;
				
				if(all.get(i).explosionAge > 75){
					all.remove(i);
				}
			}
			else{
				g.setColor(Color.BLUE);
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
}
