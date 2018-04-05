import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class Enemy_animation extends ImageView
{
	private final int INIT_GAME_X = (64*9);
	private final int INIT_GAME_Y = (64*12);
	private int true_game_x;
	private int true_game_y;
	
	private int goal_game_x;
	private int goal_game_y;
	
	private int last_x;
	private int last_y;
	
	public Enemy_animation() 
	{
		//setImage(new Image("res/raoh.png"));
		this.true_game_x = INIT_GAME_X;
		this.true_game_y = INIT_GAME_Y;
	}
	
	public void set_appearance(int array_appearance) 
	{
		switch (array_appearance) 
		{
		case 4:
			setImage(new Image("art.jpg"));
			break;
		case 5:
			setImage(new Image("raoh.png"));
			break;
		}
	}
	
	public void initial_placement(Pane pane) 
	{
		setTranslateY(INIT_GAME_Y);
		setTranslateX(INIT_GAME_X);
		pane.getChildren().add(this);
	}
	
	
	public void remove_enemy_end(Pane pane, Player player, Render render) 
	{
		pane.getChildren().remove(this);
		player.setGPA(player.getGPA() - 1);
		render.update_map(player);
		System.out.println(player.getGPA());
	}
	
	public void remove_enemy_death(Pane pane) {
		pane.getChildren().remove(this);
	}
	
	public void record_death(int array_x, int array_y) 
	{
		this.last_x = 64*array_x;
		this.last_y = 64*array_y;
		System.out.println("Death x is: " + array_x + ", " + last_x);
	}
	
	public void move(Enemy enemy, Level_generator foo, Pane pane, Render render, Player player) 
	{
		this.goal_game_x = enemy.get_x()*64;
		this.goal_game_y = enemy.get_y()*64;
		System.out.println("Current x: " + true_game_x);
		System.out.println("Current y: " + true_game_y);
		System.out.println("Goal x: " + goal_game_x);
		System.out.println("Goal y: " + goal_game_y);
		
		TranslateTransition t = new TranslateTransition(Duration.millis(1000), this);
		t.setFromX(true_game_x);
		t.setFromY(true_game_y);
		t.setToY(goal_game_y);
		t.setToX(goal_game_x);
		t.setDelay(Duration.millis(450*(foo.get_delay())));
		t.setOnFinished(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent event) 
			{
				if (t.getToX() == 64*16) 
				{
					enemy.get_doll().remove_enemy_end(pane, player, render);
					foo.set_delay(-1);
				}
				else if (t.getToX() == last_x && t.getToY() == last_y) {
					enemy.get_doll().remove_enemy_death(pane);
					foo.set_delay(-1);
				}
			}
		});
		t.play();
		
		this.true_game_x = goal_game_x;
		this.true_game_y = goal_game_y;
	}
}
