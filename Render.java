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
 *	  different Integers in the array list.
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
	private final Image FIRST_YEAR_IMAGE = new Image("Grad Student.png");
	private final Image SECOND_YEAR_IMAGE = new Image("Student.png");
	
	
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
	
	/**
	 * About set_map(int[][] map, Random_map_generator generator):  this constructor is used in the case that
	 * 		the player decided to play the game on a randomly generated map. An appropriate area is chosen
	 * 		to display the player's GPA and tuition, and starting/ending coordinates are set apprpriately.
	 * @param map:  the text-based version of the map to be used for the game.
	 * @param generator:  the map generator that has information about the text-based map.
	 */
	public void set_map(int[][] map, Random_map_generator generator) 
	{
		this.start_edge_coord_x = generator.get_start_x();
		this.start_edge_coord_y = generator.get_start_y();
		this.end_edge_coord_x = generator.get_end_x();
		this.end_edge_coord_y = generator.get_end_y();
		this.random_map = true;
		this.map = map;
		if ((map[1][2] == 0) && (map[2][2] == 0)) 
		{
			map[1][2] = SCORE_AREA;
			map[2][2] = TUITION_AREA;
		}
		else if ((map[1][14] == 0) && (map[2][14] == 0)) 
		{
			map[1][14] = SCORE_AREA;
			map[2][14] = TUITION_AREA;
		}
		else if ((map[10][2] == 0) && (map[11][2] == 0)) 
		{
			map[10][2] = SCORE_AREA;
			map[11][2] = TUITION_AREA;
		}
		else if ((map[10][14] == 0) && (map[11][14] == 0)) 
		{
			map[10][14] = SCORE_AREA;
			map[11][14] = TUITION_AREA;
		}
	}
	
	/**
	 * About set_map(int[][] map):  this constructor is used for updates to the map including tower building.
	 * 		This method is used for both the default game map and any randomly-generated map.
	 * @param map:  the text-based version of the map used for the game.
	 */
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
					break;
					
				case PATH:
					this.gc.drawImage(PATH_IMAGE, j*64, i*64);
					break;
					
				case CROSS_ROADS:
					this.gc.drawImage(PATH_IMAGE, j*64, i*64);
					break;
					
				case TA:
					this.gc.drawImage(TA_IMAGE, j*64, i*64);
					break;
					
				case RESEARCHER:
					this.gc.drawImage(RESEARCHER_IMAGE, j*64, i*64);
					break;
					
				case FIRST_YEAR:
					this.gc.drawImage(FIRST_YEAR_IMAGE, j*64, i*64);
					break;
					
				case SECOND_YEAR:
					this.gc.drawImage(SECOND_YEAR_IMAGE, j*64, i*64);
					break;
					
				case SCORE_AREA:
					this.gc.drawImage(GRASS_IMAGE, j*64, i*64);
					Font titleFont = Font.font("arial", FontWeight.EXTRA_BOLD, 25);
			        this.gc.setFont(titleFont);
					String text = "GPA: " + player.getGPA();
					this.gc.fillText(text, j*64, i*64);
					this.gc.strokeText(text, j*64, i*64);
					break;
				case TUITION_AREA:
					this.gc.drawImage(GRASS_IMAGE, j*64, i*64);
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
