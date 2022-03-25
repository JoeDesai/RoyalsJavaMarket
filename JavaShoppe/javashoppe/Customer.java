package javashoppe;

public class Customer{
	
	private int arrivalTime;
	private int serviceBeginsTime;
	private int serviceTime;
	private int departureTime;
	private int waitTime;
	private int totalTime;
	private int interarrivalTime;
	private char lane;
	private boolean satisfied;
	private String selfFull;

	private int id;
	public static int num=1;
	
	//empty constructor
	public Customer() {
		id = num;
		num++;
  }
	public Customer(int negative) {
		id = negative;
	}
	
	//full constructor
	public Customer(int interT, int servT, String selfOrFull, int perSlower) {
		interarrivalTime = interT;
		selfFull = selfOrFull;
		id = num;
		num++;
		
		if(selfFull.equalsIgnoreCase("self")) {
			double percent = (double) (perSlower + 100) /  100;
			percent = servT * percent;
			serviceTime = (int) percent;
		}else {
			serviceTime = servT;
		}
		
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
		
		return waitTime + arrivalTime;
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

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public char getLane() {
		return lane;
	}


	public void setLane(char lane) {
		this.lane = lane;
	}


	public boolean isSatisfied() {
		if(waitTime < 5) {
			return true;
		}else {
			return false;
		}
	}


	public void setSatisfied(boolean satisfied) {
		this.satisfied = satisfied;
	}


	public String getSelfFull() {
		return selfFull;
	}


	public void setSelfFull(String selfFull) {
		this.selfFull = selfFull;
	}
	
	
	
	
	
}
