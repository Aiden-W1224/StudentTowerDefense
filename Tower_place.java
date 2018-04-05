import java.util.Arrays;
import java.util.Scanner;

public class Tower_place 
{
	private int x_coord;
	private int y_coord;
	private int tower_type;
	Scanner sc = new Scanner (System.in);
	
	public Tower_place() {}
	
	public int[][] place_towers(int[][] map, Level_generator gen, int x_coord, int y_coord, int tower_num, Render render, Player player)
	{

			this.tower_type = tower_num;
		
			
			if (x_coord >= 0 && x_coord < map[0].length && y_coord >= 0 && y_coord < map.length)
			{
				Tower firstYear = new First_year(x_coord, y_coord, map);
				switch (tower_type) 
				{
				case 7:
					firstYear = new First_year(x_coord, y_coord, map);
					player.setTuition(player.getTuition() - 250);
					render.update_map(player);
					break;
				case 8:
					firstYear = new Second_year(x_coord, y_coord, map);
					player.setTuition(player.getTuition() - 500);
					render.update_map(player);
					break;
				}
				gen.add_to_arsenal(firstYear);
				map[firstYear.get_y()][firstYear.get_x()] = firstYear.get_appearance();
				System.out.println("Map now: ");
				for (int i = 0; i < map.length; i++) 
				{
					System.out.println(Arrays.toString(map[i]));
				}
				System.out.println();
			}
		
		return map;
	}
	
	public int get_x() 
	{
		return this.x_coord;
	}
	
	public int get_y() 
	{
		return this.y_coord;
	}
	
	public int get_tower_type() 
	{
		return this.tower_type;
	}
}
