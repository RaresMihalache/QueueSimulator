
import java.util.List;

public interface Strategy {
	public void addTask(List<Server> servers, Task newTask);
	public String printState(List<Server> servers);
}
