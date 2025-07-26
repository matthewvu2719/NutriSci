package view;

import java.awt.event.ActionListener;

import model.User;

public interface IProfileView {
//    String getName();
//    String getSelectedSex();
//    int getAge();
//    double getUserHeight();
//    double getUserWeight();
    
    public void addBackButtonListener(ActionListener listener);
    public User getUser();
    public void setUser(User user);
    public void addSaveButtonListener(ActionListener listener);
    public void exit();
}
