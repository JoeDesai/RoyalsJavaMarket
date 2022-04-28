package javashoppe;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Simulator {

	private int numSelf;
	private int numFull;
	private int minInterT;
	private int maxInterT;
	private int minServT;
	private int maxServT;
	private int numCust;
	private int perSlower;
	private ArrayList<String> data;
	
	//empty constructor 

	public Simulator() {
		
	};

	//full constructor 
	public Simulator(int ns, int nf, int mnit, int mxit, int mnst, int mxst, int nc, int ps) {
		numSelf = ns;
		numFull = nf;
		minInterT = mnit;
		maxInterT = mxit;
		minServT = mnst;
		maxServT = mxst;
		numCust = nc;
		perSlower = ps;
		
		
		// public static void main(String[] args) {
		boolean moreCust = true;
		int time = 0;
		
		int exitWhileLoop = 0;
		ArrayList<Queues> queues = new ArrayList<>();
		ArrayList<Registers> registers = new ArrayList<>();
		ArrayList<Customer> customers = loadData(queues, registers, numSelf, numFull, minInterT, maxInterT, minServT, maxServT, numCust, perSlower);
		Customer e = new Customer();

		int numSelfNonField = 0;
		for (int i = 0; i < registers.size(); i++) {
			if (registers.get(i).getSelfFull().equals("self"))
				numSelfNonField++;
		}
		
		int numCustNoneField = 0;
		while (moreCust) {

			if (numCustNoneField < customers.size()) {
				// feed customer into queue and increments numCust
				numCustNoneField = feed(customers, queues, time, numCustNoneField, numSelfNonField);
			}

			// check if the customer at the register is ready to leave (departureTime ==
			// time), if they are then register.get().removeCustomer();
			for (int i = 0; i < registers.size() - 1; i++) {
				if (registers.get(i).getCust() != null) {
					if (registers.get(i).getCust().getDepartureTime() == time) {
						registers.get(i).removeCust();
						System.out.println("Removes customer from register");
					}
				}
			}

			// Check every register if it is empty,
			// if a register is empty (null), add next customer that is in queue to register
			for (int i = 0; i < registers.size(); i++) {
				if (registers.get(i).getSelfFull().equals("full")) {
					if (registers.get(i).getCust() == null) { // checks if register is empty
						if (queues.get(i).size() > 0) { // checks if queue is empty

							System.out.println("TRIPLE IF STATEMENT");

							registers.get(i).addCust(queues.get(i).get(0)); // add customer to register
							registers.get(i).getCust().setLane(registers.get(i).getRegLetter()); // setting customers
																									// register letter
							System.out.println(queues.get(i).get(0).toString());
							queues.get(i).removeFirst(); // remove customer from queue
							// System.out.println("REMOVES CUSTOMER FROM
							// QUEUE\n"+queues.get(i).get(0).toString());

						}
					}

				} else { // if self checkout register
					if (registers.get(i).getCust() == null) { // checks if self service register is empty
						if (queues.get(queues.size() - 1).size() > 0) { // checks if last queue (self service queue) is
																		// empty
							registers.get(i).addCust(queues.get(queues.size() - 1).get(0)); // add customer to register
							customers.get(registers.get(i).getCust().getId() - 1)
									.setLane(registers.get(i).getRegLetter()); // setting customers register letter
							queues.get(queues.size() - 1).removeFirst(); // remove customer from queue
						}
					}
				}
			}

			time++;

			exitWhileLoop = 0;

			// loops through the registers and counts how many are empty. If all of them are
			// empty its time for the loop to end
			int emptyCount = 0;
			for (int i = 0; i < registers.size(); i++) {
				if (registers.get(i).getCust() == null) {
					emptyCount++;
				} else if (registers.get(i).getCust().getDepartureTime() <= time) {
					registers.get(i).removeCust();
				}
			}

			// sees if it is time for loop to exit. If not it prints what's going on at each
			// register
			// debugging - System.out.println(emptyCount + " == " + registers.size() + " &&
			// " + numCust + " == " + customers.size());
			System.out.println("Time: " + time);
			if (time == 0) {
				System.out.println("Start");
			}

			// prints what is going on at each register
			for (int i = 0; i < registers.size(); i++) {

				if (registers.get(i).getCust() == null) {
					exitWhileLoop++;
					System.out.println("Register " + registers.get(i).getRegLetter() + " ("
							+ registers.get(i).getSelfFull() + ")" + ": free");
				} else {
					System.out.println(
							"Register " + registers.get(i).getRegLetter() + " (" + registers.get(i).getSelfFull() + ")"
									+ ": Customer " + registers.get(i).getCust().getId());
				}
			}
			// exits while loop when all registers are empty and all the customers have gone
			// through the system
			if (emptyCount == registers.size() && numCustNoneField == customers.size()) {
				moreCust = false;
				System.out.println("End of simulation");
			}

			// System.out.println(numCust);
		}
		System.out.println("EXIT WHILE LOOP");
		// uses time minus 1 because it the simulation runs one extra minute after the
		// last customer leaves
		data = printData(registers, customers, time - 1);

	}

	public static ArrayList<Customer> loadData(ArrayList<Queues> queues, ArrayList<Registers> registers,int ns, int nf, int mnit, int mxit, int mnst, int mxst, int nc, int ps ) {

		Scanner scan = new Scanner(System.in);
		ArrayList<Customer> customers = new ArrayList<>();

		CustomerCreator letThereBeCustomers = new CustomerCreator(mnit, mxit, mnst, mxst, nc,
				customers, ps);
		// RegisterCreator regCreate = new RegisterCreator(numSelf, numFull);
		// regArray = new ArrayList<Registers>(regCreate.createRegisters());

		// creating registers
		for (int i = 0; i < nf; i++) { // Full checkouts
			Registers temp = new Registers("full");
			registers.add(temp);
			System.out.println(temp.toString());
		}

		for (int j = 0; j < ns; j++) { // self checkouts
			Registers temp = new Registers("self");
			registers.add(temp);
			System.out.println(temp.toString());
		}

		// creating queues
		if (ns > 0) {
			for (int i = 0; i < nf + 1; i++) {
				queues.add(new Queues());
			}
		} else { // if there are no self checkouts
			for (int i = 0; i < nf; i++) {
				queues.add(new Queues());
			}
		}

		return letThereBeCustomers.createCustomers();

	}

	public static ArrayList<String> printData(ArrayList<Registers> r, ArrayList<Customer> c, int time) {
		ArrayList<String> data = new ArrayList<>();
		
		DecimalFormat df = new DecimalFormat("##.##");
		double totalWaitFull = 0;
		double totalWaitSelf = 0;
		double averageWaitSelf = 0;
		double averageWaitFull = 0;
		int numFull = 0;
		double waitTime = 0;
		int satisfied = 0;
		int dissatisfied = 0;

		ArrayList<Registers> regSelf = new ArrayList<>();
		ArrayList<Registers> regFull = new ArrayList<>();

		int totalFullServiceTime = 0;
		int totalSelfServiceTime = 0;

		// increments service time for both kinds of registers
		for (int i = 0; i < c.size(); i++) {
			if (c.get(i).getSelfFull().equals("self")) {
				totalSelfServiceTime = totalSelfServiceTime + c.get(i).getServiceTime();
			} else {
				totalFullServiceTime = totalFullServiceTime + c.get(i).getServiceTime();
			}
		}

		// splits registers into two arraylists
		for (int i = 0; i < r.size(); i++) {
			if (r.get(i).getSelfFull().equals("self")) {
				regSelf.add(r.get(i));
			} else {
				regFull.add(r.get(i));
			}
		}
		// prints debigging data for time not in use
		System.out.println("Time the last customer leaves: " + time);
		System.out.println(
				"Self service time: " + totalSelfServiceTime + "||  Number of self service lanes: " + regSelf.size());
		System.out.println(
				"Full service time: " + totalFullServiceTime + "||  Number of full service lanes: " + regFull.size());

		int selfTimeNotInUse = time * regSelf.size() - totalSelfServiceTime;
		int fullTimeNotInUse = time * regFull.size() - totalFullServiceTime;
		int totalTime = selfTimeNotInUse + fullTimeNotInUse;

		System.out.println(
				"\n Cus #    | Arrival Time (absolute)       | Service Time | LOC | Self/Full| Departure Time (absolute)| Notes");
		
		data.add("\n Cus #    | Arrival Time (absolute)       | Service Time | LOC | Self/Full| Departure Time (absolute)| Notes");
		
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

			String l1 = "    " + cust.getId() + "     | " + cust.getArrivalTime() + "             		  |      "
					+ cust.getServiceTime() + "      |  " + cust.getLane() + "  |    " + cust.getSelfFull() + "  |    "
					+ cust.getDepartureTime() + "                    |    " + note;

			System.out.println(l1);
			data.add(l1);

		}

		averageWaitFull = (double) totalWaitFull / numFull;
		averageWaitSelf = (double) totalWaitSelf / (c.size() - numFull);
		waitTime = (double) (totalWaitSelf + totalWaitFull) / (double) c.size();

		String summary = "Average Wait Time: " + df.format(waitTime) + "\nSelf Checkout Average Wait Time: "
				+ df.format(averageWaitSelf) + "\nFull Service Average Wait Time: " + df.format(averageWaitFull)
				+ "\nTotal time self checkouts were not in use: " + selfTimeNotInUse
				+ "\nTotal time full checkouts were not in use: " + fullTimeNotInUse
				+ "\nTotal time checkouts were not in use: " + totalTime + "\nCustomer Satisfaction: " + satisfied
				+ " satisfied (<5 min) " + dissatisfied + " dissatisfied (>=5 min)";
		
		System.out.println(summary);
		data.add(summary);

	
		return data;
	}

	public static int feed(ArrayList<Customer> custs, ArrayList<Queues> queues, int time, int numCust, int numSelf) {

		if ((time == custs.get(numCust).getArrivalTime()) && custs.get(numCust).getSelfFull().equals("full")) {
			System.out.println("setting full service customers to correct queue");
			// sets arrival time
			custs.get(numCust).setArrivalTime(time);

			Queues shortest = queues.get(0);

			// find the smallest queue
			for (int j = queues.size() - numSelf; j >= 0; j--) {

				if (queues.get(j).size() <= shortest.size()) {
					shortest = queues.get(j);
				}

			}
			// adds customer to correct queue when queue is empty
			if (shortest.size() == 0) {
				// sets customer to correct queue and sets its wait time
				custs.get(numCust).setWaitTime(0);
				custs.get(numCust).setDepartureTime(
						time + custs.get(numCust).getServiceTime() + custs.get(numCust).getWaitTime());
				shortest.add(custs.get(numCust));

			} else {
				int numInQueue = shortest.size() - 1;
				if (shortest.size() == 0) {
					custs.get(numCust).setWaitTime(0);
				} else {
					custs.get(numCust).setWaitTime(shortest.get(numInQueue).getDepartureTime() - time);
				}
				custs.get(numCust).setDepartureTime(
						time + custs.get(numCust).getServiceTime() + custs.get(numCust).getWaitTime());
				shortest.add(custs.get(numCust));
			}

			numCust++;
		}

		// adds self service customers to the only queue for self serve
		else if (time == custs.get(numCust).getArrivalTime() && custs.get(numCust).getSelfFull().equals("self")) {
			System.out.println("setting self service customers to only queue");

			int numInQueue = queues.get(queues.size() - 1).size() - 1;
			// sets arrival time
			custs.get(numCust).setArrivalTime(time);
			if (queues.get(queues.size() - 1).size() == 0) {
				custs.get(numCust).setWaitTime(0);
			} else {
				custs.get(numCust).setWaitTime(queues.get(queues.size() - 1).get(numInQueue).getDepartureTime() - time);

			}
			custs.get(numCust)
					.setDepartureTime(time + custs.get(numCust).getServiceTime() + custs.get(numCust).getWaitTime());
			// sets to last queue in arraylist
			queues.get(queues.size() - 1).add(custs.get(numCust));

			numCust++;
		}

		return numCust;
	}
	
	//getters and setters
	public int getNumSelf() {
		return numSelf;
	}

	public void setNumSelf(int numSelf) {
		this.numSelf = numSelf;
	}

	public int getNumFull() {
		return numFull;
	}

	public void setNumFull(int numFull) {
		this.numFull = numFull;
	}

	public int getMinInterT() {
		return minInterT;
	}

	public void setMinInterT(int minInterT) {
		this.minInterT = minInterT;
	}

	public int getMaxInterT() {
		return maxInterT;
	}

	public void setMaxInterT(int maxInterT) {
		this.maxInterT = maxInterT;
	}

	public int getMinServT() {
		return minServT;
	}

	public void setMinServT(int minServT) {
		this.minServT = minServT;
	}

	public int getMaxServT() {
		return maxServT;
	}

	public void setMaxServT(int maxServT) {
		this.maxServT = maxServT;
	}

	public int getNumCust() {
		return numCust;
	}

	public void setNumCust(int numCust) {
		this.numCust = numCust;
	}

	public int getPerSlower() {
		return perSlower;
	}

	public void setPerSlower(int perSlower) {
		this.perSlower = perSlower;
	}

	public ArrayList<String> getData() {
		return data;
	}

	public void setData(ArrayList<String> data) {
		this.data = data;
	}
}
