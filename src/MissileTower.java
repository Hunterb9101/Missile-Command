import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MissileTower extends Building {
	public static ArrayList<MissileTower> allTowers = new ArrayList<>();
	int missiles = 20;
	public MissileTower(int x){
		super(x);
		type = Type.TOWER;
		c = Color.BLUE;
		allTowers.add(this);
	}
	
	public static int findLeastDistance(MouseEvent e){
		int smallestDist = 1500;
		int idx = -1;
		for(int i = 0; i< allTowers.size(); i++){
			if(Math.sqrt(Math.pow(allTowers.get(i).x - e.getX(), 2) + Math.pow(580 - e.getY(), 2)) < smallestDist && !allTowers.get(i).isDisabled){
				smallestDist = (int) Math.sqrt(Math.pow(allTowers.get(i).x - e.getX(), 2) + Math.pow(580 - e.getY(), 2));
				idx = i;
			}
		}
		return idx;
	}
	
	public void fireMissile(MouseEvent e){
		double unitVector = Math.sqrt(Math.pow(this.x + 10 - e.getX(), 2) + Math.pow(580 - e.getY(), 2));
		double dx = -(this.x + 10 - e.getX())/unitVector * 3;
		double dy = -(580 - e.getY())/unitVector * 3;
		new Missile(dx,dy,this.x + 10, 580,e.getX(),e.getY(),0);
	}
}
