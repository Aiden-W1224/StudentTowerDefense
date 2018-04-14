import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * 
 * @author PICKLE RICK
 * About Program : This is the main driver of the entire game, and is the class that should be initially run 
 *		by the user.
 */
public class GUI_driver extends Application
{
	private Render render = new Render();
	private Tower_place tower_place = new Tower_place();
	private Level_generator gen = new Level_generator(render);
	private Engine wave_generator = new Engine();
	
	private SceneSelect SCENE = new SceneSelect();
	private Image image = new Image("Testmap.png");
	public static Circle ball;

	/**
	 * About newButton(String text, int posx, int posy):  a method for making a button.
	 * @param text:  Text to include on the button.
	 * @param posx:  Translation in the x dimension relative to the Parent.
	 * @param posy:  Translation in the y dimension relative to the Parent.
	 * @return:  Returns the button.
	 */
	public static Button newButton(String text, int posx, int posy) {
		Button button = new Button();
		button.setText(text);
		button.setTranslateX(posx);
		button.setTranslateY(posy);
		return button;
		}
	
	public static void main(String[] args) 
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception 
	{
	
				// Window in javaFX is called stage
				// everything inside the stage is the scene
				primaryStage.setTitle( "University GPA Defence");
				ImageView map = new ImageView();
		        map.setImage(image);
		        Group root = new Group();
		        Scene scene = new Scene(root, 1200, 800); 
		        //Create main menu button 
		        Button menuButton = newButton("Main Menu", 0, 550);
		        menuButton.setOnAction(e -> primaryStage.setScene(scene));
		        menuButton.setTranslateY(50);
		     
		        
		        
		        //Create start game button and set it to start game 
		        Button startButton = newButton("Start Game", 600, 530);
		        startButton.setOnAction(e -> 
		        {
		        	primaryStage.setScene(SCENE.MapSelect(menuButton));
		        });
		        
		        //Creates canvas which artwork and text go onto 
		        Canvas canvas = new Canvas( 1280, 800 );
		        root.getChildren().add(canvas);
		        root.getChildren().add(startButton); 
		        
		        //Code for Text + Images
		        GraphicsContext gc = canvas.getGraphicsContext2D();
		       
		        Image background = new Image("backgroung.jpg");
		        gc.drawImage(background, 0, 0);
		        
		        gc.setFill(Color.ALICEBLUE);
		        gc.setStroke(Color.BLACK);
		        gc.setLineWidth(1);
		        Font titleFont = Font.font("comic sans", FontWeight.EXTRA_BOLD, 55);
		        gc.setFont(titleFont);
		        gc.fillText("University\n  GPA\n Defence", 500, 200);
		        gc.strokeText("University\n  GPA\n Defence", 500, 200);

		        //Create info button for main menu screen
		        Button infoButton = newButton("Game Info", 600, 580);
		        root.getChildren().add(infoButton);
		        infoButton.setOnAction(e -> 
		        {
		        	
		    		primaryStage.setScene(SCENE.infoScene(menuButton));
		        });
		      
		        primaryStage.setScene(scene);
		        primaryStage.show();
	}
}
