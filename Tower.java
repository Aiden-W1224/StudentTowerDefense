import java.util.ArrayList;

public abstract class Tower 
{
	private int x_coord;
	private int y_coord;
	private int radius;
	private int appearance;
	private int shooting_rate;
	private int shots_fired;
	private int damage;
	private ArrayList<int[]> bounds = new ArrayList<int[]>();
	
	public int get_x() 
	{
		return this.x_coord;
	}
	
	public int get_y() 
	{
		return this.y_coord;
	}
	
	public void set_damage(int damage) 
	{
		this.damage = damage;
	}
	
	public int get_damage() 
	{
		return this.damage;
	}
	
	public void set_shooting_rate(int rate) 
	{
		this.shooting_rate = rate;
	}
	
	public int get_shooting_rate() 
	{
		return this.shooting_rate;
	}
	
	public void set_shots_fired() 
	{
		this.shots_fired += 1;
	}
	
	public void reset_shots_fired() 
	{
		this.shots_fired = 0;
	}
	
	public int get_shots_fired() 
	{
		return this.shots_fired;
	}
	
	public void set_radius(int rad) 
	{
		this.radius = rad;
	}
	
	public int get_radius() 
	{
		return this.radius;
	}
	
	public ArrayList<int[]> get_bounds()
	{
		return bounds;
	}
	
	public Tower() 
	{

	}
	
	public Tower(int x, int y, int[][] map) 
	{
		this.x_coord = x;
		this.y_coord = y;
		this.shots_fired = 0;
	}
	
	public void set_appearance(int app) 
	{
		this.appearance = app;
	}
	
	public int get_appearance() 
	{
		return this.appearance;
	}
	
	public boolean detect(int[][] map) 
	{
		for (int[] coord_set : this.bounds) 
		{
			if (map[coord_set[1]][coord_set[0]] != 0 && map[coord_set[1]][coord_set[0]] != 1 && map[coord_set[1]][coord_set[0]] < 7) 
			{
				return true;
			}
		}
		return false;
	}
	
	public void set_bounds(int[][] map) 
	{
		for (int i = -this.radius; i < this.radius + 1; i++) 
		{
			for (int j = -this.radius; j < this.radius + 1; j++) 
			{
				if (((this.y_coord + i) >= 0) && ((this.x_coord + j) >= 0) && ((this.y_coord + i) < 
						map.length) && ((this.x_coord + j) < map[0].length)) 
				{
					int[] coord_set = new int[2];
					coord_set[0] = (this.x_coord + j);
					coord_set[1] = (this.y_coord + i);
					this.bounds.add(coord_set);
				}
			}
		}
	}
	
	public String toString() 
	{
		return ("(" + x_coord + ", " + y_coord + ")");
	}

}
