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
		int time = 0;

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

		while (moreCust) {h
			Customer e = customers.get(time);
			
			Integer queue1Size = queue1.size();
			Integer queue2Size = queue2.size();
			Integer queue3Size = queue3.size();
			int value1 = 0;
			int value2 = 0;
			int value3 = 0;

			value1 = queue1Size.compareTo(queue2Size);
			if (value1 <= 0) {
				value2 = queue1Size.compareTo(queue3Size);
			}
			if (value2 > 0) {
				queue3.add(e);

			} else if (value2 <= 0) {
				queue1.add(e);

			}

			else if (value1 > 0) {
				value3 = queue2Size.compareTo(queue3Size);
			}
			if (value3 > 0) {
				queue3.add(e);

			} else if (value3 <= 0) {
				queue2.add(e);

			}
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

		}

		return customers;

	}

}
