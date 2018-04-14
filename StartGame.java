import java.net.URISyntaxException;

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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class StartGame extends Application {
	
	private Render render = new Render();
	private Tower_place tower_place = new Tower_place();
	private Level_generator gen = new Level_generator(render);
	private Engine wave_generator = new Engine();
	Enemy_animation foobar = new Enemy_animation(render);
	Player player = new Player();
	private boolean random_map = false;
	private Random_map_generator map_generator = new Random_map_generator();
	
	//Tower towers = new Tower();
	
	public void map_select(boolean random_map) 
	{
		this.random_map = random_map;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	
		Pane pane = new Pane();
		if (random_map == true) 
		{
			int valid_test = map_generator.test_map();
			while (valid_test != 1) 
			{
				this.map_generator = new Random_map_generator();
				valid_test = map_generator.test_map();
			}
			render.set_map(map_generator.get_map(), this.map_generator);
		}
		render.update_map(player);
		FlowPane root = new FlowPane();
		Scene scene = new Scene(pane);
		root.setPrefWrapLength(1288);
		
		ImageView iv = new ImageView(new Image("download.png"));
		iv.setTranslateX(1088);
		pane.getChildren().add(iv);
	
		VBox vb = new VBox();
		vb.setMinSize(128, 768);
		vb.setAlignment(Pos.TOP_CENTER);
		
		root.getChildren().add(render.get_canvas());
		root.getChildren().add(vb);
		Button test_tower = new Button();
		test_tower.setText("Place tower");
		
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
					
					double xDouble =  event.getSceneX()/64;
					double yDouble =  event.getSceneY()/64;
					
					int x = (int) xDouble;
					int y = (int) yDouble;
					
					if (x <= 17) {
						if (render.get_map()[(int)y][(int)x] == 0) 
						{
							render.set_map(tower_place.place_towers(render.get_map(), gen, (int)x, (int)y, 8, render, player));
							render.update_map(player);
						}
					}
					
				}

			
			});
		});
		
		pane.getChildren().add(tower1);
		
		ImageView tower2 = new ImageView(new Image("Grad Student.png"));
		tower2.setTranslateX(1120);
		tower2.setTranslateY(150);
		tower2.setOnMouseClicked( e -> 
		{
			
			scene.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					
					double x =  event.getSceneX()/64;
					double y =  event.getSceneY()/64;
					if (x <= 17) {
						if (render.get_map()[(int)y][(int)x] == 0) {
							render.set_map(tower_place.place_towers(render.get_map(), gen, (int)x, (int)y, 7, render, player));
							render.update_map(player);
							
						}
					}
					
				}

			
			});
		});
		
		pane.getChildren().add(tower2);
		
		start_wave.setOnAction( e -> 
		{
			wave_generator.runGame(gen, render.get_map(), pane, player, render);
		});
		
		Button test = new Button("Test");
		test.setOnAction(e-> {
		});
		MediaPlayer musicplayer;
		Media mp3music = new Media(getClass().getResource("Music.mp3").toURI().toString());
		musicplayer= new MediaPlayer(mp3music);
		musicplayer.setAutoPlay(true);
		musicplayer.setVolume(1.0);
		//Loop song forever
		musicplayer.setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				musicplayer.seek(Duration.ZERO);
			}
		});
		test.setTranslateX(30);
		test.setTranslateY(550);
		//vb.getChildren().add(test);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
