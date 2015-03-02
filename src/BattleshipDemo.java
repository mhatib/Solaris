import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BattleshipDemo {
	public static void main(String[] args) {
		BattleShipUI ui1 = new BattleShipUI();
		BattleShipUI ui2 = new BattleShipUI();
		ui1.enemyUI=ui2;
		ui2.enemyUI=ui1;
		ui1.setTitle("Player1");
		ui1.setSize(500, 500);
		ui1.setVisible(true);
		ui1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ui2.setTitle("Player2");
		ui2.setSize(500, 500);
		ui2.setVisible(true);
		ui2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


	}
}

class BattleShipUI extends JFrame{
	
	GridButton gridBtns[] = new GridButton[100];
	boolean gridIsShip[] = new boolean[100];
	JPanel gridPanel = new JPanel();
	JPanel attackPanel = new JPanel();
	JPanel statsPanel = new JPanel();
	BattleShipUI enemyUI;
	int hpv=1;
	JLabel hpVal = new JLabel(" "+hpv);	
	int apv=0;
	JLabel apVal = new JLabel(" "+apv);
	JButton attackButton = new JButton();
	JLabel result = new JLabel(" ");
	
	public void connect(BattleShipUI enemyUI){
		this.enemyUI = enemyUI;
	}

	public  BattleShipUI(){

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		Container c = getContentPane();

		gridPanel.setLayout(new GridLayout(10, 10, 0, 0));
		attackPanel.setLayout(new FlowLayout());
		statsPanel.setLayout(new FlowLayout());

		Random rdm = new Random();

		for (int i = 0; i < 100; i++) {
			gridBtns[i] = new GridButton();
			gridBtns[i].setBackground(Color.WHITE);
			gridBtns[i].addActionListener(new GridBtnActionListener(this));
			gridPanel.add(gridBtns[i]);
//			TODO: add actionListener
			if (rdm.nextInt(100) > 70) {
				gridIsShip[i] = true;
				gridBtns[i].setText("B");
			} else {
				gridIsShip[i] = false;
			}

		}
		
		statsPanel.add(result);
		BufferedImage myPicture;
		try {
			myPicture = ImageIO.read(new File("src/images/photo.jpg"));
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			statsPanel.add(picLabel);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		JLabel player = new JLabel("Player:");
		JLabel playerName = new JLabel("Hatib");
		JLabel hp = new JLabel("HP:");
		JLabel ap = new JLabel("AP:");
		statsPanel.add(player);
		statsPanel.add(playerName);
		
		statsPanel.add(hp);
		statsPanel.add(hpVal);
		statsPanel.add(ap);
		statsPanel.add(apVal);


		
		
		attackButton.setText("Attack!");
		attackButton.addActionListener(new AttackBtnActionListenr(this));
		attackPanel.add(attackButton);
		
		c.add(gridPanel, BorderLayout.CENTER);
		c.add(attackPanel, BorderLayout.SOUTH);
		c.add(statsPanel, BorderLayout.NORTH);
		
	}
}

class AttackBtnActionListenr implements ActionListener{

	BattleShipUI selfUI;
	
	
	
	public AttackBtnActionListenr(BattleShipUI selfUI) {
		super();
		this.selfUI = selfUI;
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 100; i++) {
			if(selfUI.gridBtns[i].getBackground().equals(Color.BLUE)){
				selfUI.enemyUI.gridBtns[i].attacked = true;
				selfUI.enemyUI.gridBtns[i].setBackground(Color.RED);
				if(selfUI.enemyUI.gridBtns[i].getText().equals("B")){
					selfUI.enemyUI.gridBtns[i].setText("X");
					selfUI.apv++;
					selfUI.hpv--;
					selfUI.enemyUI.hpVal.setText(""+selfUI.hpv);
					selfUI.apVal.setText(""+selfUI.apv);
					if (Integer.parseInt(selfUI.enemyUI.hpVal.getText())==0){
						selfUI.attackButton.setEnabled(false);
						selfUI.enemyUI.attackButton.setEnabled(false);
						selfUI.result.setText("WINNER");
						selfUI.enemyUI.result.setText("LOSER");
					}
				}
				if(selfUI.gridBtns[i].attacked==true){
					selfUI.gridBtns[i].setBackground(Color.RED);
				}
			}
		}
	}
	
}

class GridBtnActionListener implements ActionListener{
	BattleShipUI selfUI;
	
	public GridBtnActionListener(BattleShipUI selfUI) {
		super();
		this.selfUI = selfUI;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		((GridButton)e.getSource()).setBackground(Color.BLUE);
		
	}
}


class GridButton extends JButton{

	public boolean attacked = false;
}