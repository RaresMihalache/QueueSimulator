

import java.util.*;

public class Scheduler {
	private List<Server> servers;
	private int noServers;
	private int maxTasksPerServer;
	protected Strategy strategy;
	
	public Scheduler(int noServers, int maxTasksPerServer) {
		this.noServers = noServers;
		this.maxTasksPerServer = maxTasksPerServer;
		this.servers = new ArrayList<Server>(noServers);
		for(int i=0; i < noServers; i++) {
			Server srv = new Server(this);
			servers.add(i, srv);
		}
	}
	
	public void changeStrategy(SelectionPolicy policy) {
		if(policy == SelectionPolicy.SHORTEST_QUEUE) {
			this.strategy = new ConcreteStrategyQueue();
		}
		if(policy == SelectionPolicy.SHORTEST_TIME) {
			this.strategy = new ConcreteStrategyTime();
			
		}
	}
	
	public void dispatchTask(Task newTask) {
		//call the strategy addTask method
		strategy.addTask(this.servers, newTask);
	}
	
	public List<Server> getServers() {
		return this.servers;
	}
	
	public int getMaxTasksPerServer() {
		return this.maxTasksPerServer;
	}
	
	public Strategy getStrategy() {
		return this.strategy;
	}
}
