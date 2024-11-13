import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainWindow extends JFrame {
    private MainWindow(){
        Container frame = getContentPane();
        setTitle("GradePlanner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(800, 500);
        setLocation(400, 150);
        setVisible(true);
    }
    public static void main(String[] args) {
        MainWindow window = new MainWindow();
    }
}
