package javashoppe;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Simulator {

	public static void main(String[] args) {

		LinkedList<Customer> queue1 = new LinkedList<Customer>();
		LinkedList<Customer> queue2 = new LinkedList<Customer>();
		LinkedList<Customer> queue3 = new LinkedList<Customer>();
		ArrayList<Customer> customers = loadData();
		ArrayList<LinkedList<Customer>> queues = new ArrayList<>();
		Customer e = new Customer();
		ArrayList<String> data = new ArrayList<String>();

		queues.add(queue1);
		queues.add(queue2);
		queues.add(queue3);
		LinkedList<Customer> shortest = queues.get(0);
		
		boolean moreCust = true;
		int time = 1;
		int cat = 0;
		int custNum = 0;
		char letter = 0;
		int notInUse = 0;

		while (moreCust) {

			// first customer starts at 1 minute always
			if (time == customers.get(custNum).getInterarrivalTime() && custNum == 0) {
				cat = time;
				e = customers.get(custNum);
				e.setArrivalTime(time);
				e.setDepartureTime(cat + e.getServiceTime());
				queues.get(0).add(e); // add first customer to first linkedlist queue (queue1)
				letter = 'A';
				e.setWaitTime(0);
				e.setLane((char) (65));
				data.add(log(e));
				custNum++;
			}

			if (cat + customers.get(custNum).getInterarrivalTime() == time) {
				e = customers.get(custNum);
				shortest = queues.get(2);
				letter = (char) (65);
				for (int j = queues.size() - 1; j >= 0; j--) {
					if (queues.get(j).size() <= shortest.size()) {
						shortest = queues.get(j);
						e.setLane((char) (65 + j));

						if (queues.get(j).size() == 0) {
							e.setWaitTime(0);

						} else {
							int numInQueue = queues.get(j).size() - 1;
							e.setWaitTime(queues.get(j).get(numInQueue).getDepartureTime() - time);
							
						}
					}

				}
				cat = time;
				e.setArrivalTime(time);
				e.setDepartureTime(cat + e.getServiceTime() + e.getWaitTime());
				shortest.add(e);
				data.add(log(e));
				custNum++;

				if (custNum == customers.size()) {
					moreCust = false;
				}

			}

			for (Customer c : customers) {
				if (time == c.getDepartureTime()) {
					switch (c.getLane()) {
					case 'A':
						queues.get(0).removeFirst();
						break;
					case 'B':
						queues.get(1).removeFirst();
						break;
					case 'C':
						queues.get(2).removeFirst();
						break;
					}
				}
			}
			for (int k =  0; k < queues.size(); k++) {
				if (queues.get(k).size() == 0) {
					notInUse++;
				}
			}
			time++;

		}
		printData(data, customers, notInUse);

	}

	public static ArrayList<Customer> loadData() {

		Scanner scan = new Scanner(System.in);
		ArrayList<Customer> customers = new ArrayList<>();

		System.out.println("Enter minimum interarrival time:  ");
		int minInterT = scan.nextInt();

		System.out.println("Enter maximum interarrival time:  ");
		int maxInterT = scan.nextInt();

		System.out.println("Enter minimum service time:  ");
		int minServT = scan.nextInt();

		System.out.println("Enter maximum service time:  ");
		int maxServT = scan.nextInt();

		System.out.println("Enter the number of customers?");
		int numCust = scan.nextInt();

		for (int i = 0; i < numCust; i++) {

			// calculates the times between arrival and service time for each customer
			int arrivalTime = ((int) (Math.random() * (maxInterT - minInterT + 1)) + minInterT);
			int serviceTime = ((int) (Math.random() * (maxServT - minServT + 1)) + minServT);

			Customer newCust = new Customer(arrivalTime, serviceTime);

			customers.add(newCust);
			System.out.println(newCust.toString());

		}

		return customers;

	}

	public static String log(Customer c) {
		String note = "";

		if (c.getWaitTime() == 0) {
			note = "Open Lane...Immediate Service";
		} else {
			note = "All busy... Goes into " + c.getLane() + " @ " + c.getArrivalTime() + "; Service Begins @ "
					+ c.getServiceBeginsTime() + "; Leaves @ " + c.getDepartureTime() + "; Wait: " + c.getWaitTime();
		}

		String l1 = "	" + c.getId() + " 	| " + c.getArrivalTime() + "	 		  |	" + c.getServiceTime() + "	|  "
				+ c.getLane() + "  |	" + c.getDepartureTime() + "			  |	" + note;

		return l1;
	}

	public static void printData(ArrayList<String> data, ArrayList<Customer> c, int notUsedTime) {
		double totalWait = 0;
		int satisfied = 0;
		int dissatisfied = 0;
		System.out.println(
				"\n	Cus #	| Arrival Time (absolute) |Service Time | LOC | Departure Time (absolute) | Notes");
		for (String d : data) {
			System.out.println(d);
		}

		for (Customer cust : c) {
			totalWait += cust.getWaitTime();
			if(cust.isSatisfied()) {
				satisfied++;
			}else {
				dissatisfied++;
			}
			
		}
		totalWait = (double) totalWait/ c.size();
		System.out.println("Average Wait Time: " +  totalWait + "\nTotal time checkouts were not in use: " +notUsedTime
				+ "\nCustomer Satisfaction: " +satisfied+ " satisfied (<5 min) " +dissatisfied+ " dissatisfied (>=5 min)");

	}

}
