package GamePlay;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class HeadlinePanel extends JPanel{
	private JLabel _score;
	private int score;
	private JLabel imHead;
	
	public HeadlinePanel(){
		this.setLayout(new GridBagLayout());
		
		JPanel NowScore=new JPanel(new GridBagLayout());
	
		JLabel headScore=new JLabel("score");
		Font font = new Font("",1,20);
		headScore.setFont(font);
		headScore.setForeground(Color.black);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.insets=new Insets(1, 14, 1,14);
		NowScore.add(headScore,c);
		
		_score=new JLabel(""+score);
		_score.setFont(font);
		_score.setForeground(Color.black);
		c.gridx=0;
		c.gridy=1;
		NowScore.add(_score,c);
		c.insets=new Insets(2, 2, 2, 2);
		c.gridx=2;
		this.add(NowScore,c);
		NowScore.setBorder(new LineBorder(Color.BLACK, 1));
		
		NowScore.setSize(new Dimension(50,50));
		
		imHead=new JLabel();
		ImageIcon icon=new ImageIcon("./Images/headline.gif");
		imHead.setIcon(icon);
		c.gridx=0;
		this.add(imHead,c);
		}
	public void setHeadlineImage(ImageIcon icon){
		imHead.setIcon(icon);
		this.repaint();
	}
	
	public void setScore(int num){
		score=num;
		_score.setText(score+"");
		_score.setForeground(new Color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256)));
	}
}
