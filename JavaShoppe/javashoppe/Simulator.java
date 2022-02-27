package javashoppe;

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
		
		generateCust(minInterT, maxInterT, minServT, maxServT, numCust);
		
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
				queue3.add(Customer e);
			}
			else if (value2 <= 0) {
				queue1.add(Customer e);
			}
		
		else if (value1 > 0) {
		value3 = queue2Size.compareTo(queue3Size);
		}
			if (value3 > 0) {
				queue3.add(Customer e);
				}
			else if (value 3 <= 0) {
				queue2.add(Customer e);
			}
			
			
			
			
		while(moreCust) {
			
			//iterate times? end of main document?
			
			
			
			
		}
		
		
		
		
		
		
	}

	public static void generateCust (int minInterT, int maxInterT, int minServT, int maxServT, int numCust) {
		
		for(int i = 0; i < numCust; i ++) {
			
			//customer creator??
			
			
		}
		
		
		
		
	}
	
	
}
