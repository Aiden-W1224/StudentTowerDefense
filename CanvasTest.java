import java.util.Scanner;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CanvasTest extends Application {
	
	
	public static Scanner sc = new Scanner(System.in);
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
	static Image grass = new Image("grass.png");
	Image path = new Image("dirt.png");
	
	final static int PATH = 1;
	final static int GRASS = 0;
	final static int TOWER = 2;
	final static int ENEMY = 3;

	
	public void start(Stage primaryStage) {
		primaryStage.setTitle("TEST");
		
		Group root = new Group();
		Scene testScene = new Scene(root);
		VBox vb = new VBox();
		//vb.backgroundProperty();
		primaryStage.setScene(testScene);
		Canvas test = new Canvas(1088, 768);
		root.getChildren().add(test);
		root.getChildren().add(vb);
		GraphicsContext gc = test.getGraphicsContext2D();
		createMap(gc, map);
		
		Button start = new Button("Place Tower");
		start.setOnAction(e-> {
			textUpdate(gc);
		
		}
		);
		root.getChildren().add(start);
		
		Button delete = new Button("Delete Tower");
		delete.setOnAction(e-> {
			textDelete(gc);
		});
		delete.setTranslateX(500);
		root.getChildren().add(delete);
		
		primaryStage.show();
	}
	
	
	public static void main(String[] args) {

		launch(args);
	}
	
	public static int[][] textGame() {
		boolean endGame = true;
		double life = 4.0;
		
		while (life>0.0) {
			printMap();
			System.out.println();
			System.out.println("Place two towers");
			System.out.print("y coord: ");
			int y = sc.nextInt() - 1;
			System.out.print("x coord: ");
			int x = sc.nextInt() - 1;
			System.out.print("Choose tile grass (0) or path (1) or tower(2): ");
			int choice = sc.nextInt();
			map[y][x] = choice;
			life -= 2;
			
			
		}
		return map;
	}


	public static void printMap() {
		for (int i = 0; i<map.length; i++) {
			System.out.println();
			for (int j = 0; j<map[i].length; j++) {
				System.out.print(map[i][j] + " ");
			}
		}
	}
	
	public static void updateMap(int[][] map) {
		int game = 2;
		while (game != 0) {
			textGame();
			game --;
		}
	}
	
	public void createMap(GraphicsContext gc, int[][] map) {
		
		
		for (int i = 0; i<map.length; i++) {
			for (int j = 0; j<map[i].length;j++) {
				if (map[i][j] == GRASS) {
					gc.drawImage(grass, j*64, i*64);
				}
					
				else if (map[i][j] == PATH) {
					gc.drawImage(path, j*64, i*64);
				}	
				
				/**else if (map[i][j] == ENEMY) {
					gc.drawImage(new Image("raoh.png"), j*64, i*64);
				}*/
			}
		
		}
	}
	
	public static void textUpdate(GraphicsContext gc) {
		
			System.out.println();
			System.out.println("Place a tower");
			System.out.print("x coord: ");
			int x = sc.nextInt() - 1;
			System.out.print("y coord: ");
			int y = sc.nextInt() - 1;
			System.out.print("Choose tower(2): ");
			int choice = sc.nextInt(); 
			if (map[y][x] == PATH ) {
				System.out.println("There is a path there!");
				textUpdate(gc);
			}
			else {
				map[y][x] = choice;
				placeTower(x, y, gc, map);
			}
			//map[y][x] = choice;
			//placeTower(x, y, gc, map);
		
	}

	public static void placeTower(int x, int y, GraphicsContext gc, int[][] map) {
		printMap();
		if (map[y][x] == TOWER) {
			gc.drawImage(new Image("raoh.png"), x*64, y*64);

	}
}
	public static void deleteTower(int x, int y, GraphicsContext gc, int[][] map) {
		printMap();
		if (map[y][x] == GRASS) {
			//gc.clearRect(x*64, y*64, 64, 64);
			gc.drawImage(grass, x*64, y*64);
			
		}
	}
	
	public static void textDelete(GraphicsContext gc) {
		System.out.println();
		System.out.println("Choose tower to delete");
		System.out.println("x coord: ");
		int x = sc.nextInt() - 1;
		System.out.println("y coord: ");
		int y = sc.nextInt() - 1;
		if (map[y][x] == TOWER) {
			map[y][x] = 0;
			deleteTower(x, y, gc, map);
		}
		else {
			System.out.println("There is no tower there!");
			textDelete(gc);
		}
	}
}