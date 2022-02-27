package javashoppe;

import java.util.ArrayList;
import java.util.Scanner;

public class Simulator {

	public static void main(String[] args) {

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

		for (int i = 0; i < customers.size(); i++) {
			System.out.println(customers.get(i).toString());
		}

		while (moreCust) {

			// iterate times? end of main document?

		}

	}

	public static ArrayList<Customer> generateCust(int minInterT, int maxInterT, int minServT, int maxServT,
			int numCust) {

		ArrayList<Customer> customers = new ArrayList<>();

		for (int i = 0; i < numCust; i++) {

			// calculates the times between arrival and service time for each customer
			int arrivalTime = ((int) (Math.random() * (maxInterT-minInterT+1)) + minInterT);
			int serviceTime = ((int) (Math.random() * (maxServT-minServT+1)) + minServT);

			Customer newCust = new Customer(arrivalTime, serviceTime);

			customers.add(newCust);

		}

		return customers;

	}

}
