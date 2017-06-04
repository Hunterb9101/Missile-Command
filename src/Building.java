import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

abstract class Building {
	public static ArrayList<Building> all = new ArrayList<>();
	public int x;
	public Color c = Color.green;
	public Image img;
	public boolean isDisabled = false;
	public static int destroyed = 0;
	public enum Type{NULL,CITY,TOWER};
	public Type type = Type.NULL;
	public Building(int x){
		this.x = x;
		all.add(this);
	}
	
	public static double dist(Building a, Missile b){
		return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(590 - b.y, 2));
	}
	
	public static void drawAll(Graphics g){
		for(int i = 0; i<Building.all.size(); i++){
			if(!Building.all.get(i).isDisabled){
				if(Main.colorChange){
					if(Building.all.get(i).getClass() == MissileTower.allTowers.get(0).getClass()){
						g.setColor(new Color(new Random().nextInt(128)+64,new Random().nextInt(128)+64,new Random().nextInt(128)+128));
					}
					else{
						g.setColor(new Color(new Random().nextInt(128)+128,new Random().nextInt(128)+64,new Random().nextInt(128)+64));
					}
				}
				else{
					g.setColor(Building.all.get(i).c);
				}
				g.fillRect(Building.all.get(i).x, 580, 20, 20);
			}
		}
		for(int i = 0; i<MissileTower.allTowers.size();i++){
			if(!MissileTower.allTowers.get(i).isDisabled){
				if(Main.colorChange){
					g.setColor(new Color(new Random().nextInt(128)+128,new Random().nextInt(128)+128,new Random().nextInt(128)+128));
				}
				else{
					g.setColor(Color.BLUE);
				}
				g.drawString(String.valueOf(MissileTower.allTowers.get(i).missiles),MissileTower.allTowers.get(i).x - 10, 630);
			}
			else{
				g.drawString("OUT",MissileTower.allTowers.get(i).x - 20, 630);
			}
		}
	}
}
