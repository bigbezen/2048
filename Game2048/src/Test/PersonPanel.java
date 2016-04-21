package Test;

import javax.swing.*;
import java.awt.*;
import static java.awt.GridBagConstraints.*;

public class PersonPanel extends JPanel {
	public PersonPanel() {
		super(new GridBagLayout());
		GridBagConstraints tProto = new GridBagConstraints();
		tProto.insets = new Insets(5,2,5,2);

		GridBagConstraints tLabelConst = (GridBagConstraints)tProto.clone();
		tLabelConst.anchor = EAST;
		tLabelConst.fill = NONE;
		tLabelConst.weightx = 0.0;

		GridBagConstraints tTextConst = (GridBagConstraints)tProto.clone();
		tTextConst.anchor = WEST;
		tTextConst.fill = HORIZONTAL;
		tTextConst.weightx = 1.0;

		GridBagConstraints tSelConst = (GridBagConstraints)tProto.clone();
		tSelConst.anchor = WEST;
		tSelConst.fill = NONE;
		tSelConst.weightx = 0.0;

		GridBagConstraints tConst = (GridBagConstraints)tLabelConst.clone();
		tConst.gridx = 0; tConst.gridy = 0;
		add(new JLabel("Name:"), tConst);

		tConst = (GridBagConstraints)tTextConst.clone();
		tConst.gridx = 1; tConst.gridy = 0;
		add(new JTextField(10), tConst);

		tConst = (GridBagConstraints)tLabelConst.clone();
		tConst.gridx = 2; tConst.gridy = 0;
		add(new JLabel("Surname:"), tConst);

		tConst = (GridBagConstraints)tTextConst.clone();
		tConst.gridx = 3; tConst.gridy = 0;
		add(new JTextField(10), tConst);

		tConst = (GridBagConstraints)tLabelConst.clone();
		tConst.gridx = 0; tConst.gridy = 1;
		add(new JLabel("Sex:"), tConst);

		JPanel tPane = new JPanel();
		JRadioButton tMale = new JRadioButton("M");
		JRadioButton tFemale = new JRadioButton("F");
		tPane.add(tMale);
		tPane.add(tFemale);
		ButtonGroup tGrp = new ButtonGroup();
		tGrp.add(tMale);
		tGrp.add(tFemale);
		tConst = (GridBagConstraints)tSelConst.clone();
		tConst.gridx = 1; tConst.gridy = 1;
		add(tPane, tConst);

		tConst = (GridBagConstraints)tLabelConst.clone();
		tConst.gridx = 2; tConst.gridy = 1;
		add(new JLabel("Age:"), tConst);

		tConst = (GridBagConstraints)tSelConst.clone();
		tConst.gridx = 3; tConst.gridy = 1;
		add(new JSpinner(new SpinnerNumberModel(18, 1, 120, 1)), tConst);

		tConst = (GridBagConstraints)tLabelConst.clone();
		tConst.gridx = 0; tConst.gridy = 2;
		add(new JLabel("Address:"), tConst);

		tConst = (GridBagConstraints)tTextConst.clone();
		tConst.gridx = 1; tConst.gridy = 2;
		tConst.gridwidth = 3;
		add(new JTextField(30), tConst);

		tPane = new JPanel(new GridLayout(1,1));
		tPane.setBorder(BorderFactory.createTitledBorder(
			BorderFactory.createEmptyBorder(),
			"Comments:"));
		JTextArea tComments = new JTextArea(3, 30);
		tComments.setBorder(BorderFactory.createEtchedBorder());
		tConst = new GridBagConstraints();
		tConst.insets = new Insets(10, 10, 10, 10);
		tConst.fill = BOTH;
		tConst.anchor = CENTER;
		tConst.weighty = 1.0;
		tConst.gridx = 0; tConst.gridy = 3;
		tConst.gridwidth = 4; tConst.gridheight = 2;
		tPane.add(tComments);
		add(tPane, tConst);
	}

	public static void main(String[] args) {
		Font tFont = new Font("Dialog", Font.BOLD, 16);
		UIManager.put("Label.font", tFont);
		UIManager.put("RadioButton.font", tFont);
		UIManager.put("TitledBorder.font", tFont);
		JFrame tFrame = new JFrame("Personal details");
		tFrame.setContentPane(new PersonPanel());
		tFrame.pack();
		tFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tFrame.setVisible(true);
	}
}
