package view;

import java.awt.event.ActionListener;

public interface IUserView {
    String getName();
    String getSelectedSex();
    int getAge();
    double getUserHeight();
    double getUserWeight();

    void setUserDropdownOptions(String[] usernames);
    void addCreateUserListener(ActionListener listener);
//    void showMessage(String message);
//    void clearInputs();
}
