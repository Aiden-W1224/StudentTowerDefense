import java.util.ArrayList;
import java.util.Arrays;

import javafx.animation.Timeline;
import javafx.scene.layout.Pane;

public class Test_map_03 
{	
	public Test_map_03() {}
	
	public void idk(Level_generator foo, int[][] map, Pane pane) 
	{	
		foo.reset_delay();
		ArrayList<Enemy> wave = foo.get_wave();
		int enemy_nom = 0;
		while (foo.check_wave() || enemy_nom == 0) 
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
					}
					else 
					{
						enemy.move(map);
						enemy.get_doll().move(enemy, foo, pane);
						foo.set_delay(1);
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
			if (enemy_nom < wave.size()) 
			{
				wave.get(enemy_nom).set_on_map();
				//pane.getChildren().add(wave.get(enemy_nom).get_doll());
				wave.get(enemy_nom).get_doll().initial_placement(pane);
				System.out.println("X property: " + wave.get(enemy_nom).get_doll().xProperty());
				System.out.println("Other X property: " + wave.get(enemy_nom).get_doll().getLayoutX());
				System.out.println("Other other X property: " + wave.get(enemy_nom).get_doll().getX());
				/**wave.get(enemy_nom).get_doll().move(wave.get(enemy_nom), n);
				n++;*/
				map[wave.get(enemy_nom).get_y()][wave.get(enemy_nom).get_x()] = 
					wave.get(enemy_nom).get_appearance();
				enemy_nom++;
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

