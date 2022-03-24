package javashoppe;

import java.util.ArrayList;
import java.util.Scanner;

public class Simulator {

	public static void main(String[] args) {

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

		placeCustomers(customers, e, queues, data, shortest, notInUse, queue4);

		printData(data, customers, notInUse.size());

	}

	public static void placeCustomers(ArrayList<Customer> customers, Customer e, ArrayList<Queues> queues,
			ArrayList<String> data, Queues shortest, ArrayList<Integer> notInUse, QueuesSelfCheckout queue4) {

		boolean moreCust = true;
		int time = 1;
		int cat = 0;
		int custNum = 0;
		char letter = 0;
		// these counters are used to see if the customer is the first in line or not
		int selfCheckCounter = 0;
		int fullCheckCounter = 0;

		while (moreCust) {

			// System.out.println("customer" +customers.get(custNum).getId()+ " lane"
			// +customers.get(custNum).getSelfFull());// for debugging purposes

			if (cat + customers.get(custNum).getInterarrivalTime() == time
					&& customers.get(custNum).getSelfFull().equalsIgnoreCase("full")) {
				// first customer starts at 1 minute always
				if (fullCheckCounter == 0) {
					cat = time;
					e = customers.get(custNum);
					e.setArrivalTime(time);
					e.setDepartureTime(cat + e.getServiceTime());
					queues.get(0).add(e); // add first customer to first linkedlist queue (queue1)
					letter = 'A';
					e.setWaitTime(0);
					e.setLane((char) (65));
					fullCheckCounter++;
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
					custNum++;

				}
			} else {

				// System.out.println("in else statement");

				if (selfCheckCounter == 0 && cat + customers.get(custNum).getInterarrivalTime() == time) {
					e = customers.get(custNum);

					customers.get(custNum).setLane('D');
					customers.get(custNum).setWaitTime(0);
					customers.get(custNum).setArrivalTime(time);
					customers.get(custNum).setDepartureTime(time + customers.get(custNum).getServiceTime());

					e.setLane('D');
					e.setWaitTime(0);
					e.setArrivalTime(time);
					e.setDepartureTime(time + e.getServiceTime());
					queue4.add(e);
					// data.add(log(e));
					cat = time;
					selfCheckCounter++;
					custNum++;
				} else if (selfCheckCounter == 1 && cat + customers.get(custNum).getInterarrivalTime() == time) {
					e = customers.get(custNum);

					customers.get(custNum).setLane('E');
					customers.get(custNum).setWaitTime(0);
					customers.get(custNum).setArrivalTime(time);
					customers.get(custNum).setDepartureTime(time + customers.get(custNum).getServiceTime());

					e.setLane('E');
					e.setWaitTime(0);
					e.setArrivalTime(time);
					e.setDepartureTime(time + e.getServiceTime());
					queue4.add(e);
					// data.add(log(e));
					cat = time;
					selfCheckCounter++;
					custNum++;
				}

				if (cat + customers.get(custNum).getInterarrivalTime() == time && selfCheckCounter > 1) {

					// System.out.println("test in if statement");
					// System.out.println(customers.get(custNum).getSelfFull());
					e = customers.get(custNum);
					// System.out.print(e.getSelfFull());

					cat = time;
					queue4.add(e);
					// increments self check counter
					selfCheckCounter++;

					if (queue4.size() == 0 || queue4.size() == 1) {
						e.setWaitTime(0);
					} else {
						int numInQueue = queue4.size() - 1;
						e.setWaitTime(queue4.get(numInQueue - 1).getDepartureTime() - time);
					}

					cat = time;
					e.setArrivalTime(time);
					e.setDepartureTime(cat + e.getServiceTime() + e.getWaitTime());

					customers.get(custNum).setArrivalTime(time);
					customers.get(custNum).setDepartureTime(
							cat + customers.get(custNum).getServiceTime() + customers.get(custNum).getWaitTime());

					custNum++;

				}
			}
			if (queue4.size() > 0) {
				queue4.get(0).setLane('D');
			}
			if (queue4.size() > 1) {
				queue4.get(1).setLane('E');
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
					case 'D':
						if (queue4.size() > 2) {
							queue4.removeFirst();
						} else if (queue4.size() == 2) {
							queue4.remove(0);
							queue4.add(0, new Customer());
						} else {
							queue4.remove(0);
						}
						break;
					case 'E':
						if (queue4.size() > 2) {
							queue4.removeSecond();
						} else {
							queue4.remove(1);
						}
						break;
					}
				}
			}
			for (int k = 0; k < queues.size(); k++) {
				if (queues.get(k).size() == 0) {
					notInUse.add(1);
					// adds one eliment to the array list equivalent to adding one minute to the
					// time registers are not in use
				}
			}

			time++;
			if (custNum == customers.size()) {
				moreCust = false;
			}

		}

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

		System.out.println("Percent slower");
		double perSlower = scan.nextDouble();

		CustomerCreator letThereBeCustomers = new CustomerCreator(minInterT, maxInterT, minServT, maxServT, numCust,
				customers);

		return letThereBeCustomers.createCustomers();

	}

//	public static String log(Customer c) {
//		String note = "";
//		// System.out.println(c.getSelfFull());
//		if (c.getWaitTime() == 0) {
//			note = "Open Lane...Immediate Service";
//		} else {
//			note = "All busy... Goes into " + c.getLane() + " @ " + c.getArrivalTime() + "; Service Begins @ "
//					+ c.getServiceBeginsTime() + "; Leaves @ " + c.getDepartureTime() + "; Wait: " + c.getWaitTime();
//		}
//
//		String l1 = "    " + c.getId() + "     | " + c.getArrivalTime() + "             		  |    "
//				+ c.getServiceTime() + "    |  " + c.getLane() + "  |    " + c.getSelfFull() + "  |    "
//				+ c.getDepartureTime() + "              |    " + note;
//
//		return l1;
//	}

	public static void printData(ArrayList<String> data, ArrayList<Customer> c, int notUsedTime) {
		double totalWait = 0;
		int satisfied = 0;
		int dissatisfied = 0;
		System.out.println(
				"\n    Cus #    | Arrival Time (absolute) |Service Time | LOC | Self/Full | Departure Time (absolute) | Notes");
		for (String d : data) {
			// System.out.println(d);
		}

		for (Customer cust : c) {
			totalWait += cust.getWaitTime();
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
				note = "All busy... Goes into " + cust.getLane() + " @ " + cust.getArrivalTime() + "; Service Begins @ "
						+ cust.getServiceBeginsTime() + "; Leaves @ " + cust.getDepartureTime() + "; Wait: "
						+ cust.getWaitTime();
			}

			String l1 = "    " + cust.getId() + "     | " + cust.getArrivalTime() + "             		  |    "
					+ cust.getServiceTime() + "    |  " + cust.getLane() + "  |    " + cust.getSelfFull() + "  |    "
					+ cust.getDepartureTime() + "              |    " + note;

			System.out.println(l1);
		}
		totalWait = (double) totalWait / c.size();
		System.out.println("Average Wait Time: " + totalWait + "\nTotal time checkouts were not in use: " + notUsedTime
				+ "\nCustomer Satisfaction: " + satisfied + " satisfied (<5 min) " + dissatisfied
				+ " dissatisfied (>=5 min)");

	}

}
