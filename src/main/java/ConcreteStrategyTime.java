
import java.util.List;

public class ConcreteStrategyTime implements Strategy {
	private String TextOnFrame = "";
	
	public void addTask(List<Server> servers, Task newTask) {
		int minWaitingTime = 10000;
		Server minServerWaitingTime = null;
		boolean addedTaskToEmptyServer = false;
		for(Server srv : servers) {
			int noTasksServer = srv.getNoOfTasks();
			if(noTasksServer == 0) { // add task to empty queue
				srv.addTask(newTask);
				addedTaskToEmptyServer = true;
				break;
			}
		}
		for(Server srv : servers) {
			if(addedTaskToEmptyServer == true) {
				break;
			}
			else {
				Task lastElement = srv.getTail();
				int waitingTime = lastElement.getArrivalTime() + lastElement.getWaitingTime() + lastElement.getProcessingTime() - newTask.getArrivalTime();
				if(waitingTime < minWaitingTime) {
					minWaitingTime = waitingTime;
					Scheduler minServerScheduler = srv.getScheduler();
					minServerWaitingTime = new Server(minServerScheduler);
					minServerWaitingTime = srv;
				}
			}
		}
		if(minServerWaitingTime != null) {
			minServerWaitingTime.addTask(newTask);
		}
	}
	
	public String printState(List<Server> servers) {
		for(Server srv : servers) {
			if(srv.getNoOfTasks() <= srv.getScheduler().getMaxTasksPerServer())
			{
				Task[] tasks = new Task[srv.getNoOfTasks()];
				tasks = srv.getTasks();
				this.TextOnFrame = this.TextOnFrame + "Queue" + (int)(servers.indexOf(srv) + 1) + ": ";
				if(tasks.length == 0) {
					this.TextOnFrame = this.TextOnFrame + "closed";
				}
				else {
					for(int i=0; i< tasks.length; i++) {
						this.TextOnFrame = this.TextOnFrame + "(" + tasks[i].getId() + "," + tasks[i].getArrivalTime() + "," + tasks[i].getCurrentProcessingTime() + "); ";
						if(srv.getHead() == tasks[i]) {
							tasks[i].setCurrentProcessingTime(tasks[i].getCurrentProcessingTime() - 1);
						}
					}
				}
				this.TextOnFrame += "\n";
			}
			else
				this.TextOnFrame = "-1";
		}
		String returnText = this.TextOnFrame;
		this.TextOnFrame = "";
		return returnText;
	}
	
}
