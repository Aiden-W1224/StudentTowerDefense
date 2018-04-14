import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * @author:  Athena Bolyos, Jacob Hazen, Daniel Orduz, and Aiden Wong.
 * About program:  the parent class for all Towers. The program includes code for detecting enemies within
 * 		its range in both text and GUI versions.
 */

public abstract class Tower 
{
	Timer timer = new Timer();
	private int x_coord;
	private int y_coord;
	private int radius;
	private int appearance;
	private float timeSinceLastShot;
	private int shots_fired;
	private int damage;
	private ArrayList<int[]> bounds = new ArrayList<int[]>();
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	public Pane pane;
	public Enemy enemy;
	float shooting_rate = (float) 30.0;
	TimerTask shooting = new TimerTask() {

		@Override
		public void run() {
			shoot();
			
		}
		
	};
	
	public ArrayList<Projectile> getProjectiles(){return this.projectiles;}
	
	/**
	 * About shoot():  draws a projectile onto the screen, and illustrates the towers shooting.
	 */
	public void shoot() {
		Projectile p = new Projectile(this.getScreenX(), this.getScreenY());
		p.draw(this.pane, this.enemy);
	}
	
	
	public void update(Pane pane, ArrayList<Enemy> enemies) {
		this.pane = pane;
		this.enemy = this.target(enemies);
		shoot();
		
	}
	
	/**
	 * 
	 * @param targets Takes ArrayList of Type Enemy an calculates the closet enemy to the tower by calculating the hypotenuse
	 * between tower and enemy and targeting the closest one 
	 * @return Enemy to target
	 */
	public Enemy target(ArrayList<Enemy> targets) {
		Enemy closest = targets.get(0);
		double currentHyp;
		//sets previous hypotenuse to the hypotenuse of the first enemy in the wave 
		double previousHyp = (Math.sqrt( Math.pow(Math.abs(closest.get_doll().getLayoutX() - this.getScreenX()), 2)+ Math.pow(Math.abs(closest.get_doll().getLayoutY() - this.getScreenY()), 2)));;
		for (Enemy E : targets) {
			//calculates current enemy's x and y 
			double x = Math.abs(this.getScreenX() - E.get_doll().getLayoutX());
			double y = Math.abs(this.getScreenY() - E.get_doll().getLayoutY());
			currentHyp = (Math.sqrt( Math.pow(x, 2)+ Math.pow(y, 2)));
			
			if (currentHyp < previousHyp) {
				closest = E;
				previousHyp = currentHyp;
			}
		}
		
		
		return closest;
		
	}
	
	/**
	 * Getters and setters:
	 */
	public int get_x() 
	{
		return this.x_coord;
	}
	public int get_y() 
	{
		return this.y_coord;
	}
	public double getScreenX() {
		return this.x_coord*64.0;
	}
	public double getScreenY() {
		return this.get_y()*64.0;
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
	public float get_shooting_rate() 
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
	
	 /**
	  * sets block radius around tower 
	  * @param rad takes int value for radius 
	  */
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
	
	/**
	 * Default constructor for use by subclasses.
	 */
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
	
	/**
	 * Checks if enemy in array is within tower range 
	 * @param map takes array map as parameter
	 * @return boolean. returns true if enemy is within tower range  
	 */
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
	
	/**
	 * sets tower detection radius area within the array map 
	 * @param map takes current int array as parameter 
	 */
	public void set_bounds(int[][] map) 
	{
		//sets area from radius to the left and to radius to the right and above and below by radius amount 
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
	
	public double getGUIBoundsRight(int radius) {
		double xBoundsRight = this.get_x()*64.0 + radius*64;;
		return xBoundsRight;
	}
	
	public double getGUIBoundsLeft(int radius) {
		double xBoundsLeft = this.get_x()*64.0 - radius*64;
		return xBoundsLeft;
	}
	
	public double getGUIBoundsBelow(int radius) {
		double yBoundsBelow = this.get_y()*64.0 + radius*64;
		return yBoundsBelow;
	}
	
	public double getGUIBoundsAbove(int radius) {
		double yBoundsAbove = this.get_y()*64.0 - radius*64;
		return yBoundsAbove;
	}
	
	public boolean GUIDetect(Enemy enemy) {
		if(enemy.get_doll().getLayoutX() <= this.getGUIBoundsRight(2) && enemy.get_doll().getLayoutX() > this.getGUIBoundsLeft(2) && 
				enemy.get_doll().getLayoutY() > this.getGUIBoundsAbove(2) && enemy.get_doll().getLayoutY() < this.getGUIBoundsBelow(2)) {
		
			System.out.println("SHOOTING");
			return true;
			//T.shoot(pane);
		}
		else {
			return false;
		}
	}
	
	/**
	 * About toString():  returns information about a specific tower in the form of a string.
	 */
	public String toString() 
	{
		return ("(" + x_coord + ", " + y_coord + ")");
	}
	
	

}