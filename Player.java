
public class Player {
	private double GPA;
	private int tuition;
	
	

	public Player() {
		this.GPA = 4.0;
		this.tuition = 15000;
	}
	/**
	 * 
	 * @return returns GPA
	 */
	public double getGPA() {
		return GPA;
	}
	/**
	 * 
	 * @return returns the tuition
	 */
	public int getTuition() {
		return tuition;
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
