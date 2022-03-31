package javashoppe;

public class Registers {
	public static int id = 65;
	private char regLetter;
	private Customer cust = null;
	private boolean isFull = false;
	private String selfFull;
	private int timeEmpty;

//empty constructor	
	public Registers() {
		regLetter = (char) id;
		id++;
	}

//contructor with String field
	public Registers(String selfOrFull) {
		selfFull = selfOrFull;
		regLetter = (char) id;
		id++;

	}

//getters and setters
	public void addCust(Customer customers) {
		cust = customers;
	}

	public void removeCust() {
		cust = null;
	}

	public String toString() {
		return selfFull+ " checkout... Letter: " +regLetter;
		
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		Registers.id = id;
	}

	public char getRegLetter() {
		return regLetter;
	}

	public void setRegLetter(char regLetter) {
		this.regLetter = regLetter;
	}

	public Customer getCust() {
		return cust;
	}

	public void setCust(Customer cust) {
		this.cust = cust;
	}

	public boolean isFull() {
		return isFull;
	}

	public void setFull(boolean isFull) {
		this.isFull = isFull;
	}

	public String getSelfFull() {
		return selfFull;
	}

	public void setSelfFull(String selfFull) {
		this.selfFull = selfFull;
	}

	public int getTimeEmpty() {
		return timeEmpty;
	}

	public void setTimeEmpty(int timeEmpty) {
		this.timeEmpty = timeEmpty;
	}

}
