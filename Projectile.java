import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;
import javafx.util.Duration;

public class Projectile extends ImageView {
	
	private int speed;
	private int damage;
	private int x;
	private int y;
	private ImageView projectile = new ImageView(new Image("pencil.png"));
	
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public void move(Tower T, Render MAP) {
		
		//if (T.detect(MAP.get_map()) == true) {
			Path path = new Path();
			path.getElements().add(new MoveTo(T.get_x()*64, T.get_y()*64));
			path.getElements().add(new VLineTo(0));
			PathTransition p1 = new PathTransition();
			p1.setNode(projectile);
			p1.setPath(path);
			p1.setDuration(Duration.seconds(10));
			p1.setAutoReverse(false);
			p1.setCycleCount(1);
			p1.play();
		//}
//		Path path = new Path();
//		path.getElements().add(new MoveTo(64*9.5,64*12));
//		path.getElements().add(new VLineTo(354));
//		path.getElements().add(new HLineTo(416));
//		path.getElements().add(new VLineTo(482));
//		path.getElements().add(new HLineTo(160));
//		path.getElements().add(new VLineTo(98));
//		path.getElements().add(new HLineTo(736));
//		path.getElements().add(new VLineTo(290));
//		path.getElements().add(new HLineTo(864));
//		path.getElements().add(new VLineTo(418));
//		path.getElements().add(new HLineTo(1056+64));
		
//		PathTransition p1 = new PathTransition();
//		p1.setNode(projectile);
//		p1.setPath(path);
//		p1.setDuration(Duration.seconds(5));
//		p1.setAutoReverse(false);
//		p1.setCycleCount(1);
//		p1.play();
		
	}
	public ImageView getI() {
		return this.projectile;
	}
	public void setX(int x) {
		this.x = x;
	}
//	public int getY() {
//		return y;
//	}
	public void setY(int y) {
		this.y = y;
	}
	

}
