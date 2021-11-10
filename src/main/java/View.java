import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.SwingWorker;

public class View extends JFrame implements ActionListener {
	
	//JLabels:
	private JLabel titleLbl = new JLabel("QueueSimulator");
	private JLabel noClientsLbl = new JLabel("Number of Clients:");
	private JLabel noQueuesLbl = new JLabel("Number of Queues:");
	private JLabel simulationTimeLbl = new JLabel("Simualtion Interval:");
	private JLabel minArrivalLbl = new JLabel("Min. arrival time:");
	private JLabel maxArrivalLbl = new JLabel("Max. arrival time:");
	private JLabel minServiceLbl = new JLabel("Min. service time:");
	private JLabel maxServiceLbl = new JLabel("Max. service time:");
	private JLabel maxClientsPerQueueLbl = new JLabel("Max. Clients/Queue:");
	private JLabel distributionPolicyLbl = new JLabel("Distribution Policy:");
	private JLabel versionLbl = new JLabel("Version 1.0.0.");
	
	//JRadioButtons:
	private ButtonGroup btnGroup = new ButtonGroup();
	private JRadioButton waitingTimeStrategy = new JRadioButton("Waiting time");
	private JRadioButton numberOfClientsStrategy = new JRadioButton("Number of clients");
	private int strategy; // 1 for waiting, 2 for clients in queue
	
	//JTextFields & JComboBox
	private JTextField noClientsTxtField = new JTextField();
	private JComboBox<String>  noQueuesComboBox = new JComboBox<String>();
	private JTextField simulationTimeTxtField = new JTextField();
	private JTextField minArrivalTxtField = new JTextField();
	private JTextField maxArrivalTxtField = new JTextField();
	private JTextField minServiceTxtField = new JTextField();
	private JTextField maxServiceTxtField = new JTextField();
	private JTextField maxClientsperQueueTxtField = new JTextField();
	
	//JButton & JTextArea
	private JButton simulateBtn = new JButton("Simulate");
	private JTextArea simulationArea = new JTextArea();
	
	//Panel
	private JPanel mainPanel = new JPanel();
		
	public View() {
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setSize(1500, 720);
		this.setResizable(false);
		this.setTitle("Queue Simulator");
		
		mainPanel.setLayout(null);
		mainPanel.setBackground(new Color(100, 150, 20));
		
		titleLbl.setBounds(550, 10, 540, 60);
		titleLbl.setFont(new Font("Serif", Font.BOLD, 35));
		
		simulationTimeLbl.setBounds(10, 90, 540, 40);
		simulationTimeLbl.setFont(new Font("Serif", Font.BOLD, 20));
		simulationTimeTxtField.setBounds(200, 100, 80, 23);
		simulationTimeTxtField.setFont(new Font("Serif", Font.PLAIN, 20));
		
		noClientsLbl.setBounds(550, 90, 540, 40);
		noClientsLbl.setFont(new Font("Serif", Font.BOLD, 20));
		noClientsTxtField.setBounds(730, 100, 80, 23);
		noClientsTxtField.setFont(new Font("Serif", Font.PLAIN, 20));
		
		noQueuesLbl.setBounds(1090, 90, 540, 40);
		noQueuesLbl.setFont(new Font("Serif", Font.BOLD, 20));
		noQueuesComboBox.addItem("1");
		noQueuesComboBox.addItem("2");
		noQueuesComboBox.addItem("3");
		noQueuesComboBox.addItem("4");
		noQueuesComboBox.addItem("5");
		noQueuesComboBox.addItem("6");
		noQueuesComboBox.addItem("7");
		noQueuesComboBox.addItem("8");
		noQueuesComboBox.addItem("9");
		noQueuesComboBox.addItem("10");
		noQueuesComboBox.addItem("11");
		noQueuesComboBox.addItem("12");
		noQueuesComboBox.addItem("13");
		noQueuesComboBox.addItem("14");
		noQueuesComboBox.addItem("15");
		noQueuesComboBox.addItem("16");
		noQueuesComboBox.addItem("17");
		noQueuesComboBox.addItem("18");
		noQueuesComboBox.addItem("19");
		noQueuesComboBox.addItem("20");
		noQueuesComboBox.addItem("21");
		noQueuesComboBox.addItem("22");
		noQueuesComboBox.addItem("23");
		noQueuesComboBox.addItem("24");
		noQueuesComboBox.addItem("25");

		noQueuesComboBox.setBounds(1270, 100, 60, 23);
		noQueuesComboBox.setFont(new Font("Dialog", Font.PLAIN, 13));
		
		
		maxClientsPerQueueLbl.setBounds(10, 130, 540, 40);
		maxClientsPerQueueLbl.setFont(new Font("Serif", Font.BOLD, 20));
		maxClientsperQueueTxtField.setBounds(200, 140, 80, 23);
		maxClientsperQueueTxtField.setFont(new Font("Serif", Font.PLAIN, 20));
		
		minArrivalLbl.setBounds(10, 195, 540, 40);
		minArrivalLbl.setFont(new Font("Serif", Font.BOLD, 20));
		minArrivalTxtField.setBounds(200, 205, 80, 23);
		minArrivalTxtField.setFont(new Font("Serif", Font.PLAIN, 20));
		
		maxArrivalLbl.setBounds(550, 195, 540, 40);
		maxArrivalLbl.setFont(new Font("Serif", Font.BOLD, 20));
		maxArrivalTxtField.setBounds(730, 205, 80, 23);
		maxArrivalTxtField.setFont(new Font("Serif", Font.PLAIN, 20));
		
		minServiceLbl.setBounds(10, 235, 540, 40);
		minServiceLbl.setFont(new Font("Serif", Font.BOLD, 20));
		minServiceTxtField.setBounds(200, 245, 80, 23);
		minServiceTxtField.setFont(new Font("Serif", Font.PLAIN, 20));
		
		maxServiceLbl.setBounds(550, 235, 540, 40);
		maxServiceLbl.setFont(new Font("Serif", Font.BOLD, 20));
		maxServiceTxtField.setBounds(730, 245, 80, 23);
		maxServiceTxtField.setFont(new Font("Serif", Font.PLAIN, 20));
		
		distributionPolicyLbl.setBounds(900, 145, 300, 60);
		distributionPolicyLbl.setFont(new Font("Serif", Font.BOLD, 20));
		waitingTimeStrategy.setBounds(1080, 140, 150, 20);
		waitingTimeStrategy.setFont(new Font("Serif", Font.BOLD, 20));
		waitingTimeStrategy.setBackground(new Color(100, 150, 20));
		numberOfClientsStrategy.setBounds(1080, 185, 240, 20);
		numberOfClientsStrategy.setFont(new Font("Serif", Font.BOLD, 20));
		numberOfClientsStrategy.setBackground(new Color(100, 150, 20));
		
		versionLbl.setBounds(1320, 640, 540, 40);
		versionLbl.setFont(new Font("Serif", Font.ITALIC, 25));
		
		simulateBtn.setBounds(1100, 250, 200, 50);
		simulateBtn.setFont(new Font("Serif", Font.PLAIN, 35));
		
		simulationArea.setBounds(20, 320, 1400, 320);
		simulationArea.setFont(new Font("Serif", Font.PLAIN, 25));
		simulationArea.setEditable(false);
				
		
		btnGroup.add(waitingTimeStrategy);
		btnGroup.add(numberOfClientsStrategy);
		
		mainPanel.add(titleLbl);
		mainPanel.add(simulationTimeLbl);
		mainPanel.add(simulationTimeTxtField);
		mainPanel.add(noClientsLbl);
		mainPanel.add(noClientsTxtField);
		mainPanel.add(noQueuesLbl);
		mainPanel.add(noQueuesComboBox);
		mainPanel.add(maxClientsPerQueueLbl);
		mainPanel.add(maxClientsperQueueTxtField);
		mainPanel.add(minArrivalLbl);
		mainPanel.add(minArrivalTxtField);
		mainPanel.add(maxArrivalLbl);
		mainPanel.add(maxArrivalTxtField);
		mainPanel.add(minServiceLbl);
		mainPanel.add(minServiceTxtField);
		mainPanel.add(maxServiceLbl);
		mainPanel.add(maxServiceTxtField);
		mainPanel.add(versionLbl);
		mainPanel.add(simulateBtn);
		mainPanel.add(simulationArea);
		mainPanel.add(distributionPolicyLbl);	
		mainPanel.add(waitingTimeStrategy);
		mainPanel.add(numberOfClientsStrategy);
		
		simulateBtn.addActionListener(this);

		this.add(mainPanel);
		this.setVisible(true);
	}
		
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == simulateBtn) {
			this.setSimulationStrategy();
			this.setResizable(false);
			try {
				start();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			simulateBtn.setEnabled(false);
		}
		
	}
	
	public int getTimeLimit() {
		return Integer.parseInt(this.simulationTimeTxtField.getText().toString());
	}
	
	public int getMinArrival() {
		return Integer.parseInt(this.minArrivalTxtField.getText().toString());
	}
	
	public int getMaxArrival() {
		return Integer.parseInt(this.maxArrivalTxtField.getText().toString());
	}
	
	public int getMinProcessing() {
		return Integer.parseInt(this.minServiceTxtField.getText().toString());
	}
	
	public int getMaxProcessing() {
		return Integer.parseInt(this.maxServiceTxtField.getText().toString());
	}
	
	public int getNoClients() {
		return Integer.parseInt(this.noClientsTxtField.getText().toString());
	}
	
	public int getNoServers() {
		return Integer.parseInt(this.noQueuesComboBox.getItemAt(this.noQueuesComboBox.getSelectedIndex()).toString());
	}
	
	public int getMaxClientsPerServer() {
		return Integer.parseInt(this.maxClientsperQueueTxtField.getText().toString());
	}
	
	public void setSimulationStrategy() {
		if(this.waitingTimeStrategy.isSelected()) {
			this.strategy = 1; // waiting strategy
		}
		else if(this.numberOfClientsStrategy.isSelected()) {
			this.strategy = 2; // clients in queue strategy
		}
	}
	
	public int getSimulationStrategy() {
		return this.strategy;
	}
	
	private void start() throws IOException {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
			SimulationManager simManager = new SimulationManager(View.this);
			
			@Override
			protected Void doInBackground() throws Exception {
				FileWriter myWriter = new FileWriter("log.txt");
				BufferedWriter bwr = new BufferedWriter(myWriter);

				int currentTime = 0;
				while(currentTime < simManager.getTimeLimit() && simManager.getDone() == false) {
					bwr.write(simManager.getOutput() + "\n");
					simulationArea.append(simManager.getOutput());
					Thread.sleep(1000);
					currentTime++;
					simulationArea.setText("");
				}
				if(simManager.getDone() == true) {
					bwr.write(simManager.printAvgProcessing() + "\n");
					bwr.write(simManager.printAvgWaiting() + "\n");
					bwr.write(simManager.printPeakTime() + "\n");
					
					simulationArea.append(simManager.printAvgProcessing() + "\n");
					simulationArea.append(simManager.printAvgWaiting() + "\n");
					simulationArea.append(simManager.printPeakTime()+ "\n");
				}
				bwr.close();
				View.this.simulateBtn.setEnabled(true);
				return null;
			}
			
		};
		worker.execute();
	}
	
}
