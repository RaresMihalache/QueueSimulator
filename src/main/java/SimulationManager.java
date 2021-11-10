import java.util.ArrayList;
import javax.swing.SwingUtilities;
import java.util.Collections;
import java.util.List;

public class SimulationManager implements Runnable {

	private int timeLimit;
	private int minProcessingTime;
	private int maxProcessingTime;
	private int minArrivalTime;
	private int maxArrivalTime;
	private int numberOfServers;
	private int numberOfClients;
	
	private float avgWaitingTime;
	private float avgProcessingTime;
	private int peakTime; // time when there are the most clients
	private int maxClients = 0; // used for peakTime
	private boolean done;
	
	private Scheduler scheduler;
	private List<Task> generatedTasks;
	private List<Server> serversFromScheduler;
	
	private Thread serverThreads[];
	private Thread simulationThread;
	
	private String outputString;
	
	public SimulationManager(View view) {
		this.timeLimit = view.getTimeLimit();
		this.minProcessingTime = view.getMinProcessing();
		this.maxProcessingTime = view.getMaxProcessing();
		this.minArrivalTime = view.getMinArrival();
		this.maxArrivalTime = view.getMaxArrival();
		this.numberOfServers = view.getNoServers();
		this.numberOfClients = view.getNoClients();
		
		this.generatedTasks = new ArrayList<Task>(numberOfClients);
		scheduler = new Scheduler(numberOfServers, view.getMaxClientsPerServer());
		if(view.getSimulationStrategy() == 1) { // shortest time strategy
			scheduler.changeStrategy(SelectionPolicy.SHORTEST_TIME);
		}
		else if(view.getSimulationStrategy() == 2) { // shortest queue strategy
			scheduler.changeStrategy(SelectionPolicy.SHORTEST_QUEUE);
		}
		
		this.setServers(scheduler.getServers());
		serverThreads = new Thread[numberOfServers];
		
		for(int i=0; i<numberOfServers; i++) {
			serverThreads[i] = new Thread(serversFromScheduler.get(i));
			serverThreads[i].start();
		}
		this.generateNRandomTasks();
		simulationThread = new Thread(this);
		simulationThread.start();
		
	}
	
	public void setServers(List<Server> servers) {
		this.serversFromScheduler = servers;
	}
	
	public Scheduler getScheduler() {
		return this.scheduler;
	}
	
	public List<Server> getServersFromScheduler(){
		return this.serversFromScheduler;
	}
	
	public String getOutput() {
		return this.outputString;
	}
	
	private void generateNRandomTasks() {
		for(int i=0; i < numberOfClients; i++) {
			Task newTask = new Task(i+1);
			int randomArrivalTime = (int)Math.floor(Math.random()*(maxArrivalTime - minArrivalTime + 1) + minArrivalTime);
			int randomProcessingTime = (int)Math.floor(Math.random() * (maxProcessingTime - minProcessingTime + 1) + minProcessingTime);
			newTask.setArrivalTime(randomArrivalTime);
			newTask.setProcessingTime(randomProcessingTime);
			newTask.setCurrentProcessingTime(randomProcessingTime);
			generatedTasks.add(i, newTask);
		}
		Collections.sort(generatedTasks);
	}
	
	public boolean zeroClientsWaitingServers() {
		boolean zeroClients = true;
		for(int i=0; i<this.getServersFromScheduler().size(); i++) {
			int serverQueueSize = this.getServersFromScheduler().get(i).getNoOfTasks();
			zeroClients = serverQueueSize > 0 ? false : true;
			if(zeroClients == false)
				break;
		}
		return zeroClients;
	}
	
	public boolean zeroClientsWaitingQueue() {
		int coreQueueSize = this.generatedTasks.size();
		boolean zeroClients = coreQueueSize > 0 ? false : true;
		return zeroClients;
	}
	
	public String waitingClientsCoreQueue() {
		String txt = "";
		int newLine = 1;
		for(int i=0; i< this.generatedTasks.size(); i++) {
			txt = txt +  "(" + this.generatedTasks.get(i).getId() + "," + this.generatedTasks.get(i).getArrivalTime() + "," + this.generatedTasks.get(i).getProcessingTime() + ");"; 
			if(newLine * 100 < txt.length()) {
				txt = txt + "\n\t    ";
				newLine++;
			}
		}
		return txt;
	}

	public void run() {
		int currentTime = 0;
		int noOfClients = this.numberOfClients;
		boolean getFinalFrame = false;
		while(currentTime < timeLimit && (zeroClientsWaitingQueue() == false || zeroClientsWaitingServers() == false || currentTime == 0 || getFinalFrame == false)) {
			if(zeroClientsWaitingQueue() == true && zeroClientsWaitingServers() == true && currentTime != 0) {
				getFinalFrame = true;
			}
			this.outputString = "Time " + currentTime + ":\n";
			Strategy timeStrategy = this.scheduler.getStrategy();
			for(int i =0; i< noOfClients; i++) {
				if(generatedTasks.get(i).getArrivalTime() == currentTime) {
					scheduler.dispatchTask(generatedTasks.get(i));
					generatedTasks.remove(i);
					noOfClients--;
					i--;
				}
			}
			waitingClients();
			int clientsCurrentTime = 0;
			for(Server srv : this.serversFromScheduler) {
				clientsCurrentTime += srv.getNoOfTasks();
			}
			if(clientsCurrentTime > maxClients) {
				maxClients = clientsCurrentTime;
				this.peakTime = currentTime;
			}

			this.outputString = this.outputString + timeStrategy.printState(this.serversFromScheduler);
			currentTime++;
			try {
			Thread.sleep(1000);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(currentTime == timeLimit || getFinalFrame == true) {
			this.done = true; // stop simulation
			for(int i=0; i<numberOfServers; i++) {
				serverThreads[i].stop();
			}
		}
		printAvgProcessing();
		printAvgWaiting();
	}
	
	public void waitingClients() {
		if(waitingClientsCoreQueue().length() != 0)
			this.outputString = this.outputString + "Waiting Clients: " + waitingClientsCoreQueue() + "\n";
		else
			this.outputString = this.outputString + "Waiting Clients: none.\n";
	}
	
	public String printAvgProcessing() {
		this.avgProcessingTime = 0;
		for(Server srv : this.serversFromScheduler) {
			this.avgProcessingTime += srv.getTotalProcessingTime();
		}
		this.avgProcessingTime /= this.numberOfClients;
		return "Average processing time: " + this.avgProcessingTime;
	}
	
	public String printAvgWaiting() {
		this.avgWaitingTime = 0;
		for(Server srv: this.serversFromScheduler) {
			this.avgWaitingTime += srv.getTotalWaitingTime();
		}
		this.avgWaitingTime /= this.numberOfClients;
		return "Average waiting time: " + this.avgWaitingTime;
	}
	
	public String printPeakTime() {
		return "Peak time: " + Integer.toString(this.peakTime);
	}
	
	public boolean getDone() {
		return this.done;
	}
	
	public void setTimeLimit(int newTimeLimit) {
		this.timeLimit = newTimeLimit;
	}
	
	public int getTimeLimit() {
		return this.timeLimit;
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				new View();
				
			}
			
		});
	}
}
