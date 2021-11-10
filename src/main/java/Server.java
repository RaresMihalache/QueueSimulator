
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server implements Runnable{
	private BlockingQueue<Task> tasks;
	private Scheduler scheduler; // used for accessing maxTasksPerServer
	
	private volatile boolean exit = false;
	private int totalWaitingTimeClients;
	private int totalProcessingTimeClients;
	
	public Server(Scheduler scheduler) {
		this.tasks = new LinkedBlockingQueue<Task>(scheduler.getMaxTasksPerServer());
		this.scheduler = scheduler;
		this.totalProcessingTimeClients = 0;
	}
	
	public void addTask(Task newTask) {
		if(this.tasks.isEmpty()) {
			newTask.setWaitingTime(0);
		}
		else {			
			Task lastTask = getTail();
			newTask.setWaitingTime(lastTask.getArrivalTime() + lastTask.getWaitingTime() + lastTask.getProcessingTime() - newTask.getArrivalTime());
		}
		if(this.tasks.size() < this.scheduler.getMaxTasksPerServer())
			this.tasks.add(newTask);
		else { // error
			System.out.println("Can not add more tasks to queue: " + this.getScheduler().getServers().indexOf(this));
			for(Server srv : this.getScheduler().getServers()) {
				System.out.println("Current waiting time queue " + this.getScheduler().getServers().indexOf(srv) + ": " + srv.getTotalWaitingTime());
			}
		}
		this.totalProcessingTimeClients += newTask.getProcessingTime();
		this.totalWaitingTimeClients += newTask.getWaitingTime();
	}
	
	public Task getHead() {
		return this.tasks.peek();
	}
	
	public Task getTail() {
		Iterator<Task> it = this.tasks.iterator();
		Task lastTask = null;
		while(it.hasNext()) {
			lastTask = it.next();
		}
		return lastTask;
	}
	
	public void run() {
		while(!this.exit) {
			while(true) {
				for(Task task : this.tasks) {
					try {
						for(int i=0; i < task.getProcessingTime(); i++) {
							Thread.sleep(1000);
						}
						this.tasks.take(); // take next task from queue
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public void stop() {
		this.exit = true;
	}
	
	public Queue<Task> getTaskss(){
		return this.tasks;
	}
	
	public Task[] getTasks() {
		return (Task[]) this.tasks.toArray(new Task[tasks.size()]);
	}
	
	public int getNoOfTasks() {
		return this.tasks.size();
	}
	
	public int getMaxNoOfTaksPerServer(Scheduler scheduler) {
		return scheduler.getMaxTasksPerServer();
	}
	
	public Scheduler getScheduler() {
		return this.scheduler;
	}
	
	public int getTotalProcessingTime() {
		return this.totalProcessingTimeClients;
	}
	
	public int getTotalWaitingTime() {
		return this.totalWaitingTimeClients;
	}
}
