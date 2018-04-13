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

/**
* About class: Class keeps track of enemy movement and appearance and records death locations withing the array game 
* and reflects the changes within the GUI game.
*/

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
	
	/**
	* About: Constructor sets intial game x and y at starting location
	*/
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
	
	/**
	* Sets type of enemy based on array appearance number 
	*@param Takes in int value either 7 or 8
	*/
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
	
	/**
	* About: Sets intital layout x and y on GUI version
	*@param Takes pane on which gui game takes place to add enemy sprite
	*/
	public void initial_placement(Pane pane) 
	{
		setLayoutY(INIT_GAME_Y);
		setLayoutX(INIT_GAME_X);
		pane.getChildren().add(this);
	}
	/**
	 * Method for when the enemy makes it to the end of the map
	 * @param pane Pane from which to remove image on enemy animation 
	 * @param player Play to check if GPA(health) is still valid
	 * @param render Render updates map with players new GPA  
	 */
	
	public void remove_enemy_end(Pane pane, Player player, Render render) 
	{
		pane.getChildren().remove(this);
		//if gpa is not zero take health off when enemies reach end of the map 
		if (player.getGPA() > 0) {
			player.setGPA(player.getGPA() - .5);
		}
		//if enemy gpa is below 0 display game over screen 
		else {
			ImageView gameover = new ImageView(new Image("gameover.png"));
			gameover.setTranslateX(-100);
			pane.getChildren().add(gameover);
			FadeTransition ft = new FadeTransition(Duration.millis(3000), gameover);
			ft.setFromValue(1.0);
		    	ft.setToValue(0.0);
		        ft.setCycleCount(1);
		        ft.setAutoReverse(false);
			ft.play();
			ft.playFromStart();
			
		}
		
		render.update_map(player);
		System.out.println("GPA: " + player.getGPA());
	}
	/**
	 * Method for when the enemy is killed by a tower
	 * @param pane remove image of enemy from map
	 * @param player player gains tuition (Score) after killing enemy 
	 * @param render Render updates scored based on players tuition 
	 */
	public void remove_enemy_death(Pane pane, Player player, Render render) {
		pane.getChildren().remove(this);
		player.setTuition(player.getTuition() + 100);
		render.update_map(player);
		
		
		
	}
	/**
	 * Records and prints death location
	 * @param array_x int x where enemy died on array map
	 * @param array_y int y where enemy died on array map 
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
