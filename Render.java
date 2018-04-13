import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
/**
 * 
 *@author PICKLE RICK
 *About Program : This class contains the 2D array path, and also updates the GUI according to the
 		  different Integers in the array list.
 *				  
 *
 */
public class Render
{
	/*
	 * map = [row][col]
	 * 
	 * 0 = grass, places where towers can be placed
	 * 1 = path of the enemy
	 * 13 = tells the enemy to go right everytime
	 * 6 = Score
	 * 22 = Tuition(Money to buy towers)
	 */
	private int[][] map = {
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 1, 1, 1, 1, 13, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0},
			{0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
			{0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1},
			{0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0},
			{0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 6, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 22, 0, 0},
			};
	
	private Random_map_generator map_generator;
	private int start_edge_coord_x = 9;
	private int start_edge_coord_y = 11;
	private int end_edge_coord_x = 16;
	private int end_edge_coord_y = 3;
	private boolean random_map = false;
	private Canvas canvas = new Canvas(1088, 768);
	private GraphicsContext gc = canvas.getGraphicsContext2D();
	private final int GRASS = 0;
	private final int PATH = 1;
	private final int TA = 5;
	private final int RESEARCHER = 4;
	private final int FIRST_YEAR = 7;
	private final int SECOND_YEAR = 8;
	private final int SCORE_AREA = 6;
	private final int TUITION_AREA = 22;
	private final int CROSS_ROADS = 13;
	private final Image GRASS_IMAGE = new Image("grass.png");
	private final Image PATH_IMAGE = new Image("dirt.png");
	private final Image TA_IMAGE = new Image("raoh.png");
	private final Image RESEARCHER_IMAGE = new Image("art.png");
	private final Image FIRST_YEAR_IMAGE = new Image("art.png");
	private final Image SECOND_YEAR_IMAGE = new Image("Student.png");
	private ArrayList<TileType> tiles = new ArrayList<TileType>();
	//public Object type = null;
	
	public ArrayList<TileType> getTiles() { return this.tiles; }
	
	public boolean rand() 
	{
		return this.random_map;
	}
	
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
	
	public int[][] get_map()
	{
		return this.map;
	}
	
	public void set_map(int[][] map, Random_map_generator generator) 
	{
		this.start_edge_coord_x = generator.get_start_x();
		this.start_edge_coord_y = generator.get_start_y();
		this.end_edge_coord_x = generator.get_end_x();
		this.end_edge_coord_y = generator.get_end_y();
		this.random_map = true;
		this.map = map;
	}
	
	public void set_map(int[][] map) 
	{
		this.map = map;
	}
	
	public Canvas get_canvas() 
	{
		return this.canvas;
	}
	
	/**
	 * This method iterates through the [row][col] of the map,
	 * and changes the GUI of the map according to the integers in the
	 * 2d array
	 * @param player
	 */
	public void update_map(Player player) 
	{
		for (int i = 0; i < this.map.length; i++) 
		{
			for (int j = 0; j < this.map[i].length;j++) 
			{
				switch (map[i][j]) 
				{
				case GRASS:
					this.gc.drawImage(GRASS_IMAGE, j*64, i*64);
					tiles.add(new TileType("grass", j, i));
					break;
					
				case PATH:
					this.gc.drawImage(PATH_IMAGE, j*64, i*64);
					tiles.add(new TileType("path", j, i));
					break;
					
				case CROSS_ROADS:
					this.gc.drawImage(PATH_IMAGE, j*64, i*64);
					tiles.add(new TileType("path", j, i));
					break;
					
				case TA:
					this.gc.drawImage(TA_IMAGE, j*64, i*64);
					tiles.add(new TileType("TA", j, i));
					break;
					
				case RESEARCHER:
					this.gc.drawImage(RESEARCHER_IMAGE, j*64, i*64);
					tiles.add(new TileType("RESEARCHER"));
					break;
					
				case FIRST_YEAR:
					this.gc.drawImage(FIRST_YEAR_IMAGE, j*64, i*64);
					break;
					
				case SECOND_YEAR:
					this.gc.drawImage(SECOND_YEAR_IMAGE, j*64, i*64);
					break;
					
				case SCORE_AREA:
					this.gc.drawImage(GRASS_IMAGE, j*64, i*64);
					tiles.add(new TileType("grass", j, i));
					Font titleFont = Font.font("arial", FontWeight.EXTRA_BOLD, 25);
			        this.gc.setFont(titleFont);
					String text = "GPA: " + player.getGPA();
					this.gc.fillText(text, j*64, i*64);
					this.gc.strokeText(text, j*64, i*64);
					break;
				case TUITION_AREA:
					this.gc.drawImage(GRASS_IMAGE, j*64, i*64);
					tiles.add(new TileType("grass", j, i));
					Font title = Font.font("arial", FontWeight.EXTRA_BOLD, 25);
			        this.gc.setFont(title);
					String Tuition = "Tuition: " + (player.getTuition());
					this.gc.fillText(Tuition, j*64, i*64);
					this.gc.strokeText(Tuition, j*64, i*64);
					break;
					
				}
			}
		}
		
		
	}
	
	
	
	
	
	
	
	
	
}
