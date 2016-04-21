package GamePlay;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class winFrame extends JFrame implements ActionListener {

	private JButton yes;
	private JButton no;
	private JLabel head;
	private JTextField name;
	private JPanel yesNo;
	private int score;
	private JButton save;
	private JButton restart;
	private ActionListener e;

	public winFrame(Game e) {
		super("you Win");
		this.e=e;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new GridBagLayout());
		this.setSize(450, 350);
		score=0;
		name=new JTextField(30);
		GridBagConstraints c = new GridBagConstraints();

		Icon icon = new ImageIcon("./Images/win1.gif");
		JLabel picWon = new JLabel();
		picWon.setIcon(icon);
		c.gridx = 0;
		c.gridy = 0;
		this.add(picWon, c);

		Font font = new Font("", 1, 30);
		head = new JLabel("Woad you like to continue?");
		head.setFont(font);
		head.setForeground(Color.DARK_GRAY);
		c.gridy = 1;
		this.add(head, c);

		yesNo = new JPanel(new GridBagLayout());
		yes = new JButton();
		yes.addActionListener(this);
		Icon yesIcon = new ImageIcon("./Images/yes.png");
		yes.setIcon(yesIcon);
		yes.setMargin(new Insets(0, 0, 0, 0));
		yes.setBackground(Color.LIGHT_GRAY);
		c.gridy = 0;
		c.gridx = 0;
		c.insets = new Insets(4, 25, 4, 25);
		yesNo.add(yes, c);

		no = new JButton();
		no.addActionListener(this);
		Icon noIcon = new ImageIcon("./Images/no.png");
		no.setIcon(noIcon);
		no.setMargin(new Insets(0, 0, 0, 0));
		no.setBackground(Color.LIGHT_GRAY);
		c.gridy = 0;
		c.gridx = 1;
		yesNo.add(no, c);

		c.gridy = 2;
		c.gridx = 0;
		this.add(yesNo, c);
	}
	public void setScore(int score){
		this.score=score;
	}
	
	private void changeFrame(){
		head.setText("your score "+score);
		GridBagConstraints c=new GridBagConstraints();
		yesNo.remove(yes);
		yesNo.remove(no);
		this.remove(yesNo);
		
		//TODO add action lisiner in the Game class;
		save=new JButton();
		Icon saveIcon = new ImageIcon("./Images/save.gif");
		save.setIcon(saveIcon);
		save.addActionListener(e);
		save.setMargin(new Insets(0, 0, 0, 0));
		c.gridx=0;
		c.insets = new Insets(4, 25, 4, 25);
		yesNo.add(save, c);
		
		restart=new JButton();
		Icon noIcon = new ImageIcon("./Images/no.png");
		restart.setIcon(noIcon);
		restart.addActionListener(e);
		restart.setMargin(new Insets(0, 0, 0, 0));
		c.gridx=1;
		yesNo.add(restart, c);
		
		c.gridy=3;
		c.gridx=0;
		this.add(yesNo,c);
		
		yesNo.repaint();
		c.gridy=2;
		c.gridx=0;
		this.add(name,c);
	}
	public JButton getRestart(){
		return this.restart;
	}
	
	public JButton getSave(){
		return this.save;
	}
	public String getName(){
		return name.getText();
	}
	public boolean checkName(){
		if(name.getText().matches("[a-zA-Z]*")&(name.getText().length()>0)&(name.getText().length()<=14))return true;
		name.setText(null);
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == yes) {
			this.dispose();		}
		if (e.getSource() == no) {
			changeFrame();
			// TODO Auto-generated method stub

		}
	}
}
