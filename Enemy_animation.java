import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class Enemy_animation extends ImageView
{
	private Render render;
	
	private int INIT_GAME_X = (64*9);
	private int INIT_GAME_Y = (64*12);
	private final int TILE_SIZE = 64;
	private int true_game_x;
	private int true_game_y;
	
	private int goal_game_x;
	private int goal_game_y;
	
	private int last_x;
	private int last_y;
	
	
	public Enemy_animation(Render render) 
	{
		//setImage(new Image("res/raoh.png"));
		this.render = render;
		if (render.rand() == false) 
		{
			this.true_game_x = INIT_GAME_X;
			this.true_game_y = INIT_GAME_Y;
		}
		else 
		{
			if (render.get_start_x() == 0)
			{
				this.INIT_GAME_X = -1*TILE_SIZE;
				this.INIT_GAME_Y = render.get_start_y()*TILE_SIZE;
			}
			else if (render.get_start_x() == 16) 
			{
				this.INIT_GAME_X = (render.get_start_x() + 1)*TILE_SIZE;
				this.INIT_GAME_Y = render.get_start_y()*TILE_SIZE;
			}
			else if (render.get_start_y() == 0) 
			{
				this.INIT_GAME_Y = -1*TILE_SIZE;
				this.INIT_GAME_X = render.get_start_x()*TILE_SIZE;
			}
			else if (render.get_start_y() == 11) 
			{
				this.INIT_GAME_Y = (render.get_start_y() + 1)*TILE_SIZE;
				this.INIT_GAME_X = render.get_start_x()*TILE_SIZE;
			}
			this.true_game_x = INIT_GAME_X;
			this.true_game_y = INIT_GAME_Y;
		}
	}
	
	public void set_appearance(int array_appearance) 
	{
		switch (array_appearance) 
		{
		case 4:
			setImage(new Image("art.png"));
			break;
		case 5:
			setImage(new Image("Dohataru.png"));
			break;
		}
	}
	
	public void initial_placement(Pane pane) 
	{
		setLayoutY(INIT_GAME_Y);
		setLayoutX(INIT_GAME_X);
		pane.getChildren().add(this);
	}
	/**
	 * Method for when the enemy makes it to the end of the map
	 * @param pane
	 * @param player
	 * @param render
	 */
	
	public void remove_enemy_end(Pane pane, Player player, Render render) 
	{
		pane.getChildren().remove(this);
		player.setGPA(player.getGPA() - 1);
		render.update_map(player);
		System.out.println("GPA: " + player.getGPA());
	}
	/**
	 * Method for when the enemy is killed by a tower
	 * @param pane
	 * @param player
	 * @param render
	 */
	public void remove_enemy_death(Pane pane, Player player, Render render) {
		pane.getChildren().remove(this);
		player.setTuition(player.getTuition() + 100);
		render.update_map(player);
		
		
		
	}
	/**
	 * Records and prints death location
	 * @param array_x
	 * @param array_y
	 */
	public void record_death(int array_x, int array_y) 
	{
		this.last_x = 64*array_x;
		this.last_y = 64*array_y;
		System.out.println("Death x is: " + array_x + ", " + last_x);
	}
	
	public int get_death_x() 
	{
		return this.last_x;
	}
	
	public int get_death_y() 
	{
		return this.last_y;
 	}
}