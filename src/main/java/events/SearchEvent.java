package events;

import data.manager.*;
import data.models.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SearchEvent extends KeyAdapter {
    private final JComboBox<String> comboBox;
    private final CourseManager courseManager;
    private final UserModel user;

    public SearchEvent(JComboBox<String> comboBox, CourseManager courseManager, UserModel user) {
        this.comboBox = comboBox;
        this.courseManager = courseManager;
        this.user = user;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            return;
        }
        String inputText = comboBox.getEditor().getItem().toString();

        if (inputText.isEmpty()) {
            comboBox.hidePopup();
        }
        else {
            comboBox.removeAllItems();
            ArrayList<String> results = courseManager.searchCourse(inputText, user);
            for (String lectureName : results) {
                comboBox.addItem(lectureName);
            }
            comboBox.getEditor().setItem(inputText);

            if (results.isEmpty()) {
                comboBox.hidePopup();
            }
            else {
                comboBox.showPopup();
            }
        }
    }
}
