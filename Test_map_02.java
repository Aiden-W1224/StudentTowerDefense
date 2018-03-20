import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class Test_map_02 
{
	private static int[][] map = {
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0},
			{0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
			{0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1},
			{0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0},
			{0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
			};
	private static Level_generator foo = new Level_generator();
	private static Tower_place bar = new Tower_place();
	
	public static void main(String[] args) throws InterruptedException 
	{
		System.out.println("Original map: ");
		for (int i = 0; i < map.length; i++) 
		{
			System.out.println(Arrays.toString(map[i]));
		}
		System.out.println();
		
		map = bar.place_towers(map, foo);
		
		for (int k = 0; k < foo.get_total_levels(); k++) 
		{
			ArrayList<Enemy> wave = foo.get_wave();
			int enemy_nom = 0;
			while (foo.check_wave() || enemy_nom == 0) 
			{
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
							map[enemy.get_y()][enemy.get_x()] = enemy.get_appearance();
							map[enemy.get_previous_y()][enemy.get_previous_x()] = 1;
						}
						if (enemy.get_health() <= 0)
						{
							map[enemy.get_y()][enemy.get_x()] = 1;
							enemy.eliminate();
						}
					}
				}
				if (enemy_nom < wave.size()) 
				{
					wave.get(enemy_nom).set_on_map();
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
				Thread.sleep(1000);
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
			if (k < (foo.get_total_levels() - 1)) 
			{
				map = bar.place_towers(map, foo);
			}
		}
	}
}
