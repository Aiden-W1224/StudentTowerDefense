import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
		root.setPrefWrapLength(1216);
		VBox vb = new VBox();
		vb.setMinSize(128, 768);
		vb.setAlignment(Pos.TOP_CENTER);
		root.getChildren().add(noot.get_canvas());
		root.getChildren().add(vb);
		Button test_tower = new Button();
		test_tower.setText("Place tower");
		vb.getChildren().add(test_tower);
		Button start_wave = new Button();
		start_wave.setText("Begin level");
		vb.getChildren().add(start_wave);
		pane.getChildren().add(root);
		
		test_tower.setOnAction( e -> 
		{
			noot.set_map(tower_place.place_towers(noot.get_map(), gen));
			noot.update_map();
		});
		
		start_wave.setOnAction( e -> 
		{
			wave_generator.idk(gen, noot.get_map(), pane);
		});
		
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
