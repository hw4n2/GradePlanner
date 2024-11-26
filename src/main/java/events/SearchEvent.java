package events;

import data.manager.*;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class SearchEvent extends KeyAdapter {
    private final JComboBox<String> comboBox;
    private final CourseManager courseManager;

    public SearchEvent(JComboBox<String> comboBox, CourseManager courseManager) {
        this.comboBox = comboBox;
        this.courseManager = courseManager;
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
            ArrayList<String> results = courseManager.searchCourse(inputText);
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
