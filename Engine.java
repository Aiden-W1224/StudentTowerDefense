import java.util.ArrayList;
import java.util.Arrays;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Engine 
{	
	public Engine() {}
	
	private int true_doll_x;
	private int true_doll_y;
	
	public void runGame(Level_generator generator, int[][] map, Pane pane, Player player, Render render) 
	{	
		ArrayList<Enemy> wave = generator.get_wave();
		int delay = 0;
		true_doll_x = (64*9);
		true_doll_y = (64*12);
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
			timeline.getKeyFrames().add(NORTH);
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
						if (((enemy.get_doll().getLayoutY() % 64) == 0) && 
								((enemy.get_doll().getLayoutX() % 64) == 0) && ((map[mapY - 1][mapX] != 1)
										&& (map[mapY - 1][mapX] != 13))) 
						{
							//System.out.println("What is this :" + enemy.get_doll().getLayoutY() % 64);
							timeline.stop();
							timeline.getKeyFrames().clear();
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
								((enemy.get_doll().getLayoutX() % 64) == 0) && ((map[mapY][mapX - 1] != 1)
										|| ((map[mapY - 1][mapX] == 1) && (map[mapY][mapX - 1] == 1) && 
												enemy.get_rand_dir() == 1)))
						{
							timeline.stop();
							timeline.getKeyFrames().clear();
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
								((enemy.get_doll().getLayoutX() % 64) == 0) && map[mapY + 1][mapX] != 1) 
						{
							timeline.stop();
							timeline.getKeyFrames().clear();
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
					for (Tower T : generator.get_arsenal()) {
						
//						if (T.GUIDetect(enemy)) {
//							System.out.println("SHOOTING");
//							//T.update(pane);
//							if (enemy.get_doll().getLayoutY() == enemy.get_doll().get_death_y() 
//									&& enemy.get_doll().getLayoutX() == enemy.get_doll().get_death_x()) 
//							{
//								enemy.get_doll().de
//								timeline.stop();
//								enemy.get_doll().remove_enemy_death(pane);
//							}
//						}
						//System.out.println("X: " +	T.get_x() + " " + "Y: " + T.get_y());
						if(enemy.get_doll().getLayoutX() <= T.getGUIBoundsRight(2) && enemy.get_doll().getLayoutX() > T.getGUIBoundsLeft(2) && 
								enemy.get_doll().getLayoutY() > T.getGUIBoundsAbove(2) && enemy.get_doll().getLayoutY() < T.getGUIBoundsBelow(2)) {
							
							//System.out.println("Enemy Location: (x,y) " + enemy.get_doll().getLayoutX() + " " + enemy.get_doll().getLayoutY());
							T.update(pane, wave);
							
						}
					}
					if (enemy.get_doll().getLayoutY() == enemy.get_doll().get_death_y() 
							&& enemy.get_doll().getLayoutX() == enemy.get_doll().get_death_x()) 
					{
						//System.out.println(generator.get_wave_02().toString());
						timeline.stop();
						//foo.get_wave_02().remove(enemy);
						enemy.get_doll().setLayoutY(-1);
						enemy.get_doll().setLayoutX(-1);
						enemy.get_doll().remove_enemy_death(pane, player, render);
						generator.get_wave_02().remove(enemy);
						//enemy.get_doll().setLayoutY(-1);
						//enemy.get_doll().setLayoutX(-1);
						//foo.get_wave_02().remove(enemy);
					}
				}
			};
			timeline.play();
			timer.start();
		}
		int enemyNum = 0;
		while ((generator.check_wave() || enemyNum == 0) && player.getGPA() >= 0) 
		{
			//render.update_map(player);
			System.out.println("GPA: " + player.getGPA());
			System.out.println("Enemy array: " + Arrays.toString(wave.toArray()));
			for (Enemy enemy : wave) 
			{
				if (enemy.get_x() != -1) 
				{
					if ((enemy.get_x() == 16) && (enemy.get_y() == 3)) 
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
					if (enemy.get_health() <= 0 && enemy.get_x() != -1)
					{
						map[enemy.get_y()][enemy.get_x()] = 1;
						enemy.get_doll().record_death(enemy.get_previous_x(), enemy.get_previous_y());
						enemy.eliminate();
					}
				}
			}
			
			
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

