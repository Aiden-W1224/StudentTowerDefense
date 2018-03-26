import java.util.Arrays;
import java.util.Scanner;

public class Tower_place 
{
	private int x_coord;
	private int y_coord;
	private int tower_type;
	Scanner sc = new Scanner (System.in);
	
	public Tower_place() {}
	
	public int[][] place_towers(int[][] map, Level_generator gen)
	{
		this.x_coord = 0;
		this.y_coord = 0;
		//while ((x_coord != -1) && (y_coord != -1))
		//{
			System.out.println("Select co-ordinates for a tower.");
			System.out.println("x co-ordinate: ");
			this.x_coord = sc.nextInt();
			System.out.println("y co-ordinate: ");
			this.y_coord = sc.nextInt();
			System.out.println("Type in tower type (First-year = 7, Second-year = 8): ");
			this.tower_type = sc.nextInt();
			if (x_coord >= 0 && x_coord < map[0].length && y_coord >= 0 && y_coord < map.length)
			{
				Tower floof = new First_year(x_coord, y_coord, map);
				switch (tower_type) 
				{
				case 7:
					floof = new First_year(x_coord, y_coord, map);
					break;
				case 8:
					floof = new Second_year(x_coord, y_coord, map);
					break;
				}
				/**System.out.println("Tower bounds: ");
				for (int i = 0; i < floof.get_bounds().size(); i++) 
				{
					System.out.print(Arrays.toString(floof.get_bounds().get(i)));
				}
				System.out.println("\n");*/
				gen.add_to_arsenal(floof);
				map[floof.get_y()][floof.get_x()] = floof.get_appearance();
				System.out.println("Map now: ");
				for (int i = 0; i < map.length; i++) 
				{
					System.out.println(Arrays.toString(map[i]));
				}
				System.out.println();
			}
		//}
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