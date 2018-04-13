import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Random_map_generator 
{
	private final int X_DIM = 17;
	private final int Y_DIM = 12;
	int[][] map = new int[Y_DIM][X_DIM];
	private final int SCORE_AREA = 11;
	private int start_edge;
	private int end_edge;
	Random rand = new Random();
	
	private final int VALID_BUILD = 0;
	private final int PATH = 1;
	private final int BUFFERED = 2;
	private final int INVALID_BUILD = 3;
	
	private int start_edge_coord_x;
	private int start_edge_coord_y;
	private int end_edge_coord_x;
	private int end_edge_coord_y;
	
	private int current_x;
	private int current_y;
	
	private final int UP = 7;
	private final int RIGHT = 8;
	private final int DOWN = 9;
	private final int LEFT = 10;
	
	private final int SAME_DIR = 1;
	private final int CHANGE_DIR = 2;
	
	private int previous_x;
	private int previous_y;
	
	private int ahead_tile;
	private int dir;
	private int orisinal_dir;
	
	private int index;
	private int path_index;
	
	private int current_test_x;
	private int current_test_y;
	private int previous_test_x;
	private int previous_test_y;
	
	private boolean north;
	private boolean east;
	private boolean south;
	private boolean west;
	
	ArrayList<int[]> in_bounds = new ArrayList<int[]>();
	
	public int get_start_x() 
	{
		return this.start_edge_coord_x;
	}
	public int get_start_y() 
	{
		return this.start_edge_coord_y;
	}
	public int get_end_x() 
	{
		return this.end_edge_coord_x;
	}
	public int get_end_y() 
	{
		return this.end_edge_coord_y;
	}
	
	public void clear_booleans() 
	{
		north = false;
		east = false;
		south = false;
		west = false;
	}
	
	public int[][] get_map()
	{
		for (int i = 0; i < map.length; i++) 
		{
			for (int j = 0; j < map[0].length; j++) 
			{
				if ((map[i][j] == 3) || (map[i][j] == 2)) 
				{
					map[i][j] = 0;
				}
				else if (map[i][j] == 5) 
				{
					map[i][j] = 1;
				}
			}
		}
		return this.map;
	}
	
	public int test_map() 
	{
		int enemy = 5;
		map[start_edge_coord_y][start_edge_coord_x] = enemy;
		System.out.println("Map now: ");
		for (int j = 0; j < map.length; j++) 
		{
			System.out.println(Arrays.toString(map[j]));
		}
		System.out.println("---------------------------------------------------");
		System.out.println();
		path_index = 1;
		current_test_x = start_edge_coord_x;
		current_test_y = start_edge_coord_y;
		previous_test_x = -5;
		previous_test_y = -5;	
		while ((path_index == 1) && (map[end_edge_coord_y][end_edge_coord_x] == 1)) 
		{
			path_index = 0;
			clear_booleans();
			if (((current_test_y - 1) >= 0) && (map[current_test_y - 1][current_test_x] == 1) 
					&& (current_test_y - 1 != previous_test_y)) 
			{
				path_index += 1;
				this.north = true;
			}
			if (((current_test_y + 1) < Y_DIM) && (map[current_test_y + 1][current_test_x] == 1) 
					&& (current_test_y + 1 != previous_test_y)) 
			{
				path_index += 1;
				this.south = true;
			}
			if (((current_test_x - 1) >= 0) && (map[current_test_y][current_test_x - 1] == 1) 
					&& (current_test_x - 1 != previous_test_x)) 
			{
				path_index += 1;
				this.west = true;
			}
			if (((current_test_x + 1) < X_DIM) && (map[current_test_y][current_test_x + 1] == 1) 
					&& (current_test_x + 1 != previous_test_x)) 
			{
				path_index += 1;
				this.east = true;
			}
			if (path_index == 1) 
			{
				previous_test_x = current_test_x;
				previous_test_y = current_test_y;
				if (this.north == true) 
				{
					map[current_test_y - 1][current_test_x] = enemy;
					current_test_y -= 1;
				}
				else if (this.east == true) 
				{
					map[current_test_y][current_test_x + 1] = enemy;
					current_test_x += 1;
				}
				else if (this.south == true) 
				{
					map[current_test_y + 1][current_test_x] = enemy;
					current_test_y += 1;
				}
				else if (this.west == true) 
				{
					map[current_test_y][current_test_x - 1] = enemy;
					current_test_x -= 1;
				}
				map[previous_test_y][previous_test_x] = 1;
				System.out.println("Map now: ");
				for (int j = 0; j < map.length; j++) 
				{
					System.out.println(Arrays.toString(map[j]));
				}
				System.out.println("---------------------------------------------------");
				System.out.println();
			}
		}
		return this.path_index;
	}
	
	public void set_bounds() 
	{
		in_bounds.clear();
		if ((current_x - 1) >= 0) 
		{
			int[] coords = new int[] {(current_x - 1), current_y};
			in_bounds.add(coords);
		}
		if ((current_x + 1) < X_DIM) 
		{
			int[] coords = new int[] {(current_x + 1), current_y};
			in_bounds.add(coords);
		}
		if ((current_y - 1) >= 0) 
		{
			int[] coords = new int[] {current_x, (current_y - 1)};
			in_bounds.add(coords);
		}
		if ((current_y + 1) < Y_DIM) 
		{
			int[] coords = new int[] {current_x, (current_y + 1)};
			in_bounds.add(coords);
		}
		//System.out.println("Bounds looks like: ");
		for (int j = 0; j < in_bounds.size(); j++) 
		{
			//System.out.print(Arrays.toString(in_bounds.get(j)) + ", ");
			int ecks = in_bounds.get(j)[0];
			int wai = in_bounds.get(j)[1];
			if (map[wai][ecks] == 0) 
			{
				map[wai][ecks] = BUFFERED;
			}
		}
		//System.out.println();
	}
	
	public void void_bounds() 
	{
		for (int[] foo : in_bounds) 
		{
			if (map[foo[1]][foo[0]] == BUFFERED) 
			{
				map[foo[1]][foo[0]] = INVALID_BUILD;
			}
		}
	}
	
	public Random_map_generator() 
	{
		//this.map[1][14] = SCORE_AREA;
		/**System.out.println("Empty map: ");
		for (int i = 0; i < map.length; i++) 
		{
			System.out.println(Arrays.toString(map[i]));
		}
		System.out.println("---------------------------------------------------");
		System.out.println();*/
		start_edge = rand.nextInt(4) + 1;
		switch (start_edge) 
		{
		case 1:
			start_edge_coord_y = 0; 
			start_edge_coord_x = rand.nextInt(X_DIM - 2) + 1;
			for (int i = 0; i < map[start_edge_coord_y].length; i++) 
			{
				if (i != start_edge_coord_x) 
				{
					map[start_edge_coord_y][i] = INVALID_BUILD;
				}
				else 
				{
					map[start_edge_coord_y][i] = PATH;
				}
			}
			end_edge = start_edge;
			while (end_edge == start_edge) 
			{
				end_edge = rand.nextInt(4) + 1;
			}
			System.out.println("End edge: " + end_edge);
			break;
			
		case 2:
			start_edge_coord_x = 16; 
			start_edge_coord_y = rand.nextInt(Y_DIM - 2) + 1;
			for (int i = 0; i < map.length; i++) 
			{
				if (i != start_edge_coord_y) 
				{
					map[i][start_edge_coord_x] = INVALID_BUILD;
				}
				else 
				{
					map[i][start_edge_coord_x] = PATH;
				}
			}
			end_edge = start_edge;
			while (end_edge == start_edge) 
			{
				end_edge = rand.nextInt(4) + 1;
			}
			System.out.println("End edge: " + end_edge);
			break;
			
		case 3:
			start_edge_coord_y = 11; 
			start_edge_coord_x = rand.nextInt(X_DIM - 2) + 1;
			for (int i = 0; i < map[start_edge_coord_y].length; i++) 
			{
				if (i != start_edge_coord_x) 
				{
					map[start_edge_coord_y][i] = INVALID_BUILD;
				}
				else 
				{
					map[start_edge_coord_y][i] = PATH;
				}
			}
			end_edge = start_edge;
			while (end_edge == start_edge) 
			{
				end_edge = rand.nextInt(4) + 1;
			}
			System.out.println("End edge: " + end_edge);
			break;
			
		case 4:
			start_edge_coord_x = 0; 
			start_edge_coord_y = rand.nextInt(Y_DIM - 2) + 1;
			for (int i = 0; i < map.length; i++) 
			{
				if (i != start_edge_coord_y) 
				{
					map[i][start_edge_coord_x] = INVALID_BUILD;
				}
				else 
				{
					map[i][start_edge_coord_x] = PATH;
				}
			}
			end_edge = start_edge;
			while (end_edge == start_edge) 
			{
				end_edge = rand.nextInt(4) + 1;
			}
			System.out.println("End edge: " + end_edge);
			break;
		}
		current_x = start_edge_coord_x;
		current_y = start_edge_coord_y;
		System.out.println("Start of path is at: [" + current_x + ", " + current_y + "]");
		
		int turns = rand.nextInt(5) + 3;
		for (int i = 0; i < turns; i++) 
		{
			index = 0;
			set_bounds();
			
			if (i == 0) 
			{
				if (start_edge == 1) 
				{
					dir = DOWN;
				}
				else if (start_edge == 2) 
				{
					dir = LEFT;
				}
				else if (start_edge == 3) 
				{
					dir = UP;
				}
				else if (start_edge == 4) 
				{
					dir = RIGHT;
				}
				orisinal_dir = dir;
			}
			
			//onepathponepathponekdasjf;lkajsdl;kfj
			
			if (i == 0)
			{
			for (int j = 0; j < 3; j++) 
			{
				set_bounds();
				previous_x = current_x;
				previous_y = current_y;
				if (dir == DOWN) 
				{
					map[current_y + 1][current_x] = 1;
					current_y += 1;
				}
				else if (dir == LEFT) 
				{
					map[current_y][current_x - 1] = 1;
					current_x -= 1;
				}
				else if (dir == RIGHT) 
				{
					map[current_y][current_x + 1] = 1;
					current_x += 1;
				}
				else if (dir == UP) 
				{
					map[current_y - 1][current_x] = 1;
					current_y -=1;
				}
				void_bounds();
			}
			}
			
			int next_dir = -1;
			int steps = rand.nextInt(6) + 3;
			if (dir == UP) 
			{
				next_dir = SAME_DIR;
				while (next_dir == SAME_DIR) 
				{
					next_dir = rand.nextInt(CHANGE_DIR) + SAME_DIR;
					set_bounds();
					previous_x = current_x;
					previous_y = current_y;
					if ((next_dir == SAME_DIR) && ((current_y - 1) >= 1) && (map[current_y - 1][current_x] == BUFFERED)) 
					{
						map[current_y - 1][current_x] = 1;
						current_y -=1;
						void_bounds();
					}
				}
				if (next_dir == CHANGE_DIR) 
				{
					if (current_x <= 8) 
					{
						while (((current_x + 1) < (X_DIM - 1)) && (map[current_y][current_x + 1] != INVALID_BUILD)
								&& (index < steps))
						{
							set_bounds();
							if (map[current_y][current_x + 1] == BUFFERED)
							{
								previous_x = current_x;
								previous_y = current_y;
								dir = RIGHT;
								map[current_y][current_x + 1] = 1;
								current_x += 1;
								index += 1;
							}
							void_bounds();
						}
					}
					else if (current_x >= 9) 
					{
						while (((current_x - 1) > 1) && map[current_y][current_x - 1] != INVALID_BUILD
								 && index < steps) 
						{
							if ((current_x - 1) > 0) 
							{
								set_bounds();
								if (map[current_y][current_x - 1] == BUFFERED)
								{
									previous_x = current_x;
									previous_y = current_y;
									dir = LEFT;
									map[current_y][current_x - 1] = 1;
									current_x -= 1;
									index++;
								}
								void_bounds();
							}
						}
					}
				}
			}
			else if (dir == DOWN) 
			{
				next_dir = SAME_DIR;
				while (next_dir == SAME_DIR) 
				{
					next_dir = rand.nextInt(CHANGE_DIR) + SAME_DIR;
					set_bounds();
					previous_x = current_x;
					previous_y = current_y;
					if ((next_dir == SAME_DIR) && ((current_y + 1) < (Y_DIM - 1)) && 
							(map[current_y + 1][current_x] == BUFFERED)) 
					{
						map[current_y + 1][current_x] = 1;
						current_y +=1;
						void_bounds();
					}
				}
				if (next_dir == CHANGE_DIR) 
				{
					if (current_x <= 8) 
					{
						while (((current_x + 1) < (X_DIM - 1)) && 
								(map[current_y][current_x + 1] != INVALID_BUILD) && (index < steps))
						{
							if ((current_x + 1) < X_DIM) 
							{
								set_bounds();
								if (map[current_y][current_x + 1] == BUFFERED)
								{
									previous_x = current_x;
									previous_y = current_y;
									dir = RIGHT;
									map[current_y][current_x + 1] = 1;
									current_x += 1;
									index++;
								}
								void_bounds();
							}
						}
					}
					else if (current_x >= 9) 
					{
						while (((current_x - 1) > 0) && (map[current_y][current_x - 1] != INVALID_BUILD)
								&& (index < steps)) 
						{
							if ((current_x - 1) > 0) 
							{
								set_bounds();
								if (map[current_y][current_x - 1] == BUFFERED)
								{
									previous_x = current_x;
									previous_y = current_y;
									dir = LEFT;
									map[current_y][current_x - 1] = 1;
									current_x -= 1;
									index++;
								}
								void_bounds();
							}
						}
					}
				}
			}
			else if (dir == LEFT) 
			{
				next_dir = SAME_DIR;
				while (next_dir == SAME_DIR) 
				{
					next_dir = rand.nextInt(CHANGE_DIR) + SAME_DIR;
					set_bounds();
					previous_x = current_x;
					previous_y = current_y;
					if ((next_dir == SAME_DIR) && ((current_x - 1) >= 1) && (map[current_y][current_x - 1] == BUFFERED)) 
					{
						map[current_y][current_x - 1] = 1;
						current_x -=1;
						void_bounds();
					}
				}
				if (next_dir == CHANGE_DIR) 
				{
					if (current_y <= 5) 
					{
						while (((current_y + 1) < (Y_DIM - 1)) && (map[current_y + 1][current_x] != INVALID_BUILD)
								&& (index < steps))
						{
							if ((current_y + 1) < Y_DIM) 
							{
								set_bounds();
								if (map[current_y + 1][current_x] == BUFFERED)
								{
									previous_x = current_x;
									previous_y = current_y;
									dir = DOWN;
									map[current_y + 1][current_x] = 1;
									current_y += 1;
									index++;
								}
								void_bounds();
							}
						}
					}
					else if (current_y >= 6) 
					{
						while (((current_y - 1) > 0) && (map[current_y - 1][current_x] != INVALID_BUILD)
								 && (index < steps))
						{
							if ((current_y - 1) > 0) 
							{
								set_bounds();
								if (map[current_y - 1][current_x] == BUFFERED)
								{
									previous_x = current_x;
									previous_y = current_y;
									dir = UP;
									map[current_y - 1][current_x] = 1;
									current_y -= 1;
									index++;
								}
								void_bounds();
							}
						}
					}
				}
			}
			else if (dir == RIGHT) 
			{
				next_dir = SAME_DIR;
				while (next_dir == SAME_DIR) 
				{
					next_dir = rand.nextInt(CHANGE_DIR) + SAME_DIR;
					set_bounds();
					previous_x = current_x;
					previous_y = current_y;
					if ((next_dir == SAME_DIR) && ((current_x + 1) < (X_DIM - 1)) && (map[current_y][current_x + 1] == BUFFERED)) 
					{
						map[current_y][current_x + 1] = 1;
						current_x +=1;
						void_bounds();
					}
				}
				if (next_dir == CHANGE_DIR) 
				{
					if (current_y <= 5) 
					{
						while (((current_y + 1) < (Y_DIM - 1)) && (map[current_y + 1][current_x] != INVALID_BUILD)
								 && (index < steps))
						{
							if ((current_y + 1) < Y_DIM) 
							{
								set_bounds();
								if (map[current_y + 1][current_x] == BUFFERED)
								{
									previous_x = current_x;
									previous_y = current_y;
									dir = DOWN;
									map[current_y + 1][current_x] = 1;
									current_y += 1;
									index++;
								}
								void_bounds();
							}
						}
					}
					else if (current_y >= 6) 
					{
						while (((current_y - 1) > 0) && (map[current_y - 1][current_x] != INVALID_BUILD)
								 && (index < steps))
						{
							if ((current_y - 1) > 0) 
							{
								set_bounds();
								if (map[current_y - 1][current_x] == BUFFERED)
								{
									previous_x = current_x;
									previous_y = current_y;
									dir = UP;
									map[current_y - 1][current_x] = 1;
									current_y -= 1;
									index++;
								}
								void_bounds();
							}
						}
					}
				}
			}
		}
		
		if ((orisinal_dir == UP) || (orisinal_dir == DOWN)) 
		{
			if (current_x >= 9) 
			{
				while ((current_x + 1) < X_DIM) 
				{
					map[current_y][current_x + 1] = 1;
					current_x += 1;
				}
			}
			else if (current_x <= 8) 
			{
				while ((current_x - 1) >= 0) 
				{
					map[current_y][current_x - 1] = 1;
					current_x -= 1;
				}
			}
		}
		else if ((orisinal_dir == LEFT) || (orisinal_dir == RIGHT)) 
		{
			if (current_y >= 6) 
			{
				while ((current_y + 1) < Y_DIM) 
				{
					map[current_y + 1][current_x] = 1;
					current_y += 1;
				}
			}
			else if (current_y <= 5) 
			{
				while ((current_y - 1) >= 0) 
				{
					map[current_y - 1][current_x] = 1;
					current_y -=1;
				}
			}
		}
		end_edge_coord_x = current_x;
		end_edge_coord_y = current_y;
		
		/**while (reset_count < 20) 
		{
			reset_count = 0;
			in_bounds.clear();
			if ((current_x - 1) >= 0) 
			{
				int[] coords = new int[] {(current_x - 1), current_y};
				in_bounds.add(coords);
			}
			if ((current_x + 1) < X_DIM) 
			{
				int[] coords = new int[] {(current_x + 1), current_y};
				in_bounds.add(coords);
			}
			if ((current_y - 1) >= 0) 
			{
				int[] coords = new int[] {current_x, (current_y - 1)};
				in_bounds.add(coords);
			}
			if ((current_y + 1) < Y_DIM) 
			{
				int[] coords = new int[] {current_x, (current_y + 1)};
				in_bounds.add(coords);
			}
			System.out.println("Bounds looks like: ");
			for (int j = 0; j < in_bounds.size(); j++) 
			{
				System.out.print(Arrays.toString(in_bounds.get(j)) + ", ");
				int ecks = in_bounds.get(j)[0];
				int wai = in_bounds.get(j)[1];
				if (map[wai][ecks] == 0) 
				{
					map[wai][ecks] = BUFFERED;
				}
			}
			System.out.println();
			ahead_tile = INVALID_BUILD;
			int[] chosen = new int[2];
			while (ahead_tile != BUFFERED && reset_count < 20) 
			{
				int look = rand.nextInt(in_bounds.size()) + 1;
				chosen = in_bounds.get(look - 1);
				ahead_tile = map[chosen[1]][chosen[0]];
				if (start_edge == 1 && chosen[1] < current_y) 
				{
					ahead_tile = INVALID_BUILD;
					System.out.println("Reset!");
					reset_count += 1;
				}
				else if (start_edge == 2 && chosen[0] > current_x) 
				{
					ahead_tile = INVALID_BUILD;
					System.out.println("Reset!");
					reset_count += 1;
				}
				else if (start_edge == 3 && chosen[1] > current_y) 
				{
					ahead_tile = INVALID_BUILD;
					System.out.println("Reset!");
					reset_count += 1;
				}
				else if (start_edge == 4 && chosen[0] < current_x) 
				{
					ahead_tile = INVALID_BUILD;
					System.out.println("Reset!");
					reset_count += 1;
				}
			}
			if (reset_count < 20)
			{
			previous_x = current_x;
			previous_y = current_y;
			for (int j = 0; j < in_bounds.size(); j++) 
			{
				int ecks = in_bounds.get(j)[0];
				int wai = in_bounds.get(j)[1];
				if (map[wai][ecks] == BUFFERED) 
				{
					map[wai][ecks] = INVALID_BUILD;
				}
			}
			//System.out.println("Chosen tile: " + Arrays.toString(chosen));
			map[chosen[1]][chosen[0]] = PATH;
			current_x = chosen[0];
			current_y = chosen[1];
			}
		}*/
		
		System.out.println("Map now: ");
		for (int j = 0; j < map.length; j++) 
		{
			System.out.println(Arrays.toString(map[j]));
		}
		System.out.println("---------------------------------------------------");
		System.out.println();
		
		
	}
}
