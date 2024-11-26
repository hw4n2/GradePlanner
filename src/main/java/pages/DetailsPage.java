package pages;

import data.models.*;
import data.manager.*;
import events.SearchEvent;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class DetailsPage extends JPanel {
    private JLabel infoLabel;
    private JComboBox<String> lectureInput;
    private JComboBox<String> gradeInput;
    private JTextField creditInput;
    private JTextField lectureIDInput;

    JLabel nameItem;
    JLabel gradeItem;
    JLabel creditsItem;

    public DetailsPage(CourseManager courseManager) {
        Border emptyBorder = new EmptyBorder(10, 5, 10, 5);
        Border lineBorder = new LineBorder(Color.BLACK);
        setLayout(new BorderLayout());
        setBorder(new CompoundBorder(emptyBorder, lineBorder));
        setBackground(Color.WHITE);

        JPanel tapPanel = new JPanel(new GridLayout(1, 8, 0, 0));
        tapPanel.setBackground(Color.WHITE);

        JLabel semesterLabel = new JLabel("1 Year 1 Semester");

        String[] semester = { "1 - 1", "1 - 2", "2 - 1", "2 - 2", "3 - 1", "3 - 2", "4 - 1", "4 - 2" };
        for (int i = 0; i < 8; i++) {
            JButton btn = new JButton(semester[i]);
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String semesterText = btn.getText().charAt(0) + " Year " + btn.getText().charAt(4) + " Semester";
                    semesterLabel.setText(semesterText);
                }
            });
            tapPanel.add(btn);
        }

        JPanel detailPanel = new JPanel();
        detailPanel.setBackground(Color.WHITE);
        detailPanel.setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(Color.WHITE);
        JPanel inputWrapper = new JPanel();
        inputWrapper.setBackground(Color.WHITE);
        inputWrapper.setLayout(new FlowLayout(FlowLayout.CENTER));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        JButton saveBtn = new JButton("Save");

        infoLabel = new JLabel("Major Grade 4.1  Earned Credits 12");

        JPanel addedList = new JPanel();
        addedList.setBackground(Color.WHITE);
        addedList.setLayout(new GridLayout(8, 1, 5, 0));
        for (int i = 0; i < 8; i++) {
            JPanel itemPanel = new JPanel(new GridLayout(1, 4, 15, 2));
            itemPanel.setBackground(Color.WHITE);
            itemPanel.setPreferredSize(new Dimension(0, 20));
            if(i == 0){
                nameItem = new JLabel("LectureName");
                gradeItem = new JLabel("Grade");
                creditsItem = new JLabel("Credit");
            }
            else {
                itemPanel.setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));
                nameItem = new JLabel("LectureName");
                gradeItem = new JLabel("Grade");
                creditsItem = new JLabel("Credit");
            }
            JButton deleteBtn = new JButton();
            deleteBtn.setPreferredSize(new Dimension(15, 15));
            deleteBtn.setBorder(new EmptyBorder(5, 5, 5, 5));
            deleteBtn.setText("clear");
            if(i == 0){
                deleteBtn.setText("click to clear");
                deleteBtn.setBorderPainted(false);
                deleteBtn.setContentAreaFilled(false);
                deleteBtn.setFocusPainted(false);
            }
            JLabel emptyLabel = new JLabel();

            itemPanel.add(nameItem);
            itemPanel.add(gradeItem);
            itemPanel.add(creditsItem);
            itemPanel.add(emptyLabel);
            itemPanel.add(deleteBtn);
            addedList.add(itemPanel);
        }

        c.gridx = 0; c.gridy = 0; c.weightx = 1.0; c.weighty = 0.5; c.gridwidth = 8; c.gridheight = 1; c.insets = new Insets(5, 2, 10, 0);
        infoPanel.add(semesterLabel, c);
        c.gridx = 9; c.gridy = 0; c.weightx = 0.1; c.weighty = 0.3; c.gridwidth = 1; c.gridheight = 1;
        infoPanel.add(saveBtn, c);
        c.gridx = 0; c.gridy = 2; c.weightx = 1.0; c.weighty = 0.5; c.gridwidth = 5; c.gridheight = 1;
        infoPanel.add(infoLabel, c);
        c.gridx = 0; c.gridy = 3; c.weightx = 1.0; c.weighty = 1.0; c.gridwidth = 10; c.gridheight = 5;
        infoPanel.add(addedList, c);

        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.setBackground(Color.WHITE);

        lectureInput = new JComboBox<>();
        gradeInput = new JComboBox<>();
        lectureIDInput = new JTextField(5);
        lectureIDInput.setEnabled(false);
        lectureIDInput.setDisabledTextColor(Color.BLACK);
        creditInput = new JTextField(3);
        creditInput.setEnabled(false);
        creditInput.setDisabledTextColor(Color.BLACK);
        lectureInput.setPreferredSize(new Dimension(320, 20));
        lectureInput.setMaximumRowCount(9);
        lectureInput.setEditable(true);

        gradeInput.setMaximumRowCount(9);
        gradeInput.addItem("A+");
        gradeInput.addItem("A0");
        gradeInput.addItem("B+");
        gradeInput.addItem("B0");
        gradeInput.addItem("C+");
        gradeInput.addItem("C0");
        gradeInput.addItem("D+");
        gradeInput.addItem("D0");
        gradeInput.addItem("F");

        lectureInput.getEditor().getEditorComponent().addKeyListener(new SearchEvent(lectureInput, courseManager));
        lectureInput.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() != ItemEvent.SELECTED){
                    return;
                }
                String selectedLecture = (String)lectureInput.getSelectedItem();
                CourseModel course = courseManager.getCourse(selectedLecture);
                if(course != null) {
                    lectureIDInput.setText(course.getCourseID());
                    creditInput.setText(course.getCredit());
                }
            }
        });

        JLabel lectureLabel = new JLabel("LectureName");
        JLabel lectureIDLabel = new JLabel("LectureID");
        JLabel gradeLabel = new JLabel("Grade");
        JLabel creditLabel = new JLabel("Credit");

        JButton addBtn = new JButton("+");

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(new MatteBorder(1, 0, 0, 0, Color.GRAY));
        inputPanel.add(lectureLabel);
        inputPanel.add(lectureInput);
        inputPanel.add(lectureIDLabel);
        inputPanel.add(lectureIDInput);
        inputPanel.add(creditLabel);
        inputPanel.add(creditInput);
        inputPanel.add(gradeLabel);
        inputPanel.add(gradeInput);
        inputPanel.add(addBtn);
        inputPanel.setBorder(new EmptyBorder(1, 0, 0, 0));
        topPanel.add(inputPanel);

        inputWrapper.add(topPanel);
        detailPanel.add(infoPanel, BorderLayout.NORTH);
        detailPanel.add(inputWrapper, BorderLayout.CENTER);
        add(tapPanel, BorderLayout.NORTH);
        add(detailPanel, BorderLayout.CENTER);
    }
}
