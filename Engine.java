import java.util.ArrayList;
import java.util.Arrays;

import javafx.animation.Timeline;
import javafx.scene.layout.Pane;

public class Engine 
{	
	public Engine() {}
	
	public void runGame(Level_generator foo, int[][] map, Pane pane, Player player, Render render) 
	{	
		foo.reset_delay();
		ArrayList<Enemy> wave = foo.get_wave();
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
						if (enemy.get_health() > 0) 
						{
							enemy.get_doll().move(enemy, foo, pane, render, player);
							foo.set_delay(1);
						}
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
				wave.get(enemyNum).get_doll().initial_placement(pane);
				wave.get(enemyNum).get_doll().move(wave.get(enemyNum), foo, pane, render, player);
				foo.set_delay(1);
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

