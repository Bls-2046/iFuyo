/*
 * Created by JFormDesigner on Mon Mar 03 12:59:45 CST 2025
 */

package GUI.Menu;

import org.json.JSONObject;

import java.awt.*;
import javax.swing.*;

/**
 * @author astar
 */
public class Menu extends JFrame {
    public Menu() {
        initComponents();
    }

    public  Menu(JSONObject userInfo) {

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel1 = new JPanel();
        label1 = new JLabel();
        panel2 = new JPanel();
        separator1 = new JSeparator();
        label4 = new JLabel();
        label5 = new JLabel();

        //======== this ========
        setIconImage(new ImageIcon(getClass().getResource("/Logo.png")).getImage());
        setTitle("iFuyo");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== panel1 ========
        {
            panel1.setLayout(new BorderLayout());

            //---- label1 ----
            label1.setText("     ");
            panel1.add(label1, BorderLayout.NORTH);

            //======== panel2 ========
            {
                panel2.setLayout(new BorderLayout());

                //---- separator1 ----
                separator1.setForeground(new Color(0xcccccc));
                separator1.setBackground(new Color(0xcccccc));
                panel2.add(separator1, BorderLayout.CENTER);
            }
            panel1.add(panel2, BorderLayout.SOUTH);

            //---- label4 ----
            label4.setText("    ");
            panel1.add(label4, BorderLayout.WEST);

            //---- label5 ----
            label5.setText("\u4f60\u597d~");
            label5.setFont(new Font("\u6c49\u4eea\u53f6\u53f6\u76f8\u601d\u4f53\u7b80", Font.BOLD, 20));
            label5.setForeground(Color.darkGray);
            panel1.add(label5, BorderLayout.CENTER);
        }
        contentPane.add(panel1, BorderLayout.NORTH);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel panel1;
    private JLabel label1;
    private JPanel panel2;
    private JSeparator separator1;
    private JLabel label4;
    private JLabel label5;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
