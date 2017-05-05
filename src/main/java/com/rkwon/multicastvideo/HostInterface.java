import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

class HostInterface implements ActionListener {
	public Thread hostThread;
	public HostSync host;
	public JFrame frame;

	public JButton stopAcceptClients;
	public JButton writeLogsToCsv;

	public HostInterface(HostSync host) {
		this.host = host;

		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());

		JPanel buttonPane = new JPanel();

		stopAcceptClients = new JButton("Stop Accepting Clients and Send Pings");
		writeLogsToCsv = new JButton("Write all logs to CSV");

		stopAcceptClients.addActionListener(this);
		writeLogsToCsv.addActionListener(this);

		buttonPane.add(stopAcceptClients);
		buttonPane.add(writeLogsToCsv);

		contentPane.add(buttonPane);
		
		frame = new JFrame("Multicast Video Host");
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		frame.setContentPane(contentPane);
		frame.setVisible(true);

		// We need to start the host.
		hostThread = new Thread(host);
		hostThread.start();
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Stop Accepting Clients and Send Pings")) {
			host.stopAcceptingClients();

			for(int i = 0; i < 10; i++) {
				host.ping(false);
			}

			host.ping(true);
			
		} else if(e.getActionCommand().equals("Write all logs to CSV")) {
			host.logToCSV("logs");
		}
	}
}