import java.util.ArrayList;
import java.util.Arrays;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Engine 
{	
	public Engine() {}
	
	private int true_doll_x;
	private int true_doll_y;
	
	public void runGame(Level_generator foo, int[][] map, Pane pane, Player player, Render render) 
	{	
		ArrayList<Enemy> wave = foo.get_wave();
		int delay = 0;
		true_doll_x = (64*9);
		true_doll_y = (64*12);
		for (Enemy enemy : foo.get_wave_02()) 
		{
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
					int circ = (int) (enemy.get_doll().getLayoutY()/64);
					int nirc = (int) (enemy.get_doll().getLayoutX()/64);
					if (timeline.getKeyFrames().contains(NORTH)) 
					{
						if (((enemy.get_doll().getLayoutY() % 64) == 0) && 
								((enemy.get_doll().getLayoutX() % 64) == 0) && map[circ - 1][nirc] == 0) 
						{
							timeline.stop();
							timeline.getKeyFrames().clear();
							if (map[circ][nirc - 1] == 1) 
							{
								timeline.getKeyFrames().add(WEST);
							}
							else if (map[circ][nirc + 1] == 1) 
							{
								timeline.getKeyFrames().add(EAST);
							}
							timeline.setDelay(Duration.millis(0));
							timeline.play();
						}
					}
					else if (timeline.getKeyFrames().contains(WEST)) 
					{
						if (((enemy.get_doll().getLayoutY() % 64) == 0) && 
								((enemy.get_doll().getLayoutX() % 64) == 0) && map[circ][nirc - 1] == 0) 
						{
							timeline.stop();
							timeline.getKeyFrames().clear();
							if (map[circ - 1][nirc] == 1) 
							{
								timeline.getKeyFrames().add(NORTH);
							}
							else if (map[circ + 1][nirc] == 1) 
							{
								timeline.getKeyFrames().add(SOUTH);
							}
							timeline.setDelay(Duration.millis(0));
							timeline.play();
						}
					}
					else if (timeline.getKeyFrames().contains(EAST)) 
					{
						if (((enemy.get_doll().getLayoutY() % 64) == 0) && 
								((enemy.get_doll().getLayoutX() % 64) == 0) 
								&& (nirc + 1 > 16 || map[circ][nirc + 1] == 0)) 
						{
							timeline.stop();
							if (nirc == 16) 
							{
								enemy.get_doll().remove_enemy_end(pane, player, render);
							}
							timeline.getKeyFrames().clear();
							if (map[circ - 1][nirc] == 1) 
							{
								timeline.getKeyFrames().add(NORTH);
							}
							else if (map[circ + 1][nirc] == 1) 
							{
								timeline.getKeyFrames().add(SOUTH);
							}
							timeline.setDelay(Duration.millis(0));
							timeline.play();
						}
					}
					else if (timeline.getKeyFrames().contains(SOUTH)) 
					{
						if (((enemy.get_doll().getLayoutY() % 64) == 0) && 
								((enemy.get_doll().getLayoutX() % 64) == 0) && map[circ + 1][nirc] == 0) 
						{
							timeline.stop();
							timeline.getKeyFrames().clear();
							if (map[circ][nirc - 1] == 1) 
							{
								timeline.getKeyFrames().add(WEST);
							}
							else if (map[circ][nirc + 1] == 1) 
							{
								timeline.getKeyFrames().add(EAST);
							}
							timeline.setDelay(Duration.millis(0));
							timeline.play();
						}
					}
					if (enemy.get_doll().getLayoutY() == enemy.get_doll().get_death_y() 
							&& enemy.get_doll().getLayoutX() == enemy.get_doll().get_death_x()) 
					{
						timeline.stop();
						enemy.get_doll().remove_enemy_death(pane);
					}
				}
			};
			timeline.play();
			timer.start();
		}
		int enemyNum = 0;
		while ((foo.check_wave() || enemyNum == 0) && player.getGPA() >= 0) 
		{
			System.out.println("Enemy array: " + Arrays.toString(wave.toArray()));
			for (Enemy enemy : wave) 
			{
				if (enemy.get_x() != -1) 
				{
					if ((enemy.get_x() == 16) && (enemy.get_y() == 3)) 
					{
						// Player health decreases
						map[enemy.get_y()][enemy.get_x()] = 1;
						enemy.eliminate();
						//player.setGPA(player.getGPA() - 1);
						//render.update_map(player);
						//System.out.println(player.getGPA());
						
						
					}
					else 
					{
						enemy.move(map);
						map[enemy.get_y()][enemy.get_x()] = enemy.get_appearance();
						map[enemy.get_previous_y()][enemy.get_previous_x()] = 1;
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
				/**wave.get(enemy_nom).get_doll().move(wave.get(enemy_nom), n);
				n++;*/
				map[wave.get(enemyNum).get_y()][wave.get(enemyNum).get_x()] = 
					wave.get(enemyNum).get_appearance();
				enemyNum++;
			}
			
			for (int i = 0; i < map.length; i++) 
			{
				System.out.println(Arrays.toString(map[i]));
			}
			for (Tower tower : foo.get_arsenal())
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
			System.out.println("Tower array: " + Arrays.toString(foo.get_arsenal().toArray()));
			System.out.println();
		}
		foo.increment_level();
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

