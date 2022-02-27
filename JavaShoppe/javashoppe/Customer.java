package javashoppe;

public class Customer {
	
	private int arrivalTime;
	private int serviceBeginsTime;
	private int serviceTime;
	private int departureTime;
	private int waitTime;
	private int totalTime;
	private int interarrivalTime;
	private int id;
	public static int num=1;
	
	//empty constructor
	public Customer() {
		id = num;
		num++;
	}
	
	//full constructor
	public Customer(int interT, int servT) {
		serviceTime = servT;
		interarrivalTime = interT;
		id = num;
		num++;
		
	}

	//regular to string
	public String toString() {
		return "Customer number: " + id + ", Interarrival Time: " + interarrivalTime +", Service Time: " + serviceTime;
	}
	
	//to string for writing to file
	public String toStringF() {
		return "";
	}
	
	
	//getters and setters
	
	
	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getServiceBeginsTime() {
		return serviceBeginsTime;
	}

	public void setServiceBeginsTime(int serviceBeginsTime) {
		this.serviceBeginsTime = serviceBeginsTime;
	}

	public int getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(int serviceTime) {
		this.serviceTime = serviceTime;
	}

	public int getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(int departureTime) {
		this.departureTime = departureTime;
	}

	public int getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}

	public int getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}

	public int getInterarrivalTime() {
		return interarrivalTime;
	}

	public void setInterarrivalTime(int interarrivalTime) {
		this.interarrivalTime = interarrivalTime;
	}
	
	
	
	
	
	
	
}
