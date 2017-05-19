import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;

abstract class Building {
	public static ArrayList<Building> all = new ArrayList<>();
	public int x;
	public Color c = Color.green;
	public Image img;
	
	public Building(int x){
		this.x = x;
		all.add(this);
	}
}
