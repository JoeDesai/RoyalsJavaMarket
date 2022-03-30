package javashoppe;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Simulator {

	public static void main(String[] args) {
		boolean moreCust = true;
		int time = 1;
		int cat = 0;
		int custNum = 0;
		char letter = 0;
		// these counters are used to see if the customer is the first in line or not
		int selfCheckCounter = 0;
		int fullCheckCounter = 0;
		int lastDeparture = 0;
		Queues queue1 = new Queues();
		Queues queue2 = new Queues();
		Queues queue3 = new Queues();
		QueuesSelfCheckout queue4 = new QueuesSelfCheckout();
		ArrayList<Customer> customers = loadData();
		ArrayList<Queues> queues = new ArrayList<>();
		Customer e = new Customer();
		ArrayList<String> data = new ArrayList<String>();

		queues.add(queue1);
		queues.add(queue2);
		queues.add(queue3);
		Queues shortest = queues.get(0);
		ArrayList<Integer> notInUse = new ArrayList<>();

		// placeCustomers(customers, e, queues, data, shortest, notInUse, queue4);

		printData(data, customers, notInUse.size());

	}

	public static ArrayList<Customer> loadData() {

		Scanner scan = new Scanner(System.in);
		ArrayList<Customer> customers = new ArrayList<>();

		System.out.println("Number of full-service lanes:");
		int numFull = scan.nextInt();

		System.out.println("Number of self-service lanes");
		int numSelf = scan.nextInt();

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

		System.out.println("Percent slower");
		int perSlower = scan.nextInt();

		CustomerCreator letThereBeCustomers = new CustomerCreator(minInterT, maxInterT, minServT, maxServT, numCust + 1,
				customers, perSlower);

		return letThereBeCustomers.createCustomers();

	}

	public static void printData(ArrayList<String> data, ArrayList<Customer> c, int notUsedTime) {
		DecimalFormat df = new DecimalFormat("##.##");
		double totalWaitFull = 0;
		double totalWaitSelf = 0;
		double averageWaitSelf = 0;
		double averageWaitFull = 0;
		int numFull = 0;
		double waitTime = 0;
		int satisfied = 0;
		int dissatisfied = 0;
		c.remove(c.size() - 1);

		System.out.println(
				"\n    Cus #    | Arrival Time (absolute) |Service Time | LOC | Self/Full | Departure Time (absolute) | Notes");
		for (String d : data) {
			// System.out.println(d);
		}

		for (Customer cust : c) {
			if (cust.getSelfFull().equalsIgnoreCase("full")) {
				totalWaitFull += cust.getWaitTime();
				numFull++;
			} else {
				totalWaitSelf += cust.getWaitTime();
			}

			if (cust.isSatisfied()) {
				satisfied++;
			} else {
				dissatisfied++;
			}

			String note = "";
			// System.out.println(c.getSelfFull());
			if (cust.getWaitTime() == 0) {
				note = "Open Lane...Immediate Service";
			} else {
				if (cust.getSelfFull().equalsIgnoreCase("full")) {
					note = "All busy... Goes into " + cust.getLane() + " @ " + cust.getArrivalTime()
							+ "; Service Begins @ " + cust.getServiceBeginsTime() + "; Leaves @ "
							+ cust.getDepartureTime() + "; Wait: " + cust.getWaitTime();
				} else {
					note = "All busy... Goes into Self Checkout Line @ " + cust.getArrivalTime() + "; Service Begins @ "
							+ cust.getServiceBeginsTime() + "; Leaves @ " + cust.getDepartureTime() + "; Wait: "
							+ cust.getWaitTime();
				}

			}

			String l1 = "    " + cust.getId() + "     | " + cust.getArrivalTime() + "             		  |    "
					+ cust.getServiceTime() + "    |  " + cust.getLane() + "  |    " + cust.getSelfFull() + "  |    "
					+ cust.getDepartureTime() + "              |    " + note;

			System.out.println(l1);

		}

		averageWaitFull = (double) totalWaitFull / numFull;
		averageWaitSelf = (double) totalWaitSelf / (c.size() - numFull);
		waitTime = (double) (totalWaitSelf + totalWaitFull) / (double) c.size();

		System.out.println("Average Wait Time: " + df.format(waitTime) + "\nSelf Checkout Average Wait Time: "
				+ df.format(averageWaitSelf) + "\nFull Service Average Wait Time: " + df.format(averageWaitFull)
				+ "\nTotal time checkouts were not in use: " + notUsedTime + "\nCustomer Satisfaction: " + satisfied
				+ " satisfied (<5 min) " + dissatisfied + " dissatisfied (>=5 min)");

	}

	public static int feed(ArrayList<Customer> custs, ArrayList<Queues> queues, int time, int numCust) {

		// manually assigns first customer and sets it's arrival time
		if (numCust == 0) {
			// if first customer is full service
			if (time == custs.get(numCust).getArrivalTime() && custs.get(numCust).getSelfFull().equals("full")) {
				// sets arrival time
				custs.get(numCust).setArrivalTime(time);
				// set wait time and add to correct queue
				custs.get(numCust).setWaitTime(0);
				queues.get(0).add(custs.get(numCust));

			}
			// if first customer is self service
			if (time == custs.get(numCust).getArrivalTime() && custs.get(numCust).getSelfFull().equals("self")) {
				// sets arrival time
				custs.get(numCust).setArrivalTime(time);
				// adds self service customers to the only queue for self serve
				queues.get(queues.size() - 1).add(custs.get(numCust));
			}
			numCust++;
		}
		// sets full service customers to correct queue

		else if (time == custs.get(numCust).getInterarrivalTime() + custs.get(numCust - 1).getArrivalTime()
				&& custs.get(numCust).getSelfFull().equals("full")) {
			// sets arrival time
			custs.get(numCust).setArrivalTime(time);

			Queues shortest = queues.get(0);

			// find the smallest queue
			for (int j = queues.size() - 2; j >= 0; j--) {

				if (queues.get(j).size() <= shortest.size()) {
					shortest = queues.get(j);
					// adds customer to correct queue when queue is empty
					if (queues.get(j).size() == 0) {
						custs.get(numCust).setWaitTime(0);
						queues.get(j).add(custs.get(numCust));
						// sets customer to correct queue and sets its wait time
					} else {
						int numInQueue = queues.get(j).size() - 1;
						custs.get(numCust).setWaitTime(queues.get(j).get(numInQueue).getDepartureTime() - time);
						queues.get(j).add(custs.get(numCust));
					}

				}

			}

			numCust++;
		}

		// adds self service customers to the only queue for self serve
		else if (time == custs.get(numCust).getInterarrivalTime() + custs.get(numCust - 1).getArrivalTime()
				&& custs.get(numCust).getSelfFull().equals("self")) {

			// sets to last queue in arraylist
			queues.get(queues.size() - 1).add(custs.get(numCust));

			// sets arrival time
			custs.get(numCust).setArrivalTime(time);

			numCust++;
		}

		return numCust;
	}

}
