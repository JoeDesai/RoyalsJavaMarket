package javashoppe;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Simulator {

	public static void main(String[] args) {

		LinkedList<Customer> queue1 = new LinkedList<Customer>();
		LinkedList<Customer> queue2 = new LinkedList<Customer>();
		LinkedList<Customer> queue3 = new LinkedList<Customer>();
		Scanner scan = new Scanner(System.in);

		boolean moreCust = true;
		int time = 1;
		int cat = 0;
		int custNum = 0;

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

		ArrayList<Customer> customers = generateCust(minInterT, maxInterT, minServT, maxServT, numCust);
		ArrayList<LinkedList<Customer>> queues = new ArrayList<>();
		queues.add(queue1);
		queues.add(queue2);
		queues.add(queue3);

		LinkedList<Customer> shortest = queues.get(0);
		Customer e = new Customer();
		char letter = 0;
		System.out.println(
				"\n	Cus #	| Arrival Time (absolute) |Service Time | LOC | Departure Time (absolute) | Notes");

		while (moreCust) {

			// first customer starts at 1 minute always
			if (time == customers.get(custNum).getInterarrivalTime() && custNum == 0) {
				e = customers.get(custNum);
				e.setArrivalTime(time);
				queues.get(0).add(e); // add first customer to first linkedlist queue (queue1)
				cat = time;
				letter = 'A';
				log(e, cat, letter, (cat + e.getServiceTime()));
				custNum++;
			}

			if (cat + customers.get(custNum).getInterarrivalTime() == time) {
				e = customers.get(custNum);
				shortest = queues.get(0);
				letter = (char) (65);
				for (int j = 0; j < queues.size(); j++) {
					if (queues.get(j).size() < shortest.size()) {
						shortest = queues.get(j);
						letter = (char) (65 + j);
					}
				}
				e.setArrivalTime(time);
				shortest.add(e);
				cat = time;
				log(e, cat, letter, (cat + e.getServiceTime()));
				custNum++;

				if (custNum == customers.size()) {
					moreCust = false;
				}

			}

			time++;
		}

	}

	public static ArrayList<Customer> generateCust(int minInterT, int maxInterT, int minServT, int maxServT,
			int numCust) {

		ArrayList<Customer> customers = new ArrayList<>();

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

	public static void log(Customer c, int arrivalTime, char lane, int departTime) {
		
		String l1 = "	" + c.getId() + " 	| " + arrivalTime + "	 		  |	" + c.getServiceTime() + "	|  "
				+ lane + "  |	" + departTime + "			  |	";
		
		System.out.println(l1);
	}

}
