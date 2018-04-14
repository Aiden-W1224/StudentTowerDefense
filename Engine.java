import java.util.ArrayList;
import java.util.Arrays;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * @author:  Athena Bolyos, Jacob Hazen, Daniel Orduz, Aiden Wong.
 * About program:  Engine() acts as the main driver with regards to running individual levels of the game. The
 * 		majority of Engine()'s code only runs when the player clicks a button to begin a wave. Engine() runs both
 * 		animated and text-based versions of the game.
 */

public class Engine 
{	
	/**
	 * About Engine():  a constructor to create an instance of Engine().
	 */
	public Engine() {}
	
	private int true_doll_x;
	private int true_doll_y;
	
	private final int TILE_SIZE = 64;
	
	/**
	 * About runGame(Level_generator generator, int[][] map, Pane pane, Player player, Render render):  the main
	 * 		functionality of Engine(). The animated version of the game is initialized when runGame() is called,
	 * 		 and the text-based version of the game is run through the use of a while loop.
	 * @param generator:  provides information used to determine progression through the game, including
	 * 		game level, and the number and difficulty of enemies.
	 * @param map:  an integer map used to run a text-based version of the game.
	 * @param pane:  a Pane used to animate the GUI version of the game.
	 * @param player:  used to provide and collect information about the player, such as how much money the player
	 * 		has (tuition) and how much health (GPA) they have.
	 * @param render:  used to translate the text-based version of the map into an appropriate GUI version.
	 */
	public void runGame(Level_generator generator, int[][] map, Pane pane, Player player, Render render) 
	{	
		ArrayList<Enemy> wave = generator.get_wave();
		int delay = 0;
		true_doll_x = (64*9);
		true_doll_y = (64*12);
		
		/**
		 * Variables true_doll_x and true_doll_y are set to values appropriate for the game's default map by default.
		 * 		If the player selected a random map, information about the starting point of the map is collected
		 * 		and used to determine appropriate values for true_doll_x and true_doll_y.
		 */
		if (render.rand() == true) 
		{
			if (render.get_start_x() == 0)
			{
				this.true_doll_x = -1*TILE_SIZE;
				this.true_doll_y = render.get_start_y()*TILE_SIZE;
			}
			else if (render.get_start_x() == 16) 
			{
				this.true_doll_x = (render.get_start_x() + 1)*TILE_SIZE;
				this.true_doll_y = render.get_start_y()*TILE_SIZE;
			}
			else if (render.get_start_y() == 0) 
			{
				this.true_doll_y = -1*TILE_SIZE;
				this.true_doll_x = render.get_start_x()*TILE_SIZE;
			}
			else if (render.get_start_y() == 11) 
			{
				this.true_doll_y = (render.get_start_y() + 1)*TILE_SIZE;
				this.true_doll_x = render.get_start_x()*TILE_SIZE;
			}
		}
		
		/**
		 * A for loop iterates through the ArrayList of enemies to place GUI versions of enemies onto the map,
		 * 		and move them appropriately according to information from the text-based version of the game.
		 * 		 An AnimationTimer is used to change enemy movement as appropriate.
		 */
		for (Enemy enemy : generator.get_wave_02()) 
		{
			//Place every enemy at the start of the map 
			enemy.get_doll().initial_placement(pane);
			Timeline timeline = new Timeline();
			KeyFrame NORTH = new KeyFrame(Duration.millis(31.25), evt -> 
			{
				(enemy.get_doll()).setLayoutY((enemy.get_doll()).getLayoutY() - 2);
			});
			
			KeyFrame SOUTH = new KeyFrame(Duration.millis(31.25), evt -> 
			{
				(enemy.get_doll()).setLayoutY((enemy.get_doll()).getLayoutY() + 2);
			});
			
			KeyFrame EAST = new KeyFrame(Duration.millis(31.25), evt -> 
			{
				(enemy.get_doll()).setLayoutX((enemy.get_doll()).getLayoutX() + 2);
			});
			
			KeyFrame WEST = new KeyFrame(Duration.millis(31.25), evt -> 
			{
				(enemy.get_doll()).setLayoutX((enemy.get_doll()).getLayoutX() - 2);
			});
			timeline.setCycleCount(Animation.INDEFINITE);
			if (true_doll_x < 0) 
			{
				timeline.getKeyFrames().add(EAST);
			}
			else if (true_doll_x > render.get_start_x()*TILE_SIZE) 
			{
				timeline.getKeyFrames().add(WEST);
			}
			else if (true_doll_y < 0) 
			{
				timeline.getKeyFrames().add(SOUTH);
			}
			else if (true_doll_y > render.get_start_y()*TILE_SIZE) 
			{
				timeline.getKeyFrames().add(NORTH);
			}
			timeline.setDelay(Duration.millis(1000*delay));
			delay++;
			AnimationTimer timer = new AnimationTimer() 
			{
				@Override
				public void handle(long now) 
				{
					int mapY = (int) (enemy.get_doll().getLayoutY()/64);
					int mapX = (int) (enemy.get_doll().getLayoutX()/64);
					//going north and turns west or east
					if (timeline.getKeyFrames().contains(NORTH)) 
					{
						if (((enemy.get_doll().getLayoutY() % 64 == 0) && (enemy.get_doll().getLayoutX() % 64 == 0)) 
								&& ((mapY - 1 < 0) || ((map[mapY - 1][mapX] != 1) && (map[mapY - 1][mapX] != 13))))
						{
							//System.out.println("What is this :" + enemy.get_doll().getLayoutY() % 64);
							timeline.stop();
							timeline.getKeyFrames().clear();
							if (mapY == 0) 
							{
								enemy.get_doll().remove_enemy_end(pane, player, render);
							}
							if (map[mapY][mapX] == 13) 
							{
								timeline.getKeyFrames().add(EAST);
							}
							else if (map[mapY][mapX - 1] == 1) 
							{
								timeline.getKeyFrames().add(WEST);
							}
							else if (map[mapY][mapX + 1] == 1) 
							{
								timeline.getKeyFrames().add(EAST);
							}
							timeline.setDelay(Duration.millis(0));
							timeline.play();
						}
					}
					// going west it turns north or south
					else if (timeline.getKeyFrames().contains(WEST)) 
					{
						if (((enemy.get_doll().getLayoutY() % 64) == 0) && 
								((enemy.get_doll().getLayoutX() % 64) == 0) && (mapX < 17) && (mapX - 1 < 0 || ((map[mapY][mapX - 1] != 1)
										|| ((map[mapY - 1][mapX] == 1) && (map[mapY][mapX - 1] == 1) && 
												enemy.get_rand_dir() == 1))))
						{
							timeline.stop();
							timeline.getKeyFrames().clear();
							if (mapX == 0) 
							{
								enemy.get_doll().remove_enemy_end(pane, player, render);
							}
							if ((map[mapY - 1][mapX] == 1) && (map[mapY][mapX - 1] == 1)) 
							{
								if (enemy.get_rand_dir() == 1) 
								{
									timeline.getKeyFrames().add(NORTH);
								}
							}
							else if ((map[mapY - 1][mapX] == 1) && map[mapY][mapX - 1] != 1) 
							{
								timeline.getKeyFrames().add(NORTH);
							}
							else if (map[mapY + 1][mapX] == 1) 
							{
								timeline.getKeyFrames().add(SOUTH);
							}
							timeline.setDelay(Duration.millis(0));
							timeline.play();
						}
					}
					// going east and turns north or south 
					else if (timeline.getKeyFrames().contains(EAST)) 
					{
						if (((enemy.get_doll().getLayoutY() % 64) == 0) && 
								((enemy.get_doll().getLayoutX() % 64) == 0) 
								&& (mapX + 1 > 16 || (map[mapY][mapX + 1] != 1 && map[mapY][mapX + 1] != 13))) 
						{
							timeline.stop();
							if (mapX == 16) 
							{
								enemy.get_doll().remove_enemy_end(pane, player, render);
							}
							timeline.getKeyFrames().clear();
							if (map[mapY - 1][mapX] == 1) 
							{
								timeline.getKeyFrames().add(NORTH);
							}
							else if ((map[mapY + 1][mapX] == 1) && (map[mapY][mapX] != 13)) 
							{
								timeline.getKeyFrames().add(SOUTH);
							}
							timeline.setDelay(Duration.millis(0));
							timeline.play();
						}
					}
					//going south and turns east or west
					else if (timeline.getKeyFrames().contains(SOUTH)) 
					{
						if (((enemy.get_doll().getLayoutY() % 64) == 0) && 
								((enemy.get_doll().getLayoutX() % 64) == 0) && ((
										(mapY + 1) > 11) || (map[mapY + 1][mapX] != 1))) 
						{
							timeline.stop();
							timeline.getKeyFrames().clear();
							if (mapY == 11) 
							{
								enemy.get_doll().remove_enemy_end(pane, player, render);
							}
							if (map[mapY][mapX - 1] == 1) 
							{
								timeline.getKeyFrames().add(WEST);
							}
							else if (map[mapY][mapX + 1] == 1) 
							{
								timeline.getKeyFrames().add(EAST);
							}
							timeline.setDelay(Duration.millis(0));
							timeline.play();
						}
					}
					
					//Iterate through arraylist of towers which are on the map
					for (Tower T : generator.get_arsenal()) 
					{
						if(enemy.get_doll().getLayoutX() <= T.getGUIBoundsRight(2) && enemy.get_doll().getLayoutX() > T.getGUIBoundsLeft(2) && 
								enemy.get_doll().getLayoutY() > T.getGUIBoundsAbove(2) && enemy.get_doll().getLayoutY() < T.getGUIBoundsBelow(2)) {
							
							T.update(pane, wave);
							
						}
					}
					
					/**
					 * Enemy_animation death case.
					 */
					if (enemy.get_doll().getLayoutY() == enemy.get_doll().get_death_y() 
							&& enemy.get_doll().getLayoutX() == enemy.get_doll().get_death_x()) 
					{
						timeline.stop();
						enemy.get_doll().setLayoutY(-1);
						enemy.get_doll().setLayoutX(-1);
						enemy.get_doll().remove_enemy_death(pane, player, render);
						generator.get_wave_02().remove(enemy);
					}
				}
			};
			timeline.play();
			timer.start();
		}
		int enemyNum = 0;
		/**
		 * The while loop used to run the text-based version of the game.
		 */
		while ((generator.check_wave() || enemyNum == 0) && player.getGPA() >= 0) 
		{
			System.out.println("GPA: " + player.getGPA());
			System.out.println("Enemy array: " + Arrays.toString(wave.toArray()));
			for (Enemy enemy : wave) 
			{
				// "If enemy is on the map"
				if (enemy.get_x() != -1) 
				{
					// "If enemy is at the end of the map"
					if ((enemy.get_x() == render.get_end_x()) && (enemy.get_y() == render.get_end_y())) 
					{
						map[enemy.get_y()][enemy.get_x()] = 1;
						enemy.eliminate();
					}
					else 
					{
						enemy.move(map);
						map[enemy.get_y()][enemy.get_x()] = enemy.get_appearance();
						map[enemy.get_previous_y()][enemy.get_previous_x()] = enemy.get_prev_tile();
					}
					// "If the enemy has been killed, but haven't been removed from the map yet."
					if (enemy.get_health() <= 0 && enemy.get_x() != -1)
					{
						map[enemy.get_y()][enemy.get_x()] = 1;
						enemy.get_doll().record_death(enemy.get_previous_x(), enemy.get_previous_y());
						enemy.eliminate();
					}
				}
			}
			
			// "If there are still enemies that haven't been placed on the map"
			if (enemyNum < wave.size()) 
			{
				wave.get(enemyNum).set_on_map();
				System.out.println("X property: " + wave.get(enemyNum).get_doll().xProperty());
				System.out.println("Other X property: " + wave.get(enemyNum).get_doll().getLayoutX());
				System.out.println("Other other X property: " + wave.get(enemyNum).get_doll().getX());
				map[wave.get(enemyNum).get_y()][wave.get(enemyNum).get_x()] = 
				wave.get(enemyNum).get_appearance();
				enemyNum++;
			}
			
			for (int i = 0; i < map.length; i++) 
			{
				System.out.println(Arrays.toString(map[i]));
			}
			
			/**
			 * Iterates through the ArrayList of towers. If a tower detects an enemy, then the program iterates
			 * 		through the ArrayList of enemies and determines if the enemy is near a tower. If there's
			 * 		an enemy near a tower, the enemy takes damage.
			 */
			for (Tower tower : generator.get_arsenal())
			{
				if (tower.detect(map)) 
				{
					System.out.println("Shoot!");
					for (Enemy enemy : wave) 
					{
						if ((enemy.get_x() >= tower.get_x() - tower.get_radius() && enemy.get_x() 
								<= tower.get_x() + tower.get_radius()) && (enemy.get_y() >= 
								tower.get_y() - tower.get_radius() && enemy.get_y() <= 
								tower.get_y() + tower.get_radius()) && tower.get_shots_fired() < 
								tower.get_shooting_rate()) 
						{
							enemy.hit(tower.get_damage());
							tower.set_shots_fired();
							System.out.println("Hit!");
						}
					}
				}
				tower.reset_shots_fired();
			}
			System.out.println("Enemy array: " + Arrays.toString(wave.toArray()));
			System.out.println("Tower array: " + Arrays.toString(generator.get_arsenal().toArray()));
			System.out.println();
		}
		/**
		 * Once the while loop reaches its conclusion, the level increases and runGame() reaches a conclusion.
		 */
		generator.increment_level();
		for (Enemy enemy : wave) 
		{
			if (enemy.get_x() != (-1)) 
			{
				map[enemy.get_y()][enemy.get_x()] = 1;
			}
		}
		for (int i = 0; i < map.length; i++) 
		{
			System.out.println(Arrays.toString(map[i]));
		}
		System.out.println();
	}
}

