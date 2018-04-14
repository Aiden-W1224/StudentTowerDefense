/**
 * @author:  Athena Bolyos, Jacob Hazen, Daniel Orduz, Aiden Wong.
 * About program:  First_year is a subclass of Tower.
 */

public class First_year extends Tower
{
	public First_year(int x, int y, int[][] map) 
	{
		super(x, y, map);
		set_radius(1);
		set_appearance(7);
		set_shooting_rate(1);
		set_damage(1);
		set_bounds(map);
	}
}
