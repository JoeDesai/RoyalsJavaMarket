package javashoppe;

public class Registers {
	public static int id = 65;
	private char regLetter;
	private Customer cust = null;
	private int isFull = 0;
	private String selfFull;
	private int timeEmpty;
	
//empty constructor	
public Registers() {
	regLetter = (char)id;
	id++;
}

//contructor with String field
public Registers(String selfOrFull) {
	selfFull = selfOrFull;
	regLetter = (char)id;
	id++;
}

//getters and setters
public void addCust(Customer customers) {
 cust = customers;
}

//public static void removeCust(Customer customers) {
//	cust = null;
//}

public void removeCust() {
	cust = null;
}

public static int getId() {
	return id;
}

public static void setId(int id) {
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

public int getIsFull() {
	if(cust == null) {
		return 0;
	}else {
		return 1;

	}
}

public void setIsFull(int isFull) {
	this.isFull = isFull;
}



}
