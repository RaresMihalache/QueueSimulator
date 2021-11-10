

public class Task implements Comparable<Task> {
	private int id;
	private int arrivalTime;
	private int processingTime;
	private int currentProcessingTime;
	private int waitingTime;
	
	public Task(int id) {
		this.id = id;
	}
	
	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
	public void setProcessingTime(int processingTime) {
		this.processingTime = processingTime;
	}
	
	public void setCurrentProcessingTime(int currentProcessingTime) {
		this.currentProcessingTime = currentProcessingTime;
	}
	
	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getArrivalTime() {
		return this.arrivalTime;
	}
	
	public int getProcessingTime() {
		return this.processingTime;
	}
	
	public int getCurrentProcessingTime() {
		return this.currentProcessingTime;
	}
	
	public int getWaitingTime() {
		return this.waitingTime;
	}
	
	public int compareTo(Task comparedTask) {
		return this.getArrivalTime() - comparedTask.getArrivalTime();
	}
}
