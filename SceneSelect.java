import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.FlowPane;
import javafx.geometry.Pos;

public class SceneSelect {
	
	private Image map = new Image("TestMap.png");
	
	/**
	 * Everything here occurs when the "Game Info" button is clicked.
	 * @param menuButton
	 * @return Returns the infoScene
	 */
	public Scene infoScene(Button menuButton) {
		
		StackPane instructions = new StackPane();
		Scene infoScene = new Scene(instructions, 1280, 960);
		
		
		//Text content and features(color, font, etc)
		Text welcomeText = new Text("Welcome to University GPA Defence! Here you will\n"
				+ "find all the information you require to play this game.");
		Text text = new Text(" Objective of the game: The objective of the game is to "
				+ "prevent the enemies (Professors)\n \n"
				+ " from reaching the end of the map and reducing health (GPA). "
				+ "The game is played by setting \n \n"
				+ " down towers (Students) in order to defend the end of the map. "
				+ "The students can only be placed \n \n"
				+ " on the grass, while the enemies make their way through the map "
				+ "on their designated path (Concrete). \n\n"
				+ " The difficulty increases every round with more enemies and stronger "
				+ "enemies coming in waves. \n\n"
				+ "\n"
				+ " Tower Info: There are three towers classes each do a different amount of damage to the enemies\n\n"
				+ "They cost different amounts of tuition. Click on the tower you want and place it strategically to \n\n"
				+ "defend off the enemies."
				+ "\n \n"
				+ " Enemy Info: The enemies will move through the maze, they have two paths to take, you must set up your \n\n"
				+ "towers strategically to compensate for the additional path they can take. Once the enemy reaches the end it will\n\n"
				+ "do damage to your precious GPA and lower it. Once your GPA reaches zero it's game over. ");
	    
		text.setFill(Color.BLACK);
	    text.setStyle("-fx-font: 20 arial;");
	    welcomeText.setFill(Color.PURPLE);
	    welcomeText.setStyle("-fx-font: 30 arial;");
	    System.out.print(javafx.scene.text.Font.getFamilies());
		
	    //moving text into center position
	    text.setTranslateX(-50);
		text.setTranslateY(-100);
		welcomeText.setTranslateX(-10);
		welcomeText.setTranslateY(-400);
		//infoScene.setFill(Color.ALICEBLUE);
		instructions.getChildren().add(text);
		instructions.getChildren().add(welcomeText);
		instructions.getChildren().add(menuButton);
		
		menuButton.setTranslateY(200);
  
		
		return infoScene;
		
	}
	
	public Scene MapSelect(Button menuButton)
	{
		FlowPane flow = new FlowPane();
		flow.setPrefWrapLength(1088);
		BorderPane border = new BorderPane();
		HBox vb = new HBox();
		HBox cb = new HBox();
		cb.setPrefSize(1088, 640);
		vb.setPrefSize(1088, 128);
		flow.getChildren().add(vb);
		flow.getChildren().add(cb);
		Text t = new Text("Choose Map");
		t.setFill(Color.BLACK);
		t.setTranslateX(500);
		t.setStyle("-fx-font: 50 arial;");
		vb.getChildren().add(t);
		vb.getChildren().add(menuButton);
		Scene scene = new Scene(flow, 1200, 768);
		ImageView map1 = new ImageView(map);
		map1.setOnMouseClicked(e ->
		{
			Stage gameStage = new Stage();
			StartGame run_game = new StartGame();
			run_game.map_select(false);
        	try {
				run_game.start(gameStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		cb.getChildren().add(map1);
		cb.setAlignment(Pos.CENTER);
		cb.setSpacing(20);
		
		Button random_map = new Button();
		random_map.setText("Generate random map");
		random_map.setOnAction(e -> 
		{
			Stage gameStage = new Stage();
			StartGame run_game = new StartGame();
			run_game.map_select(true);
			try 
			{
				run_game.start(gameStage);
			} 
			catch (Exception e1) 
			{
				e1.printStackTrace();
			}
		});
		cb.getChildren().add(random_map);
	
		
		
		return scene;
		
	}

}
