package com.github.ifuyo.apps.login;

import com.github.ifuyo.entity.LoginEntity;
import com.github.ifuyo.utils.layouts.RUILogin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.swing.*;
import java.awt.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class LoginView extends JFrame {

    public LoginView() {
        initComponents();
    }

    private LoginEntity loginEntity;

    private void initComponents() {
        setTitle("REACTION NETWORK UI DEMO");
        setUndecorated(true);
        setResizable(false);
        setSize(1002, 513);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(new Color(0, 0, 0, 0));

        // JLayeredPane

        JLayeredPane lp = getLayeredPane();

        // 添加内容面板 (BaseFrame)
        RUILogin labelBaseFrame = new RUILogin("login", "baseFrame.png");
        lp.add(labelBaseFrame.imageLabel(0,0), JLayeredPane.DEFAULT_LAYER);

        // 添加图片预览组件
        RUILogin labelLeftImage = new RUILogin("login","leftImageDisp.png");
        lp.add(labelLeftImage.imageLabel(61,56), JLayeredPane.PALETTE_LAYER);

        // Logo
        RUILogin labelLogo = new RUILogin("login","logo.png");
        lp.add(labelLogo.imageLabel(643,161), JLayeredPane.PALETTE_LAYER);

        // JText
        RUILogin labelInputArea = new RUILogin("login","textInput.png");
        lp.add(labelInputArea.imageLabel(591,219), JLayeredPane.PALETTE_LAYER);
        lp.add(labelInputArea.imageLabel(591,219 + 50), JLayeredPane.PALETTE_LAYER);

        JTextField usernameInput = labelInputArea.textLabel(591,219,"",Color.GRAY,13,"Agency FB");
        JPasswordField passwordInput = labelInputArea.passwordLabel(591,219 + 50,"",Color.GRAY,13,"Agency FB");

        lp.add(usernameInput, JLayeredPane.PALETTE_LAYER);
        lp.add(passwordInput, JLayeredPane.PALETTE_LAYER);

        // Button
        RUILogin buttonLogin = new RUILogin("login","loginButton.png");
        RUILogin buttonExitProgram = new RUILogin("login","exitProgramButton.png");

        JButton loginButton = buttonLogin.buttonLabel(591,353);
        JButton exitProgramButton = buttonExitProgram.buttonLabel(591,393);

        lp.add(loginButton, JLayeredPane.PALETTE_LAYER);
        lp.add(exitProgramButton, JLayeredPane.PALETTE_LAYER);

        loginEntity = new LoginEntity(usernameInput, passwordInput, loginButton, exitProgramButton);
    }

    public String getUsername() {
        return loginEntity.getUsernameObject().getText();
    }

    public String getPassword() {
        return new String(loginEntity.getPasswordObject().getPassword());
    }

    public JButton getLoginButton() {
        return loginEntity.getLoginButtonObject();
    }

    public JButton getExitProgramButton() {
        return loginEntity.getExitProgramButtonObject();
    }

    // 显示消息
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    // 显示错误消息
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "错误", JOptionPane.ERROR_MESSAGE);
    }

    public void clearInputs() {
        loginEntity.getUsernameObject().setText("");
        loginEntity.getPasswordObject().setText("");
    }
}
