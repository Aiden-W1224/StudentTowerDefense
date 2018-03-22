import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CanvasTest_02
{
	private int[][] map = {
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
	private Level_generator foo = new Level_generator();
	private Canvas sloop = new Canvas(1088, 768);
	private GraphicsContext gc = sloop.getGraphicsContext2D();
	private final int GRASS = 0;
	private final int PATH = 1;
	private final int TA = 5;
	private final int RESEARCHER = 4;
	private final int FIRST_YEAR = 7;
	private final int SECOND_YEAR = 8;
	private final Image GRASS_IMAGE = new Image("res/grass.png");
	private final Image PATH_IMAGE = new Image("res/dirt.png");
	private final Image TA_IMAGE = new Image("res/raoh.png");
	private final Image RESEARCHER_IMAGE = new Image("res/woody.jpg");
	private final Image FIRST_YEAR_IMAGE = new Image("res/kenshiro.jpeg");
	private final Image SECOND_YEAR_IMAGE = new Image("res/shrek.png");
	
	public int[][] get_map()
	{
		return this.map;
	}
	
	public void set_map(int[][] map) 
	{
		this.map = map;
	}
	
	public Canvas get_canvas() 
	{
		return this.sloop;
	}
	
	public void update_map() 
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
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	
}
