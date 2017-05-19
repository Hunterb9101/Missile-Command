import java.awt.Color;
import java.util.ArrayList;

public class City extends Building{
public static ArrayList<City> allCities = new ArrayList<>();
	public City(int x){
		super(x);
		c = Color.RED;
		allCities.add(this);
	}
}
