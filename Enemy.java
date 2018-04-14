import java.util.ArrayList;
import java.util.Random;
/**
 * 
 * @author PICKLE RICK
 * About Program : This class contains the algorithm to move the enemy throughout the game.
 * 				   It checks if the space is free and makes it way through the game not being able to
 *				   backtrack in the array. The array is set a starting point which moves through the array
 * 				   once it has reached the end of the path the program will then calculate subtracting from GPA.
 * 				   In the text based version it prints out where the enemy is in the array, and also the health of 
 *				   Enemy.
 *
 */
public abstract class Enemy 
{
	private Render render;
	private int appearance;
	private int START_X = 9;
	private int START_Y = 11;
	private int STANDBY_X = -1;
	private int STANDBY_Y = -1;
	private int TILE_SIZE = 64;
	private int health;
	private Enemy_animation doll;
	
	private int true_x;
	private int true_y;
	
	private int previous_x;
	private int previous_y;
	
	private boolean thirteen_tile = false;
	private int prev_tile = 1;
	private int rand_dir;
	
	/**
	 * About get_prev_tile():  a getter for the integer prev_tile, an instance variable that stores the value of
	 * 		the tile that the enemy steps on in the text-based version of the game. This value is specifically
	 * 		used in Engine() when moving the enemy through the array map, and replacing the tile behind the
	 * 		enemy when they move.
	 * @return:  returns the value of prev_tile.
	 */
	public int get_prev_tile() 
	{
		return this.prev_tile;
	}
	
	/**
	 * About get_rand_dir():  a getter for the integer rand_dir, a random value of either 1 or 2 that is used 
	 * 		specifically in the default game map. The value of rand_dir is used to determine the direction that 
	 * 		the enemy takes at the first cross-roads it encounters in the GUI version of the game. A value of 1 
	 * 		causes the enemy to take the shorter path upwards. A value of 2 causes the enemy to continue moving
	 * 		left into the longer path. 
	 * @return:  returns the value of rand_dir.
	 */
	public int get_rand_dir() 
	{
		return this.rand_dir;
	}
	
	/**
	 * Setters and getters.
	 */
	public int get_appearance() { return this.appearance; }
	public int get_x() { return this.true_x; }
	public int get_y() { return this.true_y; }
	public int get_previous_x() { return this.previous_x; }
	public int get_previous_y() { return this.previous_y; }
	public void set_health(int health) { this.health = health; }
	public int get_health() { return this.health; }
	public Enemy_animation get_doll() { return this.doll; }
	
	/**
	 * About Enemy(Render render):  a constructor for the class Enemy. 
	 * @param render:  used to allow an instance of Enemy to access information about the map, such as initial 
	 * 		starting position.
	 */
	public Enemy(Render render) 
	{
		this.true_x = STANDBY_X;
		this.true_y = STANDBY_Y;
		this.previous_x = 0;
		this.previous_y = 0;
		this.render = render;
		this.doll = new Enemy_animation(render);
	}
	
	/**
	 * About set_appearance(int app):  determines the integer that an enemy appears as in the text-based version of
	 * 		the game.
	 * @param app:  the integer representation of a given enemy.
	 */
	public void set_appearance(int app) 
	{
		this.appearance = app;
	}
	
	/**
	 * About set_on_map():  places the instance of Enemy onto the text-based version of the map. START_X and START_Y
	 * 		have default values based on the default map in the game. If the player chooses a random map, then
	 * 		START_X and START_Y are changed accordingly.
	 */
	public void set_on_map() 
	{
		if (this.render.rand() == true) 
		{
			this.START_X = render.get_start_x();
			this.START_Y = render.get_start_y();
		}
		this.true_x = START_X;
		this.true_y = START_Y;
	}
	
	/**
	 * About eliminate():  removes the enemy from the text-based version of the game. eliminate() is used in both
	 * 		instances where the enemy travels to the end of the map, and in instances where the enemy is killed
	 * 		by a tower.
	 */
	public void eliminate() 
	{
		this.true_x = STANDBY_X;
		this.true_y = STANDBY_Y;
		this.health = 0;
	}
	
	/**
	 * About randInt():  randInt() randomly generates either a 1 or a 2 that allows the enemy to take one of 
	 * 		two paths accordingly at the appropriate cross-roads. randInt() returns the integer directly 
	 * 		to the location from which it's called (in this case, from within move(int[][])) and also saves
	 * 		the value permanently in the instance variable rand_dir.
	 * @return:  this returns either a 1 or a 2, which allows for our enemy to make a choice between paths
	 */
	public int randInt() {
		Random rand = new Random();
		int n = rand.nextInt(2) + 1;
		this.rand_dir = n;
		return n;
	}
	
	/**
	 * About move(int[][] map):  the movement algorithm used for the text-based version of the game. The algorithm
	 * 		goes through several cases, including cross-roads cases and general cases.
	 * @param map takes current integer map as a parameter. 
	 */
	public void move(int[][] map) 
	{
		
		/**
		 * Case:  the enemy is on a vertical path at which there is a cross-roads ahead with a tile of value 13. 
		 * 		When this occurs, the boolean thirteen_tile is set to true. This case is specific to the game's 
		 * 		default map, and is not easily transferrable to a general case.
		 */
		if ((true_y - 1 >= 0) && (map[true_y - 1][true_x] == 13) && (true_y - 1 != previous_y)) {
			this.previous_y = true_y;
			this.previous_x = true_x;
			this.thirteen_tile = true;
			this.true_y -= 1;
		}
		
		/**
		 * Case:  the enemy is on a horizontal path at which there is a cross-roads ahead with a tile of value 13. 
		 * 		When this occurs, the boolean thirteen_tile is set to true. This case is specific to the game's
		 * 		default map, and is not easily transferrable to a general case.
		 */
		else if ((true_x + 1 < 17) && (map[true_y][true_x + 1] == 13)) 
		{
			this.previous_y = true_y;
			this.previous_x = true_x;
			this.thirteen_tile = true;
			this.true_x += 1;
		}
		
		/**
		 * Case:  the enemy is at a cross-roads at which there's two possible paths to take, and the enemy is not 
		 * 		standing on a tile of value 13. In this case, a random integer of value 1 or 2 is generated, and 
		 * 		used to determine the direction that the enemy travels from that point. This case is specific to
		 * 		the default map of the game, and is not easily transferrable to a general case.
		 */
		else if ((true_y - 1 >= 0) && (true_x - 1 >= 0) && (map[true_y - 1][true_x] == 1) 
				&& (true_y - 1 != previous_y) && (map[true_y][true_x - 1] == 1) 
				&& (true_x - 1 != previous_x)) {
			int choice = randInt();
			//System.out.println(choice);
			if (choice == 1) {
				this.previous_x = true_x;
				this.previous_y = true_y;
				this.true_y -=1;
		}
			else {
				this.previous_y = true_y;
				this.previous_x = true_x;
				this.true_x -= 1;
			}
		}
		
		/**
		 * Case:  the enemy is standing on a tile of value 13. If the boolean thirteen_tile was set to true at 
		 * 		some point previously, then the enemy must travel to the right, and the boolean is reset to false.
		 * 		This case is specific to the default map of the game, and is not easily transferrable to a general
		 * 		case.
		 */
		else if (thirteen_tile == true) 
		{
			this.previous_y = true_y;
			this.previous_x = true_x;
			this.true_x += 1;
			this.thirteen_tile = false;
			this.prev_tile = 13;
		}
		
		/**
		 * Case:  general case for movement.
		 */
		else if ((true_y - 1 >= 0) && (map[true_y - 1][true_x] == 1) && (true_y - 1 != previous_y)) 
		{
			//move up
			this.previous_x = true_x;
			this.previous_y = true_y;
			this.prev_tile = 1;
			this.true_y -= 1;
		}
		else if ((true_y + 1 < 12) && (map[true_y + 1][true_x] == 1) && (true_y + 1 != previous_y)) 
		{
			//move down
			this.previous_x = true_x;
			this.previous_y = true_y;
			this.prev_tile = 1;
			this.true_y += 1;
		}
		else if ((true_x - 1 >= 0) && (map[true_y][true_x - 1] == 1) && (true_x - 1 != previous_x)) 
		{
			//move left 
			this.previous_y = true_y;
			this.previous_x = true_x;
			this.prev_tile = 1;
			this.true_x -= 1;
		}
		else if ((true_x + 1 < 17) && (map[true_y][true_x + 1] == 1) && (true_x + 1 != previous_x)) 
		{
			//move right
			this.previous_y = true_y;
			this.previous_x = true_x;
			this.prev_tile = 1;
			this.true_x += 1;
		}
	}
	
	/**
	 * About hit(int damage):  hit(int damage) is used to apply damage to an instance of enemy. The enemy takes
	 * 		damage depending on what tower hits it.
	 * @param damage: takes damage as a parameter and subtracts it from the enemy health.
	 */
	public void hit(int damage) 
	{
		this.health -= damage;
	}
	
	/**
	 * About toString():  prints information about a specific instance of enemy, and is largely used for debugging
	 * 		purposes.
	 */
	public String toString() 
	{
		return ("(" + true_x + ", " + true_y + ", " + health + ")");
	}
}
