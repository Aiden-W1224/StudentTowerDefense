import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;
import javafx.util.Duration;
//import static helpers.Clock.*;
/**
* @author:  Athena Bolyos, Jacob Hazen, Daniel Orduz, and Aiden Wong.
* About: Creates projectile which fires at enemy towers contains drawing function to animate projectiles 
*/
public class Projectile extends ImageView {
	
	private double speed;
	private int damage;
	private double x, y;
	//private int y;
	private ImageView test = new ImageView(new Image("kenshiro.jpeg"));
	private Circle projectile = new Circle(3, Color.BLACK);
	
	public Projectile(double x, double y, double speed, int damage, Pane pane) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.damage = damage;
		//draw(pane);
	}
	
	/**
	* Sets projectile x,y at towers x and y 
	*/
	public Projectile(double x, double y) {
		this.x = x;
		this.y = y;
		// TODO Auto-generated constructor stub
	}

	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public void update(Pane pane) {
		x += 0.1*speed;
		//draw(pane);
	}
	
	/**
	* About: draws projectile starting at location of tower and moves towards Enemy target 
	* @param target : Type Enemy, gets target x and y and fires projectile towards there 
	* @param pane : Type Pane, adds and removes projectile from map  
	*/
	public void draw(Pane pane, Enemy target) {
		
		TranslateTransition T = new TranslateTransition();
		Circle circ = new Circle(5, Color.BLACK);
		Path path = new Path();
		path.getElements().add(new MoveTo(x+32.0, y+32.0));
		path.getElements().add(new LineTo(target.get_doll().getLayoutX()+32.0, target.get_doll().getLayoutY()+32.0));
		PathTransition p1 = new PathTransition();
		p1.setNode(circ);
		p1.setPath(path);
		p1.setDuration(Duration.seconds(0.1));
		p1.setAutoReverse(false);
		p1.setCycleCount(1);
		p1.play();
		p1.setOnFinished(e-> {
			pane.getChildren().remove(circ);
		});
		
		pane.getChildren().add(circ);
		
		
		if (projectile.getLayoutX() == target.get_doll().getLayoutX()+32.0 && projectile.getLayoutY() == target.get_doll().getLayoutY()+32.0) {
			pane.getChildren().remove(this.getProjectile());
		}
		
	}
	
	/**
	*About : Centers projectile x and y to be in the middle of the tower. Returns centered projectile  
	*/
	public Circle getProjectile() {
		this.projectile.setTranslateX(x+32.0);
		this.projectile.setTranslateY(y+32.0);
		return this.projectile;
	}
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	

}
