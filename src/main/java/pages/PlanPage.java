package pages;

import data.models.*;
import data.manager.*;
import events.SearchEvent;

import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;
import javax.swing.border.*;

public class PlanPage extends JPanel{
    private JLabel infoLabel;
    private JComboBox<String> lectureInput;
    private JTextField lectureIDInput;
    private JTextField creditInput;
    private JTextField recommendInput;
    private CourseModel curCourse;
    private CourseManager courseManager;

    private JPanel itemPanel;
    private JLabel idItem;
    private JLabel nameItem;
    private JLabel creditsItem;
    private JLabel recSemesterItem;
    private ArrayList<CourseUIModel> inputlist = null;
    private JPanel addedList;

    public PlanPage(UserModel user, CourseManager cm) {
        this.courseManager = cm;
        Border emptyBorder = new EmptyBorder(10, 5, 10, 5);
        Border lineBorder = new LineBorder(Color.BLACK);
        setLayout(new BorderLayout());
        setBorder(new CompoundBorder(emptyBorder, lineBorder));
        setBackground(Color.WHITE);

        JPanel tapPanel = new JPanel(new GridLayout(1, 8, 0, 0));
        tapPanel.setBackground(Color.WHITE);

        JLabel semesterLabel = new JLabel("- Year - Semester");
        Font tmp = semesterLabel.getFont().deriveFont(24f);
        semesterLabel.setFont(tmp);

        String[] semester = {"1 - 1", "1 - 2", "2 - 1", "2 - 2", "3 - 1", "3 - 2", "4 - 1", "4 - 2"};
        ArrayList<String> listToCmp = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            JButton btn = new JButton(semester[i]);
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    String semesterText = btn.getText().charAt(0) + " Year " + btn.getText().charAt(4) + " Semester";
                    semesterLabel.setText(semesterText);
                    inputlist = courseManager.loadCourseList(user.getStudentID(), btn.getText().charAt(0) + "-" + btn.getText().charAt(4), CourseUIModel.PLAN);
                    setCourseList();
                    setOldList(listToCmp);
                    setInfoLabel();
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
        inputWrapper.setLayout(new BorderLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(semesterLabel.getText().equals("- Year - Semester")){
                    JOptionPane.showMessageDialog(null, "Choose Semester first", "error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int modifyFlag = courseManager.isModified(inputlist, listToCmp);

                if(modifyFlag == CourseManager.BOTH_EMPTY){
                    JOptionPane.showMessageDialog(null, "Add Lecture first", "error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(modifyFlag == CourseManager.NOT_MODIFIED){
                    JOptionPane.showMessageDialog(null, "Nothing changed", "error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String sem = semesterLabel.getText().charAt(0) + "-" + semesterLabel.getText().charAt(7);
                courseManager.saveCourseList(inputlist, user.getStudentID(), sem, CourseUIModel.PLAN);
                setOldList(listToCmp);
                setInfoLabel();
            }
        });

        infoLabel = new JLabel("Credits - / 21");

        addedList = new JPanel();
        addedList.setBackground(Color.WHITE);
        addedList.setLayout(new GridLayout(8, 1, 5, 0));
        itemPanel = new JPanel(new GridLayout(1, 5, 10, 2));
        itemPanel.setBackground(Color.WHITE);
        itemPanel.setPreferredSize(new Dimension(0, 20));
        itemPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        idItem = new JLabel("LectureID");
        nameItem = new JLabel("LectureName");
        creditsItem = new JLabel("Credit");
        recSemesterItem = new JLabel("Rec. Semester");
        JButton deleteBtn = new JButton();
        deleteBtn.setPreferredSize(new Dimension(15, 15));
        deleteBtn.setBorder(new EmptyBorder(5, 5, 5, 5));
        deleteBtn.setText("click to clear");
        deleteBtn.setBorderPainted(false);
        deleteBtn.setContentAreaFilled(false);
        deleteBtn.setFocusPainted(false);
        itemPanel.add(idItem);
        itemPanel.add(nameItem);
        itemPanel.add(creditsItem);
        itemPanel.add(recSemesterItem);
        itemPanel.add(deleteBtn);
        addedList.add(itemPanel);

        c.gridx = 0; c.gridy = 0; c.weightx = 1.0; c.weighty = 0.5; c.gridwidth = 8; c.gridheight = 1; c.insets = new Insets(5, 2, 10, 0);
        infoPanel.add(semesterLabel, c);
        c.gridx = 9; c.gridy = 0; c.weightx = 0.1; c.weighty = 0.3; c.gridwidth = 1; c.gridheight = 1;
        infoPanel.add(saveBtn, c);
        c.gridx = 0; c.gridy = 2; c.weightx = 1.0; c.weighty = 0.5; c.gridwidth = 5; c.gridheight = 1;
        infoPanel.add(infoLabel, c);
        c.gridx = 0; c.gridy = 3; c.weightx = 1.0; c.weighty = 1.0; c.gridwidth = 10; c.gridheight = 5;
        infoPanel.add(addedList, c);

        JPanel topPanel = new JPanel(new GridLayout(1, 2));
        topPanel.setBackground(Color.WHITE);

        lectureInput = new JComboBox<>();
        lectureIDInput = new JTextField(6);
        lectureIDInput.setEnabled(false);
        lectureIDInput.setDisabledTextColor(Color.BLACK);
        creditInput = new JTextField(1);
        creditInput.setEnabled(false);
        creditInput.setDisabledTextColor(Color.BLACK);
        recommendInput = new JTextField(3);
        recommendInput.setEnabled(false);
        recommendInput.setDisabledTextColor(Color.BLACK);
        lectureInput.setPreferredSize(new Dimension(320, 20));
        lectureInput.setMaximumRowCount(9);
        lectureInput.setEditable(true);

        lectureInput.getEditor().getEditorComponent().addKeyListener(new SearchEvent(lectureInput, courseManager, user));
        lectureInput.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() != ItemEvent.SELECTED){
                    return;
                }
                String selectedLecture = (String)lectureInput.getSelectedItem();
                curCourse = courseManager.getCourse(selectedLecture);
                if(curCourse != null) {
                    lectureIDInput.setText(curCourse.getCourseID());
                    creditInput.setText(curCourse.getCredit());
                    recommendInput.setText(curCourse.getSemester());
                }
            }
        });

        JLabel lectureLabel = new JLabel("LectureName");
        JLabel lectureIDLabel = new JLabel("LectureID");
        JLabel creditLabel = new JLabel("Credit");
        JLabel recSemesterLabel = new JLabel("Rec. Semester");

        inputlist = new ArrayList<>();
        JButton addBtn = new JButton("+");
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(semesterLabel.getText().equals("- Year - Semester")){
                    JOptionPane.showMessageDialog(null, "Choose Semester first", "error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(inputlist.size() >= 7){
                    JOptionPane.showMessageDialog(null, "Less than 8 Lectures", "error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(curCourse == null){
                    JOptionPane.showMessageDialog(null, "Check your inputs", "error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(!inputlist.isEmpty()){
                    int credit = Integer.parseInt(courseManager.calcGradeAverage(inputlist)[1]);
                    if(credit + Integer.parseInt(creditInput.getText()) > 21){
                        JOptionPane.showMessageDialog(null, "Maximum credit 21", "error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                for(CourseUIModel c : inputlist){
                    if(c.getCourseName().equals(curCourse.getCourseName())){
                        JOptionPane.showMessageDialog(null, "Already exists:\n" + curCourse.getCourseName(), "error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                CourseUIModel courseToAdd = new CourseUIModel(CourseUIModel.PLAN, curCourse);
                inputlist.add(courseToAdd);
                setCourseList();
                setInfoLabel();
            }
        });

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(new MatteBorder(1, 0, 0, 0, Color.GRAY));
        inputPanel.add(lectureLabel);
        inputPanel.add(lectureInput);
        inputPanel.add(lectureIDLabel);
        inputPanel.add(lectureIDInput);
        inputPanel.add(creditLabel);
        inputPanel.add(creditInput);
        inputPanel.add(recSemesterLabel);
        inputPanel.add(recommendInput);
        inputPanel.add(addBtn);
        topPanel.add(inputPanel);

        inputWrapper.add(topPanel, BorderLayout.NORTH);
        detailPanel.add(infoPanel, BorderLayout.NORTH);
        detailPanel.add(inputWrapper, BorderLayout.CENTER);
        add(tapPanel, BorderLayout.NORTH);
        add(detailPanel, BorderLayout.CENTER);
    }

    private void setCourseList(){
        addedList.removeAll();
        addedList.add(itemPanel);
        for(CourseUIModel c : inputlist){
            addedList.add(c);
            c.addRemoveEvent(inputlist, addedList, itemPanel, courseManager, infoLabel, CourseUIModel.PLAN);
        }
        addedList.revalidate();
        addedList.repaint();
    }

    private void setOldList(ArrayList<String> oldList){
        oldList.clear();
        for(CourseUIModel c : inputlist){
            oldList.add(c.getCourseName());
        }
    }

    private void setInfoLabel(){
        String[] infos = courseManager.calcGradeAverage(inputlist);
        if(!inputlist.isEmpty()) infoLabel.setText("Credits " + infos[1] + " / 21");
        else infoLabel.setText("Credits - / 21");
    }
}
