import javax.swing.*;
import java.awt.*;

public class gui {
    public static void main(String...args) {

        JFrame f = new JFrame("File Organizer");
        JPanel panel = new JPanel();
        panel.setBounds(40, 80, 200, 200);
        panel.setBackground(Color.gray);
        f.setSize(400,400);
        f.setLayout(null);
        f.setVisible(true);
        System.out.println("calling gui");
        //gui.showGUI();
    }
}
