package javashoppe;

import java.util.ArrayList;

public class CustomerCreator {

	private int minInter;
	private int maxInter;
	private int minServ;
	private int maxServ;
	private int numCust;
	private int percentSlower;
	private ArrayList<Customer> customers;

	public CustomerCreator() {

	}

	public CustomerCreator(int minInterT, int maxInterT, int minServT, int maxServT, int numCustomers,
			ArrayList<Customer> cust, int perSlower) {
		minInter = minInterT;
		maxInter = maxInterT;
		minServ = minServT;
		maxServ = maxServT;
		numCust = numCustomers;
		customers = cust;
		percentSlower = perSlower;
		

	}

	public ArrayList<Customer> createCustomers() {
		String selfFull = "";
		int selfCounter = 0;
		int fullCounter = 0;
		int sumInterTime = 0;
		
    
		
		for (int i = 0; i < numCust; i++) {
			
			double tmp = Math.random();
			//double tmp = 1;
	        if (tmp <= .5) {
	            //full service queue
	        	selfFull = "full";
	        }
	        else if (tmp > .5) {
	            //self service queue
	        	selfFull = "self";
	        }
	        
			// calculates the times between arrival and service time for each customer
			int arrivalTime = ((int) (Math.random() * (maxInter - minInter + 1)) + minInter);
			int serviceTime = ((int) (Math.random() * (maxServ - minServ + 1)) + minServ);

			Customer newCust = new Customer(arrivalTime, serviceTime, selfFull, percentSlower);
			sumInterTime = sumInterTime + newCust.getInterarrivalTime();
			if ((fullCounter == 0) && (selfFull.equalsIgnoreCase("full"))) {
				newCust.setArrivalTime(sumInterTime);
				newCust.setWaitTime(0);
				newCust.setDepartureTime(newCust.getServiceTime()+ newCust.getArrivalTime());
				fullCounter++;
			}
			else if ((selfCounter == 0) && (selfFull.equalsIgnoreCase("self"))) {
				newCust.setArrivalTime(sumInterTime);
				newCust.setWaitTime(0);
				newCust.setDepartureTime(newCust.getServiceTime()+ newCust.getArrivalTime());
				selfCounter++;
			}
			newCust.setArrivalTime(sumInterTime);
			
			customers.add(newCust);
			System.out.println(newCust.toString());
			
		}

		return customers;

	}

}
