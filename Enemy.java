

import java.util.Random;

import com.sun.javafx.geom.Point2D;

import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.Image;

public class Enemy extends Sprite{
	Random rand = new Random();
	boolean spawn_enemy = false;
	private int health;
	private int speed;
	private String Student;
	private final int ACTUAL_ORIGIN_X = 31;
	private final int ACTUAL_ORIGIN_Y = 32;
	private final int TILE_LENGTH = 64;
	private Point2D start = new Point2D(ACTUAL_ORIGIN_X, ACTUAL_ORIGIN_Y);
	
	/**
	 * I don't use a lot of the variables above, don't worry too much abt them XD
	 */
	
	
	/**
	 * make_move: Used to create a path for the enemy (who I made Raoh now, I'm sorry). Basically, our game path
	 * 		uses paths that only travel along the x or y axis (never in a diagonal line). This class creates
	 * 		an instance of something called a TranslateTransition, which either moves the enemy along x or y.
	 * 		Note: setByX/setByY works with increments, so you only need to know how many pixels long the path is.
	 * 		In this case, all the tiles are 64x64 pixels, so if you know how many tiles the enemy has to traverse, 
	 * 		you just have to multiply that by the tile length. 
	 * @param path_length:  how many tiles long the path is before the axis changes
	 * @param dim:  short for "dimension" (indicates whether the enemy travels along x or y).
	 * @param raoh:  the enemy itself. I'm honestly not sure how to make an object call a method onto itself.
	 * 		On that note, if you know, feel free to edit XD
	 * @param noot:  you can change the name to something more helpful, but basically this is the next
	 * 		path for the enemy to travel. TranslateTransition has a method called setOnFinished() where
	 * 		you specify what you want to happen when the path finishes. In this case, it basically says
	 * 		when this path finishes, play the next one. I tried to just list all the paths in order without
	 * 		calling the setOnFinished() method, but it literally just plays all of the paths at the same time
	 * 		(which is interesting, but not what we're aiming for).
	 * @return
	 */
	public TranslateTransition make_move(int path_length, String dim, Enemy raoh, TranslateTransition noot) 
	{
		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(Math.abs(path_length)));
		if (dim.equals("x")) 
		{
			transition.setByX(TILE_LENGTH*path_length);
		}
		else if (dim.equals("y"))
		{
			transition.setByY(TILE_LENGTH*path_length);
		}
		transition.setNode(raoh);
		transition.setOnFinished(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent event) 
			{
				if (noot != null) 
				{
					noot.play();
				}
			}
		});
		return transition;
	}
	
	
	/**
	 * Basically, this is what runs when the button ("Move Raoh") is pressed. I'm sure there's a better way to do
	 * 		this but....dunno yet, but this is my solution so far XD
	 * @param raoh:  the enemy.
	 */
	public void move(Enemy raoh) 
	{
		TranslateTransition path10 = raoh.make_move(3, "x", raoh, null);
		TranslateTransition path9 = raoh.make_move(2, "y", raoh, path10);
		TranslateTransition path8 = raoh.make_move(2, "x", raoh, path9);
		TranslateTransition path7 = raoh.make_move(3, "y", raoh, path8);
		TranslateTransition path6 = raoh.make_move(9, "x", raoh, path7);
		TranslateTransition path5 = raoh.make_move(-6, "y", raoh, path6);
		TranslateTransition path4 = raoh.make_move(-4, "x", raoh, path5);
		TranslateTransition path3 = raoh.make_move(2, "y", raoh, path4);
		TranslateTransition path2 = raoh.make_move(-3, "x", raoh, path3);
		TranslateTransition path1 = raoh.make_move(-3, "y", raoh, path2);
		
		path1.play();
	}
	
	
	
	
	// Jacob was experimenting with some things here.
	@SuppressWarnings("unlikely-arg-type")
	public boolean spawnpoint(Integer_map map) {
		for (int i = 0; map.get_length() > i;)
		if (map.get_coord(i) == 1) {
		spawn_enemy = true;
	}
	return spawn_enemy;
	}
	
	
	
	/**
	 * I set the image for the enemy (raoh.png) and set the enemy's starting position.
	 */
	public Enemy() {
		this.speed = speed;
		this.health = health;
		super.set_image("res/raoh.png");
		super.setImage(super.get_image());
		super.setX(64*9);
		super.setY(64*8);
		
	}
	
	
	
	public int getHealth() {
		return health;
	}
	
	
	
	
	public boolean death() {
		return health == 0;
	}
	
	
	public void subtractedHealth(int amount) {
	        health = (health - amount < 0) ? 0 : (health - amount);
	    }
	
	
}
