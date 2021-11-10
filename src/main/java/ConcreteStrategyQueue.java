
import java.util.List;

public class ConcreteStrategyQueue implements Strategy {

	private String TextOnFrame = "";
	
	public void addTask(List<Server> servers, Task t) {
		int minLengthServer = servers.get(0).getNoOfTasks();
		int minIndxServer = 0;
		// get server that has the least amount of tasks
		for(Server srv : servers) {
			if(srv.getNoOfTasks() < minLengthServer) {
				minLengthServer = srv.getNoOfTasks();
				minIndxServer = servers.indexOf(srv);
			}
		}
		if(servers.get(minIndxServer).getNoOfTasks() != servers.get(minIndxServer).getScheduler().getMaxTasksPerServer()) // queue is NOT full
			servers.get(minIndxServer).addTask(t);
		else // queue is full
			System.out.println("can not occupy the server queue at this moment!");
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
