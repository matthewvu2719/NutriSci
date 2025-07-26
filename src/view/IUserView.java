package view;

import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import model.User;

public interface IUserView {
    String getName();
    String getSelectedSex();
    int getAge();
    double getUserHeight();
    double getUserWeight();

    void setUserDropdownOptions(User[] usernames);
    void addCreateUserListener(ActionListener listener);
    
    void addLoadUserListener(ActionListener listener);
    
    public JComboBox<User> getUserDropdown();

    public void setUserDropdown(JComboBox<User> userDropdown);
    public void exit();
}
