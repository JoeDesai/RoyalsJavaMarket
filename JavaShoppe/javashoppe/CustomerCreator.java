package javashoppe;

import java.util.ArrayList;

public class CustomerCreator {

	private int minInter;
	private int maxInter;
	private int minServ;
	private int maxServ;
	private int numCust;
	private ArrayList<Customer> customers;

	public CustomerCreator() {

	}

	public CustomerCreator(int minInterT, int maxInterT, int minServT, int maxServT, int numCustomers,
			ArrayList<Customer> cust) {
		minInter = minInterT;
		maxInter = maxInterT;
		minServ = minServT;
		maxServ = maxServT;
		numCust = numCustomers;
		customers = cust;
		

	}

	public ArrayList<Customer> createCustomers() {
		String selfFull = "";
		
		int tmp = (int) ( Math.random() * 2 + 1);
        if (tmp == 1) {
            //full service queue
        	selfFull = "full";
        }
        else if (tmp == 2) {
            //self service queue
        	selfFull = "self";
        }
    
		
		for (int i = 0; i < numCust; i++) {

			// calculates the times between arrival and service time for each customer
			int arrivalTime = ((int) (Math.random() * (maxInter - minInter + 1)) + minInter);
			int serviceTime = ((int) (Math.random() * (maxServ - minServ + 1)) + minServ);

			Customer newCust = new Customer(arrivalTime, serviceTime, selfFull);

			customers.add(newCust);
			System.out.println(newCust.toString());

		}

		return customers;

	}

}
