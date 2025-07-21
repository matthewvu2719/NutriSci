package view;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextArea;

public interface IMealView {
	public void setFoodDropdownOptions(String[] food);
	public void addAddFoodListener(ActionListener listener);
	public void addSwapListener(ActionListener listener);
	
	JComboBox<String> getFoodDropdown();
    JButton getAddButton();
    JButton getSwapButton();
    JTextArea getMealTextArea();
}
