
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
