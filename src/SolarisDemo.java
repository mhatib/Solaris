

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SolarisDemo extends JFrame {

	private JButton btn[] = new JButton[100];
	private JButton btn2[] = new JButton[5];
	private JButton btn3[] = new JButton[5];

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		try {
			SolarisDemo frame = new SolarisDemo();
			frame.setTitle("Solar");
			frame.setSize(500, 500);
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public SolarisDemo() {

		ActionListener al = new ButtonActionListener();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		Container c = getContentPane();
		
		JPanel gridPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		JPanel statsPanel = new JPanel();

		
		
		gridPanel.setLayout(new GridLayout(10, 10, 0, 0));
		buttonPanel.setLayout(new FlowLayout());
		statsPanel.setLayout(new FlowLayout());

		for (int i = 0; i < 100; i++) {
			btn[i] = new JButton();
			btn[i].setBackground(Color.WHITE);
			gridPanel.add(btn[i]);
			btn[i].addActionListener(al);
		}
		
		Random randomGenerator = new Random();
	    for (int idx = 1; idx <= 10; ++idx){
	      int randomInt = randomGenerator.nextInt(100);
	      btn[randomInt].setText("SHIP");
	    }
		for (int i = 0; i < 5; i++) {
			btn2[i] = new JButton();
			btn2[i].setBackground(Color.WHITE);
			statsPanel.add(btn2[i]);
			btn2[i].addActionListener(al);
		}
		
		buttonPanel.add(new JButton());
		buttonPanel.add(new JButton());
		buttonPanel.add(new JButton());
		
		c.add(gridPanel, BorderLayout.CENTER);
		c.add(buttonPanel, BorderLayout.SOUTH);
		c.add(statsPanel, BorderLayout.NORTH);

	}

}

class ButtonActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton pressedButton = (JButton) e.getSource();
		System.out.println(e.getSource());
		// if (pressedButton.getBackground() == Color.WHITE) {
		pressedButton.setBackground(Color.BLUE);
		// } else {
		// pressedButton.setBackground(Color.WHITE);
		// }

	}

}