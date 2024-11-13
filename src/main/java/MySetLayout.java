import javax.swing.*;
import java.awt.*;

public class MySetLayout {
    private MySetLayout(){} //gpt
    public static void setgbc(GridBagLayout layout, GridBagConstraints gbc, JComponent c, int x, int y, int w, int h){
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        layout.setConstraints(c, gbc);
    }
}
