package view;

import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import model.Log;
import model.User;

public interface IMainFrame {
	void addAddLogListener(ActionListener listener);
	void addEditUserListener(ActionListener listener);
	void addEditLogListener(ActionListener listener);
	void addLogSelectionListener(ListSelectionListener listener);
	public void addBackListener(ActionListener listener);
	void setLogList(List<Log> logs);
	public Log getSelectedLog();
	public User getSelectedUser();
	public void exit();
	public DefaultTableModel getTable();
	public Set<String> getLogDates();
	public JButton getAddLogButton();
	public JButton getEditLogButton();
	public List<Log> getLogList();

}
