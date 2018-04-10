import java.util.ArrayList;

public abstract class Enemy 
{
	private int appearance;
	private final int START_X = 9;
	private final int START_Y = 11;
	private final int STANDBY_X = -1;
	private final int STANDBY_Y = -1;
	private final int TILE_SIZE = 64;
	private int health;
	private Enemy_animation doll;
	
	private int true_x;
	private int true_y;
	
	private int previous_x;
	private int previous_y;
	
	public int get_appearance() { return this.appearance; }
	public int get_x() { return this.true_x; }
	public int get_y() { return this.true_y; }
	public int get_previous_x() { return this.previous_x; }
	public int get_previous_y() { return this.previous_y; }
	public void set_health(int health) { this.health = health; }
	public int get_health() { return this.health; }
	public Enemy_animation get_doll() { return this.doll; }
	
	public Enemy() 
	{
		this.true_x = STANDBY_X;
		this.true_y = STANDBY_Y;
		this.previous_x = 0;
		this.previous_y = 0;
		this.doll = new Enemy_animation();
	}
	
	public void set_appearance(int app) 
	{
		this.appearance = app;
	}
	
	public void set_on_map() 
	{
		this.true_x = START_X;
		this.true_y = START_Y;
	}
	
	public void eliminate() 
	{
		this.true_x = STANDBY_X;
		this.true_y = STANDBY_Y;
		this.health = 0;
	}
	/**
	 * movement for enemy
	 * @param map
	 */
	
	public void move(int[][] map) 
	{
		if ((map[true_y - 1][true_x] == 1) && (true_y - 1 != previous_y)) 
		{
			this.previous_x = true_x;
			this.previous_y = true_y;
			this.true_y -=1;
		}
		else if ((map[true_y + 1][true_x] == 1) && (true_y + 1 != previous_y)) 
		{
			this.previous_x = true_x;
			this.previous_y = true_y;
			this.true_y += 1;
		}
		else if ((map[true_y][true_x - 1] == 1) && (true_x - 1 != previous_x)) 
		{
			this.previous_y = true_y;
			this.previous_x = true_x;
			this.true_x -= 1;
		}
		else if ((map[true_y][true_x + 1] == 1) && (true_x + 1 != previous_x)) 
		{
			this.previous_y = true_y;
			this.previous_x = true_x;
			this.true_x += 1;
		}
	}
	
	public void hit(int damage) 
	{
		this.health -= damage;
	}
	
	
	public String toString() 
	{
		return ("(" + true_x + ", " + true_y + ", " + health + ")");
	}
}
