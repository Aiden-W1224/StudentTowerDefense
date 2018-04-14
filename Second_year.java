/**
 * @author:  Athena Bolyos, Jacob Hazen, Daniel Orduz, and Aiden Wong.
 * About program:  Second_year is a subclass of Tower.
 */

public class Second_year extends Tower
{
	public Second_year(int x, int y, int[][] map) 
	{
		super(x, y, map);
		set_radius(2);
		set_appearance(8);
		set_shooting_rate(1);
		set_damage(2);
		set_bounds(map);
	}
}
