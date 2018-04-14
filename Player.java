/**
 * 
 * @author PICKLE RICK
 *About Program : This class contains the setters and getters for the player this includes
 *				  the GPA(health), Tuition (money)
 *				 
 */
public class Player {
	private double GPA;
	private int tuition;
	
	/**
	 * About Player():  a constructor used at the beginning of a game. The player's GPA is set to 4.0 and
	 * 		the player is given 5000 units of tuition.
	 */
	public Player() {
		this.GPA = 4.0;
		this.tuition = 5000;
	}
	
	/**
	 * 
	 * @return returns GPA
	 */
	public double getGPA() {
		return GPA;
	}
	
	public void setGPA(double GPA) {
		this.GPA = GPA;
	}
	/**
	 * 
	 * @return returns the tuition
	 */
	public int getTuition() {
		return tuition;
	}
	
	public void setTuition(int tuition) {
		this.tuition = tuition;
	}
	
	/**
	 * 
	 * @return true if you got tuition, false if you don't tuition
	 */
	public boolean seeBankrupt() {
		return tuition != 0;
	}
	/**
	 * subtracts tuition for the price of student
	 */
	public void buyAStudent() {
		if(seeBankrupt()) {
			tuition = tuition - 5000;
			
		}
		
	}
	/**
	 * 
	 * @return true if you lose, false if you still alive
	 */
	public boolean loseGame() {
		return GPA <= 0;
	}
	
}
