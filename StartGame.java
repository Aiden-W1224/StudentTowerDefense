import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartGame extends Application {
	
	private CanvasTest_02 noot = new CanvasTest_02();
	private Tower_place tower_place = new Tower_place();
	private Level_generator gen = new Level_generator();
	private Test_map_03 wave_generator = new Test_map_03();
	Enemy_animation foobar = new Enemy_animation();

	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane pane = new Pane();
		noot.update_map();
		FlowPane root = new FlowPane();
		Scene scene = new Scene(pane);
		root.setPrefWrapLength(1288);
		
		ImageView iv = new ImageView(new Image("download.png"));
		iv.setTranslateX(1088);
		pane.getChildren().add(iv);
		//GraphicsContext gc = noot.get_canvas().getGraphicsContext2D();
		//gc.drawImage(new Image("download.png"), 1088, 0);
		
		VBox vb = new VBox();
		vb.setMinSize(128, 768);
		vb.setAlignment(Pos.TOP_CENTER);
		//root.getChildren().add(gc);
		root.getChildren().add(noot.get_canvas());
		root.getChildren().add(vb);
		Button test_tower = new Button();
		test_tower.setText("Place tower");
		//vb.getChildren().add(test_tower);
		Button start_wave = new Button();
		start_wave.setText("Begin level");
		start_wave.setTranslateY(500);
		start_wave.setTranslateX(30);
		vb.getChildren().add(start_wave);
		pane.getChildren().add(root);
		
		ImageView tower1 = new ImageView(new Image("Student.png"));
		tower1.setTranslateX(1200);
		tower1.setTranslateY(150);
		tower1.setOnMouseClicked( e -> 
		{
			
			scene.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					for (TileType T : noot.getTiles()) {
						double x =  event.getSceneX()/64;
						double y =  event.getSceneY()/64;
						if (((int)x == T.getXcoord() && (int)y == T.getYcoord()) && (T.getTileType() == "grass")) {
							System.out.println("grass???");
							noot.set_map(tower_place.place_towers(noot.get_map(), gen, T.getXcoord(), T.getYcoord(), 8));
							noot.update_map();
						}
					}
					
				}

			
			});
		});
		
		pane.getChildren().add(tower1);
		
		ImageView tower2 = new ImageView(new Image("art.png"));
		tower2.setTranslateX(1120);
		tower2.setTranslateY(150);
		tower2.setOnMouseClicked( e -> 
		{
			
			scene.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					for (TileType T : noot.getTiles()) {
						double x =  event.getSceneX()/64;
						double y =  event.getSceneY()/64;
						if (((int)x == T.getXcoord() && (int)y == T.getYcoord()) && (T.getTileType() == "grass")) {
							System.out.println("grass???");
							noot.set_map(tower_place.place_towers(noot.get_map(), gen, T.getXcoord(), T.getYcoord(), 7));
							noot.update_map();
						}
					}
					
				}

			
			});
		});
		
		pane.getChildren().add(tower2);
		
		start_wave.setOnAction( e -> 
		{
			wave_generator.idk(gen, noot.get_map(), pane);
		});
		
		//Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
