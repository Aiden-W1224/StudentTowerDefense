import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
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
* @author Athena Bolyos, Jacob Hazen, Daniel Orduz, Aiden Wong.
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
	* About Enemy_animation(Render render):  a constructor that accepts a parameter render that allows the instance
	* 		of Enemy_animation to store information about important parts of the map. INIT_GAME_X and INIT_GAME_Y
	* 		 are set to default values based on the default map of the game. If the user selects a randomized
	* 		 map, then INIT_GAME_X and INIT_GAME_Y are set accordingly.
	*/
	public Enemy_animation(Render render) 
	{
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
	* About set_appearance(int array_appearance):  determines the appearance of the Enemy_animation based on 
	* 		its associated text-based version's appearance.
	* @param array_appearance:  the integer used to represent the enemy in the text-based version of the game.
	*/
	public void set_appearance(int array_appearance) 
	{
		switch (array_appearance) 
		{
		case 4:
			setImage(new Image("Dohataru.png"));
			break;
		case 5:
			setImage(new Image("TA.gif"));
			break;
		}
	}
	
	/**
	* About initial_placement(Pane pane):  places the Enemy_animation onto the GUI version of the map based on
	* 		its initial values of INIT_GAME_Y and INIT_GAME_X.
	* @param pane:  the Pane onto which the Enemy_animation is placed.
	*/
	public void initial_placement(Pane pane) 
	{
		setLayoutY(INIT_GAME_Y);
		setLayoutX(INIT_GAME_X);
		pane.getChildren().add(this);
	}
	
	/**
	 * About remove_enemy_end(Pane pane, Player player, Render render):  used to remove the Enemy_animation from
	 * 		the GUI version of the map in the case where the enemy reaches the end of the map.
	 * @param pane:  Pane from which to remove image on enemy animation 
	 * @param player:  Play to check if GPA(health) is still valid
	 * @param render:  Render updates map with players new GPA  
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
	 * About remove_enemy_death(Pane pane, Player player, Render render):  used to remove an Enemy_animation
	 * 		from the GUI version of the map in the case where the enemy was killed by a tower.
	 * @param pane:  the Pane from which to remove the Enemy_animation.
	 * @param player:  player gains tuition (Score) after killing enemy 
	 * @param render:  Render updates scored based on players tuition 
	 */
	public void remove_enemy_death(Pane pane, Player player, Render render) {
		pane.getChildren().remove(this);
		player.setTuition(player.getTuition() + 100);
		render.update_map(player);
	}
	
	/**
	 * About record_death(int array_x, int array_y):  records the location at which the text-based enemy died on the
	 * 		map. This information is used by Enemy_animation to reflect this information in the GUI afterwards.
	 * @param array_x int x where enemy died on array map
	 * @param array_y int y where enemy died on array map 
	 */
	public void record_death(int array_x, int array_y) 
	{
		this.last_x = 64*array_x;
		this.last_y = 64*array_y;
		System.out.println("Death x is: " + array_x + ", " + last_x);
	}
	
	/**
	 * About get_death_x():  returns the x co-ordinate at which the text-based enemy died.
	 */
	public int get_death_x() 
	{
		return this.last_x;
	}
	
	/**
	 * About get_death_y():  returns the y co-ordinate at which the text-based enemy died.
	 */
	public int get_death_y() 
	{
		return this.last_y;
 	}
}
