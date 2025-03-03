// 主入口
import javax.swing.*;
import GUI.Login.Login;
import com.formdev.flatlaf.FlatIntelliJLaf;

public class MainKt {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf()); // 设置 IntelliJ 风格
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            Login loginGUI = new Login();
            loginGUI.setVisible(true);
        });
    }
}

