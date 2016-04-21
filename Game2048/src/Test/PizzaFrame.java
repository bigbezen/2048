package Test;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.LinkedList;

public class PizzaFrame extends JFrame {
	private Collection<JCheckBox> _toppings;
	private JSpinner _pizzaCost;
	private JSpinner _topCost;
	private JLabel _total;

	private void recalc() {
		int tCount = 0;
		for (JCheckBox tBox : getToppings()) {
			if (tBox.isSelected()) tCount++;
		}
		Integer tPizzaCost = (Integer)getPizzaCost().getValue();
		Integer tTopsCost = (Integer)getTopCost().getValue();
		int tTotal = tPizzaCost +tCount * tTopsCost;
		getTotal().setText("Total: "+tTotal);
	}

	private JPanel makeSpinnerPanel(String label, JSpinner spin) {
		JPanel tPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		tPane.setMaximumSize(new Dimension(250, 50));
		tPane.setMinimumSize(new Dimension(250, 50));
		tPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		JLabel tLabel = new JLabel(label);
		tPane.add(tLabel);
		tPane.add(spin);
		spin.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				recalc();
			}
		});
		return tPane;
	}

	private void addToppingBox(JCheckBox box, Container c) {
		box.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				recalc();
			}
		});
		c.add(box);
		getToppings().add(box);
	}

	public PizzaFrame() {
		super("Pizza");
		setToppings(new LinkedList<JCheckBox>());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		JPanel tTops = new JPanel();
		tTops.setLayout(new BoxLayout(tTops, BoxLayout.Y_AXIS));
		tTops.setAlignmentX(Component.LEFT_ALIGNMENT);
		tTops.setBorder(BorderFactory.createTitledBorder("Pizza toppings"));
		addToppingBox(new JCheckBox("Chilli"), tTops);
		addToppingBox(new JCheckBox("Extra cheese"), tTops);
		addToppingBox(new JCheckBox("Mushrooms"), tTops);
		addToppingBox(new JCheckBox("Onions"), tTops);
		getContentPane().add(tTops);

		setPizzaCost(new JSpinner(new SpinnerNumberModel(30, 1, 100, 1)));
		JPanel tPizzaCostPane = makeSpinnerPanel("The pizza costs:", getPizzaCost());
		getContentPane().add(tPizzaCostPane);
		setTopCost(new JSpinner(new SpinnerNumberModel(4, 1, 10, 1)));
		JPanel tTopCostPane = makeSpinnerPanel("Each toppings costs: ", getTopCost());
		getContentPane().add(tTopCostPane);

		setTotal(new JLabel());
		getTotal().setAlignmentX(Component.LEFT_ALIGNMENT);
		getContentPane().add(getTotal());
		recalc();
		setResizable(false);
		setVisible(true);
		pack();
	}

	public static void main(String[] args) {
		Font tFont = new Font("Dialog", Font.BOLD, 16);
		UIManager.put("Label.font", tFont);
		UIManager.put("CheckBox.font", tFont);
		UIManager.put("TitledBorder.font", tFont);
		new PizzaFrame();
	}

	private Collection<JCheckBox> getToppings() {
		return _toppings;
	}

	private void setToppings(Collection<JCheckBox> toppings) {
		_toppings = toppings;
	}

	private JSpinner getTopCost() {
		return _topCost;
	}

	private void setTopCost(JSpinner topCost) {
		_topCost = topCost;
	}

	private JLabel getTotal() {
		return _total;
	}

	private void setTotal(JLabel total) {
		_total = total;
	}

	private JSpinner getPizzaCost() {
		return _pizzaCost;
	}

	private void setPizzaCost(JSpinner pizzaCost) {
		_pizzaCost = pizzaCost;
	}
}
